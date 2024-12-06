package se.pbt.newsstream.config;

import com.sendgrid.SendGrid;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for setting up beans for external API integrations.
 * Automatically binds properties defined in application.yml to the {@link NewsApiProperties} and {@link SendGridProperties}.
 * Configures WebClients for the News API and SendGrid API, as well as a SendGrid client for email operations.
 */
@Configuration
@EnableConfigurationProperties({NewsApiProperties.class, SendGridProperties.class})
public class ApiClientConfig {

    private final NewsApiProperties newsApiProperties;
    private final SendGridProperties sendGridProperties;

    /**
     * Initializes the configuration with the properties for News API and SendGrid API.
     */
    public ApiClientConfig(NewsApiProperties newsApiProperties, SendGridProperties sendGridProperties) {
        this.newsApiProperties = newsApiProperties;
        this.sendGridProperties = sendGridProperties;
    }

    /**
     * Creates a WebClient bean for interacting with the News API.
     * Uses base URL and API key from NewsApiProperties.
     */
    @Bean
    public WebClient newsApiWebClient() {
        return WebClient.builder()
                .baseUrl(newsApiProperties.baseUrl())
                .defaultHeader("Authorization", newsApiProperties.apiKey())
                .build();
    }

    /**
     * Creates a WebClient bean for interacting with the SendGrid API.
     * Uses base URL and API key from SendGridProperties.
     */
    @Bean
    public WebClient sendGridWebClient() {
        return WebClient.builder()
                .baseUrl(sendGridProperties.baseUrl())
                .defaultHeader("Authorization", "Bearer " + sendGridProperties.apiKey())
                .build();
    }

    /**
     * Creates a SendGrid client for sending emails.
     */
    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(sendGridProperties.apiKey());
    }
}

