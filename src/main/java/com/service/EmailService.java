package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to MuseNote!");
        message.setText("Hi " + username + ",\n\nThank you for registering with MuseNote! 🎶\nWe're excited to have you onboard.\n\nHappy posting!\n\n- MuseNote Team");
        message.setFrom("nainikavempalli@gmail.com");

        mailSender.send(message);
    }
}

