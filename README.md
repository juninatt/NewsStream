# NewsStream

NewsStream is a Java application built with Spring Boot and Maven that fetches news articles from an external API (News API) 
based on user-defined criteria. It combines data collection, subscriptions, and notifications to deliver near real-time updates on specific news topics.

## Features

NewsStream provides the following core features:
* **News Collection and Filtering**: 
Fetches articles from the News API based on user-specific topics and categories. 
Articles can either be saved in the database for later analysis or used directly for notifications.
* **Notification Service**: 
Sends email notifications to users when new articles match their preferences, using SendGrid for email delivery.
* **Reactive Programming**: 
News articles are streamed in near real-time using Project Reactor and Spring WebFlux, enabling efficient and non-blocking notification handling.
* **Scheduled Execution**: 
Uses Spring Boot’s @Scheduled annotation to automatically fetch news updates at regular intervals.

---

## Setup

### Requirements
   * Java 17 or later
   * Maven for dependency management
   * [SendGrid account](https://sendgrid.com/) for email integration (create an API key and add it to application.yml)
   * [News API account](https://newsapi.org/)  to access news data (API key required)

### Installation
1. Clone the repository:
```bash
git clone https://github.com/juninatt/NewsStream.git
```

2. Configure API keys in application.yml:
```yaml
news:
  api:
    key: YOUR_NEWS_API_KEY
    
sendgrid:
  api-key: YOUR_SENDGRID_API_KEY
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
``` 

* Testing: Access the app at `http://localhost:8080` to test scheduled fetching and notifications.

---
### Known Issues
To avoid email delivery failures it is important to adhere to industry sender requirements, which are updated regularly.
See https://sendgrid.com/en-us/blog/email-delivery-failure-causes for more information.

### Author
Petter Bergström / juninatt

