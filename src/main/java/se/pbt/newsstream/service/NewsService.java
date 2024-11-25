package se.pbt.newsstream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import se.pbt.newsstream.client.NewsApiClient;
import se.pbt.newsstream.mapper.NewsStreamMapper;
import se.pbt.newsstream.model.NewsApiArticleResponse;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.util.UriBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service for managing news articles including fetching, saving, and publishing.
 * The service integrates with {@link NewsApiClient} for API communication,
 * {@link DatabaseService} for persistence, and {@link Sinks.Many} for reactive article streaming.
 */
@Service
public class NewsService {
    @Value("${persistence.save-results}")
    private boolean saveResults;
    private final DatabaseService databaseService;
    private final NewsApiClient newsApiClient;
    private final Sinks.Many<NewsArticle> articleSink = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    public NewsService(DatabaseService databaseService, NewsApiClient newsApiClient) {
        this.databaseService = databaseService;
        this.newsApiClient = newsApiClient;
    }

    //  News API communication

    /**
     * Provides a reactive stream of news articles.
     */
    public Flux<NewsArticle> getArticleStream() {
        return articleSink.asFlux();
    }

    /**
     * Fetches news articles by topic with a specified limit.
     */
    public List<NewsArticle> findNewsByTopicWithLimit(String topic, int limit) {
        String uri = UriBuilder.buildUriForTopic(topic, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsStreamMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }

    /**
     * Fetches news articles by category with a specified limit.
     */
    public List<NewsArticle> fetchNewsByCategoryWithLimit(String category, int limit) {
        String uri = UriBuilder.buildUriForCategory(category, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);
        return response != null ? response.getNewsArticles().stream()
                .map(NewsStreamMapper::mapToNewsArticle)
                .collect(Collectors.toList()) : List.of();
    }

    /**
     * Fetches the latest news article for a given topic.
     */
    public NewsArticle fetchLatestNewsByTopic(String topic) {
        List<NewsArticle> articles = findNewsByTopicWithLimit(topic, 1);
        return articles.isEmpty() ? null : articles.get(0);
    }

    // Including persistence

    /**
     * Fetches and publishes news articles by a specific topic.
     */
    public void fetchAndPublishNews(String topic, int limit) {
        fetchAndHandleNews(
                l -> findNewsByTopicWithLimit(topic, l),
                limit,
                "Published articles for topic: "
        );
    }

    /**
     * Fetches and publishes news articles by a specific category.
     */
    public void fetchAndPublishNewsByCategory(String category, int limit) {
        fetchAndHandleNews(
                l -> fetchNewsByCategoryWithLimit(category, l),
                limit,
                "Published articles for category: "
        );
    }

    /**
     * Retrieves all saved news articles from the database.
     */
    public List<NewsArticle> findAllSavedArticles() {
        return databaseService.findAllSavedArticles();
    }

    /**
     * Sets the flag indicating whether results should be saved to the database.
     */
    public void setSaveResults(boolean saveResults) {
        this.saveResults = saveResults;
    }

    // Private helper methods

    /**
     * Saves a list of articles to the database if saving is enabled.
     */
    private void saveArticlesIfEnabled(List<NewsArticle> articles) {
        if (saveResults) {
            databaseService.saveArticles(articles);
        }
    }

    /**
     * A generic method to fetch, process, and log articles.
     */
    private void fetchAndHandleNews(
            Function<Integer, List<NewsArticle>> fetchFunction,
            int limit,
            String logMessage
    ) {
        List<NewsArticle> articles = fetchFunction.apply(limit);
        articles.forEach(articleSink::tryEmitNext);
        saveArticlesIfEnabled(articles);
        System.out.println(logMessage + articles);
    }
}
