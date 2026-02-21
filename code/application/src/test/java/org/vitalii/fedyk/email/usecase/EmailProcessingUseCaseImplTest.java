package org.vitalii.fedyk.email.usecase;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.common.LocaleResolver;
import org.vitalii.fedyk.email.mapper.PurchaseEmailContextMapper;
import org.vitalii.fedyk.email.model.PurchaseEmailContext;
import org.vitalii.fedyk.email.repository.EmailRepository;
import org.vitalii.fedyk.sqs.model.PurchaseCompleteEvent;

@ExtendWith(MockitoExtension.class)
class EmailProcessingUseCaseImplTest {
  @Mock private EmailRepository emailRepository;

  @Mock private LocaleResolver localeResolver;

  @Mock private PurchaseEmailContextMapper purchaseEmailContextMapper;

  @InjectMocks private EmailProcessingUseCaseImpl useCase;

  private static final Locale ukrainianLocale = new Locale("uk", "UA");

  @Test
  void shouldOrchestrateEmailSendingSuccessfully() {
    // Given
    final var event =
        PurchaseCompleteEvent.builder()
            .email("user@gmail.com")
            .locale("uk-UA")
            .currency("UAH")
            .build();

    final var context = PurchaseEmailContext.builder().firstName("John").lastName("Green").build();

    final var email = "user@gmail.com";

    when(this.localeResolver.resolveLocale(event.getLocale())).thenReturn(ukrainianLocale);
    when(this.purchaseEmailContextMapper.toEmailContext(event)).thenReturn(context);
    doNothing().when(this.emailRepository).sendBooksPurchaseEmail(context, email, ukrainianLocale);

    // When
    this.useCase.sendBookPurchaseEmail(event);

    // Then
    verify(this.localeResolver).resolveLocale(event.getLocale());
    verify(this.purchaseEmailContextMapper).toEmailContext(event);
    verify(this.emailRepository).sendBooksPurchaseEmail(context, email, ukrainianLocale);
  }
}
