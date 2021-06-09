package it.francescofiora.batch.api.jms;

import it.francescofiora.batch.api.jms.message.JmsMessage;

public interface JmsValidator {

  JmsMessage validate(Object obj);
}
