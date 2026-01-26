# Employee Leave Management System (ELMS)

A backend application built with Spring Boot to manage employee leave applications, approvals, rejections, and updates efficiently.

---

## 📊 Database Schema

The API automatically generates and manages three primary tables using Hibernate:

- users: Stores employee/manager details and roles
- leave_types: Master data for leave categories (e.g., Sick, Casual)
- leave_requests: Transactional table connecting users to leave requests with status tracking

---

## 🛠️ Getting Started

### 1. Clone the Repository

git clone https://github.com/PolakiJayaKrishna/employee-leave-management-api.git

---

## 🚀 Features

- Submit Leave Applications: Employees can submit leave requests with start/end dates and reasons
- Edit Leave Applications: Employees can update dates, reason, or leave type before approval
- Approve / Reject Requests: Managers can update the status of any leave request
- View All Requests: Retrieve all submitted leave records
- Employee History: View leave history by User ID
- Error Handling: Centralized exception handling with clean JSON responses

---

## 🛠️ Tech Stack

- Java 17 (Corretto)
- Spring Boot 4.0.1
- Spring Data JPA (Hibernate)
- H2 Database (In-memory)
- Lombok

---

## 📡 API Endpoints

Method | Endpoint                          | Description
-------|-----------------------------------|--------------------------------------------
POST   | /api/leave-requests/apply         | Submit a new leave request
GET    | /api/leave-requests/all           | View all leave requests in the system
GET    | /api/leave-requests/user/{id}     | View leave history for a specific employee
PUT    | /api/leave-requests/{id}          | Edit an existing leave request
PUT    | /api/leave-requests/{id}/status   | Update leave status (approve/reject)

---

## 🧪 Testing with Postman

### 1. Apply for Leave (POST)

URL:
http://localhost:8080/api/leave-requests/apply

Body (raw JSON):

{
"user": { "id": 1 },
"leaveType": { "id": 1 },
"startDate": "2026-03-01",
"endDate": "2026-03-05",
"reason": "Family trip",
"status": "PENDING"
}

---

### 2. Edit Leave Request (PUT)

URL:
http://localhost:8080/api/leave-requests/1

Body (raw JSON):

{
"startDate": "2026-04-10",
"endDate": "2026-04-12",
"reason": "Updated: Trip rescheduled",
"leaveType": { "id": 1 }
}

---

### 3. View User History (GET)

URL:
http://localhost:8080/api/leave-requests/user/1

Returns all leave requests belonging to the user with ID 1.

---

### 4. Update Status (PUT)

URL:
http://localhost:8080/api/leave-requests/1/status?status=APPROVED

---

## ⚙️ Database Access (H2 Console)

The project uses an in-memory H2 database.

Steps:
1. Start the application
2. Open http://localhost:8080/h2-console
3. Use the following credentials:

JDBC URL: jdbc:h2:mem:elmsdb  
Username: sa  
Password: (blank)

---

## 🏗️ Project Structure

elms.entities   : Database models (User, LeaveRequest, LeaveType)  
elms.repository : Data access interfaces  
elms.service    : Business logic implementation  
elms.controller : REST API endpoints  
elms.exception  : Global error handling using @RestControllerAdvice

---

## 🚧 Roadmap

- REST Controllers for leave workflows (DONE)
- Global exception handling using @ControllerAdvice (DONE)
- Edit/update leave requests (DONE)
- JWT-based authentication with Spring Security (PLANNED)
- Role-based access control: EMPLOYEE / MANAGER / ADMIN (PLANNED)
