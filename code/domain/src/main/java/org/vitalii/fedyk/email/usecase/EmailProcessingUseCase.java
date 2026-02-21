package org.vitalii.fedyk.email.usecase;

import org.vitalii.fedyk.sqs.model.PurchaseCompleteEvent;

/**
 * Use case interface for processing and sending emails related to user registration. Encapsulates
 * the business logic for sending registration emails using the application's email infrastructure.
 */
public interface EmailProcessingUseCase {
  void sendBookPurchaseEmail(PurchaseCompleteEvent purchaseCompleteEvent);
}
