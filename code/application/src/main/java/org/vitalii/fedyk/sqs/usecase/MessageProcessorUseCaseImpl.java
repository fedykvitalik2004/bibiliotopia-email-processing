package org.vitalii.fedyk.sqs.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.email.MessageHandler;
import org.vitalii.fedyk.sqs.model.Message;
import org.vitalii.fedyk.sqs.repository.MessageRepository;

@Component
@Slf4j
public class MessageProcessorUseCaseImpl implements MessageProcessorUseCase {
  private final List<MessageHandler<?>> messageHandlers;

  private final ObjectMapper objectMapper;

  private final MessageRepository messageRepository;

  @Value("${aws.sqs.queue-url}")
  private String url;

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
    final List<Message> messages = messageRepository.receiveMessages(url, 10);

    for (Message message : messages) {
      final String type = message.getAttributes().get("className");

      if (type == null) {
        log.warn(
            "Skipping message with ID {}. 'className' attribute is missing. Message body: {}",
            message.getId(),
            message.getBody());
        continue;
      }

      messageHandlers.stream()
          .filter(handler -> handler.getAttributeName().equals(type))
          .findFirst()
          .ifPresent(
              handler -> {
                final boolean processed = deserializeAndHandle(handler, message);

                if (processed) {
                  messageRepository.deleteMessage(url, message.getReceiptHandle());
                }
              });
    }
  }

  private <T> boolean deserializeAndHandle(final MessageHandler<T> handler, final Message msg) {
    try {
      final T obj = objectMapper.readValue(msg.getBody(), handler.getMessageClass());
      handler.handle(obj);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }
}
