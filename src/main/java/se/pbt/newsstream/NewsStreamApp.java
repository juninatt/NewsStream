package se.pbt.newsstream;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.pbt.newsstream.service.NewsService;

@SpringBootApplication
public class NewsStreamApp {
	private final NewsService newsService;

	public NewsStreamApp(NewsService newsService) {
		this.newsService = newsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NewsStreamApp.class, args);
	}

	@PostConstruct
	public void init() {
		newsService.testFetchNews();
	}
}
