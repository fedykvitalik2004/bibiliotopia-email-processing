package org.vitalii.fedyk.email.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.email.MessageHandler;
import org.vitalii.fedyk.email.usecase.EmailProcessingUseCase;
import org.vitalii.fedyk.sqs.model.UserRegistrationData;

@Component
@AllArgsConstructor
public class UserRegistrationDataHandler implements MessageHandler<UserRegistrationData> {
  private EmailProcessingUseCase emailProcessing;

  @Override
  public void handle(UserRegistrationData message) {
    emailProcessing.sendRegistrationEmail(message);
  }

  @Override
  public Class<UserRegistrationData> getMessageClass() {
    return UserRegistrationData.class;
  }

  @Override
  public String getAttributeName() {
    return UserRegistrationData.class.getSimpleName();
  }
}
