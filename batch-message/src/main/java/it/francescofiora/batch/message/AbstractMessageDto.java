package it.francescofiora.batch.message;

import it.francescofiora.batch.common.enumeration.TaskType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMessageDto implements MessageDto {

  @NotNull
  private Long taskId;

  @NotNull
  private TaskType type;
}
