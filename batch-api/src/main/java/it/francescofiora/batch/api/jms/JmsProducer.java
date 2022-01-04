package it.francescofiora.batch.api.jms;

import it.francescofiora.batch.message.MessageDtoRequest;

/**
 * Jms Producer.
 */
public interface JmsProducer {

  void send(MessageDtoRequest request);
}
