package it.francescofiora.batch.executor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.francescofiora.batch.common.dto.DtoIdentifier;
import it.francescofiora.batch.common.dto.DtoUtils;
import it.francescofiora.batch.common.enumeration.TaskType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Main Task Dto.
 */
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public class MainTaskDto implements Serializable, DtoIdentifier {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Schema(description = "Unique Task identifier", example = "1", required = true)
  @JsonProperty("id")
  private Long id;

  @NotNull
  @Schema(description = "Type of task", example = "SHORT", required = true)
  @JsonProperty("type")
  private TaskType type;

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public boolean equals(Object obj) {
    return DtoUtils.equals(this, obj);
  }
}
