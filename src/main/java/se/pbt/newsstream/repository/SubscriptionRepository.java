package se.pbt.newsstream.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pbt.newsstream.model.Subscription;

/**
 * Repository for managing {@link Subscription} entities in the database.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}