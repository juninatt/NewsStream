package se.pbt.newsstream.notification.notifier;

import se.pbt.newsstream.model.Subscription;

/**
 * Functional interface for sending notifications for a subscription.
 */
@FunctionalInterface
public interface Notifier {
    /**
     * Sends a notification for the given subscription with the specified message.
     */
    void notify(Subscription subscription, String message);
}
