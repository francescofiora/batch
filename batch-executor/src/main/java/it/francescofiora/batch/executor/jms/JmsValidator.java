package it.francescofiora.batch.executor.jms;

import it.francescofiora.batch.executor.jms.message.JmsMessage;

/**
 * Jms Validator.
 */
public interface JmsValidator {

  JmsMessage validate(Object obj);
}
