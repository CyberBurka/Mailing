package com.example.mailing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Сервис для отправки писем с использованием JavaMail API.
 */
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LogManager.getLogger(EmailServiceImpl.class);

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

    /**
     * Конструктор для инициализации сервиса отправки писем.
     *
     * @param smtpHost адрес SMTP сервера
     * @param smtpPort порт SMTP сервера
     * @param username имя пользователя для аутентификации
     * @param password пароль для аутентификации
     */
    public EmailServiceImpl(String smtpHost, int smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    /**
     * Отправляет письмо на указанный email.
     *
     * @param recipientEmail адрес получателя
     * @param subject        тема письма
     * @param body           тело письма
     */
    @Override
    public void sendEmail(String recipientEmail, String subject, String body) {
        log.info("Начинается отправка письма на адрес: " + recipientEmail);

        // Настройки для подключения к SMTP серверу
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Создание сессии с аутентификацией
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Формирование сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Отправка сообщения
            Transport.send(message);
            log.info("Письмо успешно отправлено на адрес: " + recipientEmail);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке письма на адрес: " + recipientEmail, e);
        }
    }
}