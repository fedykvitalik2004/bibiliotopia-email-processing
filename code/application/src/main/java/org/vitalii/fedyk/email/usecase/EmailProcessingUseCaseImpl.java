package org.vitalii.fedyk.email.usecase;

import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.common.LocaleResolver;
import org.vitalii.fedyk.email.mapper.PurchaseEmailContextMapper;
import org.vitalii.fedyk.email.model.PurchaseEmailContext;
import org.vitalii.fedyk.email.repository.EmailRepository;
import org.vitalii.fedyk.sqs.model.BookPurchaseEvent;

@Service
@AllArgsConstructor
public class EmailProcessingUseCaseImpl implements EmailProcessingUseCase {
  private final EmailRepository emailRepository;

  private final LocaleResolver localeResolver;

  private final PurchaseEmailContextMapper purchaseEmailContextMapper;

  @Override
  public void sendBookPurchaseEmail(final BookPurchaseEvent bookPurchaseData) {
    final Locale locale = this.localeResolver.resolveLocale(bookPurchaseData.getLocale());

    final PurchaseEmailContext purchaseData =
        this.purchaseEmailContextMapper.toEmailContext(bookPurchaseData);

    this.emailRepository.sendBooksPurchaseEmail(purchaseData, bookPurchaseData.getEmail(), locale);
  }
}
