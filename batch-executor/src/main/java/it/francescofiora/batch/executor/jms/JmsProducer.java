package it.francescofiora.batch.executor.jms;

import it.francescofiora.batch.message.MessageDtoResponse;

/**
 * Jms Producer.
 */
public interface JmsProducer {

  void send(MessageDtoResponse response);
}
