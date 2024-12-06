package se.pbt.newsstream.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.Subscription;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Service for managing and sending notifications for subscriptions.
 * This class coordinates different types of notifications, such as email and SMS,
 * and ensures that notifications are sent according to the specified interval.
 * It uses {@link EmailNotifier} and {@link SMSNotifier} to handle the delivery of messages.
 */
@Service
public class NotificationManager {
    private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);
    private final Map<Subscription.NotificationType, Notifier> notifiers;

    /**
     * Initializes the manager with available notifiers for each notification type.
     */
    public NotificationManager(EmailNotifier emailNotifier, SMSNotifier smsNotifier) {
        notifiers = Map.of(
                Subscription.NotificationType.EMAIL, emailNotifier,
                Subscription.NotificationType.SMS, smsNotifier
        );
    }

    /**
     * Processes a subscription and sends notifications if the notification interval has elapsed.
     * If a notification is sent, the "lastNotified" timestamp is updated.
     */
    public void processSubscription(Subscription subscription) {
        OffsetDateTime now = OffsetDateTime.now();

        if (subscription.getLastNotified() == null ||
                subscription.getLastNotified().plus(subscription.getNotificationInterval()).isBefore(now)) {

            logger.info("Processing subscription: {}", subscription.getName());

            subscription.getNotificationTypes().forEach(type -> {
                Notifier notifier = notifiers.get(type);
                if (notifier != null) {
                    logger.info("Sending {} notification for subscription: {}", type, subscription.getName());
                    notifier.notify(subscription, "New updates for your subscription: " + subscription.getName());
                } else {
                    logger.warn("No notifier available for notification type: {}", type);
                }
            });

            subscription.setLastNotified(now);
            logger.info("Updated lastNotified timestamp for subscription: {}", subscription.getName());
        } else {
            logger.info("No notification needed for subscription: {} at this time", subscription.getName());
        }
    }
}
