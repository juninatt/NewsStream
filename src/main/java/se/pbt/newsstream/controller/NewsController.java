package se.pbt.newsstream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.service.NewsService;

import java.util.List;

/**
 * Controller for handling HTTP requests from the user related to news articles.
 * This class forwards the requests to the News API through the {@link NewsService} to
 * fetch and optionally save the articles as {@link NewsArticle}.
 */
@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    /**
     * Fetches news articles based on a given topic.
     */
    @GetMapping("/api/news")
    public List<NewsArticle> getNewsOnTopicWithLimit(
            @RequestParam String topic,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return newsService.fetchNews(topic, limit);
    }

    /**
     * Fetches news articles based on a given category.
     */
    @GetMapping("/api/news/category")
    public List<NewsArticle> getNewsByCategoryWithLimit(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return newsService.fetchNewsByCategory(category, limit);
    }

    /**
     * Fetches the latest news article based on a given topic.
     */
    @GetMapping("/news/latest")
    public NewsArticle fetchLatestNewsByTopic(@RequestParam String topic) {
        return newsService.getLatestNewsByTopic(topic);
    }
}