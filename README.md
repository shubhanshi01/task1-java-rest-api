# Task 1: Java Backend REST API

> **Kaiburr Assessment - Task 1**  
> **Author:**Shubhanshi Gupta  
> **Date:**10:30 

## Overview

This project implements a REST API in Java that provides endpoints for searching, creating, deleting and running "task" objects. Task objects represent shell commands that can be run in a kubernetes pod.

## Requirements Implemented

- [x] REST API with endpoints for task CRUD operations
- [x] Task objects stored in MongoDB database  
- [x] Command validation for security
- [x] Task execution with output storage
- [x] All endpoints tested with Postman/curl

##  Tech Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

- **Java 17**
- **Spring Boot 3.5.6**  
- **Spring Data MongoDB**
- **Maven**
- **IntelliJ IDEA**


### Prerequisites
```bash
Java 17+
Maven 3.6+
 Docker (for MongoDB)
```

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/[your-username]/task1-java-rest-api.git
   cd task1-java-rest-api
   ```

2. **Start MongoDB**
   ```bash
   docker run -d --name mongodb -p 27017:27017 mongo:latest
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verify**
   ```bash
   curl http://localhost:8989/api/tasks
   # Should return: []
   ```

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks?id={id}` | Get task by ID |
| `PUT` | `/api/tasks` | Create/update task |
| `DELETE` | `/api/tasks/{id}` | Delete task |
| `GET` | `/api/tasks/search?name={name}` | Search tasks by name |
| `PUT` | `/api/tasks/{id}/execute` | Execute task command |



##  Screenshots
<img width="272" height="553" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/6bd01654-a07b-4b8b-b43d-42bf2563c74a" />
<img width="327" height="175" alt="Shubhanshi01" src="https://github.com/user-attachments/assets/1630afe6-a966-4ec9-ae8b-401c8de0733d" />
<img width="882" height="583" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/3ed86f6b-7508-4859-9676-f3995a7732c4" />
<img width="1002" height="620" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/99846e9b-adfd-4c06-a9d1-33eaa104fe78" />
<img width="896" height="515" alt="Shubhanshi 2025-09-28 155340 4" src="https://github.com/user-attachments/assets/68acca87-613b-4a3e-a1d3-39ed5d22bc9b" />
<img width="762" height="241" alt="Shubhanshi 2025-09-28 1553405" src="https://github.com/user-attachments/assets/afb26cfb-642f-411d-bac0-b5afb17a7120" />
<img width="962" height="528" alt="Shubhanshi 2025-09-28 1553402" src="https://github.com/user-attachments/assets/352ac2c6-e385-430b-bbd8-d59e936de8b1" />
<img width="902" height="507" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/9094cf6f-e569-46a1-a8d7-a7b122d01a30" />
<img width="785" height="230" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/d72de431-1e17-4d8c-9074-9b32f2290da1" />
<img width="327" height="175" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/2287a6f3-de68-46ca-8ab2-75fee22e9982" />
<img width="896" height="515" alt="Shubhanshi 2025-09-28 155340" src="https://github.com/user-attachments/assets/d2f5324f-6ec3-4c70-92fc-353e66d262f4" />

### Project Setup

**Screenshot 1: IntelliJ Project Structure**


**Screenshot 2: Application Startup**
![Application Startup](screenshots/02_application_startup.png) 
*Spring Boot console showing successful startup on port 8989 with Shubhanshi visible*

---

###  API Testing


## Security Features

-  **Command Validation** - Prevents unsafe commands (`rm`, `del`, `format`, etc.)
-  **Input Sanitization** - Cleans and validates all inputs  
-  **Timeout Protection** - 30-second limit on command execution
-  **Error Handling** - Comprehensive error responses

## ⚙️ Configuration

```properties
# Server Configuration
server.port=8989

# MongoDB Configuration  
spring.data.mongodb.uri=mongodb://localhost:27017/task-management

# Logging
logging.level.com.example=DEBUG
```

## 🧪 Testing

### Postman Collection
All endpoints tested with comprehensive request/response validation.

### curl Examples
```bash
# Get all tasks
curl http://localhost:8989/api/tasks

# Create task
curl -X PUT "http://localhost:8989/api/tasks" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","owner":"User","command":"echo test"}'

# Execute task  
curl -X PUT "http://localhost:8989/api/tasks/{id}/execute"
```

This project is part of the Kaiburr assessment and is for evaluation purposes.

---

<div align="center">

**✅ All requirements successfully implemented and tested ✅**

*Task 1 Complete - Shubhanshi - [28-09-2025]*

</div>
