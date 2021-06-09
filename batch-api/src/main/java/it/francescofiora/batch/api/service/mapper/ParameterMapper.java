package it.francescofiora.batch.api.service.mapper;

import it.francescofiora.batch.api.domain.Parameter;
import it.francescofiora.batch.api.dto.ParameterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Parameter} and its DTO {@link ParameterDto}.
 */
@Mapper(componentModel = "spring")
public interface ParameterMapper {

  @Mapping(target = "id", ignore = true)
  Parameter toEntity(ParameterDto dto);
}
