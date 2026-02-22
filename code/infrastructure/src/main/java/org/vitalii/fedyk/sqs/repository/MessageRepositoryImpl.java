package org.vitalii.fedyk.sqs.repository;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.sqs.model.Message;
import org.vitalii.fedyk.sqs.properties.AwsMainProperties;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.batchmanager.SqsAsyncBatchManager;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

/** {@inheritDoc} */
@Slf4j
@Repository
public class MessageRepositoryImpl implements MessageRepository {
  private final SqsAsyncClient sqsClient;

  private final SqsAsyncBatchManager batchManager;

  private final AwsMainProperties awsMainProperties;

  @Autowired
  public MessageRepositoryImpl(
      final SqsAsyncClient sqsClient,
      final SqsAsyncBatchManager batchManager,
      final AwsMainProperties awsMainProperties) {
    this.sqsClient = sqsClient;
    this.batchManager = batchManager;
    this.awsMainProperties = awsMainProperties;
  }

  @Override
  public CompletableFuture<List<Message>> receiveMessages(final int maxWaitingSeconds) {
    final CompletableFuture<ReceiveMessageResponse> responseFuture =
        batchManager.receiveMessage(
            builder ->
                builder
                    .queueUrl(awsMainProperties.getQueueUrl())
                    .waitTimeSeconds(Math.min(maxWaitingSeconds, 20)));

    return responseFuture.thenApply(
        response -> response.messages().stream().map(this::mapToMessage).toList());
  }

  @Override
  public void deleteMessage(final String receiptHandle) {
    try {
      final DeleteMessageRequest request =
          DeleteMessageRequest.builder()
              .queueUrl(awsMainProperties.getQueueUrl())
              .receiptHandle(receiptHandle)
              .build();

      this.sqsClient.deleteMessage(request);
    } catch (SqsException exception) {
      log.error("Message by receiptHandle {} was not deleted. Error happened", receiptHandle);
    }
  }

  private Message mapToMessage(software.amazon.awssdk.services.sqs.model.Message sqsMessage) {
    final Map<String, String> attributes = new HashMap<>();

    if (sqsMessage.messageAttributes() != null) {
      sqsMessage
          .messageAttributes()
          .forEach((key, value) -> attributes.put(key, value.stringValue()));
    }

    return new Message(
        sqsMessage.messageId(),
        sqsMessage.body(),
        attributes,
        sqsMessage.receiptHandle(),
        Instant.now());
  }
}
