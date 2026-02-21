package org.vitalii.fedyk.currency.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LocaleUtilsTest {
  @ParameterizedTest
  @ValueSource(strings = {"ar", "fa", "he", "iw", "ji", "ur", "yi"})
  void shouldReturnRtlForRtlLanguages(final String language) {
    // Given
    final var locale = new Locale(language);

    // When
    final var direction = LocaleUtils.getLocationDirection(locale);

    // Then
    assertEquals("rtl", direction);
  }

  @ParameterizedTest
  @ValueSource(strings = {"en", "uk", "fr", "de", "es"})
  void shouldReturnLtrForLtrLanguages(final String language) {
    // Given
    final var locale = new Locale(language);

    // When
    final var direction = LocaleUtils.getLocationDirection(locale);

    // Then
    assertEquals("ltr", direction);
  }
}
