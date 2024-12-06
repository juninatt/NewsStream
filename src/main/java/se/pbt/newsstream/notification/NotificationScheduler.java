package se.pbt.newsstream.notification;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.pbt.newsstream.repository.SubscriptionRepository;

@Component
public class NotificationScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationManager notificationManager;

    public NotificationScheduler(SubscriptionRepository subscriptionRepository, NotificationManager notificationManager) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationManager = notificationManager;
    }

    /**
     * Scheduled task to process all subscriptions.
     */
    @Scheduled(fixedRate = 60000) // Körs var 60:e sekund
    public void scheduleNotifications() {
        subscriptionRepository.findAll().forEach(notificationManager::processSubscription);
    }
}
