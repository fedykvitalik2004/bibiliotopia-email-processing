package org.vitalii.fedyk.common;

import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DefaultLocaleResolver implements LocaleResolver {
  private final Locale defaultLocale = Locale.UK;

  private final Set<Locale> supportedLocales =
      Set.of(defaultLocale, Locale.forLanguageTag("uk-UA"));

  @Override
  public Locale resolveLocale(final String locale) {
    if (locale == null || !supportedLocales.contains(Locale.forLanguageTag(locale))) {
      return defaultLocale;
    } else {
      return Locale.forLanguageTag(locale);
    }
  }

  public static void main(String[] args) {
    System.out.println(Locale.forLanguageTag("uk-UA").toLanguageTag());
  }
}
