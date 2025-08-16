package com.example.demo.services.interfaces;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}