package it.francescofiora.batch.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.francescofiora.batch.api.util.TestUtils;
import it.francescofiora.batch.common.enumeration.TaskType;
import org.junit.jupiter.api.Test;

class NewTaskDtoTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    NewTaskDto newTaskDto1 = TestUtils.createNewTaskDto();
    newTaskDto1.setDescription("Description");
    NewTaskDto newTaskDto2 = TestUtils.createNewTaskDto();

    TestUtils.checkEqualHashAndToString(newTaskDto1, newTaskDto1);
    TestUtils.checkEqualHashAndToString(newTaskDto1, newTaskDto2);

    newTaskDto2.setDescription("Description2");
    TestUtils.checkNotEqualHashAndToString(newTaskDto1, newTaskDto2);

    newTaskDto2.setDescription(null);
    TestUtils.checkNotEqualHashAndToString(newTaskDto1, newTaskDto2);

    newTaskDto2 = TestUtils.createNewTaskDto();
    newTaskDto2.setType(TaskType.SHORT);
    TestUtils.checkNotEqualHashAndToString(newTaskDto1, newTaskDto2);

    newTaskDto2.setType(null);
    TestUtils.checkNotEqualHashAndToString(newTaskDto1, newTaskDto2);

    newTaskDto2 = TestUtils.createNewTaskDto();
    newTaskDto2.getParameters().add(new ParameterDto());
    assertThat(newTaskDto1).isNotEqualTo(newTaskDto2);

    newTaskDto2.setParameters(null);
    assertThat(newTaskDto1).isNotEqualTo(newTaskDto2);
  }
}
