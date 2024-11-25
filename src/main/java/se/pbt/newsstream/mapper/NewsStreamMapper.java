package se.pbt.newsstream.mapper;

import se.pbt.newsstream.model.NewsApiArticleResponse.NewsArticleDTO;
import se.pbt.newsstream.model.NewsArticle;

/**
 * Utility class for mapping between data transfer objects (DTOs) and entities.
 */
public class NewsStreamMapper {

    /**
     * Maps a {@link NewsArticleDTO} to a {@link NewsArticle}.
     *
     * @param dto the DTO to map from
     * @return a new instance of {@link NewsArticle} containing the data from the DTO
     */
    public static NewsArticle mapToNewsArticle(NewsArticleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO must not be null");
        }
        return new NewsArticle(
                dto.title(),
                dto.description(),
                dto.content(),
                dto.url(),
                dto.urlToImage(),
                dto.author(),
                dto.publishedAt(),
                dto.getSourceName() // Extracts only the source name
        );
    }
}
