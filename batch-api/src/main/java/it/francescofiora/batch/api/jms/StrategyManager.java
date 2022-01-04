package it.francescofiora.batch.api.jms;

import it.francescofiora.batch.api.jms.message.JmsMessage;

/**
 * Strategy Manager.
 */
public interface StrategyManager {

  void exec(JmsMessage message);
}
