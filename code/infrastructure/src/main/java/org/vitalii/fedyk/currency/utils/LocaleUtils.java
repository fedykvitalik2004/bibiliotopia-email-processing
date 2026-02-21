package org.vitalii.fedyk.currency.utils;

import java.util.Locale;
import lombok.experimental.UtilityClass;

/** Utility class for working with {@link Locale} objects. */
@UtilityClass
public class LocaleUtils {
  /**
   * Determines the text direction for a given locale.
   *
   * @param locale the locale to check
   * @return "ltr" for left-to-right languages, "rtl" for right-to-left languages
   */
  public String getLocationDirection(final Locale locale) {
    return switch (locale.getLanguage()) {
      case "ar", "fa", "he", "iw", "ji", "ur", "yi" -> "rtl";
      default -> "ltr";
    };
  }
}
