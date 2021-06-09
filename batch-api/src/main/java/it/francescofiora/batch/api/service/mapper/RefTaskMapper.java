package it.francescofiora.batch.api.service.mapper;

import it.francescofiora.batch.api.domain.Task;
import it.francescofiora.batch.api.dto.RefTaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefTaskMapper {

  RefTaskDto toDto(Task entity);

  @Mapping(target = "description", ignore = true)
  @Mapping(target = "type", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "result", ignore = true)
  @Mapping(target = "parameters", ignore = true)
  @Mapping(target = "dependencies", ignore = true)
  Task toEntity(RefTaskDto taskDto); 
}
