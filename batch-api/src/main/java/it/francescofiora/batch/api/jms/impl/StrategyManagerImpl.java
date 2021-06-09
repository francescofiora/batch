package it.francescofiora.batch.api.jms.impl;

import it.francescofiora.batch.api.jms.StrategyManager;
import it.francescofiora.batch.api.jms.message.JmsMessage;
import it.francescofiora.batch.api.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StrategyManagerImpl implements StrategyManager {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  private final TaskService taskService;

  public StrategyManagerImpl(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void exec(JmsMessage message) {
    log.info("Response - message: " + message.getJmsMessageId() + "; task: "
        + message.getResponse().getTaskId());
    taskService.response(message.getResponse());
  }

}
