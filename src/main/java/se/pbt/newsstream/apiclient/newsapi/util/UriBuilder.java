package se.pbt.newsstream.apiclient.newsapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for building URIs with query parameters for the News API.
 * This class constructs URI paths dynamically based on specified criteria.
 */
@Component
public final class UriBuilder {

    private static String language;
    private static String sortBy;

    private UriBuilder() {}

    @Value("${news.api.language:en}")
    public void setLanguage(String lang) {
        UriBuilder.language = lang;
    }

    @Value("${news.api.sortBy:publishedAt}")
    public void setSortBy(String sort) {
        UriBuilder.sortBy = sort;
    }

    /**
     * Builds a URI path for fetching news articles based on a topic.
     */
    public static String buildUriForTopic(String topic, int limit) {
        UriComponentsBuilder builder = createBaseUriBuilder("/everything")
                .queryParam("q", topic)
                .queryParam("sortBy", sortBy);
        return addCommonParams(builder, limit).toUriString();
    }

    /**
     * Builds a URI path for fetching news articles based on a category.
     */
    public static String buildUriForCategory(String category, int limit) {
        UriComponentsBuilder builder = createBaseUriBuilder("/top-headlines")
                .queryParam("category", category);
        return addCommonParams(builder, limit).toUriString();
    }

    /**
     * Builds a URI path for fetching top headlines based on a region.
     */
    public static String buildUriForRegion(String region) {
        UriComponentsBuilder builder = createBaseUriBuilder("/top-headlines")
                .queryParam("country", region);
        return addCommonParams(builder, null).toUriString();
    }

    /**
     * Creates a base UriComponentsBuilder instance with a specified path.
     */
    private static UriComponentsBuilder createBaseUriBuilder(String path) {
        return UriComponentsBuilder.newInstance().path(path);
    }

    /**
     * Adds common query parameters such as language and page size.
     */
    private static UriComponentsBuilder addCommonParams(UriComponentsBuilder builder, Integer limit) {
        builder.queryParam("language", language);
        if (limit != null) {
            builder.queryParam("pageSize", limit);
        }
        return builder;
    }
}


