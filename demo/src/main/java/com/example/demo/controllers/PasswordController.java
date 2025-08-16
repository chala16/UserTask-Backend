package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.interfaces.EmailService;
import com.example.demo.services.interfaces.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordController {

    private final PasswordResetService passwordResetService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PasswordController(PasswordResetService passwordResetService,
                              UserRepository userRepository,
                              EmailService emailService) {
        this.passwordResetService = passwordResetService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam String email) {
        try {
            String token = passwordResetService.createResetToken(email);
            String resetLink = "http://localhost:5173/reset-password?token=" + token;

            emailService.sendEmail(email, "Password Reset Request",
                    "Click the link to reset your password: " + resetLink);

            return ResponseEntity.ok(new ApiResponse<>(true, "Password reset link sent successfully", resetLink));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam String token,
                                                           @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new ApiResponse<>(true, "Password has been reset successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestParam String oldPassword,
                                                            @RequestParam String newPassword,
                                                            Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            passwordResetService.changePassword(user, oldPassword, newPassword);
            return ResponseEntity.ok(new ApiResponse<>(true, "Password changed successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }
}