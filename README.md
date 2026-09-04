# Sunrise Dental Clinic Management System

A Java Swing desktop application integrated with MySQL and a decoupled REST API service for clinic operations management.

## Key Features
* **Authentication & Authorization:** Role-based access control for clinic personnel.
* **Appointment Management:** Full CRUD capabilities for scheduling patient visits.
* **REST Web Service:** Native HTTP Server on port `8080` providing JSON data endpoints (`/api/appointments/{id}`) without external web frameworks.
* **Decoupled Architecture:** Asynchronous HTTP GET requests in Swing UI using `SwingWorker` threads.
* **Automated Billing:** MySQL stored procedures and triggers for auto-calculating invoices.

## Tech Stack
* **Language:** Java 25
* **GUI Framework:** Java Swing
* **Database:** MySQL Server
* **Build Tool:** Apache Maven
* **Testing:** JUnit 5

## Setup & Running Instructions
1. Import the database schema and dummy data from `database_schema.sql` into MySQL.
2. Update database connection settings in `src/main/java/com/sunrise/dentalclinic/dao/` if needed.
3. Open the project in NetBeans and execute **Clean and Build**.
4. Run `DentalclinicSystem.java` to launch the application and auto-initialize the REST Server on `http://localhost:8080`.
