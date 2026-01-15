# Employee Leave Management API 🧱

A robust RESTful API built with **Spring Boot** and **Java** designed to streamline the employee leave application and approval process. This project focuses on backend architecture, relational data modeling, and automated database persistence.

## 🏗️ Technical Architecture
The project follows a standard **N-tier architecture** to ensure separation of concerns:
* **Entity Layer**: Core models (`User`, `LeaveType`, `LeaveRequest`) with JPA mappings.
* **Repository Layer**: Data access using `JpaRepository` for efficient database communication.
* **Service Layer**: Business logic implementation, including status transitions and resource validation.

## 🚀 Tech Stack
* **Java 17** (Amazon Corretto)
* **Spring Boot 3.x** (Framework)
* **Spring Data JPA / Hibernate** (ORM & Persistence)
* **H2 Database** (In-memory development database)
* **Lombok** (Boilerplate reduction)
* **Maven** (Build & Dependency Management)

## ✨ Key Features Implemented
* **Relational Mapping**: Configured `@ManyToOne` associations to link `LeaveRequests` with `Users` and `LeaveTypes`.
* **Professional Service Layer**:
    * Implemented **Constructor Injection** using `@RequiredArgsConstructor` for clean, testable code.
    * Integrated **Defensive Programming** using Java’s `Optional` API and `.orElseThrow()` to prevent generic system crashes and prepare for global exception handling.
* **Enum-based Logic**: Strict data integrity using `LeaveStatus` and `Role` enums for status tracking.
* **Schema Resolution**: Successfully debugged Hibernate `SchemaManagementException` by optimizing `@Table` mappings and relational constraints.

## 📊 Database Schema
The API automatically generates and manages three primary tables:
* **users**: Stores employee/manager details and roles.
* **leave_types**: Master data for leave categories (e.g., Sick, Casual).
* **leave_requests**: Transactional table connecting users to specific leave requests with status tracking.

## 🛠️ Getting Started
1.  **Clone the repository**:
    ```bash
    git clone [https://github.com/PolakiJayaKrishna/employee-leave-management-api.git](https://github.com/PolakiJayaKrishna/employee-leave-management-api.git)
    ```
2.  **Build and Run**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
3.  **H2 Console**: Access at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:elmsdb`).

## 🚧 Roadmap
* [ ] **REST Controllers**: Exposing API endpoints for client consumption.
* [ ] **Global Exception Handling**: Standardizing API error responses with a `@ControllerAdvice`.
* [ ] **Spring Security**: Implementing JWT-based authentication for secure access.