package se.pbt.newsstream.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Represents a subscription to news updates, supporting both scheduled and triggered notifications.
 * A subscription allows filtering of articles based on keywords or categories.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Subscription name cannot be blank")
    private String name;
    @ManyToOne
    @NotNull(message = "Subscriber cannot be null")
    private Subscriber subscriber;
    @ElementCollection
    @NotNull(message = "At least one keyword or category must be specified")
    private Set<String> keywords;
    @ElementCollection
    @NotNull(message = "Notification types must be specified")
    private Set<NotificationType> notificationTypes;
    @NotNull(message = "Notification mode must be specified")
    @Enumerated(EnumType.STRING)
    private NotificationMode notificationMode;
    private OffsetDateTime lastNotified;
    private Duration notificationInterval; // Null for triggered notifications

    public Subscription() {}

    public Subscription(
            Subscriber subscriber,
            String name,
            Set<NotificationType> notificationTypes,
            NotificationMode notificationMode,
            Set<String> keywords,
            Duration notificationInterval
    ) {
        this.subscriber = subscriber;
        this.name = name;
        this.notificationTypes = notificationTypes;
        this.notificationMode = notificationMode;
        this.keywords = keywords;
        this.notificationInterval = notificationInterval;
    }

    public enum NotificationType {
        EMAIL, SMS
    }

    public enum NotificationMode {
        SCHEDULED, TRIGGERED
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Subscriber getSubscriber() { return subscriber; }
    public Set<String> getTriggerKeywords() { return keywords; }
    public Set<NotificationType> getNotificationTypes() { return notificationTypes; }
    public NotificationMode getNotificationMode() { return notificationMode;  }
    public Duration getNotificationInterval() { return notificationInterval; }
    public OffsetDateTime getLastNotified() { return lastNotified; }
    public void setLastNotified(OffsetDateTime lastNotified) { this.lastNotified = lastNotified; }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subscriber=" + subscriber +
                ", keywords=" + keywords +
                ", notificationTypes=" + notificationTypes +
                ", notificationMode=" + notificationMode +
                ", lastNotified=" + lastNotified +
                ", notificationInterval=" + notificationInterval +
                '}';
    }
}

