package se.pbt.newsstream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.service.NewsService;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/api/news")
    public List<NewsArticle> getNews(@RequestParam String topic) {
        return newsService.fetchNews(topic);
    }
}