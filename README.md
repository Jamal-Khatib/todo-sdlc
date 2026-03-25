# Todo Application

This is a full-featured Todo list application built with Spring Boot and a vanilla JavaScript frontend. It provides a clean, modern UI for managing tasks, including features for creating, editing, deleting, filtering, and sorting. It also includes a CSV import feature.

## Features

- **Full CRUD Operations**: Create, read, update, and delete tasks.
- **Rich Filtering**: Filter tasks by a search term (title or description), priority, or tags.
- **Flexible Sorting**: Sort tasks by creation date or last updated date.
- **Pagination**: The backend supports pagination, and the frontend includes controls for navigating through pages.
- **Edit in Place**: An edit modal allows for quick updates to existing tasks.
- **CSV Import**: Easily import a list of tasks from a CSV file.
- **Modern UI**: A responsive and clean user interface built with Thymeleaf and modern CSS.

## Technology Stack

- **Backend**: Spring Boot 3, Spring Data JPA, Spring Web
- **Database**: SQLite (production-ready), H2 (for testing)
- **Frontend**: Thymeleaf, Vanilla JavaScript, HTML5, CSS3
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Spring Boot Test, JaCoCo
- **CSV Parsing**: OpenCSV

## Getting Started

### Prerequisites

- Java 17 or later
- Apache Maven

### Running the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/Jamal-Khatib/todo-sdlc.git
   cd todo-sdlc
   ```

2. Run the application using Maven:
   ```sh
   mvn spring-boot:run
   ```

3. Open your browser and navigate to `http://localhost:8080`.

### Running Tests

To run the backend unit and integration tests:

```sh
npm test
```

To generate a test coverage report:

```sh
mvn clean install
```
The report will be available in `target/site/jacoco/index.html`.

## API Endpoints

| Method | Endpoint                 | Description                                      |
|--------|--------------------------|--------------------------------------------------|
| `GET`    | `/api/tasks`             | Get a paginated, filtered, and sorted list of tasks. |
| `GET`    | `/api/tasks/{id}`        | Get a single task by its ID.                     |
| `POST`   | `/api/tasks`             | Create a new task.                               |
| `PATCH`  | `/api/tasks/{id}`        | Update an existing task.                         |
| `DELETE` | `/api/tasks/{id}`        | Delete a task.                                   |
| `POST`   | `/api/tasks/import`      | Import tasks from a CSV file.                    |

## Database Schema

The `Task` entity has the following fields:

- `id` (UUID, Primary Key)
- `title` (String)
- `description` (String)
- `priority` (Enum: `LOW`, `MEDIUM`, `HIGH`)
- `dueDate` (LocalDate)
- `completed` (boolean)
- `tags` (Set of Strings)
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)
- `version` (long, for optimistic locking)
- `deletedAt` (LocalDateTime, for soft deletes)
