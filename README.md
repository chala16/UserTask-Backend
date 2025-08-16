# 📌 User Task Backend

## 🚀 Features

* User registration & login with JWT
* Password reset (via email) and change password
* Task CRUD operations (per user)
* Fetch today’s tasks
* JWT-based stateless authentication
* CORS configured for frontend (`http://localhost:5173`)

---

## ⚙️ Tech Stack

* Java 17+
* Spring Boot 3.x
* Spring Security (JWT)
* Spring Data JPA (Hibernate)
* MySQL
* Lombok
* Java Mail Sender

---

## 📂 Project Structure

```
src/main/java/com/example/demo/
├── config/          # Security & CORS config
├── controllers/     # REST Controllers
├── models/          # JPA Entities
├── payload/         # DTOs / Responses
├── repos/           # JPA Repositories
├── security/        # JWT Filter
├── services/        # Services (interfaces + impl)
```

---

## 🔧 Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/chala16/UserTaskBackend.git
cd UserTaskBackend
```

### 2. Configure Database

Create MySQL database:

```sql
CREATE DATABASE task_app;
```

### 3. Configure `application.properties`

Update `src/main/resources/application.properties` with your own values:

```properties
spring.application.name=demo
server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/task_app
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Config
security.jwt.secret-key=YOUR_BASE64_SECRET
security.jwt.expiration-time=3600000   # 1 hour

# Mail Config
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

👉 **Notes:**

* Use a Base64-encoded JWT secret (`openssl rand -base64 32`).
* For Gmail SMTP, generate an **App Password** (not your normal password).

### 4. Build & Run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

API will run at: `http://localhost:8082`

---

## 📡 API Endpoints

### 🔑 Auth

| Method | Endpoint                                               | Description                    |
| ------ | ------------------------------------------------------ | ------------------------------ |
| POST   | `/api/auth/register`                                   | Register user                  |
| POST   | `/api/auth/login`                                      | Login & get JWT                |
| POST   | `/api/auth/forgot-password?email={email}`              | Send reset link                |
| POST   | `/api/auth/reset-password?token={t}&newPassword={pwd}` | Reset password                 |
| PUT    | `/api/auth/change-password`                            | Change password (JWT required) |

### ✅ Tasks (JWT required)

| Method | Endpoint           | Description       |
| ------ | ------------------ | ----------------- |
| GET    | `/api/tasks`       | Get all tasks     |
| POST   | `/api/tasks`       | Create task       |
| PUT    | `/api/tasks/{id}`  | Update task       |
| DELETE | `/api/tasks/{id}`  | Delete task       |
| GET    | `/api/tasks/today` | Get today’s tasks |

---

## 🔐 Authentication

Send JWT in the **Authorization** header:

```
Authorization: Bearer <your_token>
```

---

## 🖥️ Frontend Setup

This API is configured for:

```
http://localhost:5173
```

Update `CustomCorsConfiguration.java` if your frontend runs on another port.

