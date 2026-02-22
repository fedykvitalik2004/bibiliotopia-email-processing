package org.vitalii.fedyk.email.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.email.MessageHandler;
import org.vitalii.fedyk.email.usecase.EmailProcessingUseCase;
import org.vitalii.fedyk.sqs.model.PurchaseCompleteEvent;

/** Handles book purchase messages from AWS SQS. */
@Component
@AllArgsConstructor
public class BookPurchaseHandler implements MessageHandler<PurchaseCompleteEvent> {
  private final EmailProcessingUseCase emailProcessingUseCase;

  @Override
  public void handle(final PurchaseCompleteEvent message) {
    emailProcessingUseCase.sendBookPurchaseEmail(message);
  }

  @Override
  public Class<PurchaseCompleteEvent> getMessageClass() {
    return PurchaseCompleteEvent.class;
  }

  @Override
  public String getAttributeName() {
    return PurchaseCompleteEvent.class.getSimpleName();
  }
}
