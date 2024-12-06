package se.pbt.newsstream.notification;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.pbt.newsstream.config.SendGridProperties;
import se.pbt.newsstream.model.Subscription;

/**
 * Implementation of {@link Notifier} for sending email notifications using SendGrid.
 * Uses {@link SendGrid} to send emails and retrieves configuration from {@link SendGridProperties}.
 */
@Component
public class EmailNotifier implements Notifier {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotifier.class);

    private final SendGrid sendGrid;
    private final SendGridProperties sendGridProperties;

    /**
     * Initializes the notifier with the SendGrid client and configuration properties.
     */
    public EmailNotifier(SendGrid sendGrid, SendGridProperties sendGridProperties) {
        this.sendGrid = sendGrid;
        this.sendGridProperties = sendGridProperties;
    }

    /**
     * Sends an email notification to the subscriber of the given subscription.
     * Constructs an email using SendGrid's API and logs the result.
     */
    @Override
    public void notify(Subscription subscription, String message) {
        String recipient = subscription.getSubscriber().getEmail();
        Mail mail = createMail(subscription, recipient, message);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            logger.info("Email sent successfully to {}. Status code: {}", recipient, response.getStatusCode());
        } catch (Exception ex) {
            logger.error("Failed to send email to {}: {}", recipient, ex.getMessage(), ex);
        }
    }

    /**
     * Creates an email using SendGrid's API with the given recipient and message.
     * The sender's email address is fetched from SendGridProperties.
     */
    private Mail createMail(Subscription subscription, String recipient, String message) {
        Email from = new Email(sendGridProperties.senderEmail());
        String subject = "Update: " + subscription.getName();
        Email to = new Email(recipient);
        Content content = new Content("text/plain", message);
        return new Mail(from, subject, to, content);
    }
}
