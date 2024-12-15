package com.example.mailing;

/**
 * Точка входа
 */
public class Main {
    public static void main(String[] args) {
        // Создаем экземпляры сервисов
        EmailService emailService = new EmailServiceImpl(
                "smtp.yandex.ru",
                587,               //
                "app@yandex.ru",//email
                "btdnoiauflbueidn" // Пароль приложения
        );


        FileReaderServiceImpl fileReaderServiceImpl = new FileReaderServiceImpl();

        // Запускаем JavaFX приложение, передавая сервисы
        EmailAppLauncher.startApplication(emailService, fileReaderServiceImpl);

    }
}
