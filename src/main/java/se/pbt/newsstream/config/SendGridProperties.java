package se.pbt.newsstream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for SendGrid API integration.
 * Maps properties under the `sendgrid` prefix from the application configuration (e.g., application.yml).
 */
@ConfigurationProperties(prefix = "sendgrid")
public record SendGridProperties(String apiKey, String baseUrl, String senderEmail) {}
