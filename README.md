# 🛒 Spring Boot Store2  

A **Spring Boot–based backend for an eCommerce platform**.  
This project provides the core server-side functionality including database entities, services, controllers, and security configuration.  
All APIs were built and successfully tested using **Postman**.

---

## 🚀 Project Overview  
**Spring Boot Store2** is a backend REST API application designed for an eCommerce system.  
It manages essential operations like handling products, users, orders, and authentication.  
The project follows a clean, modular structure using the layered architecture approach — **Controller → Service → Repository**.

---

## 🧩 Features  
- ✅ RESTful API endpoints  
- ✅ Secure authentication and authorization  
- ✅ Entity and table creation using JPA/Hibernate  
- ✅ Service layer for business logic  
- ✅ Controller layer for API handling  
- ✅ Successfully tested using **Postman**  
- ✅ Maven project structure for easy setup and deployment  

---

## 🛠️ Technologies Used  
- **Java** (JDK 17 or above)  
- **Spring Boot** (REST + Security + JPA)  
- **Spring Data JPA**  
- **Hibernate**  
- **MySQL / H2 Database**  
- **Postman** (for testing APIs)  
- **Maven** (for dependency management)  

---

## 📁 Project Structure  
spring-boot-store2
├── src
│ ├── main
│ │ ├── java/com/example/store2
│ │ │ ├── controller/ # API endpoints
│ │ │ ├── service/ # Business logic
│ │ │ ├── repository/ # Database interaction
│ │ │ ├── model/ # Entities and tables
│ │ │ └── security/ # Authentication and Authorization
│ └── resources
│ ├── application.properties
│ └── ...
├── pom.xml
└── README.md


---

## ⚙️ How to Run  

### Prerequisites  
- Java 17+  
- Maven 3.x  
- MySQL (if using an external DB)

### Steps  
1. Clone the repository:  
   ```bash
   git clone https://github.com/katherakarthik/spring-boot-store2.git
   cd spring-boot-store2
2. Configure database in application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/storedb
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
3. Run the Project
   mvn spring-boot:run
4. Access the APIs at:
http://localhost:8080/

🧪 API Testing (Postman)

All endpoints have been tested successfully in Postman.
Examples:

POST /api/auth/register – Register a user

POST /api/auth/login – Login and get JWT token

GET /api/products – View all products

POST /api/products – Add new product

GET /api/orders – View orders

🤝 Contributing

If you’d like to improve this project:

Fork the repo

Create a feature branch

Commit and push your changes

Open a Pull Request

👨‍💻 Author

Kathera Karthik
