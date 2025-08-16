package com.example.demo.services.interfaces;

import com.example.demo.models.User;

public interface AuthService {
    void registerUser(User user);
    String authenticateUser(String email, String password);
    boolean isEmailAlreadyExists(String email);
}