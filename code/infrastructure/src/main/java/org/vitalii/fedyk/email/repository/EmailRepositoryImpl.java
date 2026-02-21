package org.vitalii.fedyk.email.repository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.vitalii.fedyk.email.exception.EmailSendingException;
import org.vitalii.fedyk.email.model.PurchaseEmailContext;

@Repository
@Slf4j
public class EmailRepositoryImpl implements EmailRepository {
  private final JavaMailSender sender;

  private final TemplateEngine templateEngine;

  private final String from;

  private final MessageSource messageSource;

  @Autowired
  public EmailRepositoryImpl(
      @Value("${spring.mail.username}") final String from,
      final TemplateEngine templateEngine,
      final JavaMailSender sender,
      final MessageSource messageSource) {
    this.from = from;
    this.templateEngine = templateEngine;
    this.sender = sender;
    this.messageSource = messageSource;
  }

  @Override
  public void sendEmail(
      final String to,
      final String subject,
      final String templateName,
      final Map<String, Object> variables,
      final Locale locale) {
    try {
      final MimeMessage mimeMessage = this.sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      final Context context = new Context();
      context.setVariables(variables);
      context.setLocale(locale);

      final String content = this.templateEngine.process(templateName, context);

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);

      this.sender.send(mimeMessage);
    } catch (MessagingException exception) {
      log.error("Email to {} was not sent", to, exception);
      throw new EmailSendingException("Email not sent", exception);
    }
  }

  @Override
  public void sendBooksPurchaseEmail(
      final PurchaseEmailContext context, final String email, final Locale locale) {
    final String subject = this.messageSource.getMessage("email.purchase.subject", null, locale);

    final Map<String, Object> data =
        Map.of(
            "firstName", context.firstName(),
            "lastName", context.lastName(),
            "books", context.books(),
            "totalOrderPrice", context.totalOrderPrice());
    this.sendEmail(email, subject, "book_purchase", data, locale);
  }
}
