package se.pbt.newsstream.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pbt.newsstream.model.NewsArticle;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for managing {@link NewsArticle} entities in the database.
 */
@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {

    /**
     * Finds a {@link NewsArticle} by title and publication date to prevent duplicates.
     */
    Optional<NewsArticle> findByTitleAndPublishedAt(String title, LocalDateTime publishedAt);
}
