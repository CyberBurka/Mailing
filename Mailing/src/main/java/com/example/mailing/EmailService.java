package com.example.mailing;

/**
 * Сервис отправки письма
 */
public interface EmailService {
    void sendEmail(String recipientEmail, String subject, String body);
}
