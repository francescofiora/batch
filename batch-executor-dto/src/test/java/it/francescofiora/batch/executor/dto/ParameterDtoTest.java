package it.francescofiora.batch.executor.dto;

import it.francescofiora.batch.executor.util.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * ParameterDto Test.
 */
public class ParameterDtoTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    ParameterDto parameterDto1 = TestUtils.createParameterDto();
    ParameterDto parameterDto2 = TestUtils.createParameterDto();
    TestUtils.checkEqualHashAndToString(parameterDto1, parameterDto2);

    parameterDto1.setName("Name2");
    TestUtils.checkNotEqualHashAndToString(parameterDto1, parameterDto2);

    parameterDto1.setName(null);
    TestUtils.checkNotEqualHashAndToString(parameterDto1, parameterDto2);

    parameterDto1 = TestUtils.createParameterDto();
    parameterDto1.setValue("Value2");
    TestUtils.checkNotEqualHashAndToString(parameterDto1, parameterDto2);

    parameterDto1.setValue(null);
    TestUtils.checkNotEqualHashAndToString(parameterDto1, parameterDto2);
  }
}
