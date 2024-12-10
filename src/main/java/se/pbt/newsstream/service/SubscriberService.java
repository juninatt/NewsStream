package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.repository.SubscriberRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Subscriber}s.
 * Provides functionality for retrieving, creating, and deleting subscribers in the application.
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
}

