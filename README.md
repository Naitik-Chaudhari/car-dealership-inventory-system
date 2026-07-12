# Car Dealership Inventory System

A RESTful backend application for managing a car dealership inventory with secure authentication, role-based authorization, and inventory management.

The project is built using **Spring Boot**, **Spring Security**, **JWT**, **Spring Data JPA**, and **PostgreSQL**, following the **Test-Driven Development (TDD)** methodology.

---

## Features

### Authentication

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Authentication
- Role-Based Authorization (ADMIN / USER)

---

### Vehicle Management

- Add Vehicle (Admin only)
- View All Vehicles
- Search Vehicles
  - Make
  - Model
  - Category
  - Price Range
- Update Vehicle (Admin only)
- Delete Vehicle (Admin only)

---

### Inventory Management

- Purchase Vehicle
  - Decreases vehicle quantity
  - Prevents purchasing beyond available stock

- Restock Vehicle (Admin only)
  - Increases vehicle quantity

---

### Validation & Error Handling

- Jakarta Bean Validation
- Global Exception Handling
- Standardized API Error Responses

---

### Security

- JWT Authentication
- Stateless Session Management
- Spring Security
- Role-Based Endpoint Authorization

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- JWT (JJWT)
- Maven
- Lombok
- JUnit 5
- Mockito
- MockMvc

---

## Architecture

The application follows a layered architecture.

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
```

---

## Project Structure

```
src
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── repository
├── security
├── service
│   └── impl
├── specification
└── test
```

---

## REST API

### Authentication

| Method | Endpoint | Access |
|---------|----------|--------|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |

---

### Vehicles

| Method | Endpoint | Access |
|---------|----------|--------|
| POST | `/api/vehicles` | ADMIN |
| GET | `/api/vehicles` | Authenticated |
| GET | `/api/vehicles/search` | Authenticated |
| PUT | `/api/vehicles/{id}` | ADMIN |
| DELETE | `/api/vehicles/{id}` | ADMIN |

---

### Inventory

| Method | Endpoint | Access |
|---------|----------|--------|
| POST | `/api/vehicles/{id}/purchase` | Authenticated |
| POST | `/api/vehicles/{id}/restock` | ADMIN |

---

## Search Examples

Search by make

```
GET /api/vehicles/search?make=Toyota
```

Search by model

```
GET /api/vehicles/search?model=Fortuner
```

Search by category

```
GET /api/vehicles/search?category=SUV
```

Search by price range

```
GET /api/vehicles/search?minPrice=1000000&maxPrice=5000000
```

Search using multiple filters

```
GET /api/vehicles/search?make=Toyota&category=SUV
```

---

## Authentication

Protected endpoints require a JWT access token.

```
Authorization: Bearer <JWT_TOKEN>
```

---

## Sample Error Response

```json
{
  "status": 404,
  "message": "Vehicle not found"
}
```

---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/Naitik-Chaudhari/car-dealership-inventory-system.git
```

### Configure PostgreSQL

Update the database configuration in `application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/car_dealership
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
jwt.expiration=86400000
```

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## Testing

Run all tests

```bash
mvn test
```

The project includes:

- Unit Tests
- Controller Tests
- JWT Tests
- Security Tests
- Validation Tests

---

## Features Completed

- ✅ User Registration
- ✅ User Login
- ✅ JWT Authentication
- ✅ Role-Based Authorization
- ✅ Add Vehicle
- ✅ View All Vehicles
- ✅ Search Vehicles
- ✅ Update Vehicle
- ✅ Delete Vehicle
- ✅ Purchase Vehicle
- ✅ Restock Vehicle
- ✅ Global Exception Handling
- ✅ Request Validation
- ✅ Comprehensive Unit & Controller Tests

---

## Future Enhancements

The following features are planned for future development:

- React Frontend
- Swagger / OpenAPI Documentation
- Docker Support
- Pagination & Sorting
- Refresh Token Authentication
- Vehicle Image Upload
- Order History
- CI/CD Pipeline

---

## Development Methodology

This project was developed using the **Test-Driven Development (TDD)** approach.

For each feature:

1. Write a failing test (Red)
2. Implement the minimum code to pass (Green)
3. Refactor while keeping all tests green (Refactor)

---

## Author

**Naitik Chaudhari**

- GitHub: https://github.com/Naitik-Chaudhari
