package it.francescofiora.batch.api.jms.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.francescofiora.batch.api.jms.JmsValidator;
import it.francescofiora.batch.api.jms.errors.JmsException;
import it.francescofiora.batch.api.jms.message.JmsMessage;
import it.francescofiora.batch.message.MessageDtoResponse;
import it.francescofiora.batch.message.MessageDtoResponseImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Component;

/**
 * Jms Validator Impl.
 */
@Slf4j
@Component
public class JmsValidatorImpl implements JmsValidator {

  private final ObjectMapper mapper;

  public JmsValidatorImpl(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public JmsMessage validate(Object obj) {
    if (obj instanceof ActiveMQTextMessage) {
      ActiveMQTextMessage txtMessage = (ActiveMQTextMessage) obj;

      MessageDtoResponse response = null;
      try {
        response = mapper.readValue(txtMessage.getText(), MessageDtoResponseImpl.class);
      } catch (Exception e) {
        log.error(e.getMessage());
        throw new JmsException(e.getMessage(), e);
      }

      return new JmsMessage(response, txtMessage.getJMSMessageID(), txtMessage.getTimestamp());
    }

    throw new JmsException(obj + " not valid");
  }

}
