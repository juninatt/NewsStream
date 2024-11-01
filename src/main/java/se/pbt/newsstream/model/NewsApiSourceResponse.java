package se.pbt.newsstream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the response from the News API for source queries, providing the
 * response status and a list of news sources available from the API.
 */
public record NewsApiSourceResponse(
        String status,
        @JsonProperty("sources") List<Source> sources
) {}

