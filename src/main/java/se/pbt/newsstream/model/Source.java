package se.pbt.newsstream.model;

/**
 * Represents a news source providing articles on the News API, including key
 * identifiers and relevant metadata to categorize and contextualize articles.
 */
public record Source(
        String id,
        String name,
        String description,
        String url,
        String category,
        String language,
        String country
) {}
