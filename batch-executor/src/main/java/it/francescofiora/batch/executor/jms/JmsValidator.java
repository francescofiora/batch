package it.francescofiora.batch.executor.jms;

import it.francescofiora.batch.executor.jms.message.JmsMessage;

public interface JmsValidator {

  JmsMessage validate(Object obj);
}
