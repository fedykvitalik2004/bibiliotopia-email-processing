package org.vitalii.fedyk.email.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.common.FormattingUtils;
import org.vitalii.fedyk.email.model.PurchaseEmailContext;
import org.vitalii.fedyk.sqs.model.PurchaseCompleteEvent;

/**
 * Maps {@link PurchaseCompleteEvent} domain event to {@link PurchaseEmailContext} used for
 * rendering purchase confirmation emails.
 */
@Component
@AllArgsConstructor
public class PurchaseEmailContextMapper {

  /**
   * Converts purchase event into email-ready context object.
   *
   * <p>All monetary values are calculated and formatted according to:
   *
   * <ul>
   *   <li>Currency provided in the event
   *   <li>Locale provided in the event
   * </ul>
   *
   * @param event purchase event received from messaging system (must not be null)
   * @return populated {@link PurchaseEmailContext} ready for email rendering
   */
  public PurchaseEmailContext toEmailContext(final PurchaseCompleteEvent event) {
    final Locale locale = Locale.forLanguageTag(event.getLocale());

    BigDecimal totalOrderAmount = BigDecimal.ZERO;
    final List<PurchaseEmailContext.EmailLineItem> emailItems = new java.util.ArrayList<>();

    for (var item : event.getBooks()) {
      final BigDecimal lineTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
      totalOrderAmount = totalOrderAmount.add(lineTotal);

      emailItems.add(
          new PurchaseEmailContext.EmailLineItem(
              item.getTitle(),
              item.getAuthor(),
              FormattingUtils.getPrice(item.getPrice(), event.getCurrency(), locale),
              item.getQuantity(),
              FormattingUtils.getPrice(lineTotal, event.getCurrency(), locale)));
    }

    return new PurchaseEmailContext(
        event.getFirstName(),
        event.getLastName(),
        emailItems,
        FormattingUtils.getPrice(totalOrderAmount, event.getCurrency(), locale));
  }
}
