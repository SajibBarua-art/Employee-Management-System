# Employee Management System

**Description**: The primary goal of this project is to manage a company's employees and their associations with various entities such as "Todo List", "Designation", "Project", and "Role". Built on the Spring Boot framework, this application serves as the backend for an employee management system, providing essential functionalities for handling employee-related tasks and their relationships within the organization.

---

## Table of Contents
1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Technologies Used](#technologies-used)
5. [Contributing](#contributing)
6. [Contact](#contact)

---

## Features
- Manage employee data and related entities.
- Connect employees to multiple entities such as:
   - **Todo List**: Task management for employees.
   - **Designation**: Employee job roles and responsibilities.
   - **Project**: Employees assigned to various company projects.
   - **Role**: Role-based access control for different functionalities.
- Log in and sign up features are also included.
- Used Spring Boot Security and JWT.
- RESTful API for CRUD operations on employees and their associated data.
- Easy integration with a frontend via well-defined API endpoints.
- All exceptions are handled in a common manner.

Example:
- Employee management with role-based access control.
- Fetch all the Projects of an employee or fetch all the employees based on a designation.
- Integration with PostgreSQL for database management.

---

## Installation

Provide a step-by-step guide to setting up the project locally.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SajibBarua-art/Employee-Management-System
   ```
2. **Navigate to the project directory:**
   ```bash
   cd Employee-Management-System
   ```
3. **Build your project:** (using Maven)
   ```bash
   mvn clean install
   ```
4. **Configure the database:**

   Set up the database connection in **application.properties** (for Spring Boot projects):
   ```
   spring.application.name=Employee-Management-System
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.datasource.url=jdbc:postgresql://localhost:5432/EmployeeManagementSystem
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   ```
5. **Configure the Liquibase:**

   Set up the Liquibase connection in **application.properties**:
   ```
   spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
   spring.liquibase.enabled=true

   spring.jpa.hibernate.ddl-auto=none #No changes is made to the database structures by Hibernate
   ```

   Set up the Liquibase connection in **liquibase.properties**:
   ```
   # Database connection properties
   url=jdbc:postgresql://localhost:5432/EmployeeManagementSystem
   username=your_postgres_username
   password=your_postgres_password
   driver=org.postgresql.Driver

   # Change log file path
   changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml
   ```
6. **Configure the JWT secret key:**

   Add the line in **application.properties**
   ```
   jwt.util.secret.key=your_secret_key
   ```
---

## Usage
1. **Firstly, to create a new employee or sign up, navigate to:**
   ```
   http://localhost:8080/api/public/signup
   ```
   Into the Postman >> body >> raw >> json, pass them:
   ```
   {
       "firstName": "Robin",
       "lastName": "Robin",
       "email": "robin@gmail.com",
       "password": "robin"
   }
2. **Secondly, to log in, navigate to:**
   ```
   http://localhost:8080/api/public/login
   ```
   Into the Postman >> body >> raw >> json, pass them:
   ```
   {
       "email": "robin@gmail.com",
       "password": "robin"
   }
   ```
   After succesfull log in, you will get a **Bearer** token.
3. **To set Bearer token in Postman:**
   Postman >> Authorization >> Auth Type >> Bearer Token.
4. **To role based access:**
   - USER: You can only access API endpoints with the **/api/employees/**
   - ADMIN: To access API endpoints with the **/api/admin/**

   For more Details: [(visit the Postman collection)](https://www.postman.com/ems888-0439/workspace/ems-workspace/collection/36644546-70843a3f-bb01-49cd-80d2-d357ab33acff?action=share&creator=36644546)

---

## Technologies Used
- **Java:** (version 22)
- **Spring Boot:** (version 3.3.1)
- **Spring Security version:** (version 6.3.3)
- **PostgreSQL:** (version 42.7.3)
- **Liquibase:** (version 4.27.0)
- **Maven/Gradle:** Maven

---

## Contributing
- Fork the repository.
- Create a new branch ```(git checkout -b feature-branch)```
- Commit your changes ```(git commit -m 'Add some feature')```
- Push to the branch ```(git push origin feature-branch)```
- Open a pull request.

---

## Contact
For any kinds of suggestions, issues, or contributions:
- Email: sajib715b@gmail.com
- LinkedIn: [Sajib Barua](https://www.linkedin.com/in/sajib-barua-475814203)