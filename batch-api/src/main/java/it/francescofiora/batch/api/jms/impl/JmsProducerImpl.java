package it.francescofiora.batch.api.jms.impl;

import it.francescofiora.batch.api.jms.JmsProducer;
import it.francescofiora.batch.message.MessageDtoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducerImpl implements JmsProducer {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  @Value("${activemq.queue.request:QUEUE_REQUEST}")
  private String destination;

  private final JmsTemplate jmsTemplate;

  public JmsProducerImpl(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void send(MessageDtoRequest request) {
    jmsTemplate.convertAndSend(destination, request);
    log.info("Producer Message " + request.getTaskId() + " Sent to " + destination);
  }

}
