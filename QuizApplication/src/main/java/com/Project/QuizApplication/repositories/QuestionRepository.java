package com.Project.QuizApplication.repositories;

import com.Project.QuizApplication.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Extra helper if needed
    List<Question> findByQuizId(Long quizId);
    @Query("SELECT q FROM Question q")
    List<Question> getAllQuestions();
}
