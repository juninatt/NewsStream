package se.pbt.newsstream.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.pbt.newsstream.apiclient.newsapi.NewsApiClient;
import se.pbt.newsstream.model.NewsArticle;

import java.util.List;

/**
 * Service class for managing news articles.
 * Provides methods for fetching news articles by topic or category using the {@link NewsApiClient}.
 */
@Service
public class NewsService {
    @Value("${persistence.save-results}")
    private boolean saveResults;
    private final NewsApiClient newsApiClient;

    public NewsService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }


    /**
     * Fetch a list of news articles by topic with a specified limit.
     */
    public List<NewsArticle> findNewsByTopicWithLimit(String topic, int limit) {
        return newsApiClient.newsByTopicLimit(topic, limit);
    }

    /**
     * Fetch a list of news articles by category with a specified limit.
     */
    public List<NewsArticle> fetchNewsByCategoryWithLimit(String category, int limit) {
        return newsApiClient.newsByCategoryLimit(category, limit);
    }
}
