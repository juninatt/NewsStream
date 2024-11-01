package se.pbt.newsstream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the response from the News API for article queries, including the
 * status of the response, total results, and a list of matching articles.
 */
public record NewsApiArticleResponse(
        String status,
        int totalResults,
        @JsonProperty("articles") List<NewsArticle> articles
) {}

