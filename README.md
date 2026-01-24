# Employee Leave Management System (ELMS)

A backend application built with Spring Boot to manage employee leave applications, approvals, and rejections efficiently.

---

## 📊 Database Schema
The API automatically generates and manages three primary tables using Hibernate:
* **users**: Stores employee/manager details and roles.
* **leave_types**: Master data for leave categories (e.g., Sick, Casual).
* **leave_requests**: Transactional table connecting users to specific leave requests with status tracking.

---

## 🛠️ Getting Started

### 1. Clone the repository
```bash
git clone [https://github.com/PolakiJayaKrishna/employee-leave-management-api.git](https://github.com/PolakiJayaKrishna/employee-leave-management-api.git)

---

## 🚀 Features
* **Submit Leave Applications**: Employees can submit leave requests with start/end dates and reasons.
* **Approve/Reject Requests**: Managers can update the status of any leave request.
* **View All Requests**: Endpoint to retrieve all submitted leave records.
* **Employee History**: Employees can view their specific leave history by User ID.
* **Error Handling**: Global exception handling to provide clean JSON error messages.

---

## 🛠️ Tech Stack
* **Java 17** (Corretto)
* **Spring Boot 4.0.1**
* **Spring Data JPA** (Hibernate)
* **H2 Database** (In-memory)
* **Lombok**

---

## 📡 API Endpoints

| Method | Endpoint                          | Description                               |
|:-------|:----------------------------------|:------------------------------------------|
| POST   | `/api/leave-requests/apply`       | Submit a new leave request                |
| GET    | `/api/leave-requests/all`         | View all leave requests in the system     |
| GET    | `/api/leave-requests/user/{id}`   | View leave history for a specific employee|
| PUT    | `/api/leave-requests/{id}/status` | Update status (via Request Param)         |

---

## 🧪 Testing with Postman

### 1. Apply for Leave (POST)
**URL:** `http://localhost:8080/api/leave-requests/apply`  
**Body (raw JSON):**
```json
{
  "user": { "id": 1 },
  "leaveType": { "id": 1 },
  "startDate": "2026-03-01",
  "endDate": "2026-03-05",
  "reason": "Family trip",
  "status": "PENDING"
}

2. View User History (GET)
URL: http://localhost:8080/api/leave-requests/user/1

Use this to see all leaves belonging only to the user with ID 1.

3. Update Status (PUT)
URL: http://localhost:8080/api/leave-requests/1/status?status=APPROVED

⚙️ Database Access
The project uses an in-memory H2 database. To access the browser interface:

Start the application.

Go to: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:elmsdb

User: sa | Password: (blank)

🏗️ Project Structure
elms.entities: Database models (User, LeaveRequest, LeaveType).

elms.repository: Data access interfaces.

elms.service: Business logic implementation.

elms.controller: REST API Endpoints.

elms.exception: Global error handling logic using @RestControllerAdvice.

🚧 Roadmap
✅ REST Controllers: Exposing API endpoints for client consumption.

✅ Global Exception Handling: Standardizing API error responses with a @ControllerAdvice.

⏳ Spring Security: Implementing JWT-based authentication for secure access.

⏳ Update Requests: Allowing employees to edit pending leave applications.
