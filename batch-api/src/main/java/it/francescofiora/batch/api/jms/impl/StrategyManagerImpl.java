package it.francescofiora.batch.api.jms.impl;

import it.francescofiora.batch.api.jms.StrategyManager;
import it.francescofiora.batch.api.jms.message.JmsMessage;
import it.francescofiora.batch.api.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Strategy Manager Impl.
 */
@Slf4j
@Component
public class StrategyManagerImpl implements StrategyManager {

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
