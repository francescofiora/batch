package it.francescofiora.batch.executor.service.mapper;

import it.francescofiora.batch.common.enumeration.TaskType;
import it.francescofiora.batch.executor.domain.Task;
import it.francescofiora.batch.executor.dto.TaskExecutorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskExecutorDto}.
 */
@Mapper(componentModel = "spring", uses = {}, imports = TaskType.class)
public interface TaskMapper {

  @Mapping(source = "taskRef", target = "task.id")
  @Mapping(expression = "java(TaskType.valueOf(task.getTaskType()))", target = "task.type")
  @Mapping(source = "result", target = "result.value")
  TaskExecutorDto toDto(Task task);
}
