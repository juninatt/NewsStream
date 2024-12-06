package se.pbt.newsstream.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.service.SubscriptionService;

import java.util.List;

/**
 * This controller provides endpoints to create, delete and fetch subscribers news {@link Subscription}s.
 */
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Retrieves all existing {@link Subscription}s from the database.
     */
    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findAll();
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * Creates a new {@link Subscription} and saves it to the database.
     */
    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody @Valid Subscription subscription) {
        Subscription createdSubscription = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    /**
     * Finds the {@link Subscription} with matching ID and removes it from the database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable long id) {
        if (subscriptionService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
