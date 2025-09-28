package org.vitalii.fedyk.email.exception;

/**
 * Exception thrown when an error occurs during the process of sending an email. This is a runtime
 * exception and can wrap lower-level exceptions from the underlying email infrastructure.
 */
public class EmailSendingException extends RuntimeException {

  /**
   * Constructs a new {@code EmailSendingException} with the specified detail message and cause.
   *
   * @param message the detail message explaining the cause of the exception
   * @param cause the underlying exception that triggered this exception
   */
  public EmailSendingException(String message, Throwable cause) {
    super(message, cause);
  }
}
