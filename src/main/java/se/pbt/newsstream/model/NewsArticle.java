package se.pbt.newsstream.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

/**
 * Represents a news article fetched from the News API, containing essential
 * details about the article's content.
 */
@Entity
public class NewsArticle {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private final String title;
        @Column(length = 400)
        private final String description;
        private final String content;
        private final String url;
        private final String urlToImage;
        private final String author;
        private final OffsetDateTime publishedAt;
        private final String source;

        public NewsArticle(
                String title,
                String description,
                String content,
                String url,
                String urlToImage,
                String author,
                OffsetDateTime publishedAt,
                String source
        ) {
                this.title = title;
                this.description = description;
                this.content = content;
                this.url = url;
                this.urlToImage = urlToImage;
                this.author = author;
                this.publishedAt = publishedAt;
                this.source = source;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getContent() { return content; }
        public String getUrl() { return url; }
        public String getUrlToImage() { return urlToImage; }
        public String getAuthor() { return author; }
        public OffsetDateTime getPublishedAt() { return publishedAt; }
        public String getSource() { return source; }

        @Override
        public String toString() {
                return "NewsArticle{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", content='" + content + '\'' +
                        ", url='" + url + '\'' +
                        ", urlToImage='" + urlToImage + '\'' +
                        ", author='" + author + '\'' +
                        ", publishedAt=" + publishedAt +
                        ", source=" + source +
                        '}';
        }
}
