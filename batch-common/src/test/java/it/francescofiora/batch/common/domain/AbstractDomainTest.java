package it.francescofiora.batch.common.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AbstractDomainTest {

  static class DummyDto extends AbstractDomain {

    private Long id;

    @Override
    public Long getId() {
      return id;
    }

    @Override
    public void setId(Long id) {
      this.id = id;
    }
  }

  @Test
  void equalsVerifier() {
    DummyDto dtoObj1 = new DummyDto();
    assertThat(dtoObj1.equals(dtoObj1)).isTrue();

    DummyDto dtoObj2 = new DummyDto();
    assertThat(dtoObj1.equals(dtoObj2)).isFalse();

    dtoObj1.setId(1L);
    assertThat(dtoObj1.equals(null)).isFalse();
    assertThat(dtoObj1.equals(new Object())).isFalse();

    dtoObj2.setId(2L);
    assertThat(dtoObj1.equals(dtoObj2)).isFalse();
    assertThat(dtoObj1.hashCode()).isNotEqualTo(dtoObj2.hashCode());

    dtoObj2.setId(1L);
    assertThat(dtoObj1.equals(dtoObj2)).isTrue();
    assertThat(dtoObj1.hashCode()).isEqualTo(dtoObj2.hashCode());
  }
}
