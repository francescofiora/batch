package it.francescofiora.batch.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.francescofiora.batch.common.dto.DtoIdentifier;
import it.francescofiora.batch.common.dto.DtoUtils;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public class RefTaskDto implements Serializable, DtoIdentifier {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Schema(description = "Unique identifier", example = "1", required = true)
  @JsonProperty("id")
  private Long id;
  
  @Override
  public boolean equals(Object obj) {
    return DtoUtils.equals(this, obj);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
