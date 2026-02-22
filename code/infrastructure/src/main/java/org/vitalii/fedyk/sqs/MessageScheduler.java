package org.vitalii.fedyk.sqs;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.sqs.usecase.MessageProcessorUseCase;

/** Infrastructure scheduler responsible for triggering message processing at a fixed interval. */
@Component
@AllArgsConstructor
public class MessageScheduler {
  private final MessageProcessorUseCase messageProcessorUseCase;

  @Scheduled(fixedRate = 5000)
  public void process() {
    this.messageProcessorUseCase.execute();
  }
}
