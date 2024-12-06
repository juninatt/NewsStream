package se.pbt.newsstream.notification.notifier;

import org.springframework.stereotype.Component;
import se.pbt.newsstream.model.Subscription;

/**
 * Implementation of {@link Notifier} for sending SMS notifications.
 */
@Component
public class SMSNotifier implements Notifier {
    @Override
    public void notify(Subscription subscription, String message) {
        String phoneNumber = subscription.getSubscriber().getPhoneNumber();
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        // TODO: Add SMS notification functionality
    }
}
