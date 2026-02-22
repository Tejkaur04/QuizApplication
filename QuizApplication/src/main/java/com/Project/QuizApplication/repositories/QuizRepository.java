package com.Project.QuizApplication.repositories;

import com.Project.QuizApplication.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
