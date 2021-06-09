package it.francescofiora.batch.executor.jms.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.francescofiora.batch.executor.jms.JmsValidator;
import it.francescofiora.batch.executor.jms.errors.JmsException;
import it.francescofiora.batch.executor.jms.message.JmsMessage;
import it.francescofiora.batch.message.MessageDtoRequest;
import it.francescofiora.batch.message.MessageDtoRequestImpl;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JmsValidatorImpl implements JmsValidator {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
  
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
