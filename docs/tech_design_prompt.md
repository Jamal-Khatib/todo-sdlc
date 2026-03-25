**Generate High-Level Technical Design and Architecture (Todo App)**

**Context**

*   Read and ground your answer in:
    *   `final_requirements.md`
    *   `plan.md`
*   **MVP Summary**: Spring Boot 3.x Web API + Thymeleaf Views, vanilla JS (no frontend frameworks), Spring Data JPA + SQLite (or H2 for dev; single instance acceptable in prod for MVP), RFC7807 ProblemDetails errors, soft delete, pagination/filter/sort/search, and basic accessibility.

**Goal**

*   Produce a practical, high-level technical design and architecture proposal to implement the MVP efficiently and safely, aligned to the user stories and sprint plan.

**Deliverables**

*   **Tech Stack and Rationale**
    *   **Backend**: Spring Boot 3.x (Spring Web, Spring Data JPA), Spring Validation, Jackson, SLF4J with Logback.
    *   **UI**: Thymeleaf Templates with fragments/partials, vanilla JavaScript modules, and a lightweight `fetch` API wrapper.
    *   **Tooling**: Justify choices for build tools (Maven/Gradle), testing libraries (JUnit 5, Mockito, AssertJ), and any optional third-party libraries.

*   **Architecture and Layering**
    *   Propose clear layers (e.g., Presentation, Application/Service, Domain, Infrastructure/Data) and the dependencies between them.
    *   Define the responsibilities for each layer and outline the dependency injection strategy (constructor-based injection).
    *   Break down the primary components/modules (e.g., `TaskController`, `TaskService`, `TaskRepository`, `Task` Entity, DTOs, global `ExceptionHandler`).

*   **Data Model and Persistence**
    *   Define the `Task` entity, including all fields, and specify the strategy for soft delete (e.g., `deletedAt` timestamp) and optimistic concurrency control (e.g., `@Version`).
    *   Detail the Spring Data JPA configuration, including keys, indexes, and a strategy to globally exclude soft-deleted items from queries (e.g., `@Where` annotation or repository-level filters).
    *   Describe the database migration strategy (e.g., using `spring.jpa.hibernate.ddl-auto` for development) and outline the planned path for migrating to PostgreSQL post-MVP.

*   **API Design Conventions**
    *   List the REST endpoints aligned with the requirements (CRUD operations, including `PATCH` for partial updates).
    *   Confirm the query parameters for pagination, sorting, filtering, and searching.
    *   Design the error handling approach using `@ControllerAdvice` to map exceptions to RFC7807 `ProblemDetail` responses.
    *   State the API versioning stance for the MVP (i.e., none).
    *   Specify the plan to generate OpenAPI 3.x documentation using `springdoc-openapi`.

*   **UI Architecture (Thymeleaf + vanilla JS)**
    *   Structure the pages, views, and partials (e.g., `tasks/index.html` as the main view, a `_list.html` fragment for the task list).
    *   Outline the JavaScript module organization: an API client (fetch wrapper), UI event handlers, and state management (syncing URL with filters).
    *   Describe the progressive enhancement approach and guidelines for ensuring a responsive and accessible layout.
    *   Detail the workflow for creating a task (e.g., Add button reveals form -> Save triggers fetch POST -> list is refreshed via HTMX-style partial update without a full page reload).

*   **Cross-Cutting Concerns and Best Practices**
    *   Detail the validation strategy (using Spring Validation annotations on DTOs) and the DTO-to-entity mapping approach (e.g., MapStruct or manual mappers).
    *   Design the error handling middleware to map domain-specific exceptions to standard `ProblemDetail` responses.
    *   Define the strategy for structured logging, correlation IDs (`X-Request-ID`), and application configuration.
    *   Outline security measures: security headers (via Spring Security), CORS policy (same-origin for MVP), and a `/health` endpoint.
    *   Address performance considerations: database indexing, pagination defaults, and strategies to avoid N+1 query problems.

*   **Testing Strategy**
    *   **Unit Tests**: For services, validators, and other business logic components (using Mockito).
    *   **Integration Tests**: For API endpoints using `SpringBootTest` with `MockMvc` or `TestRestTemplate` to verify responses and `ProblemDetail` errors.
    *   **UI Testing**: A lightweight manual checklist for the MVP. Mention that automated end-to-end testing (e.g., with Playwright) is out of scope for the MVP.

*   **Diagrams (use Markdown Mermaid)**
    *   A **Layered Architecture Diagram** showing the separation of concerns.
    *   A **Component Diagram** illustrating the key backend components and their interactions.
    *   A **Sequence Diagram** for the task creation flow, from the UI to the database and back.

**Output Format**

*   **Title**: “High-Level Technical Design — Todo App (MVP)”
*   Use Markdown with clear sections matching the Deliverables above.
*   Include at least one of each required Mermaid diagram (Layered Architecture, Component, Sequence).

**Style**

*   Be concise, actionable, and opinionated, providing clear rationale for decisions. Highlight best practices and explicitly state where simplicity is chosen for the MVP.
