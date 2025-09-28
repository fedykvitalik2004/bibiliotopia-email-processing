package org.vitalii.fedyk.common;

import java.util.Locale;

/**
 * Interface for resolving a {@link Locale} from a given string representation. Implementations may
 * handle different formats of locale strings (e.g., "uk-UA", "en_US") and provide a normalized
 * {@link Locale} object for use in the application.
 */
public interface LocaleResolver {

  /**
   * Resolves a {@link Locale} from the given locale string.
   *
   * @param locale the locale string to resolve (e.g., "uk-UA", "en_US")
   * @return the corresponding {@link Locale} object
   */
  Locale resolveLocale(String locale);
}
