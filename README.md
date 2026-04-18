# Task Manager API

A secure, multi-user task management REST API built with Spring Boot, Spring Data JPA and PostgreSQL, featuring JWT-based authentication, user-scoped access control, and robust support for pagination, filtering, and search.

---

## 🚀 Features

* User registration & authentication (JWT)
* Access & refresh token mechanism
* Secure logout with refresh token invalidation
* Multi-user support with strict task ownership enforcement
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
* Spring Security
* JSON Web Tokens (jjwt)
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
├── security          # JWT auth system
└── service           # Business Logic
```

---

## ⚙️ Configuration

The application uses environment variables for sensitive data.

### Required Environment Variables

```
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_URL=jdbc:postgresql://localhost:5432/taskdb
JWT_SECRET=your_base64_secret_key
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
export JWT_SECRET=your_base64_secret_key
```

### 3. Run the application

```
mvn spring-boot:run
```

---

## 🔐 Authentication Endpoints

### Register

```
POST /auth/register
```

### Login

```
POST /auth/login
```

#### Response

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

### Refresh Token

```
POST /auth/refresh
```

```json
{
  "refreshToken": "your-refresh-token"
}
```

#### Response

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

### Logout

```
POST /auth/logout
```

Requires:

```
Authorization: Bearer <accessToken>
```

## 🔑 Using Protected Endpoints

Include access token in header:

```
Authorization: Bearer <accessToken>
```

## 📡 Task API Endpoints

🔒 All task endpoints require authentication

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

## 🧪 Quick Test Flow

1. Register user
2. Login → copy tokens
3. Call protected endpoint with access token
4. Use refresh token to get new access token
5. Logout and verify refresh token is invalid

---

## 🔄 Authentication Flow

1. User logs in → receives access + refresh tokens
2. Access token is used for API requests
3. When access token expires → use refresh token
4. Logout deletes refresh token → session invalidated

---

## 🔒 Security Notes

- Passwords are hashed using BCrypt
- JWT tokens are signed using a secure secret key
- Refresh tokens are stored in the database
- Access tokens are stateless and short-lived

---

## ❗ Error Handling

All errors are returned in a consistent format:

```json
{
  "message": "Refresh token expired",
  "status": 401,
  "timestamp": 1710000000000
}
```

---

## 🧠 Future Improvements

* Docker support
* CI/CD pipeline
* Flyway for database migrations

