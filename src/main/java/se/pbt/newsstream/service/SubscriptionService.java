package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.repository.SubscriptionRepository;

import java.util.List;

/**
 * Service class for handling {@link Subscription} operations. Provides methods for creating, retrieving and deleting
 * subscriptions by interacting with the {@link SubscriptionRepository}.
 */
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Retrieves all existing {@link Subscription}s from the database.
     */
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    /**
     * Saves the provided {@link Subscription} to the database.
     */
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    /**
     * Find the {@link Subscription} with matching ID and removes it from the database.
     */
    public boolean delete(long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
