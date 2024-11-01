package se.pbt.newsstream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Represents a news article fetched from the News API, containing essential
 * details about the article's content, source, and publication specifics.
 */
public record NewsArticle(
        Source source,
        String author,
        String title,
        String description,
        String url,
        String urlToImage,
        @JsonProperty("publishedAt") LocalDateTime publishedAt,
        String content
) {}
