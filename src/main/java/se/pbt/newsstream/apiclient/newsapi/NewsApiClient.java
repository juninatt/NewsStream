package se.pbt.newsstream.apiclient.newsapi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.pbt.newsstream.apiclient.newsapi.util.UriBuilder;
import se.pbt.newsstream.mapper.NewsStreamMapper;
import se.pbt.newsstream.model.NewsApiArticleResponse;
import se.pbt.newsstream.model.NewsArticle;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Client for interacting with the News API.
 * Provides methods to fetch news articles based on topics or categories with optional limits.
 */
@Service
public class NewsApiClient {
    private final WebClient newsApiClient;

    public NewsApiClient(WebClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    /**
     * Retrieves a list of news articles based on a specific topic, limited by the specified number.
     */
    public List<NewsArticle> newsByTopicLimit(String topic, int limit) {
        String uri = UriBuilder.buildUriForTopic(topic, limit);
        NewsApiArticleResponse response = fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsStreamMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }

    /**
     * Retrieves a list of news articles based on a specific category, limited by the specified number.
     */
    public List<NewsArticle> newsByCategoryLimit(String category, int limit) {
        String uri = UriBuilder.buildUriForCategory(category, limit);
        NewsApiArticleResponse response = fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsStreamMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }

    /**
     * Sends a GET request to the News API and retrieves a response as a {@link NewsApiArticleResponse}.
     */
    public NewsApiArticleResponse fetchArticles(String uri) {
        return newsApiClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NewsApiArticleResponse.class)
                .block();
    }
}
