package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.client.NewsApiClient;
import se.pbt.newsstream.mapper.NewsMapper;
import se.pbt.newsstream.model.NewsApiArticleResponse;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.util.UriBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for fetching news articles from an external API based on specified topics or categories.
 *
 * @see NewsApiClient
 * @see NewsMapper
 * @see UriBuilder
 */
@Service
public class NewsApiService {

    private final NewsApiClient newsApiClient;


    public NewsApiService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    /**
     * Fetches a list of news articles based on a specified topic.
     */
    public List<NewsArticle> fetchArticlesByTopic(String topic, int limit) {
        String uri = UriBuilder.buildUriForTopic(topic, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }

    /**
     * Fetches a list of news articles based on a specified category.
     */
    public List<NewsArticle> fetchArticlesByCategory(String category, int limit) {
        String uri = UriBuilder.buildUriForCategory(category, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }
}

