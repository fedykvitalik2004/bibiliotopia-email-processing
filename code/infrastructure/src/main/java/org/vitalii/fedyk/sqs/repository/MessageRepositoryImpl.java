package org.vitalii.fedyk.sqs.repository;

import java.time.Instant;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.sqs.model.Message;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
  private SqsClient sqsClient;

  @Autowired
  public MessageRepositoryImpl(final SqsClient sqsClient) {
    this.sqsClient = sqsClient;
  }

  @Override
  public Optional<Message> receiveMessage(String queueUrl) {
    return Optional.empty();
  }

  @Override
  public List<Message> receiveMessages(final String queueUrl, final int maxMessages) {
    try {
      final ReceiveMessageRequest request =
          ReceiveMessageRequest.builder()
              .queueUrl(queueUrl)
              .maxNumberOfMessages(Math.min(maxMessages, 10))
              .messageAttributeNames("All")
              .waitTimeSeconds(3)
              .messageSystemAttributeNames(MessageSystemAttributeName.ALL)
              .build();

      final ReceiveMessageResponse response = sqsClient.receiveMessage(request);

      return response.messages().stream().map(this::mapToMessage).toList();
    } catch (SqsException exception) {
      return Collections.emptyList();
    }
  }

  @Override
  public void deleteMessage(final String queueUrl, final String receiptHandle) {
    try {
      final DeleteMessageRequest request =
          DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(receiptHandle).build();

      sqsClient.deleteMessage(request);
    } catch (SqsException exception) {

    }
  }

  private Message mapToMessage(software.amazon.awssdk.services.sqs.model.Message sqsMessage) {
    final Map<String, String> attributes = new HashMap<>();

    if (sqsMessage.messageAttributes() != null) {
      sqsMessage
          .messageAttributes()
          .forEach(
              (key, value) -> {
                attributes.put(key, value.stringValue());
              });
    }

    return new Message(
        sqsMessage.messageId(),
        sqsMessage.body(),
        attributes,
        sqsMessage.receiptHandle(),
        Instant.now());
  }
}
