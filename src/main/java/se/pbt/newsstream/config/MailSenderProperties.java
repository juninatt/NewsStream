package se.pbt.newsstream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the MailerSend API.
 * Load values from the {@code mail-sender} section in {@code application.yml}.
 */
@ConfigurationProperties(prefix = "mail-sender")
public record MailSenderProperties(Email email) {

    /**
     * Configuration for MailerSend email settings.
     * Includes API key, base URL, sender address, and sender name.
     */
    public record Email(
            String apiKey,
            String baseUrl,
            String senderAddress,
            String senderName
    ) {}
}

