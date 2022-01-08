package it.francescofiora.batch.executor.repository;

import it.francescofiora.batch.executor.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Parameter Repository.
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
