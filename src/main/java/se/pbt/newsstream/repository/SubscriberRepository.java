package se.pbt.newsstream.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pbt.newsstream.model.Subscriber;

/**
 * Repository for managing {@link Subscriber} entities in the database.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
