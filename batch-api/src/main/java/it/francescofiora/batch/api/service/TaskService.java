package it.francescofiora.batch.api.service;

import it.francescofiora.batch.api.dto.NewTaskDto;
import it.francescofiora.batch.api.dto.TaskDto;
import it.francescofiora.batch.api.dto.UpdatableTaskDto;
import it.francescofiora.batch.message.MessageDtoResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Task Service.
 */
public interface TaskService {

  /**
   * Create a task.
   *
   * @param taskDto the entity to create.
   * @return the persisted entity.
   */
  TaskDto create(NewTaskDto taskDto);

  /**
   * Patch a task.
   *
   * @param taskDto the entity to patch.
   */
  void patch(UpdatableTaskDto taskDto);

  /**
   * Get all the tasks.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<TaskDto> findAll(Pageable pageable);

  /**
   * Get the "id" task.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<TaskDto> findOne(Long id);

  /**
   * Delete the "id" task.
   * 
   * @param id the id of the entity.
   */
  void delete(Long id);

  /**
   * handle response.
   * @param response MessageDtoResponse
   */
  void response(MessageDtoResponse response);
}
