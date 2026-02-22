package com.Project.QuizApplication.repositories;

import com.Project.QuizApplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query for login
    User findByEmailAndPassword(String email, String password);
}