package se.pbt.newsstream.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pbt.newsstream.model.Subscription;

/**
 * Repository for managing {@link Subscription} entities in the database.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}