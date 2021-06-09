package it.francescofiora.batch.executor.repository;

public interface CustomTaskRepository {

  int markInstance(String appInstanceId, int maxRow);
}
