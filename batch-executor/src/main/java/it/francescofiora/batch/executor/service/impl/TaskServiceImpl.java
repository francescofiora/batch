package it.francescofiora.batch.executor.service.impl;

import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.executor.domain.Parameter;
import it.francescofiora.batch.executor.domain.Task;
import it.francescofiora.batch.executor.dto.TaskExecutorDto;
import it.francescofiora.batch.executor.jms.message.JmsMessage;
import it.francescofiora.batch.executor.repository.ParameterRepository;
import it.francescofiora.batch.executor.repository.TaskRepository;
import it.francescofiora.batch.executor.service.TaskService;
import it.francescofiora.batch.executor.service.mapper.TaskMapper;
import it.francescofiora.batch.executor.tasklet.JmsParameters;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Task Service Impl.
 */
@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

  private final TaskMapper taskMapper;

  private final TaskRepository taskRepository;
  private final ParameterRepository parameterRepositor;

  @Value("${spring.application.instance_id}")
  private String instanceId;

  /**
   * Constructor.
   *
   * @param taskRepository TaskRepository
   * @param parameterRepositor ParameterRepository
   * @param taskMapper TaskMapper
   */
  public TaskServiceImpl(TaskRepository taskRepository, ParameterRepository parameterRepositor,
      TaskMapper taskMapper) {
    super();
    this.taskRepository = taskRepository;
    this.parameterRepositor = parameterRepositor;
    this.taskMapper = taskMapper;
  }

  @Override
  public Task save(Task task) {
    log.debug("Request to save a Task : {}", task);

    parameterRepositor.saveAll(task.getParameters());

    return taskRepository.save(task);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Task> findByTaskRef(Long taskRef) {
    log.debug("Request to find a Task : {}", taskRef);
    return taskRepository.findByTaskRef(taskRef);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TaskExecutorDto> findAll(Pageable pageable) {
    log.debug("Request to get all Tasks");
    return taskRepository.findAll(pageable).map(taskMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TaskExecutorDto> findOne(Long id) {
    log.debug("Request to get Task : {}", id);
    return taskRepository.findById(id).map(taskMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Task : {}", id);
    Optional<Task> opt = taskRepository.findById(id);
    if (opt.isPresent()) {
      taskRepository.deleteById(id);
      parameterRepositor.deleteAll(opt.get().getParameters());
    }
  }

  @Override
  public void handleEvent(JmsMessage event) {
    Optional<Task> taskOpt =
        taskRepository.findByTaskRef(getTaskRef(event.getRequest().getParameters()));
    if (taskOpt.isPresent()) {
      updateTaskFromEvent(event, taskOpt.get());
    } else {
      createTaskFromEvent(event);
    }
  }

  private void updateTaskFromEvent(final JmsMessage event, final Task task) {
    if (TaskStatus.IN_PROGRESS.equals(task.getStatus())) {
      log.debug("Event " + event + " not processed because Task is already in Progress");
      return;
    }
    task.setJmsMessageId(event.getJmsMessageId());
    task.setMessageCreated(new Timestamp(event.getTimestamp()));
    task.setStatus(TaskStatus.NOT_STARTED);
    taskRepository.save(task);
  }

  private void createTaskFromEvent(final JmsMessage event) {
    Map<String, String> parameters = event.getRequest().getParameters();

    // @formatter:off
    Task task = new Task()
        .taskRef(getTaskRef(parameters))
        .taskType(parameters.get(JmsParameters.TASK_TYPE))
        .jmsMessageId(event.getJmsMessageId())
        .messageCreated(new Timestamp(event.getTimestamp()))
        .status(TaskStatus.NOT_STARTED);
    // @formatter:on

    // @formatter:off
    task.setParameters(parameters.keySet().stream()
        .map(key -> new Parameter().name(key).value(parameters.get(key)))
        .filter(parameter -> parameter.getValue() != null)
        .collect(Collectors.toSet()));
    // @formatter:on

    parameterRepositor.saveAll(task.getParameters());
    taskRepository.save(task);
  }

  private Long getTaskRef(Map<String, String> parameters) {
    return Long.valueOf(parameters.get(JmsParameters.TASK_REF));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Long> taskIdsToExecute() {
    return taskRepository.taskIdsToExecute(TaskStatus.NOT_STARTED, instanceId);
  }

  @Override
  public void execute(Long id) {
    Optional<Task> opt = taskRepository.findById(id);
    if (opt.isPresent() && TaskStatus.NOT_STARTED.equals(opt.get().getStatus())) {
      try {
        Task task = opt.get();
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

      } catch (Exception e) {
        Task task = opt.get();
        task.setStatus(TaskStatus.ERROR);
        task.setResult(e.getMessage());
        taskRepository.save(task);
      }
    }
  }

  @Override
  public int markInstance(int maxRow) {
    return taskRepository.markInstance(instanceId, maxRow);
  }
}
