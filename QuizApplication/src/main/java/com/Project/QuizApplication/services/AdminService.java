package com.Project.QuizApplication.services;

import com.Project.QuizApplication.models.Category;
import com.Project.QuizApplication.models.Question;
import com.Project.QuizApplication.models.Quiz;
import com.Project.QuizApplication.models.User;
import com.Project.QuizApplication.repositories.CategoryRepository;
import com.Project.QuizApplication.repositories.QuestionRepository;
import com.Project.QuizApplication.repositories.QuizRepository;
import com.Project.QuizApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import for transaction management

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;

    // ---------- User ----------
    public long getUserCount() {
        return userRepository.count();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Add or update user methods if needed
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        // Add other fields as necessary
        return userRepository.save(user);
    }

    @Transactional // Ensure that deleting a user and its related data is atomic
    public void deleteUser(Long id) {
        // If User has a direct relationship with Quiz/Question (e.g., created by),
        // you might need to handle those relationships here (e.g., set null or delete cascade)
        userRepository.deleteById(id);
    }

    // ---------- Category ----------
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (updatedCategory.getName() == null || updatedCategory.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    @Transactional // Important for cascading deletes if categories own quizzes
    public void deleteCategory(Long id) {
        // If a category has quizzes, Spring Data JPA's @OneToMany(cascade = CascadeType.ALL)
        // or @OneToMany(orphanRemoval = true) on the Category entity would handle deleting associated quizzes.
        // If not, you'd need to explicitly delete quizzes first.
        categoryRepository.deleteById(id);
    }

    // ---------- Quiz ----------
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz addQuiz(Long categoryId, Quiz quiz) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        quiz.setCategory(category);
        if (quiz.getTitle() == null || quiz.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz title cannot be empty.");
        }
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        quiz.setTitle(updatedQuiz.getTitle());
        quiz.setDescription(updatedQuiz.getDescription());
        // If category can be changed, add logic here:
        // if (updatedQuiz.getCategory() != null) {
        //     Category category = categoryRepository.findById(updatedQuiz.getCategory().getId()).orElseThrow();
        //     quiz.setCategory(category);
        // }
        return quizRepository.save(quiz);
    }

    @Transactional // Important for cascading deletes if quizzes own questions
    public void deleteQuiz(Long id) {
        // If a quiz has questions, Spring Data JPA's @OneToMany(cascade = CascadeType.ALL)
        // or @OneToMany(orphanRemoval = true) on the Quiz entity would handle deleting associated questions.
        // If not, you'd need to explicitly delete questions first.
        quizRepository.deleteById(id);
    }

    // ---------- Question ----------
    public Question addQuestion(Long quizId, Question question) {
        // Fetch the quiz by its ID
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
        // Set the quiz to the question
        question.setQuiz(quiz);
        // Save the question
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        // Re-apply validation for updated data
        if (updatedQuestion.getText() == null || updatedQuestion.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty.");
        }
        if (updatedQuestion.getOptionA() == null || updatedQuestion.getOptionA().trim().isEmpty() ||
            updatedQuestion.getOptionB() == null || updatedQuestion.getOptionB().trim().isEmpty() ||
            updatedQuestion.getOptionC() == null || updatedQuestion.getOptionC().trim().isEmpty() ||
            updatedQuestion.getOptionD() == null || updatedQuestion.getOptionD().trim().isEmpty()) {
            throw new IllegalArgumentException("All answer options must be provided.");
        }
        if (updatedQuestion.getCorrectOption() < 1 || updatedQuestion.getCorrectOption() > 4) {
            throw new IllegalArgumentException("Invalid correct option selected. Must be 1, 2, 3, or 4.");
        }

        question.setText(updatedQuestion.getText());
        question.setOptionA(updatedQuestion.getOptionA());
        question.setOptionB(updatedQuestion.getOptionB());
        question.setOptionC(updatedQuestion.getOptionC());
        question.setOptionD(updatedQuestion.getOptionD());
        question.setCorrectOption(updatedQuestion.getCorrectOption());
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}