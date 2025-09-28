package org.vitalii.fedyk.email.repository;

import java.util.Locale;
import java.util.Map;

/**
 * Repository interface for sending emails. Provides an abstraction for email delivery mechanisms,
 * allowing the application to send localized and templated emails without depending on a specific
 * email provider.
 */
public interface EmailRepository {

  /**
   * Sends an email to the specified recipient using the given template and variables.
   *
   * @param to the recipient email address
   * @param subject the subject of the email
   * @param templateName the name of the template to use for the email body
   * @param variables a map of template variables to be replaced in the template
   * @param locale the locale to be used for localization of the template
   */
  void sendEmail(
      String to, String subject, String templateName, Map<String, Object> variables, Locale locale);
}
