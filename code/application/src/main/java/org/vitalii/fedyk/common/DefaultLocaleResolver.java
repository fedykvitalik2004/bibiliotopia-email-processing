package org.vitalii.fedyk.common;

import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Component;

/** Resolves a locale from a string representation (language tag). */
@Component
public class DefaultLocaleResolver implements LocaleResolver {

  private final Locale defaultLocale = Locale.forLanguageTag("uk-UA");

  private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "uk", "he");

  @Override
  public Locale resolveLocale(final String languageTag) {
    if (languageTag == null || languageTag.isBlank()) {
      return defaultLocale;
    }

    final Locale locale = Locale.forLanguageTag(languageTag);

    if (!SUPPORTED_LANGUAGES.contains(locale.getLanguage()) || locale.getCountry().isEmpty()) {
      return defaultLocale;
    }

    return locale;
  }
}
