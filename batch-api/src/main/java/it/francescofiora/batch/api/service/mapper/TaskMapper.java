package it.francescofiora.batch.api.service.mapper;

import it.francescofiora.batch.api.domain.Task;
import it.francescofiora.batch.api.dto.NewTaskDto;
import it.francescofiora.batch.api.dto.TaskDto;
import it.francescofiora.batch.api.dto.UpdatableTaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDto}.
 */
@Mapper(componentModel = "spring", uses = {ParameterMapper.class, RefTaskMapper.class})
public interface TaskMapper {

  @Mapping(source = "result", target = "result.value")
  TaskDto toDto(Task entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "result", ignore = true)
  Task toEntity(NewTaskDto taskDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "type", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "result", ignore = true)
  @Mapping(target = "parameters", ignore = true)
  @Mapping(target = "dependencies", ignore = true)
  void updateEntityFromDto(UpdatableTaskDto taskDto, @MappingTarget Task task);

  /**
   * new Task from Id.
   *
   * @param id Long
   * @return Task
   */
  default Task fromId(Long id) {
    if (id == null) {
      return null;
    }
    Task task = new Task();
    task.setId(id);
    return task;
  }
}
