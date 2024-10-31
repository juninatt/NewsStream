package se.pbt.newsstream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String BASE_URL = "https://newsapi.org/v2";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Bearer YOUR_API_KEY") // Byt ut med din API-nyckel
                .build();
    }
}
