package se.pbt.newsstream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.service.NewsService;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsControllerTest {
    @MockBean
    private NewsService newsService;
    private final WebTestClient webTestClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public NewsControllerTest(WebTestClient webTestClient, ObjectMapper objectMapper) {
        this.webTestClient = webTestClient;
        this.objectMapper = objectMapper;
    }

    @Nested
    @DisplayName("Test Get by Category :")
    class GetNewsByCategoryTests {

        @Test
        @DisplayName("Should return news articles for specified category 'business' with default limit")
        public void shouldReturnNewsArticlesForCategoryBusiness() throws Exception {
            NewsArticle sampleArticle = createSampleArticle();
            List<NewsArticle> mockArticles = Collections.singletonList(sampleArticle);
            String expectedJson = objectMapper.writeValueAsString(mockArticles);

            when(newsService.fetchNewsByCategory("business", false, 10)).thenReturn(mockArticles);

            webTestClient.get().uri("/api/news/category?category=business&limit=10&storeResults=false")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("Test Get on Topic:")
    class GetNewsOnTopicTests {

        @Test
        @DisplayName("Should return news articles for specified topic 'technology' with custom limit of 5")
        public void shouldReturnNewsArticlesForTopicTechnologyWithLimit5() throws Exception {
            NewsArticle sampleArticle = createSampleArticle();
            List<NewsArticle> mockArticles = Collections.singletonList(sampleArticle);
            String expectedJson = objectMapper.writeValueAsString(mockArticles);

            when(newsService.fetchNews("technology", false, 5)).thenReturn(mockArticles);

            webTestClient.get().uri("/api/news?topic=technology&limit=5&storeResults=false")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().json(expectedJson);
        }
    }

    @Nested
    @DisplayName("Test Get Latest News:")
    class FetchLatestNewsByTopicTests {

        @Test
        @DisplayName("Should return the latest news article for specified topic 'health'")
        public void shouldReturnLatestNewsArticleForTopicHealth() throws Exception {
            NewsArticle sampleArticle = createSampleArticle();
            String expectedJson = objectMapper.writeValueAsString(sampleArticle);

            when(newsService.getLatestNewsByTopic("health")).thenReturn(sampleArticle);

            webTestClient.get().uri("/news/latest?topic=health")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().json(expectedJson);
        }
    }

    /**
     * Creates a sample {@link NewsArticle} instance for use in test cases.
     * This method generates a standardized test article with preset values
     * for title, description, content, URL, image URL, author, publication date, and source.
     */
    private NewsArticle createSampleArticle() {
        return new NewsArticle(
                "Test Title",
                "Test Description",
                "Sample content for the article.",
                "http://example.com",
                "http://example.com/image.jpg",
                "Test Author",
                OffsetDateTime.now(),
                "Test Source"
        );
    }
}
