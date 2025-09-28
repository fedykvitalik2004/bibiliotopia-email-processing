package org.vitalii.fedyk.email;

/**
 * Generic interface for handling messages of a specific type. Implementations define how to process
 * messages of type {@code T}, and provide metadata about the message type and related attributes.
 *
 * @param <T> the type of message this handler can process
 */
public interface MessageHandler<T> {

  /**
   * Handles the given message.
   *
   * @param message the message to process
   */
  void handle(T message);

  /**
   * Returns the {@link Class} object representing the type of messages this handler can process.
   *
   * @return the message class
   */
  Class<T> getMessageClass();

  /**
   * Returns the name of the attribute associated with the message. This can be used to identify or
   * route messages based on an attribute key.
   *
   * @return the attribute name
   */
  String getAttributeName();
}
