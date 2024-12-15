package com.example.mailing;

import javafx.application.Application;

/**
 * Класс лаунчер для javafx приложения
 */
public class EmailAppLauncher {

    private static EmailService emailService;
    private static FileReaderServiceImpl fileReaderServiceImpl;

    /**
     * Стартует приложение с передачей зависимостей.
     *
     * @param emailSvc сервис для отправки писем
     * @param fileSvc  сервис для работы с файлами
     */
    public static void startApplication(EmailService emailSvc, FileReaderServiceImpl fileSvc) {
        emailService = emailSvc;
        fileReaderServiceImpl = fileSvc;
        Application.launch(EmailApplication.class);
    }

    public static EmailService getEmailService() {
        return emailService;
    }

    public static FileReaderServiceImpl getFileReaderService() {
        return fileReaderServiceImpl;
    }
}
