package se.pbt.newsstream.service;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.repository.SubscriptionRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Service for managing subscriptions and starting notification schedules.
 */
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;
    private final TaskScheduler taskScheduler;
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               NotificationService notificationService,
                               TaskScheduler taskScheduler) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationService = notificationService;
        this.taskScheduler = taskScheduler;
    }

    /**
     * Initializes subscriptions for a list of subscribers and starts their schedules.
     */
    public void initializeSubscriptions(List<Subscriber> subscribers) {
        for (Subscriber subscriber : subscribers) {
            for (Subscription subscription : subscriber.getSubscriptions()) {
                saveSubscription(subscription);
                startSubscription(subscription);
            }
        }
    }

    /**
     * Starts a subscription by scheduling notifications or observing news changes.
     */
    public void startSubscription(Subscription subscription) {
        if (subscription.getNotificationInterval() != null) {
            ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(
                    () -> notifySubscriber(subscription),
                    subscription.getNotificationInterval().toMillis()
            );
            scheduledTasks.put(subscription.getId(), future);
        } else {
            observeNews(subscription);
        }
    }

    /**
     * Stops a subscription schedule.
     */
    public void stopSubscription(long subscriptionId) {
        ScheduledFuture<?> future = scheduledTasks.get(subscriptionId);
        if (future != null) {
            future.cancel(true);
            scheduledTasks.remove(subscriptionId);
        }
    }

    /**
     * Sends notifications for a subscription based on its settings.
     */
    private void notifySubscriber(Subscription subscription) {
        List<NewsArticle> articles = fetchRelevantArticles(subscription);
        for (NewsArticle article : articles) {
            notificationService.sendNotification(article, subscription);
        }
        subscription.setLastNotified(OffsetDateTime.now());
        saveSubscription(subscription);
    }

    /**
     * Observes news changes and sends notifications immediately.
     */
    private void observeNews(Subscription subscription) {
        Flux<NewsArticle> newsStream = fetchNewsStream(subscription);
        newsStream.subscribe(article -> notificationService.sendNotification(article, subscription));
    }

    /**
     * Saves a subscription to the repository.
     */
    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    /**
     * Fetches relevant articles based on subscription settings.
     */
    private List<NewsArticle> fetchRelevantArticles(Subscription subscription) {
        // Implement logic to fetch articles matching subscription's filters or topic
        return List.of(); // Placeholder
    }

    /**
     * Fetches a reactive news stream based on subscription settings.
     */
    private Flux<NewsArticle> fetchNewsStream(Subscription subscription) {
        // Implement logic to fetch a reactive news stream
        return Flux.empty(); // Placeholder
    }
}
