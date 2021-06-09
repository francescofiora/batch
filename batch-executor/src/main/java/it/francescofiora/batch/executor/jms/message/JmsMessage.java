package it.francescofiora.batch.executor.jms.message;

import it.francescofiora.batch.message.MessageDtoRequest;
import lombok.Getter;

@Getter
public final class JmsMessage {

  private final MessageDtoRequest request;
  private final String jmsMessageId;
  private final Long timestamp;

  /**
   * Constructor.
   *
   * @param request MessageDtoRequest
   * @param jmsMessageId String
   * @param timestamp long
   */
  public JmsMessage(MessageDtoRequest request, String jmsMessageId, Long timestamp) {
    this.request = request;
    this.jmsMessageId = jmsMessageId;
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "JmsMessage {request=" + getRequest() + ", jmsMessageId=" + getJmsMessageId()
        + ", timestamp=" + getTimestamp() + "}";
  }
}
