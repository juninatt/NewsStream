package se.pbt.newsstream.apiclient.mailsender;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.springframework.stereotype.Service;
import se.pbt.newsstream.config.MailSenderProperties;
import se.pbt.newsstream.model.NewsArticle;

@Service
public class MailSenderClient {

    private final MailerSend mailerSend;
    private final String senderAddress;
    private final String senderName;

    public MailSenderClient(MailerSend mailerSend, MailSenderProperties mailSenderProperties) {
        this.mailerSend = mailerSend;
        this.senderAddress = mailSenderProperties.email().senderAddress();
        this.senderName = mailSenderProperties.email().senderName();
    }

    public void sendEmail(String recipient, NewsArticle content) {
        Email email = createEmail(recipient, content);
        sendEmail(email);
    }

    private void sendEmail(Email email) {
        try {
            MailerSendResponse response = mailerSend.emails().send(email);
            System.out.println("E-post skickat! Statuskod: " + response.responseStatusCode);
        } catch (MailerSendException e) {
            System.err.println("Fel vid skickande av e-post: " + e.getMessage());
        }
    }

    private Email createEmail(String recipient, NewsArticle content) {
        Email email = new Email();
        email.setFrom(senderName, senderAddress);
        email.addRecipient(recipient, recipient);
        email.setSubject(content.getTitle());
        email.setPlain(content.getContent());
        return email;
    }
}
