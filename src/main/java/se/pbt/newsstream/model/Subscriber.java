package se.pbt.newsstream.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

/**
 * Represents a subscriber who receives notifications about news articles.
 * A subscriber can be notified via email, SMS, or both, based on their chosen notification types.
 * 
 * @see NewsArticle
 */
@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long databaseId;
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

    public long getDatabaseId() {  return databaseId;  }
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

    @Override
    public String toString() {
        return "Subscriber{" +
                "databaseId=" + databaseId +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notificationTypes=" + notificationTypes +
                '}';
    }
}

