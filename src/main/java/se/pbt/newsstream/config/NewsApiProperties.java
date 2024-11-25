package se.pbt.newsstream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the News API integration.
 * Maps properties under the `news-api` prefix from the application configuration (e.g., application.yml).
 */
@ConfigurationProperties(prefix = "news-api")
public record NewsApiProperties(
        String apiKey,
        String baseUrl,
        DefaultProperties defaultProperties
) {
    public record DefaultProperties(String language, String sortBy) {}
}
