package org.vitalii.fedyk.email.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.common.LocaleResolver;
import org.vitalii.fedyk.email.repository.EmailRepository;
import org.vitalii.fedyk.sqs.model.UserRegistrationData;

@ExtendWith(MockitoExtension.class)
class EmailProcessingUseCaseImplTest {
  @Mock private EmailRepository emailRepository;

  @Mock private LocaleResolver localeResolver;

  @InjectMocks private EmailProcessingUseCaseImpl emailProcessingUseCase;

  @Test
  void sendRegistrationEmail() {
    // given
    final var userRegistration =
        UserRegistrationData.builder()
            .firstName("Sam")
            .lastName("Green")
            .email("email@com.ua")
            .locale("en-GB")
            .build();
    final Map<String, Object> data =
        Map.of(
            "firstName", userRegistration.getFirstName(),
            "lastName", userRegistration.getLastName());

    final var resolvedLocale = Locale.UK;
    when(localeResolver.resolveLocale(any())).thenReturn(resolvedLocale);
    doNothing().when(emailRepository).sendEmail(any(), any(), any(), any(), any());

    // when
    emailProcessingUseCase.sendRegistrationEmail(userRegistration);

    // then
    verify(localeResolver).resolveLocale(Locale.UK.toLanguageTag());
    verify(emailRepository)
        .sendEmail(
            userRegistration.getEmail(),
            "Welcome to Bibliotopia – Your Reading Adventure Begins!",
            "registration",
            data,
            resolvedLocale);
  }
}
