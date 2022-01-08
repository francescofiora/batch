package it.francescofiora.batch.executor.repository;

/**
 * Custom Task Repository.
 */
public interface CustomTaskRepository {

  int markInstance(String appInstanceId, int maxRow);
}
