package se.pbt.newsstream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import se.pbt.newsstream.model.NewsArticle;

import java.util.List;
import java.util.function.Function;

/**
 * Service for managing news articles including fetching, saving, and publishing.
 */
@Service
public class NewsService {
    @Value("${news.persistence.save-results}")
    private boolean saveResults;
    private final NewsApiService newsApiService;
    private final DatabaseService databaseService;
    private final Sinks.Many<NewsArticle> articleSink = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    public NewsService(NewsApiService newsApiService, DatabaseService databaseService) {
        this.newsApiService = newsApiService;
        this.databaseService = databaseService;
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
        return newsApiService.fetchArticlesByTopic(topic, limit);
    }

    /**
     * Fetches news articles by category with a specified limit.
     */
    public List<NewsArticle> fetchNewsByCategoryWithLimit(String category, int limit) {
        return newsApiService.fetchArticlesByCategory(category, limit);
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
     * Fetches and saves current headlines for multiple regions.
     */
    public void fetchAndSaveCurrentHeadlinesByRegions(List<String> regions) {
        regions.forEach(region -> {
            List<NewsArticle> articles = newsApiService.fetchArticlesByTopic(region, 5);
            saveArticlesIfEnabled(articles);
            System.out.println("Articles saved for region: " + region);
        });
    }

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
