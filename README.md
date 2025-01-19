# NewsStream

NewsStream is a Java application built with Spring Boot and Maven that fetches news articles from [News API](https://newsapi.org/)
based on user-defined criteria. It combines data collection, subscriptions, and notifications to deliver near real-time updates on specific news topics.

## Features

* **News Collection and Filtering**:
  Fetches articles from [News API](https://newsapi.org/) based on user-specific topics and categories.
  Articles can be saved in the database or used directly for notifications.
* **Email Notifications**:
  Sends email notifications using [MailSender](https://www.mailersend.com/).
  Requires either a verified sender or a registered domain in your MailSender account for notifications to work.

---

## Setup

### Requirements
* Java 17 or later
* Maven for dependency management
* [MailSender account](https://www.mailersend.com/):
    * Add an API key to `application.yml` and set up a verified sender or register a domain.
* [News API account](https://newsapi.org/):
    * Add an API key to `application.yml`.

### Installation
1. Clone the repository:
```bash
git clone https://github.com/juninatt/NewsStream.git
```

2. Configure API keys in application.yml. See [application-template.yml](src/main/resources/templates/application-template.yml)

3. Build and run the app:
```bash
```bash
mvn clean install
mvn spring-boot:run
```

---
### Known Issues
X

### Author
Petter Bergström / juninatt

