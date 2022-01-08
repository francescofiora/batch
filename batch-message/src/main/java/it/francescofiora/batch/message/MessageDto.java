package it.francescofiora.batch.message;

import it.francescofiora.batch.common.enumeration.TaskType;
import javax.validation.constraints.NotNull;

/**
 * Message Dto.
 */
public interface MessageDto {

  @NotNull
  TaskType getType();

  @NotNull
  Long getTaskId();
}
