# 🧠 Quiz Application --- Spring Boot

A full-stack **Quiz Management System** built using **Spring Boot** that
lets users attempt quizzes while admins manage questions, categories,
and quiz content.

Built to understand how real backend systems handle authentication,
database persistence, and layered architecture.

------------------------------------------------------------------------

## ✨ What This Project Does

### 👤 Users

-   Register & login
-   Attempt quizzes
-   Get instant scores
-   Browse category-based quizzes

### 🛠 Admin

-   Create quiz categories
-   Add / edit / delete questions
-   Manage quizzes dynamically

------------------------------------------------------------------------

## ⚡ Tech Stack

  Layer          Tech
  -------------- ----------------------------
  Backend        Spring Boot, Java
  Architecture   MVC Pattern
  ORM            Spring Data JPA, Hibernate
  Database       MySQL
  Frontend       Thymeleaf, HTML, CSS
  Build Tool     Maven

------------------------------------------------------------------------

## 🧩 Core Features

-   Authentication system
-   Role-based access (Admin/User)
-   Dynamic quiz generation
-   Automatic answer evaluation
-   Persistent database storage
-   Clean layered backend design

------------------------------------------------------------------------

## 🏗️ Architecture (Simple View)

    Client → Controller → Service → Repository → Database

-   **Controller** → handles requests\
-   **Service** → business logic\
-   **Repository** → database operations\
-   **Database** → stores users, quizzes, questions

------------------------------------------------------------------------

## 📂 Project Structure

    src/main/java
    │
    ├── controller      → Request handling
    ├── service         → Business logic
    ├── repository      → JPA repositories
    ├── model           → Entities
    └── QuizApplication → Entry point

------------------------------------------------------------------------

## 🚀 Run Locally

### 1️⃣ Clone Repository

``` bash
git clone https://github.com/<your-username>/QuizApplication.git
cd QuizApplication
```

### 2️⃣ Create Database

``` sql
CREATE DATABASE quiz_db;
```

### 3️⃣ Configure Database

Update inside:

    src/main/resources/application.properties

``` properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4️⃣ Start Application

``` bash
mvn spring-boot:run
```

Open in browser:

    http://localhost:8080

------------------------------------------------------------------------

## 🧠 What I Learned

-   Designing backend systems using Spring Boot
-   MVC architecture in real applications
-   Database modeling with JPA
-   Authentication flow implementation
-   Writing maintainable layered code

------------------------------------------------------------------------

## 🔮 Future Improvements

-   JWT Authentication
-   Quiz timer
-   Leaderboard system
-   REST API version
-   Cloud deployment
