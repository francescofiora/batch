package it.francescofiora.batch.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.rule.Rule;

public class DtoEqualsTester implements Rule {

  @Override
  public void evaluate(PojoClass pojoClass) {
    if (pojoClass.isConcrete()) {
      try {
        equalsVerifier(pojoClass.getClazz());
      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  private <T> void equalsVerifier(Class<T> clazz) throws Exception {
    T dtoObj1 = clazz.getConstructor().newInstance();
    T dtoObj2 = clazz.getConstructor().newInstance();

    // Test equals
    assertThat(dtoObj1.equals(dtoObj2)).isTrue();
    assertThat(dtoObj1.equals(dtoObj1)).isTrue();
    assertThat(dtoObj1.equals(null)).isFalse();
    assertThat(dtoObj1.equals(new Object())).isFalse();

    // Test toString
    assertThat(dtoObj1.toString()).isNotNull();

    // Test hashCode
    assertThat(dtoObj1.hashCode()).isEqualTo(dtoObj2.hashCode());
  }
}
