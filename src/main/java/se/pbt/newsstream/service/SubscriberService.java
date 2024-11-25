package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.repository.SubscriberRepository;

import java.util.List;

/**
 * Service for managing subscribers and their subscriptions.
 */
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Finds all subscribers.
     */
    public List<Subscriber> findAllSubscribers() {
        return subscriberRepository.findAll();
    }

    /**
     * Adds a new subscription to an existing subscriber.
     */
    public void addSubscriptionToSubscriber(long subscriberId, Subscription subscription) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found"));
        subscriber.getSubscriptions().add(subscription);
        subscriberRepository.save(subscriber);
    }

    /**
     * Saves a new or updated subscriber.
     */
    public void saveSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }
}

