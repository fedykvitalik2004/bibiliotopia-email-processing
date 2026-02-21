package org.vitalii.fedyk.common;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import lombok.experimental.UtilityClass;

/** Utility class for formatting values. */
@UtilityClass
public class FormattingUtils {

  /**
   * Formats price value according to locale and currency rules.
   *
   * <p>The method:
   *
   * <ul>
   *   <li>Creates currency-aware {@link NumberFormat} instance based on locale
   *   <li>Sets currency using ISO 4217 currency code
   *   <li>Formats number using locale-specific rules
   * </ul>
   *
   * <p>Example outputs:
   *
   * <pre>
   * Locale UK + UAH → "4 312,24 ₴"
   * Locale US + USD → "$4,312.24"
   * </pre>
   *
   * @param price monetary value to format (should not be null)
   * @param currencyCode ISO 4217 currency code (for example: USD, UAH, EUR)
   * @param locale target locale used for formatting rules
   * @return formatted currency string according to locale rules
   */
  public String getPrice(final BigDecimal price, final String currencyCode, Locale locale) {
    final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
    numberFormat.setCurrency(Currency.getInstance(currencyCode));
    return numberFormat.format(price);
  }
}
