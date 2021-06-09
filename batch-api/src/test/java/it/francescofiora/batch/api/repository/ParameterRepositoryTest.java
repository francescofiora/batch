package it.francescofiora.batch.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import it.francescofiora.batch.api.domain.Parameter;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ParameterRepositoryTest extends AbstractTestRepository {

  @Autowired
  private ParameterRepository parameterRepository;

  @Test
  void testCrud() throws Exception {
    Parameter expected = new Parameter().name("name").value("value");
    expected = parameterRepository.save(expected);

    Optional<Parameter> opt = parameterRepository.findById(expected.getId());
    assertThat(opt).isPresent().get().isEqualTo(expected);

    parameterRepository.delete(expected);
    opt = parameterRepository.findById(expected.getId());
    assertThat(opt).isNotPresent();
  }
}