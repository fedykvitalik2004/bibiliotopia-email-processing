package org.vitalii.fedyk.email.repository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.vitalii.fedyk.email.exception.EmailSendingException;

@Repository
@Slf4j
public class EmailRepositoryImpl implements EmailRepository {
  private final JavaMailSender sender;

  private final TemplateEngine templateEngine;

  private final String from;

  @Autowired
  public EmailRepositoryImpl(
      @Value("${spring.mail.username}") final String from,
      final TemplateEngine templateEngine,
      final JavaMailSender sender) {
    this.from = from;
    this.templateEngine = templateEngine;
    this.sender = sender;
  }

  @Override
  public void sendEmail(
      final String to,
      final String subject,
      final String templateName,
      final Map<String, Object> variables,
      final Locale locale) {
    try {
      final MimeMessage mimeMessage = sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      final Context context = new Context();
      context.setVariables(variables);
      context.setLocale(locale);

      final String content = templateEngine.process(templateName, context);

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);

      sender.send(mimeMessage);
    } catch (MessagingException exception) {
      log.error("Email to {} was not sent", to, exception);
      throw new EmailSendingException("Email not sent", exception);
    }
  }
}
