# Library Management System

A modern Java-based library management system with database integration, allowing librarians to manage books efficiently.

## Features

- ✅ Check book availability (available, checked out, not in collection)
- ✅ Update book titles with validation
- ✅ Add new books to the collection
- ✅ Remove books from the collection
- ✅ Database persistence with JPA/Hibernate
- ✅ RESTful API endpoints
- ✅ Clean architecture with service/repository pattern
- ✅ Comprehensive error handling and validation

## Project Structure

```
library-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── library/
│   │   │           ├── LibraryApplication.java
│   │   │           ├── controller/
│   │   │           │   └── BookController.java
│   │   │           ├── model/
│   │   │           │   └── Book.java
│   │   │           ├── repository/
│   │   │           │   └── BookRepository.java
│   │   │           ├── service/
│   │   │           │   ├── BookService.java
│   │   │           │   └── BookServiceImpl.java
│   │   │           ├── exception/
│   │   │           │   ├── BookNotFoundException.java
│   │   │           │   ├── DuplicateBookException.java
│   │   │           │   └── GlobalExceptionHandler.java
│   │   │           └── dto/
│   │   │               ├── BookDTO.java
│   │   │               └── BookResponseDTO.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/
│       └── java/
│           └── com/
│               └── library/
│                   ├── service/
│                   │   └── BookServiceTest.java
│                   └── controller/
│                       └── BookControllerTest.java
├── pom.xml
└── README.md
```

## Technology Stack

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- H2/PostgreSQL Database
- Maven
- JUnit 5
- Mockito
- Lombok
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.8+
- PostgreSQL (optional, can use H2 for development)

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/library-management-system.git
   ```

2. Navigate to the project directory:
   ```
   cd library-management-system
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`.

## API Documentation

Once the application is running, you can access the Swagger UI at `http://localhost:8080/swagger-ui.html`

### Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/books | Get all books |
| GET | /api/books/{id} | Get book by ID |
| GET | /api/books/{title}/availability | Check book availability |
| POST | /api/books | Add a new book |
| PUT | /api/books/{id} | Update book details |
| PATCH | /api/books/{id}/title | Update book title |
| DELETE | /api/books/{id} | Remove a book |

## Database Schema

The system uses a simple but extensible database schema:

```sql
CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE,
    author VARCHAR(255),
    isbn VARCHAR(20) UNIQUE,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Original Requirements

This project is an enhanced version of a basic library system that originally:
- Tracked book availability in a HashMap
- Allowed checking if books were available, checked out, or not in collection
- Provided a simple method to update book titles

The original system has been expanded to include database persistence, complete CRUD operations, validation, and a web API.

## Future Enhancements

- User authentication and authorization
- Borrower management
- Due date tracking and notification system
- Book categories and search functionality
- Integration with external book APIs for metadata