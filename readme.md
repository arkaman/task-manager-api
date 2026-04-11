# Task Manager API

A RESTful Task Management API built using Spring Boot, Spring Data JPA, and PostgreSQL. This application allows users to create, update, retrieve, and delete tasks with validation, pagination, filtering, and search support.

---

## 🚀 Features

* Create, update, delete, and retrieve tasks
* Pagination support for task listing
* Filtering by status and priority
* Keyword search (title + description)
* Input validation using Jakarta Validation
* Global exception handling
* Clean layered architecture (Controller, Service, Repository)
* DTO-based request/response handling
* UUID-based primary keys
* PostgreSQL integration
* Environment-based configuration (dev/prod)

---

## 🛠️ Tech Stack

* Java 21+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok

---

## 📁 Project Structure

```
src/main/java/io/github/arkaman/taskmanager/
├── controller        # REST Controllers
├── domain
│   ├── dto           # Request/Response DTOs
│   └── entity        # JPA Entities
├── exception         # Custom Exceptions
├── mapper            # Entity ↔ DTO mapping
├── repository        # JPA Repositories
│   └── spec          # Specifications for filtering/search
├── service           # Business Logic
```

---

## ⚙️ Configuration

The application uses environment variables for sensitive data.

### Required Environment Variables

```
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_URL=jdbc:postgresql://localhost:5432/taskdb
```

---

## 📄 Application Profiles

### Development (`dev`)

* `ddl-auto=update`
* SQL logging enabled

### Production (`prod`)

* `ddl-auto=validate`
* SQL logging disabled

Switch profile:

```
SPRING_PROFILES_ACTIVE=prod
```

---

## ▶️ Running the Application

### 1. Clone the repository

```
git clone https://github.com/arkaman/task-manager-api.git
cd task-manager-api
```

### 2. Set environment variables

Set the following environment variables according to your local PostgreSQL setup:

```
export DB_USERNAME=your_db_username
export DB_PASSWORD=your_db_password
export DB_URL=jdbc:postgresql://localhost:5432/taskdb
```

### 3. Run the application

```
mvn spring-boot:run
```

---

## 📡 API Endpoints

### Create Task

```
POST /api/v1/tasks
```

### Get Tasks

```
GET /api/v1/tasks
```

#### Query Parameters

| Parameter | Type | Description |
|----------|------|-------------|
| page     | int  | Page number (default: 0) |
| size     | int  | Page size (default: 10) |
| status   | enum | Filter by task status |
| priority | enum | Filter by priority |
| keyword  | string | Search in title & description |

#### Example

```
GET /api/v1/tasks?status=PENDING&priority=HIGH&keyword=api&page=0&size=5
```

### Get Task by ID

```
GET /api/v1/tasks/{taskId}
```

### Update Task

```
PUT /api/v1/tasks/{taskId}
```

### Delete Task

```
DELETE /api/v1/tasks/{taskId}
```

---

## 🧪 Sample Request

### Create Task

```json
{
  "title": "Finish project",
  "description": "Complete the task manager API",
  "dueDate": "2026-04-10",
  "priority": "HIGH"
}
```

---

## ❗ Error Handling

All errors are returned in a consistent format:

```json
{
  "error": "Task with ID '...' not found."
}
```

---

## 🧠 Future Improvements

* Authentication & authorization (JWT)
* Docker support
* CI/CD pipeline
* Flyway for database migrations

