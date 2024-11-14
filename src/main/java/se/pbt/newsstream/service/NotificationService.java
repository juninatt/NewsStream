package se.pbt.newsstream.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.model.Subscriber;

import java.util.List;

/**
 * Service for sending notifications to subscribers({@link Subscriber}) based on their chosen notification type.
 * Currently only offers support for sending notifications via email.
 */
@Service
public class NotificationService {

    @Value("${sendgrid.api-key}")
    private String apiKey;

    private final List<Subscriber> subscribers;

    /**
     * Initializes the NotificationService and subscribes to the article stream from {@link NewsService}.
     */
    public NotificationService(NewsService newsService, List<Subscriber> subscribers) {
        this.subscribers = subscribers;

        newsService.getArticleStream()
                .subscribe(this::sendNotification);

        newsService.getArticleStream()
                .doOnError(error -> System.err.println("Error in notification sending: " + error.getMessage()))
                .subscribe(
                        this::sendNotification,
                        error -> System.err.println("Subscription error: " + error.getMessage()),
                        () -> System.out.println("Subscription completed")
                );
    }

    /**
     * Sends notifications for a given {@link NewsArticle} to each {@link Subscriber} based on their preferred notification type.
     */
    private void sendNotification(NewsArticle article) {
        for (Subscriber subscriber : subscribers) {
            for (Subscriber.NotificationType type : subscriber.getNotificationTypes()) {
                switch (type) {
                    case EMAIL -> sendEmailNotification(article, subscriber.getEmail());
                    case SMS -> sendSmsNotification(article, subscriber.getPhoneNumber());
                }
            }
        }

    }

    /**
     * Sends an email notification to a specified recipient with details about the article.
     */
    private void sendEmailNotification(NewsArticle article, String recipient) {
        Email from = new Email("juninatt@proton.me");
        String subject = "New Article: " + article.getTitle();
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Check out this article: " + article.getUrl());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email sent successfully to " + recipient + ". Status code: " + response.getStatusCode());
        } catch (Exception ex) {
            System.err.println("Error sending email to " + recipient + ": " + ex.getMessage());
        }
    }

    /**
     * Simulates sending an SMS notification with details about the article.
     */
    private void sendSmsNotification(NewsArticle article, String phoneNumber) {
        System.out.println("Sending SMS to " + phoneNumber + " with article: " + article.getTitle());
    }
}
