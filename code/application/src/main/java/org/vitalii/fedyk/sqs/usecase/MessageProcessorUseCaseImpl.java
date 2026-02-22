package org.vitalii.fedyk.sqs.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.email.MessageHandler;
import org.vitalii.fedyk.sqs.model.Message;
import org.vitalii.fedyk.sqs.repository.MessageRepository;

// todo: fix according to clean architecture
@Component
@Slf4j
public class MessageProcessorUseCaseImpl implements MessageProcessorUseCase {
  private final List<MessageHandler<?>> messageHandlers;

  private final ObjectMapper objectMapper;

  private final MessageRepository messageRepository;

  @Autowired
  public MessageProcessorUseCaseImpl(
      final List<MessageHandler<?>> messageHandlers,
      final ObjectMapper objectMapper,
      final MessageRepository messageRepository) {
    this.messageHandlers = messageHandlers;
    this.objectMapper = objectMapper;
    this.messageRepository = messageRepository;
  }

  @Override
  public void execute() {
    final CompletableFuture<List<Message>> completableFuture =
        this.messageRepository.receiveMessages(2);
    completableFuture.thenAccept(
        messages -> {
          log.info("Message amount {}", messages.size());

          for (Message message : messages) {
            final String type = message.getAttributes().get("className");

            if (type == null) {
              log.warn(
                  "Skipping message with ID {}. 'className' attribute is missing. Message body: {}",
                  message.getId(),
                  message.getBody());
              continue;
            }

            this.messageHandlers.stream()
                .filter(handler -> handler.getAttributeName().equals(type))
                .findFirst()
                .ifPresent(handler -> deserializeAndHandle(handler, message));
          }
        });
  }

  private <T> void deserializeAndHandle(final MessageHandler<T> handler, final Message msg) {
    try {
      final T obj = this.objectMapper.readValue(msg.getBody(), handler.getMessageClass());
      handler.handle(obj);
      this.messageRepository.deleteMessage(msg.getReceiptHandle());
    } catch (Exception exception) {
      log.error("Failed casting from SQS: {}", exception.getMessage());
    }
  }
}
