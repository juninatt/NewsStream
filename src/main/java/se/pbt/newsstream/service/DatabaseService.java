package se.pbt.newsstream.service;

import org.springframework.stereotype.Service;
import se.pbt.newsstream.model.NewsArticle;
import se.pbt.newsstream.model.Subscriber;
import se.pbt.newsstream.repository.NewsRepository;
import se.pbt.newsstream.repository.SubscriberRepository;

import java.util.List;

/**
 * Service for handling database operations related to news articles and subscribers.
 *
 * @see NewsArticle
 * @see Subscriber
 */
@Service
public class DatabaseService {

    private final NewsRepository newsRepository;
    private final SubscriberRepository subscriberRepository;


    public DatabaseService(NewsRepository newsRepository, SubscriberRepository subscriberRepository) {
        this.newsRepository = newsRepository;
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Saves a list of news articles to the database.
     */
    public void saveArticles(List<NewsArticle> articles) {
        newsRepository.saveAll(articles);
    }

    /**
     * Retrieves all saved news articles from the database.
     */
    public List<NewsArticle> findAllSavedArticles() {
        return newsRepository.findAll();
    }

    /**
     * Saves a list of subscribers to the database.
     */
    public void saveSubscribers(List<Subscriber> subscribers) {
        subscriberRepository.saveAll(subscribers);
    }

    /**
     * Retrieves all saved subscribers from the database.
     */
    public List<Subscriber> findSavedSubscribers() {
        return subscriberRepository.findAll();
    }
}
