package se.pbt.newsstream.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import se.pbt.newsstream.model.NewsApiArticleResponse;

/**
 * Client class for handling communication with the News API.
 * This class is responsible for making HTTP requests to the News API endpoints and retrieving responses.
 */
@Component
public class NewsApiClient {

    private final WebClient newsApiWebClient;


    public NewsApiClient(WebClient newsApiWebClient) {
        this.newsApiWebClient = newsApiWebClient;
    }

    /**
     * Sends a GET request to the News API and retrieves a response as a {@link NewsApiArticleResponse}}.
     */
    public NewsApiArticleResponse fetchArticles(String uri) {
        return newsApiWebClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NewsApiArticleResponse.class)
                .block();
    }
}
