package org.vitalii.fedyk.sqs.repository;

import java.util.List;
import java.util.Optional;
import org.vitalii.fedyk.sqs.model.Message;

/**
 * Repository interface for interacting with a message queue. Provides methods for receiving and
 * deleting messages from a given queue.
 */
public interface MessageRepository {

  /**
   * Receives a single message from the specified queue.
   *
   * @param queueUrl the URL or identifier of the queue
   * @return an {@link Optional} containing the received {@link Message}, or empty if no message is
   *     available
   */
  Optional<Message> receiveMessage(String queueUrl);

  /**
   * Receives multiple messages from the specified queue, up to the given maximum number.
   *
   * @param queueUrl the URL or identifier of the queue
   * @param maxMessages the maximum number of messages to receive
   * @return a list of received {@link Message} objects, which may be empty if no messages are
   *     available
   */
  List<Message> receiveMessages(String queueUrl, int maxMessages);

  /**
   * Deletes a message from the specified queue.
   *
   * @param queueUrl the URL or identifier of the queue
   * @param receiptHandle the unique receipt handle identifying the message to delete
   */
  void deleteMessage(String queueUrl, String receiptHandle);
}
