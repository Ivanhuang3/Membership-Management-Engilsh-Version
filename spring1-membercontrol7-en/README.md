# Spring1-MemberControl7-EN

This project is a member management system based on Spring Boot, implementing RESTful API CRUD (Create, Read, Update, Delete) functionality.

## Project Structure

```
src/main/java/com/example/demo/
├── MemberControl7EnApplication.java - Main application
├── Brad07.java - Member CRUD controller
├── model/
│   ├── Member.java - Member model
│   └── Response.java - API response model
└── utils/
    └── BCrypt.java - Password encryption utility
```

## Database Setup

This project uses MySQL database. Please ensure you have created the `member` database and the following table:

```sql
CREATE TABLE IF NOT EXISTS member (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account VARCHAR(255) NOT NULL UNIQUE,
  passwd VARCHAR(255) NOT NULL,
  realname VARCHAR(255) NOT NULL,
  icon BLOB
);
```

## API Endpoints

### 1. Add Member

```
POST /brad07/member
```

Request body (JSON):
```json
{
  "account": "user1",
  "passwd": "password123",
  "realname": "User One"
}
```

### 2. Delete Member

```
DELETE /brad07/member/{id}
```

### 3. Update Member

```
PUT /brad07/member/{id}
```

Request body (JSON):
```json
{
  "account": "user1updated",
  "realname": "Updated User"
}
```

### 4. Get All Members

```
GET /brad07/members
```

### 5. Get Single Member

```
GET /brad07/member/{id}
```

### 6. Search Members

```
GET /brad07/members/search/{keyword}
```

## Running the Project

1. Ensure MySQL is running and the `member` database and necessary tables are created
2. Run the following command:

```bash
mvn spring-boot:run
```

## Response Format

All API endpoints return a unified JSON format:

```json
{
  "error": 0,      // 0 means success, non-zero means error
  "mesg": "OK",    // Error or success message
  "insertId": 0,   // ID returned by insert operation
  "data": null     // Data returned by query operation
}
``` 