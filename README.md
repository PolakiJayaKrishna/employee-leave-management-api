# 🌴 Employee Leave Management System (ELMS)

A robust Spring Boot backend application designed to streamline employee leave requests, manager approvals, and automated leave balance tracking.

---

## 🚀 Key Features

- Smart Leave Requesting: Automatically calculates leave duration in days using ChronoUnit and validates date ranges
- Automated Balance Management: Real-time deduction of leave days from the employee leave bank upon manager approval
- Transaction Integrity: Uses @Transactional to ensure leave status updates and balance deductions happen atomically
- Security-First DTOs: Data Transfer Objects prevent sensitive information like passwords from being exposed in API responses
- Role-Based Logic:
    - Employees: Apply for leave, view leave history, and check remaining balance
    - Managers: Approve/reject requests, filter by status, and monitor leave data

---

## 🛠️ Tech Stack

- Backend: Java 17+, Spring Boot, Spring Data JPA
- Database: MySQL
- Tools: Lombok, Maven, Postman

---

## 📊 Database Logic & Business Rules

### Leave Balance Workflow

1. Application: Employee requests X days. System checks if X ≤ Remaining Balance
2. Pending: Status set to PENDING. No deduction yet
3. Approval: Manager approves request  
   Formula: New Balance = Old Balance - Duration
4. Validation: Approval is blocked if balance would become negative

---

## 🛣️ API Endpoints

### Leave Requests

| Method | Endpoint                          | Description                                      |
|--------|-----------------------------------|--------------------------------------------------|
| POST   | /api/leave-requests/apply         | Submit a new leave request                       |
| GET    | /api/leave-requests/all           | View all requests (Admin/Manager)                |
| PUT    | /api/leave-requests/{id}/status   | Approve or reject a leave request                |
| GET    | /api/leave-requests/user/{userId} | Get leave history and balance for a specific user|

---

## 🚦 Getting Started

1. Clone the project

git clone https://github.com/your-username/elms-backend.git

2. Configure database credentials

Update src/main/resources/application.properties with your DB username and password.

3. Run the application

mvn spring-boot:run

---

## 🧠 Why This Project Stands Out

- Demonstrates real business logic beyond CRUD
- Implements transactional safety and consistency
- Shows understanding of backend security principles using DTOs
- Follows layered architecture (Controller → Service → Repository → DB)

---

## 📌 Future Enhancements

- JWT Authentication & Authorization
- Role-based access using Spring Security
- Email notifications for approvals/rejections
- Leave analytics dashboard
- Admin panel for leave type management

---
