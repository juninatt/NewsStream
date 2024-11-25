package se.pbt.newsstream.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for setting up WebClient beans for external API integrations.
 * This class configures WebClients for News API from {@link NewsApiProperties} and SendGrid API from {@link SendGridProperties}.
 */
@Configuration
@EnableConfigurationProperties({NewsApiProperties.class, SendGridProperties.class})
public class ApiClientConfig {

    private final NewsApiProperties newsApiProperties;
    private final SendGridProperties sendGridProperties;

    public ApiClientConfig(NewsApiProperties newsApiProperties, SendGridProperties sendGridProperties) {
        this.newsApiProperties = newsApiProperties;
        this.sendGridProperties = sendGridProperties;
    }

    /**
     * Creates a WebClient bean for interacting with the News API.
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
     */
    @Bean
    public WebClient sendGridWebClient() {
        return WebClient.builder()
                .baseUrl(sendGridProperties.baseUrl())
                .defaultHeader("Authorization", "Bearer " + sendGridProperties.apiKey())
                .build();
    }
}
