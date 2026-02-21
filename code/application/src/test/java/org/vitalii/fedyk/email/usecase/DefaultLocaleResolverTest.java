package org.vitalii.fedyk.email.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.common.DefaultLocaleResolver;
import org.vitalii.fedyk.common.LocaleResolver;

@ExtendWith(MockitoExtension.class)
class DefaultLocaleResolverTest {
  private final LocaleResolver localeResolver = new DefaultLocaleResolver();

  @Test
  void resolveLocale_whenLocaleIsNull() {
    // When
    final var actual = this.localeResolver.resolveLocale(null);

    // Then
    assertEquals(new Locale("uk", "UA"), actual);
  }

  @Test
  void resolveLocale_whenLocaleIsBlank() {
    // When
    final var actual = this.localeResolver.resolveLocale("    ");

    // Then
    assertEquals(new Locale("uk", "UA"), actual);
  }

  static Stream<Arguments> localesStream() {
    return Stream.of(
        Arguments.of(
            "en-US", Locale.forLanguageTag("en-US"),
            "en", Locale.forLanguageTag("uk-UA"),
            "he-UA", Locale.forLanguageTag("he-UA")));
  }

  @ParameterizedTest
  @MethodSource("localesStream")
  void resolveLocale_whenLocaleIsSupported(final String input, final Locale expected) {
    // When
    final var actual = localeResolver.resolveLocale(input);

    // Then
    assertEquals(expected, actual);
  }
}
