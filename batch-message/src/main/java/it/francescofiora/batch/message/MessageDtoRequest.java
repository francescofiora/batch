package it.francescofiora.batch.message;

import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MessageDtoRequest extends MessageDto {

  @NotNull
  Map<String, String> getParameters();
}
