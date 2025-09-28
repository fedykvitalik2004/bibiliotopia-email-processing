package org.vitalii.fedyk.email.usecase;

import org.vitalii.fedyk.sqs.model.UserRegistrationData;

/**
 * Use case interface for processing and sending emails related to user registration. Encapsulates
 * the business logic for sending registration emails using the application's email infrastructure.
 */
public interface EmailProcessingUseCase {

  /**
   * Sends a registration email to the user based on the provided registration data.
   *
   * @param registrationData the data of the user to whom the registration email should be sent
   */
  void sendRegistrationEmail(UserRegistrationData registrationData);
}
