package org.vitalii.fedyk.email.usecase;

import static org.junit.jupiter.api.Assertions.*;

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
    // given
    final var locale = (String) null;

    // when
    final var actual = localeResolver.resolveLocale(locale);

    // then
    assertEquals(Locale.UK, actual);
  }

  static Stream<Arguments> localesStream() {
    return Stream.of(
        Arguments.of(Locale.UK.toLanguageTag(), Locale.UK),
        Arguments.of(
            Locale.forLanguageTag("uk-UA").toLanguageTag(), Locale.forLanguageTag("uk-UA")),
        Arguments.of(Locale.FRANCE.toLanguageTag(), Locale.UK),
        Arguments.of(Locale.forLanguageTag("uk").toLanguageTag(), Locale.UK));
  }

  @ParameterizedTest
  @MethodSource("localesStream")
  void resolveLocale_whenLocaleIsSupported(final String input, final Locale expected) {
    // when
    final var actual = localeResolver.resolveLocale(input);

    // then
    assertEquals(expected, actual);
  }
}
