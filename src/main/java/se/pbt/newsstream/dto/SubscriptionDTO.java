package se.pbt.newsstream.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;

import se.pbt.newsstream.model.Subscription;
import se.pbt.newsstream.model.Subscription.NotificationType;
import se.pbt.newsstream.model.Subscription.NotificationMode;

/**
 * Data Transfer Object for creating a {@link Subscription}.
 */
public record SubscriptionDTO(
        @NotBlank(message = "Subscription name cannot be blank")
        String name,
        @NotNull(message = "At least one keyword or category must be specified")
        Set<String> keywords,
        @NotNull(message = "Notification types must be specified")
        Set<NotificationType> notificationTypes,
        @NotNull(message = "Notification mode must be specified")
        NotificationMode notificationMode,
        Duration notificationInterval // Nullable for triggered notifications
) {}
