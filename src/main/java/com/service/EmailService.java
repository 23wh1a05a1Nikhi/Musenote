package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${EMAILID}")
    private String fromEmail;
    
    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP for MuseNote Registration");
        message.setText("Your OTP is: " + otp + "\n\nIt is valid for 5 minutes.");
        mailSender.send(message);
    } 
    
    
    public void sendWelcomeEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to MuseNote!");
        message.setText("Hi " + username + ",\n\nThank you for registering with MuseNote! 🎶\nWe're excited to have you onboard.\n\nHappy posting!\n\n- MuseNote Team");
        message.setFrom(fromEmail);

        mailSender.send(message);
    }
    
       
}
