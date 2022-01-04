package it.francescofiora.batch.api.jms;

import it.francescofiora.batch.api.jms.message.JmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Jms Consumer.
 */
@Slf4j
@Component
public class JmsConsumer {

  private final JmsValidator validator;

  private final StrategyManager strategyManager;

  /**
   * Constructor.
   *
   * @param validator       JmsValidator
   * @param strategyManager StrategyManager
   */
  public JmsConsumer(JmsValidator validator, StrategyManager strategyManager) {
    this.validator = validator;
    this.strategyManager = strategyManager;
  }

  /**
   * Jms Listener.
   *
   * @param obj Object
   */
  @JmsListener(destination = "${activemq.queue.response:QUEUE_RESPONSE}")
  public void receiveMessage(Object obj) {
    log.debug("Message received: " + obj);

    JmsMessage message = validator.validate(obj);
    log.debug("Message validated: " + message);

    strategyManager.exec(message);
    log.debug("Message executed: " + message);
  }

}
