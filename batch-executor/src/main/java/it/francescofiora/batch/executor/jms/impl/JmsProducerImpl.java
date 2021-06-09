package it.francescofiora.batch.executor.jms.impl;

import it.francescofiora.batch.executor.jms.JmsProducer;
import it.francescofiora.batch.message.MessageDtoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducerImpl implements JmsProducer {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  @Value("${activemq.queue.response:QUEUE_RESPONSE}")
  private String destination;

  private final JmsTemplate jmsTemplate;

  public JmsProducerImpl(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void send(MessageDtoResponse response) {
    jmsTemplate.convertAndSend(destination, response);
    log.info("Producer Message " + response.getTaskId() + " Sent to " + destination);
  }
}
