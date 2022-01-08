package it.francescofiora.batch.executor.service;

import it.francescofiora.batch.executor.domain.Task;
import it.francescofiora.batch.executor.dto.TaskExecutorDto;
import it.francescofiora.batch.executor.jms.message.JmsMessage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Task Service.
 */
public interface TaskService {
  Task save(Task task);

  void handleEvent(JmsMessage event);
  
  Optional<Task> findByTaskRef(Long taskRef);

  Page<TaskExecutorDto> findAll(Pageable pageable);

  Optional<TaskExecutorDto> findOne(Long id);

  void delete(Long id);
  
  List<Long> taskIdsToExecute();
  
  int markInstance(int maxRow);

  void execute(Long id);
}
