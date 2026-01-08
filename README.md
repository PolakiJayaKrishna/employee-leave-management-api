# Employee Leave Management System (ELMS) 🧱

A Spring Boot application built to manage employee leave requests, approvals, and leave balances.

## 🚀 Progress: Day 1 — Foundation Completed
- Created the core package structure following standard Spring Boot architecture.
- Implemented **Enums** for fixed states: `Role` (ADMIN, MANAGER, EMPLOYEE) and `LeaveStatus`.
- Built the **Core Entities**:
    - `User`: Handles system authentication and roles.
    - `LeaveType`: Master data for defining leave rules (Sick, Casual, etc.).
- Configured **H2 In-Memory Database** for local development.
- Verified database schema generation via Hibernate.

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**

## 🏗️ How to Run
1. Clone the repository.
2. Run `./mvnw spring-boot:run` or start the application from your IDE.
3. The H2 Console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:elmsdb`).

## 🧱 Data Model (Current)
- **User**: [id, name, email, password, role, createdAt]
- **LeaveType**: [id, name, maxDays]