package se.pbt.newsstream.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.pbt.newsstream.model.NewsArticle;
import java.util.List;

@Service
public class NewsService {

    private final WebClient webClient;

    @Value("${news.api.key}")
    private String apiKey;


    public NewsService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<NewsArticle> fetchNews(String topic) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/everything")
                        .queryParam("q", topic)
                        .queryParam("language", "en")
                        .queryParam("sortBy", "publishedAt")
                        .build())
                .retrieve()
                .bodyToFlux(NewsArticle.class)
                .collectList()
                .block();
    }

    public void testFetchNews() {
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/everything")
                        .queryParam("q", "technology")  // Testa med ett ämne, som "technology"
                        .queryParam("language", "en")
                        .queryParam("apiKey", apiKey)  // Lägg till din API-nyckel här
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        // Block för att få svaret och skriva ut det i konsolen
        System.out.println(response.block());
    }
}