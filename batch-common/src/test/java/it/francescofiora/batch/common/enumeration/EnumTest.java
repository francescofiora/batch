package it.francescofiora.batch.common.enumeration;

import org.junit.jupiter.api.Test;

class EnumTest {

  @Test
  void testTaskStatus() {
    superficialEnumCodeCoverage(TaskStatus.class);
  }

  @Test
  void testTaskType() {
    superficialEnumCodeCoverage(TaskType.class);
  }

  private void superficialEnumCodeCoverage(Class<? extends Enum<?>> enumClass) {
    try {
      for (Object o : (Object[]) enumClass.getMethod("values").invoke(null)) {
        enumClass.getMethod("valueOf", String.class).invoke(null, o.toString());
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
