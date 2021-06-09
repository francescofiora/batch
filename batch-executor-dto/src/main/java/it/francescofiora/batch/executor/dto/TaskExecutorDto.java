package it.francescofiora.batch.executor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.francescofiora.batch.common.dto.DtoIdentifier;
import it.francescofiora.batch.common.dto.DtoUtils;
import it.francescofiora.batch.common.enumeration.TaskStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskExecutorDto implements Serializable, DtoIdentifier {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Schema(description = "Unique Task Execution identifier", example = "1", required = true)
  @JsonProperty("id")
  private Long id;

  @NotNull
  @Valid
  @Schema(description = "Task", required = true)
  @JsonProperty("task")
  private MainTaskDto task;

  @NotBlank
  @Schema(description = "JMS Unique identifier", required = true)
  @JsonProperty("jmsMessageId")
  private String jmsMessageId;

  @NotBlank
  @Schema(description = "Job Name", required = false)
  @JsonProperty("jobName")
  private String jobName;

  @NotBlank
  @Schema(description = "Application Instance Id", required = false)
  @JsonProperty("appInstanceId")
  private String appInstanceId;
  
  @NotNull
  @Schema(description = "Status of task", example = "TERMINATED", required = true)
  @JsonProperty("status")
  private TaskStatus status;

  @NotNull
  @Schema(description = "Message Created", required = true)
  @JsonProperty("messageCreated")
  private Timestamp messageCreated;

  @Valid
  @Schema(description = "Result of execution", required = false)
  @JsonProperty("result")
  private ResultDto result;

  @NotEmpty
  @Valid
  @Schema(required = true)
  @JsonProperty("parameters")
  private Set<ParameterDto> parameters = new HashSet<>();

  @Override
  public boolean equals(Object obj) {
    return DtoUtils.equals(this, obj);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "TaskDto{id='" + getId() + "', task='" + getTask() + "', status='" + getStatus() + "'}";
  }
}
