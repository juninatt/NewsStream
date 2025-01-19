package se.pbt.newsstream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.service.NewsService;

import java.util.List;

/**
 * Controller for handling HTTP requests from the user related to news articles.
 * This class forwards the requests to the News API through the {@link NewsService} to
 * fetch and optionally save the articles as {@link NewsArticle} objects.
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    /**
     * Fetches a list of {@link NewsArticle}s based on a given topic.
     */
    @GetMapping("/topic")
    public List<NewsArticle> getNewsOnTopicWithLimit(
            @RequestParam String topic,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return newsService.findNewsByTopicWithLimit(topic, limit);
    }

    /**
     * Fetches a list of {@link NewsArticle}s based on a given category.
     */
    @GetMapping("/category")
    public List<NewsArticle> getNewsByCategoryWithLimit(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return newsService.fetchNewsByCategoryWithLimit(category, limit);
    }

    /**
     * Fetches the latest {@link NewsArticle} based on a given topic.
     */
    @GetMapping("/topic/latest")
    public NewsArticle getLatestNewsByTopic(@RequestParam String topic, int limit) {
        return newsService.findNewsByTopicWithLimit(topic, 1).get(0);
    }
}