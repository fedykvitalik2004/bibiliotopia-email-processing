package org.vitalii.fedyk.sqs.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.vitalii.fedyk.sqs.model.Message;

/**
 * Repository interface for interacting with a message queue. Provides methods for receiving and
 * deleting messages from a given queue.
 */
public interface MessageRepository {

  /**
   * Receives multiple messages from the specified queue, up to the given maximum number.
   *
   * @param maxWaitingSeconds to wait until messages will arrive
   * @return a {@link CompletableFuture} that will complete with a list of messages retrieved from
   *     the queue
   */
  CompletableFuture<List<Message>> receiveMessages(int maxWaitingSeconds);

  /**
   * Deletes a message from the specified queue.
   *
   * @param receiptHandle the unique receipt handle identifying the message to delete
   */
  void deleteMessage(String receiptHandle);
}
