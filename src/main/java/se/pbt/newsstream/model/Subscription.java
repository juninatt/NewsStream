package se.pbt.newsstream.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Represents a subscription to a specific topic or category of news.
 * Each subscription contains its own notification types and interval settings.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NotNull(message = "Subscriber cannot be null")
    private Subscriber subscriber;
    @NotBlank(message = "Subscription name cannot be blank")
    private String name;
    @NotNull(message = "Notification interval must be specified")
    private Duration notificationInterval;
    @ElementCollection
    @NotNull(message = "Notification types must be specified")
    private Set<NotificationType> notificationTypes;
    private OffsetDateTime lastNotified;

    public Subscription(
            Subscriber subscriber,
            String name,
            Duration notificationInterval,
            Set<NotificationType> notificationTypes,
            OffsetDateTime lastNotified
    ) {
        this.subscriber = subscriber;
        this.name = name;
        this.notificationInterval = notificationInterval;
        this.notificationTypes = notificationTypes;
        this.lastNotified = lastNotified;
    }

    public Subscription() {}

    public enum NotificationType {
        EMAIL, SMS
    }

    public long getId() { return id;  }
    public Subscriber getSubscriber() { return subscriber; }
    public String getName() { return name; }
    public Duration getNotificationInterval() { return notificationInterval; }
    public Set<NotificationType> getNotificationTypes() { return notificationTypes; }
    public OffsetDateTime getLastNotified() { return lastNotified; }

    public void setLastNotified(OffsetDateTime lastNotified) { this.lastNotified = lastNotified; }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriber=" + subscriber.getId() +
                ", name='" + name + '\'' +
                ", notificationInterval=" + notificationInterval +
                ", notificationTypes=" + notificationTypes +
                ", lastNotified=" + lastNotified +
                '}';
    }
}

