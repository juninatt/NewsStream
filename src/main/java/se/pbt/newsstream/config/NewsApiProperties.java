package se.pbt.newsstream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the News API.
 * Load values from the {@code news-api} section in {@code application.yml}.
 */
@ConfigurationProperties(prefix = "news-api")
public record NewsApiProperties(
        String apiKey,
        String baseUrl
) {
    /**
     * Configuration for News Api default properties.
     * Includes default language and sorting method.
     */
    public record DefaultProperties(String language, String sortBy) {}
}
