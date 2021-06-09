package it.francescofiora.batch.api.repository;

import it.francescofiora.batch.api.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
