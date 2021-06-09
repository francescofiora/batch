package it.francescofiora.batch.executor.repository;

import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.executor.domain.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, CustomTaskRepository {

  Optional<Task> findByTaskRef(Long taskRef);

  @Query("SELECT t.id FROM Task t WHERE t.status = :status and t.appInstanceId = :appInstanceId ")
  List<Long> taskIdsToExecute(@Param("status") TaskStatus status,
      @Param("appInstanceId") String appInstanceId);
}
