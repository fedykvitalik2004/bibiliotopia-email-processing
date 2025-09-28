package org.vitalii.fedyk.email.usecase;

import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.common.LocaleResolver;
import org.vitalii.fedyk.email.repository.EmailRepository;
import org.vitalii.fedyk.sqs.model.UserRegistrationData;

@Service
@AllArgsConstructor
public class EmailProcessingUseCaseImpl implements EmailProcessingUseCase {
  private EmailRepository emailRepository;

  private LocaleResolver localeResolver;

  @Override
  public void sendRegistrationEmail(final UserRegistrationData registrationData) {
    final Map<String, Object> data =
        Map.of(
            "firstName", registrationData.getFirstName(),
            "lastName", registrationData.getLastName());
    final Locale resolvedLocale = localeResolver.resolveLocale(registrationData.getLocale());
    emailRepository.sendEmail(
        registrationData.getEmail(),
        "Welcome to Bibliotopia – Your Reading Adventure Begins!",
        "registration",
        data,
        resolvedLocale);
  }
}
