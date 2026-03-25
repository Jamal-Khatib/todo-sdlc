# AGENTS Guidelines

## Project Overview
- This project is a full-stack Todo application demonstrating AI-assisted software delivery using Java and Spring Boot.
- The architecture follows a standard layered pattern: `TaskController` for API endpoints, `TaskService` for business logic, and `TaskRepository` for data access with Spring Data JPA over an SQLite database.
- The frontend is built with Thymeleaf for server-side rendering and vanilla JavaScript for client-side interactions, located in `src/main/resources`.
- Data is transported via immutable Java `record` DTOs, ensuring a consistent contract from the database to the UI.
- Client-side logic is managed by `app.js`, which handles DOM manipulation, event listeners, and API calls via the `fetch` API.
- Automated test coverage includes JUnit 5 unit tests with Mockito and integration tests using Spring Boot's test slices (`@WebMvcTest`, `@DataJpaTest`) over an H2 in-memory database.

## Build and Test Commands
- `mvn clean install`: Cleans the project, compiles code, runs tests, and packages the application.
- `mvn spring-boot:run`: Runs the application on a local server.
- `mvn test`: Executes the full test suite.
- `mvn jacoco:report`: Generates a code coverage report (also run as part of `install`).

## Code Style Guidelines
- Target Java 17 and Spring Boot 3.x; prefer asynchronous APIs where appropriate.
- Keep controllers thin by delegating business logic and orchestration to the service layer.
- Use Java `record` types for immutable DTOs and enums for constrained options (e.g., `Priority`).
- Use standard Spring annotations (`@RestController`, `@Service`, `@Repository`) to define component roles.
- JavaScript should use `const` and `let`, be organized within event listeners, and use shared helper functions to avoid duplicating DOM logic.
- Tests must follow an Arrange-Act-Assert structure, focus on a single behavior per test, and use descriptive naming conventions like `methodName_should_expectedBehavior_when_state`.

## Testing Instructions
- Install Java 17 and Maven to build and run the project.
- Execute the full test suite with `mvn test`. All tests run against an in-memory H2 database and require no external services.
- Generate a code coverage report by running `mvn clean install`. The results can be found at `target/site/jacoco/index.html`.

## Security Considerations
- Treat all inbound data as untrusted: `TaskService` must perform validation on all data, and CSV imports must check headers and data formats before persistence.
- Configure file size limits for the CSV import endpoint in `application.properties` to prevent denial-of-service attacks.
- Store secrets outside the repository. Use environment variables or Spring's externalized configuration for production secrets like database credentials.
- Rely on Spring Data JPA to prevent SQL injection vulnerabilities; avoid raw SQL queries.
- Thymeleaf provides default protection against XSS, but always be cautious when rendering user-provided content.

# High Priority Agent Protocol
- All chat responses MUST begin with the prefix: "Hooray, yes sir, on it".
