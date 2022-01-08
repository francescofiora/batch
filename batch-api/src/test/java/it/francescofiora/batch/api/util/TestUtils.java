package it.francescofiora.batch.api.util;

import static org.assertj.core.api.Assertions.assertThat;

import it.francescofiora.batch.api.domain.Parameter;
import it.francescofiora.batch.api.domain.Task;
import it.francescofiora.batch.api.dto.NewTaskDto;
import it.francescofiora.batch.api.dto.ParameterDto;
import it.francescofiora.batch.api.dto.UpdatableTaskDto;
import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.common.enumeration.TaskType;
import it.francescofiora.batch.message.MessageDtoRequest;
import it.francescofiora.batch.message.MessageDtoRequestImpl;
import it.francescofiora.batch.message.MessageDtoResponse;
import it.francescofiora.batch.message.MessageDtoResponseImpl;

/**
 * Utility for testing.
 */
public interface TestUtils {

  /**
   * Create an example of Parameter.
   *
   * @param name the name of Parameter
   * @param value the value of Parameter
   * @return Parameter
   */
  static Parameter createParameter(final String name, final String value) {
    Parameter parameter = new Parameter();
    parameter.setName(name);
    parameter.setValue(value);
    return parameter;
  }

  /**
   * create NewTaskDto.
   *
   * @return NewTaskDto
   */
  static NewTaskDto createNewTaskDto() {
    NewTaskDto taskDto = new NewTaskDto();
    taskDto.setDescription("Description");
    taskDto.setType(TaskType.LONG);
    taskDto.getParameters().add(createParameterDto());
    return taskDto;
  }

  /**
   * create ParameterDto.
   *
   * @return ParameterDto
   */
  static ParameterDto createParameterDto() {
    ParameterDto parameterDto = new ParameterDto();
    parameterDto.setName("Name");
    parameterDto.setValue("Value");
    return parameterDto;
  }

  /**
   * create UpdatableTaskDto.
   *
   * @return UpdatableTaskDto
   */
  static UpdatableTaskDto createUpdatableTaskDto(Long id) {
    UpdatableTaskDto taskDto = new UpdatableTaskDto();
    taskDto.setId(id);
    taskDto.setDescription("Description updated");
    return taskDto;
  }

  /**
   * Create first example of Task.
   *
   * @return Task
   */
  static Task createTask1() {
    Task task = new Task();
    task.setDescription("first");
    task.setStatus(TaskStatus.SCHEDULATED);
    task.setType(TaskType.SHORT);
    task.getParameters().add(createParameter("key", "value"));
    task.setResult("result 1");
    return task;
  }

  /**
   * Create second example of Task.
   *
   * @return Task
   */
  static Task createTask2() {
    Task task = new Task();
    task.setDescription("second");
    task.setStatus(TaskStatus.SCHEDULATED);
    task.setType(TaskType.LONG);
    task.setResult("result 2");
    return task;
  }

  /**
   * Create third example of Task.
   *
   * @return Task
   */
  static Task createTask3() {
    Task task = new Task();
    task.setDescription("third");
    task.setStatus(TaskStatus.SCHEDULATED);
    task.setType(TaskType.NEW_TYPE);
    task.setResult("result 3");
    return task;
  }

  /**
   * create MessageDtoRequest.
   *
   * @return MessageDtoRequest
   */
  static MessageDtoRequest createMessageDtoRequest() {
    // @formatter:off
    return new MessageDtoRequestImpl()
        .taskId(1L)
        .type(TaskType.LONG)
        .addParameter("Key", "Value");
    // @formatter:on
  }

  /**
   * create MessageDtoResponse.
   *
   * @return MessageDtoResponse
   */
  static MessageDtoResponse createMessageDtoResponse() {
    // @formatter:off
    return new MessageDtoResponseImpl()
        .taskId(1L)
        .type(TaskType.LONG)
        .status(TaskStatus.TERMINATED)
        .result("Result");
    // @formatter:on
  }

  /**
   * compare if expected Task and actual Task have same data.
   *
   * @param expected Task
   * @param actual Task
   * @return true if data are equal
   */
  static boolean taskEquals(Task expected, Task actual) {
    return expected.getType().equals(actual.getType())
        && expected.getDescription().equals(actual.getDescription())
        && expected.getResult().equals(actual.getResult())
        && expected.getStatus().equals(actual.getStatus())
        && expected.getParameters().equals(actual.getParameters());
  }

  /**
   * assert that obj1 is equal to obj2 and also their hashCode and ToString.
   *
   * @param obj1 the Object to compare
   * @param obj2 the Object to compare
   */
  static void checkEqualHashAndToString(final Object obj1, final Object obj2) {
    assertThat(obj1.equals(obj2)).isTrue();
    assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());
    assertThat(obj1.toString()).isEqualTo(obj2.toString());
  }

  /**
   * assert that obj1 is not equal to obj2 and also their hashCode and ToString.
   *
   * @param obj1 the Object to compare
   * @param obj2 the Object to compare
   */
  static void checkNotEqualHashAndToString(final Object obj1, final Object obj2) {
    assertThat(obj1.equals(obj2)).isFalse();
    assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
    assertThat(obj1.toString()).isNotEqualTo(obj2.toString());
  }
}
