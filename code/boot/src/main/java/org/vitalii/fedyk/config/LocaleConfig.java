package org.vitalii.fedyk.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Spring configuration for internationalization (i18n) message resources. Provides a {@link
 * MessageSource} bean for resolving messages from property files.
 */
@Configuration
public class LocaleConfig {

  /**
   * Configures a {@link ReloadableResourceBundleMessageSource} to load messages for localization.
   *
   * @return a configured {@link MessageSource} instance
   */
  @Bean
  public MessageSource messageSource() {
    final ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setBasename("classpath:messages");
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setFallbackToSystemLocale(false);
    return messageSource;
  }
}
