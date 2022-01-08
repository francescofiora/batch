package it.francescofiora.batch.api.jms;

import it.francescofiora.batch.api.jms.message.JmsMessage;

/**
 * Jms Validator.
 */
public interface JmsValidator {

  JmsMessage validate(Object obj);
}
