package it.francescofiora.batch.executor.jms;

import it.francescofiora.batch.executor.jms.message.JmsMessage;
import it.francescofiora.batch.executor.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  private final JmsValidator validator;

  private final TaskService taskService;

  public JmsConsumer(JmsValidator validator, TaskService taskService) {
    this.validator = validator;
    this.taskService = taskService;
  }

  /**
   * Jms Listener.
   *
   * @param obj Object
   */
  @JmsListener(destination = "${activemq.queue.request:QUEUE_REQUEST}")
  public void receiveMessage(Object obj) {
    log.info("Receiver Message " + obj);

    JmsMessage event = validator.validate(obj);
    log.debug("Validated Message " + event);

    taskService.handleEvent(event);
  }
}
