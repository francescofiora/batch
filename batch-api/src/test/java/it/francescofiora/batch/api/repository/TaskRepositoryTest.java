package it.francescofiora.batch.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import it.francescofiora.batch.api.domain.Task;
import it.francescofiora.batch.api.util.TestUtils;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class TaskRepositoryTest extends AbstractTestRepository {

  @Autowired
  private TaskRepository taskRepository;

  @Test
  void testCrud() throws Exception {
    Task expected1 = TestUtils.createTask1();
    Task expected2 = TestUtils.createTask2();
    expected1 = taskRepository.save(expected1);
    expected2 = taskRepository.save(expected2);

    Page<Task> tasks = taskRepository.findAll(PageRequest.of(0, 10));
    assertThat(tasks).isNotNull().isNotEmpty();

    for (Task actual : tasks) {
      assertThat(actual).isNotNull();
      assertThat(TestUtils.taskEquals(expected1, actual) || TestUtils.taskEquals(expected2, actual))
          .isTrue();
    }

    Task expected3 = TestUtils.createTask3();
    Task task = tasks.getContent().get(0);
    task.setDescription(expected3.getDescription());
    task.setResult(expected3.getResult());
    task.setStatus(expected3.getStatus());
    task.setType(expected3.getType());
    task.setParameters(expected3.getParameters());
    task = taskRepository.save(task);

    Optional<Task> optional = taskRepository.findById(task.getId());
    assertThat(optional).isPresent();
    task = optional.get();
    assertThat(TestUtils.taskEquals(expected3, task)).isTrue();

    for (Task actual : tasks) {
      taskRepository.delete(actual);
    }

    tasks = taskRepository.findAll(PageRequest.of(0, 10));
    assertThat(tasks).isNotNull().isEmpty();
  }
}
