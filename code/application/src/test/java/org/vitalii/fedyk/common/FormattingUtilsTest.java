package org.vitalii.fedyk.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FormattingUtilsTest {
  @ParameterizedTest
  @MethodSource("priceTestCases")
  void shouldFormatPrice(String currency, Locale locale, String expected) {

    final String result = FormattingUtils.getPrice(new BigDecimal("4312.24"), currency, locale);

    assertEquals(expected, result.replace("\u00A0", " "));
  }

  static Stream<Arguments> priceTestCases() {
    return Stream.of(
        of("USD", new Locale("uk", "UA"), "4 312,24 USD"),
        of("UAH", new Locale("uk", "UA"), "4 312,24 ₴"),
        of("USD", Locale.CANADA, "US$4,312.24"),
        of("USD", Locale.US, "$4,312.24"));
  }
}
