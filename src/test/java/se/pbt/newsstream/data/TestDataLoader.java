package se.pbt.newsstream.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.repository.SubscriberRepository;

/**
 * A utility component for loading test data into the application during startup.
 */
@Component
public class TestDataLoader {

    private final SubscriberRepository subscriberRepository;

    public TestDataLoader(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @PostConstruct
    public void loadTestData() {
        subscriberRepository.save(new Subscriber("JohnDoe", "john.doe@example.com", "+1234567890"));
        subscriberRepository.save(new Subscriber("JaneSmith", "jane.smith@example.com", "+1987654321"));
        System.out.println(subscriberRepository.findAll());
    }
}
