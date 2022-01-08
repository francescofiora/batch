package it.francescofiora.batch.executor.jms.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.francescofiora.batch.executor.jms.JmsValidator;
import it.francescofiora.batch.executor.jms.errors.JmsException;
import it.francescofiora.batch.executor.jms.message.JmsMessage;
import it.francescofiora.batch.message.MessageDtoRequest;
import it.francescofiora.batch.message.MessageDtoRequestImpl;
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

      MessageDtoRequest request = null;
      try {
        request = mapper.readValue(txtMessage.getText(), MessageDtoRequestImpl.class);
      } catch (Exception e) {
        log.error(e.getMessage());
        throw new JmsException(e.getMessage());
      }

      return new JmsMessage(request, txtMessage.getJMSMessageID(), txtMessage.getTimestamp());
    }

    throw new RuntimeException(obj + " not valid");
  }

}
