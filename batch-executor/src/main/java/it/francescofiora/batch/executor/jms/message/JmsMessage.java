package it.francescofiora.batch.executor.jms.message;

import it.francescofiora.batch.message.MessageDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Jms Message.
 */
@Getter
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public final class JmsMessage {

  private final MessageDtoRequest request;
  private final String jmsMessageId;
  private final Long timestamp;
}
