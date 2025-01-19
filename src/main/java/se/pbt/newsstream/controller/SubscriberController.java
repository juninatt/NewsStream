package se.pbt.newsstream.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.pbt.newsstream.dto.SubscriptionDTO;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.service.SubscriberService;

import java.util.List;

/**
 * This controller provides endpoints to create, deleter, and fetch {@link Subscriber}s.
 */
@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    // Subscriber only

    /**
     * Retrieves all existing {@link Subscriber}s from the database.
     */
    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberService.findAllSubscribers();
        return ResponseEntity.ok(subscribers);
    }

    /**
     * Creates a new {@link Subscriber} and saves it to the database.
     */
    @PostMapping
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody @Valid Subscriber subscriber) {
        Subscriber createdSubscriber = subscriberService.saveSubscriber(subscriber);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscriber);
    }

    /**
     * Deletes the {@link Subscriber} with the  given ID from the database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(
            @PathVariable long id
    ) {
        if (subscriberService.deleteSubscriber(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Subscription related

    public ResponseEntity<Subscription> addSubscriptionToSubscriber(
            @PathVariable long subscriberId,
            @RequestBody @Valid SubscriptionDTO subscriptionDTO
    ) {
        Subscription createdSubscription = subscriberService.addSubscription(subscriberId, subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }
}
