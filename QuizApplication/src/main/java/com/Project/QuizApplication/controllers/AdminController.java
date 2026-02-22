package com.Project.QuizApplication.controllers;

import com.Project.QuizApplication.models.Category;
import com.Project.QuizApplication.models.Question;
import com.Project.QuizApplication.models.Quiz;
import com.Project.QuizApplication.models.User;
import com.Project.QuizApplication.services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
//        model.addAttribute("userCount", adminService.getUserCount());
        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("categories", adminService.getAllCategories());
        model.addAttribute("quizzes", adminService.getAllQuizzes());
        model.addAttribute("questions", adminService.getAllQuestions());
        return "admin-dashboard";
    }

    // ---------- Category ----------
    @PostMapping("/categories")
    public String addCategory(@ModelAttribute Category category) {
        adminService.addCategory(category);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/categories/{id}/update")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        adminService.updateCategory(id, category);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return "redirect:/admin/dashboard";
    }

    // ---------- Quiz ----------
    @PostMapping("/quizzes")
    public String addQuiz(@RequestParam String title,
                         @RequestParam Long categoryId,
                         @RequestParam(required = false) String description) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);

        adminService.addQuiz(categoryId, quiz);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/categories/{catId}/quizzes")
    public String addQuiz(@PathVariable Long catId, @ModelAttribute Quiz quiz) {
        adminService.addQuiz(catId, quiz);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/quizzes/{id}/update")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        adminService.updateQuiz(id, quiz);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/quizzes/{id}/delete")
    public String deleteQuiz(@PathVariable Long id) {
        adminService.deleteQuiz(id);
        return "redirect:/admin/dashboard";
    }

    // ---------- Question ----------
    @PostMapping("/questions")
    public String addQuestion(@RequestParam Long quizId,
                             @RequestParam String questionText,
                             @RequestParam String optionA, // Corrected parameter name
                             @RequestParam String optionB, // Corrected parameter name
                             @RequestParam String optionC, // Corrected parameter name
                             @RequestParam String optionD, // Corrected parameter name
                             @RequestParam int correctOption,
                             RedirectAttributes redirectAttributes) {

        try {
            Question question = new Question();
            question.setText(questionText);
            question.setOptionA(optionA); // Use setOptionA
            question.setOptionB(optionB); // Use setOptionB
            question.setOptionC(optionC); // Use setOptionC
            question.setOptionD(optionD); // Use setOptionD
            question.setCorrectOption(correctOption);

            adminService.addQuestion(quizId, question);

            redirectAttributes.addFlashAttribute("successMessage", "Question added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/dashboard";
    }


    @PostMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id,
                                 @RequestParam String questionText,
                                 @RequestParam String optionA, // Ensure consistency
                                 @RequestParam String optionB, // Ensure consistency
                                 @RequestParam String optionC, // Ensure consistency
                                 @RequestParam String optionD, // Ensure consistency
                                 @RequestParam int correctOption) {

        Question updatedQuestion = new Question();
        updatedQuestion.setText(questionText);
        updatedQuestion.setOptionA(optionA);
        updatedQuestion.setOptionB(optionB);
        updatedQuestion.setOptionC(optionC);
        updatedQuestion.setOptionD(optionD);
        updatedQuestion.setCorrectOption(correctOption);

        adminService.updateQuestion(id, updatedQuestion);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id) {
        adminService.deleteQuestion(id);
        return "redirect:/admin/dashboard";
    }
 // ---------- User ----------

    // API to delete a user
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id); // Assuming AdminService has a deleteUser method
        return "redirect:/admin/dashboard";
    }

    // API to update a user
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String email,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            adminService.updateUser(id, user); // Assuming AdminService has an updateUser method
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}