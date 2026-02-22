package com.Project.QuizApplication.services;

import com.Project.QuizApplication.models.User;
import com.Project.QuizApplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean isValidPassword(String password) {
        return password != null && pattern.matcher(password).matches();
    }

    @Transactional
    public User registerUser(User user) {
        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException(
                "Password must be at least 8 characters, contain 1 uppercase letter and 1 special character."
            );
        }
        // Save raw password (⚠ not secure, but works without Spring Security)
        return userRepository.save(user);
    }

    public User authenticate(String email, String rawPassword) {
        User dbUser = userRepository.findByEmailAndPassword(email, rawPassword);
        return dbUser; // will return null if not found
    }
}
