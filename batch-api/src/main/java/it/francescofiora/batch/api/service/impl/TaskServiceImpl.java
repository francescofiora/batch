package it.francescofiora.batch.api.service.impl;

import it.francescofiora.batch.api.domain.Parameter;
import it.francescofiora.batch.api.domain.Task;
import it.francescofiora.batch.api.dto.NewTaskDto;
import it.francescofiora.batch.api.dto.TaskDto;
import it.francescofiora.batch.api.dto.UpdatableTaskDto;
import it.francescofiora.batch.api.jms.JmsProducer;
import it.francescofiora.batch.api.jms.errors.JmsException;
import it.francescofiora.batch.api.repository.TaskRepository;
import it.francescofiora.batch.api.service.TaskService;
import it.francescofiora.batch.api.service.mapper.TaskMapper;
import it.francescofiora.batch.api.web.errors.BadRequestAlertException;
import it.francescofiora.batch.api.web.errors.NotFoundAlertException;
import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.message.MessageDtoRequestImpl;
import it.francescofiora.batch.message.MessageDtoResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Task Service Impl.
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

  private static final String ENTITY_NAME = "TaskDto";

  private final TaskRepository taskRepository;

  private final TaskMapper taskMapper;

  private final JmsProducer jmsProducer;

  /**
   * Constructor.
   *
   * @param taskRepository TaskRepository
   * @param taskMapper TaskMapper
   * @param jmsProducer JmsProducer
   */
  public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper,
      JmsProducer jmsProducer) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
    this.jmsProducer = jmsProducer;
  }

  @Override
  public TaskDto create(NewTaskDto taskDto) {
    log.debug("Request to create Task : {}", taskDto);
    Task task = taskMapper.toEntity(taskDto);

    Set<Task> set = task.getDependencies();
    if (set != null && !set.isEmpty()) {
      task.setDependencies(new HashSet<>());
      for (Task dependency : set) {
        Optional<Task> opt = taskRepository.findById(dependency.getId());
        if (!opt.isPresent()) {
          throw new BadRequestAlertException(ENTITY_NAME, dependency.getId().toString(),
              "Dependency not Found");
        }
        task.getDependencies().add(dependency);
      }
    }

    task = taskRepository.save(task);

    send(task);
    return taskMapper.toDto(task);
  }

  private void send(Task task) {
    try {
      // @formatter:off
      jmsProducer.send(new MessageDtoRequestImpl()
          .taskId(task.getId())
          .type(task.getType())
          .addParameters(task.getParameters().stream()
              .collect(Collectors.toMap(Parameter::getName, Parameter::getValue))));
      // @formatter:on
    } catch (Exception e) {
      task.setStatus(TaskStatus.ERROR);
      task.setResult(e.getMessage());
      taskRepository.save(task);
    }
  }

  @Override
  public void patch(UpdatableTaskDto taskDto) {
    log.debug("Request to patch Task : {}", taskDto);

    Optional<Task> taskOpt = taskRepository.findById(taskDto.getId());
    if (!taskOpt.isPresent()) {
      String id = String.valueOf(taskDto.getId());
      throw new NotFoundAlertException(ENTITY_NAME, id, ENTITY_NAME + " not found with id " + id);
    }
    Task task = taskOpt.get();
    taskMapper.updateEntityFromDto(taskDto, task);
    taskRepository.save(task);
  }

  @Override
  public Page<TaskDto> findAll(Pageable pageable) {
    log.debug("Request to get all Tasks");
    return taskRepository.findAll(pageable).map(taskMapper::toDto);
  }

  @Override
  public Optional<TaskDto> findOne(Long id) {
    log.debug("Request to get Task : {}", id);
    return taskRepository.findById(id).map(taskMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Task : {}", id);
    taskRepository.deleteById(id);
  }

  @Override
  public void response(MessageDtoResponse response) {
    log.debug("Response Task : {}", response);

    Optional<Task> taskOpt = taskRepository.findById(response.getTaskId());
    if (!taskOpt.isPresent()) {
      throw new JmsException("Not found!");
    }
    Task task = taskOpt.get();
    task.setStatus(response.getStatus());
    task.setResult(response.getResult());
    taskRepository.save(task);
  }
}
