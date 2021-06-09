package it.francescofiora.batch.executor.jms;

import it.francescofiora.batch.message.MessageDtoResponse;

public interface JmsProducer {

  void send(MessageDtoResponse response);
}
