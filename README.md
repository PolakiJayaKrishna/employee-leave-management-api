# Employee Leave Management System (ELMS)

A simple backend application built with Spring Boot to manage employee leave applications, approvals, and rejections.

## 🚀 Features
* **Submit Leave Applications**: Employees can submit leave requests with start/end dates and reasons.
* **Approve/Reject Requests**: Managers can update the status of any leave request.
* **View All Requests**: Endpoint to retrieve all submitted leave records.
* **Error Handling**: Global exception handling to provide clean JSON error messages when records are not found.

## 🛠️ Tech Stack
* **Java 17** (Corretto)
* **Spring Boot 4.0.1**
* **Spring Data JPA** (Hibernate)
* **H2 Database** (In-memory)
* **Lombok**

## 📡 API Endpoints

| Method | Endpoint                          | Description                          |
|:-------|:----------------------------------|:-------------------------------------|
| POST   | `/api/leave-requests/apply`       | Submit a new leave request           |
| GET    | `/api/leave-requests/all`         | View all leave requests in the system|
| PUT    | `/api/leave-requests/{id}/status` | Update status (via Request Param)    |

## 🧪 Testing with Postman

### 1. Apply for Leave (POST)
**URL:** `http://localhost:8080/api/leave-requests/apply`  
**Body (raw JSON):**
{
"user": { "id": 1 },
"leaveType": { "id": 1 },
"startDate": "2026-03-01",
"endDate": "2026-03-05",
"reason": "Family trip",
"status": "PENDING"
}

### 2. Update Status (PUT)
**URL:** `http://localhost:8080/api/leave-requests/1/status?status=APPROVED`

---

## ⚙️ Database Access
The project uses an in-memory H2 database. To access the browser interface:
1. Start the application.
2. Go to: http://localhost:8080/h2-console
3. **JDBC URL:** jdbc:h2:mem:elmsdb
4. **User:** sa | **Password:** (blank)

---

## 🏗️ Project Structure
* `elms.entities`: Database models (User, LeaveRequest, LeaveType).
* `elms.repository`: Data access interfaces.
* `elms.service`: Business logic implementation.
* `elms.controller`: REST API Endpoints.
* `elms.exception`: Global error handling logic.