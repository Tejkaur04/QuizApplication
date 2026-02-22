package com.Project.QuizApplication.controllers;

import com.Project.QuizApplication.models.Question;
import com.Project.QuizApplication.models.Quiz;
import com.Project.QuizApplication.repositories.QuestionRepository;
import com.Project.QuizApplication.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private QuizRepository quizRepository;
    @Autowired private QuestionRepository questionRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "user-dashboard";
    }

    @GetMapping("/quiz/{id}")
    public String attemptQuiz(@PathVariable Long id, Model model) {
        // Fetch quiz details
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        // Fetch questions for this quiz
        List<Question> questions = questionRepository.findByQuizId(id);

        // Pass to Thymeleaf template
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "attempt-quiz";
    }

    @PostMapping("/quiz/{id}/submit")
    public String submitQuiz(@PathVariable Long id,
                             @RequestParam Map<String, String> answers,
                             Model model) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuizId(id);
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String submittedAnswerStr = answers.get("answers[" + i + "]");
            
            if (submittedAnswerStr != null) {
                try {
                    int submittedAnswer = Integer.parseInt(submittedAnswerStr);
                    if (question.getCorrectOption() == submittedAnswer) {
                        score++;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid answer format for question index " + i);
                }
            }
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        return "quiz-result";
    }

}