package com.example.foodzz_v2.foodzz_v2.service.mail;

public interface MailService {
    void sendVerificationMail(String email, String username, String veririficationLink);
    void sendConfirmationMail(String email, String username);
    void sendForgotPasswordMail(String email);
}
