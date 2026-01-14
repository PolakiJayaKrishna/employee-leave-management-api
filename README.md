# Employee Leave Management API 🧱

A robust RESTful API built with **Spring Boot** and **Java** designed to streamline the employee leave application and approval process. This project focuses on backend architecture, relational data modeling, and automated database persistence.

## 🚀 Tech Stack
* **Java 17** (Amazon Corretto)
* **Spring Boot 3.x** (Framework)
* **Spring Data JPA / Hibernate** (ORM & Persistence)
* **H2 Database** (In-memory development database)
* **Lombok** (Boilerplate reduction)
* **Maven** (Build & Dependency Management)

## 🏗️ Technical Architecture
The project follows a standard N-tier architecture to ensure separation of concerns:

1.  **Entity Layer**: Defined core models (`User`, `LeaveType`, `LeaveRequest`) with JPA annotations and relational mappings.
2.  **Data Access Layer (Repository)**: Leverages `JpaRepository` for seamless CRUD operations and custom query methods.
3.  **Service Layer**: Implements core business logic, including leave application processing and status updates with custom exception handling.

## 📊 Database Schema
The API automatically generates and manages three primary tables:
* **users**: Stores employee/manager details and roles.
* **leave_types**: Master data for leave categories (e.g., Sick, Casual) and their maximum allowed days.
* **leave_requests**: Transactional table connecting users to specific leave requests with status tracking.

## ✨ Key Features
* **Relational Mapping**: Implemented `@ManyToOne` associations to link LeaveRequests to specific Users and LeaveTypes.
* **Enum-based Status Management**: Uses `LeaveStatus` and `Role` enums to ensure data integrity and restricted logic.
* **Robust Error Handling**: Integrated `orElseThrow()` logic within the Service layer to handle missing resources and prevent generic crashes.
* **Automated Schema Management**: Utilizes Hibernate's DDL-auto features for rapid development and testing.

## 🛠️ Getting Started
1.  Clone the repository:
    ```bash
    git clone [https://github.com/PolakiJayaKrishna/employee-leave-management-api.git](https://github.com/PolakiJayaKrishna/employee-leave-management-api.git)
    ```
2.  Navigate to the project directory and build using Maven:
    ```bash
    mvn clean install
    ```
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
4.  Access the H2 Console (while the app is running) at: `http://localhost:8080/h2-console`
    * **JDBC URL**: `jdbc:h2:mem:elmsdb`
    * **User**: `sa` (Password: blank)

## 🚧 Roadmap
* Implementing **REST Controllers** to expose API endpoints.
* Adding **Global Exception Handling** for standardized API responses.
* Integrating **Spring Security** for JWT-based authentication.