package se.pbt.newsstream.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${news.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://newsapi.org/v2";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", apiKey)
                .build();
    }
}
