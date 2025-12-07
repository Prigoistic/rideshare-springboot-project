# 🚗 RideShare Backend - Complete Mini Project

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen?style=for-the-badge&logo=springboot)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green?style=for-the-badge&logo=mongodb)
![JWT](https://img.shields.io/badge/JWT-Auth-blue?style=for-the-badge&logo=jsonwebtokens)

**A production-ready ride-sharing backend with JWT authentication, MongoDB, and clean architecture**

[Features](#-features) • [Quick Start](#-quick-start) • [API Docs](#-api-endpoints) • [Architecture](#-architecture-diagram)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture-diagram)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Quick Start](#-quick-start)
- [API Endpoints](#-api-endpoints)
- [Testing Guide](#-testing-guide)
- [Configuration](#-configuration)
- [Requirements Checklist](#-requirements-checklist)

---

## 🎯 Overview

A complete ride-sharing backend application built with Spring Boot, MongoDB, and JWT authentication. This project implements clean architecture principles with proper separation of concerns, input validation, and comprehensive error handling.

### What This Project Does

- 👥 **User Management**: Register and authenticate users (Passengers & Drivers)
- 🚗 **Ride Requests**: Passengers can request rides
- 🚕 **Ride Acceptance**: Drivers can view and accept ride requests
- ✅ **Ride Completion**: Both parties can mark rides as completed
- 📊 **Ride History**: Users can view their past rides
- 🔒 **Security**: JWT-based authentication with role-based access control

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🔐 Authentication & Security
- ✅ User registration with role selection
- ✅ JWT token-based authentication
- ✅ BCrypt password encryption
- ✅ Role-based authorization (USER/DRIVER)
- ✅ Secure token validation

</td>
<td width="50%">

### 🎯 Core Functionality
- ✅ Create ride requests
- ✅ View available rides (Driver)
- ✅ Accept rides (Driver)
- ✅ Complete rides
- ✅ View ride history

</td>
</tr>
<tr>
<td width="50%">

### 🛡️ Quality & Standards
- ✅ Input validation on all endpoints
- ✅ Global exception handling
- ✅ Clean architecture pattern
- ✅ Proper DTOs for all requests
- ✅ Meaningful error messages

</td>
<td width="50%">

### 📦 Production Ready
- ✅ Zero compilation errors
- ✅ MongoDB Atlas integration
- ✅ Comprehensive API documentation
- ✅ Ready for deployment
- ✅ Tested and working

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

| Technology | Purpose | Version |
|------------|---------|---------|
| **Java** | Programming Language | 17 |
| **Spring Boot** | Application Framework | 4.0.0 |
| **Spring Security** | Authentication & Authorization | 6.x |
| **MongoDB** | NoSQL Database | Atlas |
| **JJWT** | JWT Token Management | 0.11.5 |
| **Lombok** | Reduce Boilerplate | Latest |
| **Jakarta Validation** | Request Validation | 3.x |
| **Maven** | Build Tool | 3.x |

---

## 🏗️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                         CLIENT                              │
│               (Postman / cURL / Frontend)                   │
└─────────────────────┬───────────────────────────────────────┘
                      │ HTTP + JWT Token
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    SPRING SECURITY                          │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  JwtFilter → Validates Token → Authentication         │ │
│  └───────────────────────────────────────────────────────┘ │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                      CONTROLLERS                            │
│  ┌──────────────────┐        ┌──────────────────────────┐  │
│  │ AuthController   │        │   RideController         │  │
│  │  /api/auth/**    │        │   /api/v1/**             │  │
│  └──────────────────┘        └──────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │ @Valid DTOs
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                       SERVICES                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │   RideService - Business Logic & Validation          │  │
│  │   CustomUserDetailsService - User Authentication     │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    REPOSITORIES                             │
│  ┌──────────────────┐        ┌──────────────────────────┐  │
│  │ UserRepository   │        │   RideRepository         │  │
│  │ (MongoRepository)│        │   (MongoRepository)      │  │
│  └──────────────────┘        └──────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    MONGODB ATLAS                            │
│  ┌──────────────────┐        ┌──────────────────────────┐  │
│  │  users           │        │   rides                  │  │
│  └──────────────────┘        └──────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Flow Description

1. **Client** sends HTTP request with JWT token
2. **JwtFilter** validates token and sets authentication context
3. **Controller** receives request, validates with `@Valid`
4. **Service** executes business logic and authorization checks
5. **Repository** performs database operations
6. **MongoDB** stores/retrieves data
7. **Response** flows back through the layers

---

## 📁 Project Structure

```
src/main/java/org/example/rideshare/
│
├── 📱 RideshareApplication.java    # Main entry point
├── 📊 RideStatus.java              # Enum: REQUESTED, ACCEPTED, COMPLETED
│
├── ⚙️ config/                      # Configuration & Security
│   ├── SecurityConfig.java         # Spring Security + JWT setup
│   ├── JwtFilter.java              # Token validation filter
│   ├── PasswordConfig.java         # BCrypt encoder
│   └── EndpointLogger.java         # Request logging
│
├── 🎮 controller/                  # REST API Endpoints
│   ├── AuthController.java         # /api/auth/** (register, login)
│   └── RideController.java         # /api/v1/** (ride operations)
│
├── 📦 dto/                         # Data Transfer Objects
│   ├── RegisterRequest.java        # User registration payload
│   ├── AuthRequest.java            # Login payload
│   ├── AuthResponse.java           # JWT token response
│   ├── CreateRideRequest.java      # Ride creation payload
│   ├── RideResponse.java           # Ride response DTO
│   └── ErrorResponse.java          # Error response structure
│
├── ⚠️ exception/                   # Exception Handling
│   ├── GlobalExceptionHandler.java # @ControllerAdvice handler
│   ├── NotFoundException.java      # 404 errors
│   ├── BadRequestException.java    # 400 errors
│   └── UnauthorizedException.java  # 401 errors
│
├── 🗄️ model/                       # Domain Entities
│   ├── User.java                   # User entity (@Document)
│   ├── Ride.java                   # Ride entity (@Document)
│   └── Role.java                   # Enum: ROLE_USER, ROLE_DRIVER
│
├── 🔌 repository/                  # Data Access Layer
│   ├── UserRepository.java         # User CRUD + findByUsername
│   └── RideRepository.java         # Ride CRUD + custom queries
│
├── 🎯 service/                     # Business Logic
│   ├── RideService.java            # Ride business logic
│   └── CustomUserDetailsService.java # Spring Security integration
│
└── 🔧 util/                        # Utilities
    └── JwtUtil.java                # JWT token generation/validation

src/main/resources/
└── application.properties          # Application configuration
```

---

## 🗄️ Database Schema

### Entity Relationship Diagram

```
┌─────────────────────┐                    ┌─────────────────────┐
│       USER          │                    │       USER          │
│   (ROLE_USER)       │                    │   (ROLE_DRIVER)     │
└──────────┬──────────┘                    └──────────┬──────────┘
           │                                          │
           │ requests                                 │ accepts
           │                                          │
           ▼                                          ▼
    ┌──────────────────────────────────────────────────────┐
    │                      RIDE                            │
    ├──────────────────────────────────────────────────────┤
    │  id: String (auto-generated)                         │
    │  userId: String (FK → User.id)                       │
    │  driverId: String (FK → User.id) [nullable]          │
    │  pickupLocation: String                              │
    │  dropLocation: String                                │
    │  status: REQUESTED | ACCEPTED | COMPLETED            │
    │  createdAt: Date                                     │
    └──────────────────────────────────────────────────────┘
```

### MongoDB Collections

#### users Collection
```json
{
  "_id": "657abc123...",
  "username": "john_user",
  "password": "$2a$10$...",  // BCrypt hashed
  "role": "ROLE_USER"  // or "ROLE_DRIVER"
}
```

#### rides Collection
```json
{
  "_id": "657def456...",
  "userId": "657abc123...",
  "driverId": "657ghi789...",  // null initially
  "pickupLocation": "Downtown, Bangalore",
  "dropLocation": "Airport",
  "status": "ACCEPTED",  // REQUESTED → ACCEPTED → COMPLETED
  "createdAt": "2025-12-07T10:30:00.000Z"
}
```

### Ride Status Flow
```
REQUESTED → ACCEPTED → COMPLETED
    ↑           ↑           ↑
    │           │           │
User creates  Driver    User/Driver
  ride      accepts     completes
```

---

## 🚀 Quick Start

### Prerequisites

- ✅ Java 17 or higher
- ✅ Maven 3.x
- ✅ MongoDB Atlas account (free tier works)
- ✅ Postman or cURL for testing

### Setup Steps

#### 1️⃣ Clone & Navigate
```bash
cd rideshare-backend
```

#### 2️⃣ Configure Environment
Create `.env.properties` in project root:

```properties
SERVER_PORT=8081
SPRING_DATA_MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/rideshare
JWT_SECRET=ridesharebackendsupersecretkeyforjwttokensigning2025secureandverylongenough
JWT_EXPIRATION=86400000
```

> ⚠️ **Important**: JWT_SECRET must be at least 32 characters (256 bits) with alphanumeric characters only

#### 3️⃣ Build Project
```bash
./mvnw clean package
```

#### 4️⃣ Run Application
```bash
./mvnw spring-boot:run
```

#### 5️⃣ Verify Setup
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"pass123","role":"ROLE_USER"}'
```

✅ **Success!** You should see: `{"message":"User registered successfully","username":"testuser"}`

---

## 🔗 API Endpoints

### 📌 Authentication Endpoints (Public)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `POST` | `/api/auth/register` | Register new user | `{username, password, role}` |
| `POST` | `/api/auth/login` | Login & get JWT | `{username, password}` |

### 📌 User Endpoints (ROLE_USER Required)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/v1/rides` | Create ride request | Bearer Token |
| `GET` | `/api/v1/user/rides` | View my ride history | Bearer Token |
| `POST` | `/api/v1/rides/{id}/complete` | Complete ride | Bearer Token |

### 📌 Driver Endpoints (ROLE_DRIVER Required)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/v1/driver/rides/requests` | View pending rides | Bearer Token |
| `POST` | `/api/v1/driver/rides/{id}/accept` | Accept a ride | Bearer Token |
| `POST` | `/api/v1/rides/{id}/complete` | Complete ride | Bearer Token |

---

## 🧪 Testing Guide

### Complete Test Flow

```bash
# ========================================
# 1. REGISTER USER (Passenger)
# ========================================
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "password": "pass123",
    "role": "ROLE_USER"
  }'

# Response: {"message":"User registered successfully","username":"alice"}

# ========================================
# 2. REGISTER DRIVER
# ========================================
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bob_driver",
    "password": "pass123",
    "role": "ROLE_DRIVER"
  }'

# ========================================
# 3. LOGIN AS USER
# ========================================
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "password": "pass123"
  }'

# Response: {"token":"eyJhbGciOiJIUzI1NiJ9..."}
# ⭐ SAVE THIS TOKEN AS USER_TOKEN

# ========================================
# 4. CREATE RIDE (as User)
# ========================================
curl -X POST http://localhost:8081/api/v1/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer USER_TOKEN" \
  -d '{
    "pickupLocation": "Koramangala, Bangalore",
    "dropLocation": "Indiranagar"
  }'

# Response: {"id":"657...","status":"REQUESTED",...}
# ⭐ SAVE RIDE ID

# ========================================
# 5. LOGIN AS DRIVER
# ========================================
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bob_driver",
    "password": "pass123"
  }'

# ⭐ SAVE THIS TOKEN AS DRIVER_TOKEN

# ========================================
# 6. VIEW PENDING RIDES (as Driver)
# ========================================
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
  -H "Authorization: Bearer DRIVER_TOKEN"

# Response: [{"id":"657...","status":"REQUESTED",...}]

# ========================================
# 7. ACCEPT RIDE (as Driver)
# ========================================
curl -X POST http://localhost:8081/api/v1/driver/rides/RIDE_ID/accept \
  -H "Authorization: Bearer DRIVER_TOKEN"

# Response: {"id":"657...","status":"ACCEPTED","driverId":"658...",...}

# ========================================
# 8. COMPLETE RIDE (as User or Driver)
# ========================================
curl -X POST http://localhost:8081/api/v1/rides/RIDE_ID/complete \
  -H "Authorization: Bearer USER_TOKEN"

# Response: {"id":"657...","status":"COMPLETED",...}

# ========================================
# 9. VIEW RIDE HISTORY (as User)
# ========================================
curl -X GET http://localhost:8081/api/v1/user/rides \
  -H "Authorization: Bearer USER_TOKEN"

# Response: [{"id":"657...","status":"COMPLETED",...}]
```

### Example Requests & Responses

<details>
<summary><b>Click to expand detailed examples</b></summary>

#### Register User
**Request:**
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

**Response (200 OK):**
```json
{
  "message": "User registered successfully",
  "username": "john"
}
```

#### Login
**Request:**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "1234"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoiam9obiIsImlhdCI6MTczNTk5OTk5OSwiZXhwIjoxNzM2MDg2Mzk5fQ..."
}
```

#### Create Ride
**Request:**
```bash
POST /api/v1/rides
Authorization: Bearer <token>
Content-Type: application/json

{
  "pickupLocation": "Downtown",
  "dropLocation": "Airport"
}
```

**Response (200 OK):**
```json
{
  "id": "657abc123def456",
  "userId": "657user123",
  "driverId": null,
  "pickupLocation": "Downtown",
  "dropLocation": "Airport",
  "status": "REQUESTED",
  "createdAt": "2025-12-07T10:30:00.000Z"
}
```

#### Error Response Example
**Response (400 BAD_REQUEST):**
```json
{
  "error": "VALIDATION_ERROR",
  "message": "{pickupLocation=Pickup location is required}",
  "timestamp": "2025-12-07T10:30:00.000Z"
}
```

</details>

---

## ⚙️ Configuration

### Environment Variables

Create `.env.properties` in project root:

```properties
# Server Configuration
SERVER_PORT=8081

# MongoDB Configuration
SPRING_DATA_MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/rideshare?retryWrites=true&w=majority

# JWT Configuration
JWT_SECRET=ridesharebackendsupersecretkeyforjwttokensigning2025secureandverylongenough
JWT_EXPIRATION=86400000
```

### Configuration Details

| Property | Description | Required | Example |
|----------|-------------|----------|---------|
| `SERVER_PORT` | Application port | No (default: 8080) | `8081` |
| `SPRING_DATA_MONGODB_URI` | MongoDB connection string | Yes | `mongodb+srv://...` |
| `JWT_SECRET` | Secret key for JWT signing | Yes | Min 32 chars, alphanumeric |
| `JWT_EXPIRATION` | Token expiry in milliseconds | No (default: 24h) | `86400000` (24 hours) |

### MongoDB Setup

1. Create free account at [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Create a new cluster
3. Add database user
4. Whitelist your IP (or use `0.0.0.0/0` for testing)
5. Get connection string and update `.env.properties`

---

## ✅ Requirements Checklist

### Assignment Requirements

- [x] ✅ **Complete functioning API** - All endpoints working
- [x] ✅ **Proper folder structure** - Follows clean architecture
- [x] ✅ **DTOs + Validation** - All requests validated
- [x] ✅ **Exception Handling** - Global handler with proper errors
- [x] ✅ **JWT Auth implemented correctly** - Token-based security
- [x] ✅ **README explaining endpoints** - This document
- [x] ✅ **Postman collection** - cURL commands provided

### Student Requirements Mapping

| Requirement | Implementation | Status |
|-------------|----------------|--------|
| User Registration + Login (JWT) | `AuthController` with BCrypt & JWT | ✅ Done |
| Request a Ride (Passenger) | `POST /api/v1/rides` | ✅ Done |
| Driver View Pending Requests | `GET /api/v1/driver/rides/requests` | ✅ Done |
| Driver Accepts Ride | `POST /api/v1/driver/rides/{id}/accept` | ✅ Done |
| Complete Ride | `POST /api/v1/rides/{id}/complete` | ✅ Done |
| User Gets Own Rides | `GET /api/v1/user/rides` | ✅ Done |
| Input Validation | Jakarta `@Valid` on all DTOs | ✅ Done |
| Exception Handling | `GlobalExceptionHandler` with custom exceptions | ✅ Done |
| JWT in Headers | `Authorization: Bearer <token>` | ✅ Done |
| Clean Architecture | Controller → Service → Repository | ✅ Done |

---

## 🎓 Key Implementation Details

### Input Validation
```java
public class CreateRideRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;
    
    @NotBlank(message = "Drop location is required")
    private String dropLocation;
}
```

### JWT Flow
```
1. User logs in → Receive JWT token
2. Store token in client
3. Send token in header: Authorization: Bearer <token>
4. JwtFilter validates token on every request
5. Extract username and role from token
6. Set Spring Security context
7. @PreAuthorize checks role
```

### Clean Architecture
```
Controller (HTTP Layer)
    ↓
Service (Business Logic)
    ↓
Repository (Data Access)
    ↓
MongoDB (Persistence)
```

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| ❌ "The signing key's size is X bits..." | JWT_SECRET must be ≥32 chars, alphanumeric only |
| ❌ "Authentication failed" | Check MongoDB URI in `.env.properties` |
| ❌ "Access Denied" / 403 | Verify user has correct role (USER/DRIVER) |
| ❌ "Invalid JWT" / 401 | Token expired or invalid - login again |
| ❌ Port 8081 already in use | Change `SERVER_PORT` or kill process: `pkill -f spring-boot` |
| ❌ MongoDB connection timeout | Check network, whitelist IP in Atlas |

---

## 📈 Future Enhancements

- 🔄 Real-time location tracking with WebSocket
- 💳 Payment gateway integration
- ⭐ Rating & review system
- 📱 Mobile app integration
- 🗺️ Google Maps API integration
- 💬 In-app messaging
- 📊 Admin dashboard
- 📧 Email notifications

---

## 📄 License

This project is created for educational purposes.

---

## 👨‍💻 Author

**RideShare Backend Project**  
Complete implementation with all student requirements fulfilled.

---

## 🙏 Acknowledgments

Built with:
- Spring Boot framework
- MongoDB Atlas
- JWT for authentication
- Clean architecture principles

---

<div align="center">

### 🎉 Project Complete & Production Ready!

**All requirements fulfilled • Clean code • Fully documented • Tested & Working**

Made with ❤️ using Spring Boot

</div>
