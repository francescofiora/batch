package it.francescofiora.batch.message;

import it.francescofiora.batch.common.enumeration.TaskStatus;
import javax.validation.constraints.NotNull;

/**
 * Message Dto Response.
 */
public interface MessageDtoResponse extends MessageDto {

  @NotNull
  TaskStatus getStatus();
  
  @NotNull
  String getResult();
}
