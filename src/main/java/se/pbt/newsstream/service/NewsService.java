package se.pbt.newsstream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.pbt.newsstream.client.NewsApiClient;
import se.pbt.newsstream.mapper.NewsMapper;
import se.pbt.newsstream.model.NewsApiArticleResponse;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.repository.NewsRepository;
import se.pbt.newsstream.util.UriBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service for managing news data retrieval and processing.
 * This class handles the logic for fetching articles from the News API based on various criteria
 * and optionally saving the data to the database.
 */
@Service
public class NewsService {

    // Determines whether API results should be persisted to the database
    @Value("${news.persistence.save-results}")
    private boolean saveResults;
    private final NewsApiClient newsApiClient;
    private final NewsRepository newsRepository;


    /**
     * Constructs a NewsService with the required dependencies.
     */
    @Autowired
    public NewsService(NewsApiClient newsApiClient, NewsRepository newsRepository) {
        this.newsApiClient = newsApiClient;
        this.newsRepository = newsRepository;
    }

    /**
     * Fetches news articles based on a topic from the News API and optionally stores them in the database.
     * The number of articles returned can be limited.
     */
    public List<NewsArticle> findNewsByTopicWithLimit(String topic, int limit) {
        String uri = UriBuilder.buildUriForTopic(topic, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);

        List<NewsArticle> articles = response != null ? response.getNewsArticles().stream()
                .map(NewsMapper::mapToNewsArticle)
                .collect(toList()) : List.of();

        if (saveResults) {
            newsRepository.saveAll(articles);
        }

        return articles;
    }

    /**
     * Fetches news articles based on a category from the News API and optionally stores them in the database.
     * The number of articles returned can be limited.
     */
    public List<NewsArticle> fetchNewsByCategoryWithLimit(String category, int limit) {
        String uri = UriBuilder.buildUriForCategory(category, limit);
        NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);

        List<NewsArticle> articles = response != null ? response.getNewsArticles().stream()
                .map(NewsMapper::mapToNewsArticle)
                .collect(toList()) : List.of();

        if (saveResults) {
            newsRepository.saveAll(articles);
        }

        return articles;
    }

    /**
     * Fetches the latest news article based on a given topic.
     */
    public NewsArticle fetchLatestNewsByTopic(String topic) {
        List<NewsArticle> articles = findNewsByTopicWithLimit(topic, 1);
        return (articles != null && !articles.isEmpty()) ? articles.get(0) : null;
    }

    /**
     * Fetches current top headlines for a list of regions and saves them in the database.
     * The method prints the raw API response and logs saved articles.
     */
    public void fetchCurrentHeadlinesByRegions(List<String> regions) {
        for (String region : regions) {
            String uri = UriBuilder.buildUriForRegion(region);
            NewsApiArticleResponse response = newsApiClient.fetchArticles(uri);

            List<NewsArticle> articles = response != null ? response.getNewsArticles().stream()
                    .map(NewsMapper::mapToNewsArticle)
                    .collect(toList()) : List.of();

            if (!articles.isEmpty()) {
                System.out.println(newsRepository.saveAll(articles));
                System.out.println("Articles saved to database for region: " + region);
            }
        }
    }

    /**
     * Retrieves all saved news articles from the database.
     */
    public List<NewsArticle> findAllSavedArticles() {
        return newsRepository.findAll();
    }

}
