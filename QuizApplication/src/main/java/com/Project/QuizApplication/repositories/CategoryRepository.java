package com.Project.QuizApplication.repositories;


import com.Project.QuizApplication.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}