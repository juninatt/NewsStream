package se.pbt.newsstream.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

/**
 * Represents a subscriber who receives notifications about news articles({@link NewsArticle}.
 * A subscriber can be notified via email, SMS, or both, based on their chosen notification types.
 */
@Entity
public class Subscriber {
    @Email(message = "Invalid email format")
    private final String email;
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private final String phoneNumber;
    @ElementCollection
    @NotEmpty(message = "At least one notification type must be selected")
    private final Set<NotificationType> notificationTypes;

    public Subscriber(String email, String phoneNumber, Set<NotificationType> notificationTypes) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.notificationTypes = notificationTypes;
    }


    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }


    public enum NotificationType {
        EMAIL, SMS
    }
}

