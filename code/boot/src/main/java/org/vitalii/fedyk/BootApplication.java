package org.vitalii.fedyk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.email.usecase.EmailProcessingUseCaseImpl;
import org.vitalii.fedyk.sqs.model.UserRegistrationData;
import org.vitalii.fedyk.sqs.usecase.MessageProcessorUseCaseImpl;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class BootApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootApplication.class, args);
  }
}

@Component
class CL implements CommandLineRunner {
  @Autowired private MessageSource messageSource;

  @Autowired private EmailProcessingUseCaseImpl emailProcessingUseCase;

  @Autowired private MessageProcessorUseCaseImpl messageProcessorUseCase;

  @Override
  public void run(String... args) throws Exception {
    messageProcessorUseCase.execute();
    emailProcessingUseCase.sendRegistrationEmail(
        UserRegistrationData.builder()
            .lastName("rf")
            .firstName("Ewrgewr")
            .email("djdjfj@gma.com")
            .locale("uk-UA")
            .build());
  }
}
