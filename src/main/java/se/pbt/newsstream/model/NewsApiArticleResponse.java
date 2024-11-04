package se.pbt.newsstream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents the response from the News API containing a list of news articles.
 * This class maps the JSON response structure to Java objects for further processing.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiArticleResponse {

    @JsonProperty("articles")
    private List<NewsArticleDTO> newsArticles;


    public List<NewsArticleDTO> getNewsArticles() {
        return newsArticles;
    }

    public void setNewsArticles(List<NewsArticleDTO> articles) {
        this.newsArticles = articles;
    }

    @Override
    public String toString() {
        return "NewsApiArticleResponse{" +
                "newsArticles=" + newsArticles +
                '}';
    }


    /**
     * Represents a Data Transfer Object (DTO) for a {@link NewsArticle}.
     * This is used to map the JSON structure directly into Java objects.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NewsArticleDTO(
            String title,
            String description,
            String content,
            String url,
            String urlToImage,
            String author,
            OffsetDateTime publishedAt,
            SourceDTO source
    ) {
        public String getSourceName() {
            return (source != null) ? source.name() : null;
        }
    }

    /**
     * Represents the source of a {@link NewsArticle} with minimal details.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SourceDTO(String name) {}
}
