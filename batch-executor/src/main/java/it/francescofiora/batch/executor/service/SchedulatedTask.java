package it.francescofiora.batch.executor.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Schedulated Task.
 */
@Component
public class SchedulatedTask {

  private final TaskService taskService;

  public SchedulatedTask(TaskService taskService) {
    super();
    this.taskService = taskService;
  }

  /**
   * Schedule Task.
   */
  @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}",
      initialDelayString = "${initialDelay.in.milliseconds}")
  public void scheduleTask() {
    int rows = taskService.markInstance(10);
    while (rows > 0) {
      taskService.taskIdsToExecute().forEach(taskId -> taskService.execute(taskId));
      rows = taskService.markInstance(10);
    }
  }
}
