package it.francescofiora.batch.api.util;

import static org.assertj.core.api.Assertions.assertThat;

import it.francescofiora.batch.api.dto.NewTaskDto;
import it.francescofiora.batch.api.dto.ParameterDto;
import it.francescofiora.batch.api.dto.UpdatableTaskDto;
import it.francescofiora.batch.common.enumeration.TaskType;

/**
 * Utility for testing.
 */
public interface TestUtils {

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
