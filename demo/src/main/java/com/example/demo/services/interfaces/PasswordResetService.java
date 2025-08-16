package com.example.demo.services.interfaces;

import com.example.demo.models.User;

public interface PasswordResetService {
    String createResetToken(String email);
    boolean validateToken(String token);
    void resetPassword(String token, String newPassword);
    void changePassword(User user, String oldPassword, String newPassword);
}