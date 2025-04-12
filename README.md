# 🏫 Garynation University Enrollment System

A microservices-based web application for managing university course enrollments, grades, and notifications. Built with a modular backend architecture and a simple HTML/CSS/JS frontend.

---

## ⚙️ Project Structure

-   **Frontend (Static)** — Simple HTML/CSS/JS served via VS Code Live Server
-   **Backend (Microservices)**:
    -   `api-gateway` – Central entry point
    -   `auth-service` – User authentication
    -   `course-service` – Course creation and deletion
    -   `enrollment-service` – Enrollment management
    -   `grade-service` – Grade assignment
    -   `notification-service` – Event-based user notifications
    -   `discovery-service` – Service registry (Eureka)

---

## 🚀 Running the Project

### 📦 1. Start Backend Services (Docker Compose)

Ensure you have Docker and Docker Compose installed.

From the root of the project, run:

```bash
docker-compose up --build
```

This will:

-   Build and start all microservices

-   Automatically register them via discovery-service (Eureka)

-   Expose services on the following ports:

#### Service Port

| Service              | Port |
| -------------------- | ---- |
| frontend (manual)    | 5500 |
| api-gateway          | 8081 |
| auth-service         | 8082 |
| course-service       | 8083 |
| enrollment-service   | 8084 |
| grade-service        | 8085 |
| notification-service | 8086 |
| discovery-service    | 8761 |

### 🌐 2. Run the Frontend (VS Code Live Server)

Open the frontend-services/ folder in VS Code.

Right-click index.html → "Open with Live Server"

This will launch the frontend on:
http://localhost:5500

Note: Default port for VS Code Live Server may differ.

### 📬 Notifications

System events (e.g., enrollment, drop, grade updates) trigger notifications stored through notification-service and viewable via the UI modal.

### ✅ Done!

You're now running a complete microservices-powered university enrollment system — scalable, modular, and Dockerized 💪
