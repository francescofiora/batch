package it.francescofiora.batch.executor.jms.errors;

/**
 * Jms Exception.
 */
public class JmsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JmsException(String message) {
    super(message);
  }

  public JmsException(String message, Throwable cause) {
    super(message, cause);
  }
}
