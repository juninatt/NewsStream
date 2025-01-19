package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.dto.SubscriptionDTO;
import se.pbt.newsstream.mapper.NewsStreamMapper;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.repository.SubscriberRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Subscriber}s and their subscriptions.
 * Provides functionality for retrieving, creating, and deleting subscribers,
 * as well as managing subscriptions for each subscriber.
 */
@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Retrieves all {@link Subscriber}s from the database.
     */
    public List<Subscriber> findAllSubscribers() {
        return subscriberRepository.findAll();
    }

    /**
     * Saves a new {@link Subscriber} to the database.
     */
    public Subscriber saveSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    /**
     * Deletes the {@link Subscriber} with the specified ID from the database.
     */
    public boolean deleteSubscriber(long id) {
        Optional<Subscriber> subscriber = subscriberRepository.findById(id);
        if (subscriber.isPresent()) {
            subscriberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Adds a new {@link Subscription} to the specified subscriber.
     */
    public Subscription addSubscription(long subscriberId, SubscriptionDTO subscriptionDTO) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Subscription subscription = NewsStreamMapper.mapToSubscription(subscriptionDTO, subscriber);

        subscriber.getSubscriptions().add(subscription);

        subscriberRepository.save(subscriber);

        return subscription;
    }

    /**
     * Deletes a {@link Subscription} from the specified subscriber.
     */
    public Subscription deleteSubscription(long subscriberId, long subscriptionId) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Subscription subscriptionToRemove = subscriber.getSubscriptions().stream()
                .filter(subscription -> subscription.getId() == subscriptionId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscriber.getSubscriptions().remove(subscriptionToRemove);

        subscriberRepository.save(subscriber);

        return subscriptionToRemove;
    }
}
