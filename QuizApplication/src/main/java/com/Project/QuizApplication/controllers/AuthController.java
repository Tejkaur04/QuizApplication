package com.Project.QuizApplication.controllers;

import com.Project.QuizApplication.models.User;
import com.Project.QuizApplication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
	
	public AuthController() {
        System.out.println(">>> AuthController loaded <<<");
    }
    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        try {
            authService.registerUser(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        User dbUser = authService.authenticate(user.getEmail(), user.getPassword());
        if (dbUser != null) {
            if ("ADMIN".equalsIgnoreCase(dbUser.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        }
        model.addAttribute("error", "Invalid email or password!");
        return "login";
    }
}

