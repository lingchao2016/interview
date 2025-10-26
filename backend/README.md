# Java Spring Boot API Coding Exercise

## Steps to get started:

#### Prerequisites
- Maven
- Java 21 (updated from Java 1.8)
- Docker and Docker Compose (for containerized deployment)

#### Fork the repository and clone it locally
- https://github.com/Tekmetric/interview.git

#### Import project into IDE
- Project root is located in `backend` folder

## Running the Application

### Option 1: Using Docker (Recommended)

This option runs the application with all dependencies (Redis, OpenSearch) in containers.

#### Build and start all services:
```bash
docker-compose up --build -d
```

This command will:
- Build the Spring Boot application with Java 21
- Start Redis (cache)
- Start OpenSearch (search engine)
- Start the Spring Boot application

#### Check service status:
```bash
docker-compose ps
```

#### View logs:
```bash
docker-compose logs -f          # All services
docker-compose logs -f app      # Just the Spring Boot app
```

#### Stop all services:
```bash
docker-compose down
```

#### Access the application:
- API: http://localhost:8080
- OpenSearch: http://localhost:9200
- Redis: localhost:6379

### Option 2: Running Locally with Maven

**Note:** This requires Redis and OpenSearch to be running locally or accessible.

#### Build and run your app:
```bash
mvn clean package -DskipTests
java -jar target/interview-1.0-SNAPSHOT.jar
```

Or simply:
```bash
mvn package && java -jar target/interview-1.0-SNAPSHOT.jar
```

## Testing the Application

#### Test that your app is running:
```bash
curl -X GET http://localhost:8080/api/welcome
```

#### Access Swagger UI:
- http://localhost:8080/swagger-ui.html

#### Access H2 Console:
- Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

## Technology Stack

- **Java 21** with Spring Boot 3.2.0
- **Spring Security** with JWT authentication
- **H2 Database** (in-memory)
- **Redis** for caching
- **OpenSearch** for search functionality
- **Swagger/OpenAPI** for API documentation

## Project Structure

- Event Management API with CRUD operations
- JWT-based authentication
- Redis caching layer
- OpenSearch integration
- Docker containerization
