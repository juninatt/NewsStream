package se.pbt.newsstream.config;

import com.mailersend.sdk.MailerSend;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating beans related to external API integrations.
 * Configures clients for interacting with the News API and MailerSend API.
 * Uses {@link  NewsApiProperties} and {@link MailSenderProperties} to load settings from {@code application.yml}.
 */
@Configuration
@EnableConfigurationProperties({NewsApiProperties.class, MailSenderProperties.class})
public class ApiClientConfig {
    private final NewsApiProperties newsApiProperties;
    private final MailSenderProperties mailSenderProperties;

    public ApiClientConfig(NewsApiProperties newsApiProperties, MailSenderProperties mailSenderProperties) {
        this.newsApiProperties = newsApiProperties;
        this.mailSenderProperties = mailSenderProperties;
    }

    /**
     * Creates a {@link WebClient} bean configured for the News API.
     */
    @Bean
    public WebClient newsApiWebClient() {
        return WebClient.builder()
                .baseUrl(newsApiProperties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, newsApiProperties.apiKey())
                .build();
    }

    /**
     * Creates a {@link MailerSend} bean for interacting with the MailerSend API.
     */
    @Bean
    public MailerSend mailSender() {
        MailerSend mailerSend = new MailerSend();
        mailerSend.setToken(mailSenderProperties.email().apiKey());
        return mailerSend;
    }
}


