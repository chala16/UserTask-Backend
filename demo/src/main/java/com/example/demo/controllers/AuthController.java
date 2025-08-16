package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.services.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody User user) {
        try {
            authService.registerUser(user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User request) {
        try {
            String token = authService.authenticateUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }
}