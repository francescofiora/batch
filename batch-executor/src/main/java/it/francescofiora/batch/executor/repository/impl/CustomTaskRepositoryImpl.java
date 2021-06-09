package it.francescofiora.batch.executor.repository.impl;

import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.executor.repository.CustomTaskRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTaskRepositoryImpl implements CustomTaskRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public int markInstance(String appInstanceId, int maxRow) {
    List<Long> ids = em.createQuery("SELECT t.id FROM Task t WHERE t.status = :status", Long.class)
        .setParameter("status", TaskStatus.NOT_STARTED)
        .setMaxResults(maxRow)
        .getResultList();

    if (ids.isEmpty()) {
      return 0;
    }
    
    return em.createQuery("UPDATE Task t SET t.appInstanceId = :appInstanceId WHERE t.id IN :ids")
        .setParameter("ids", ids)
        .setParameter("appInstanceId", appInstanceId)
        .executeUpdate();
  }
}
