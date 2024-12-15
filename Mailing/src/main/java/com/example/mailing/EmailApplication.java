package com.example.mailing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Класс javafx приложения
 */
public class EmailApplication extends Application {

    private EmailService emailService;
    private FileReaderServiceImpl fileReaderServiceImpl;

    private TextArea templateTextArea;    // Поле для отображения шаблона
    private TextArea recipientsTextArea; // Поле для отображения списка получателей
    private File templateFile;
    private File recipientsFile;

    @Override
    public void init() {
        // Получаем сервисы из EmailAppLauncher
        this.emailService = EmailAppLauncher.getEmailService();
        this.fileReaderServiceImpl = EmailAppLauncher.getFileReaderService();
    }

    @Override
    public void start(Stage primaryStage) {
        // Корневой макет
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        // Кнопка выбора файла шаблона
        Button chooseTemplateButton = new Button("Выбрать файл шаблона");
        chooseTemplateButton.setId("chooseTemplateButton"); // Установка ID
        chooseTemplateButton.setOnAction(event -> chooseTemplateFile(primaryStage));

        // Кнопка выбора файла с получателями
        Button chooseRecipientsButton = new Button("Выбрать файл с получателями");
        chooseRecipientsButton.setId("chooseRecipientsButton"); // Установка ID
        chooseRecipientsButton.setOnAction(event -> chooseRecipientsFile(primaryStage));

        // Поле для отображения шаблона
        templateTextArea = new TextArea();
        templateTextArea.setId("templateTextArea"); // Установка ID
        templateTextArea.setEditable(false);
        templateTextArea.setPromptText("Шаблон сообщения будет отображен здесь");

        // Поле для отображения списка получателей
        recipientsTextArea = new TextArea();
        recipientsTextArea.setId("recipientsTextArea"); // Установка ID
        recipientsTextArea.setEditable(false);
        recipientsTextArea.setPromptText("Список получателей будет отображен здесь");

        // Кнопка для отправки писем
        Button sendEmailsButton = new Button("Отправить письма");
        sendEmailsButton.setId("sendEmailsButton"); // Установка ID
        sendEmailsButton.setOnAction(event -> sendEmails());

        // Добавление элементов в макет
        root.getChildren().addAll(
                chooseTemplateButton,
                chooseRecipientsButton,
                new Label("Шаблон сообщения:"),
                templateTextArea,
                new Label("Список получателей:"),
                recipientsTextArea,
                sendEmailsButton
        );

        // Настройка сцены
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Email Application");
        primaryStage.show();
    }

    /**
     * Выбор файла шаблона сообщения.
     */
    private void chooseTemplateFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл шаблона");
        templateFile = fileChooser.showOpenDialog(stage);
        if (templateFile != null) {
            try {
                Template template = fileReaderServiceImpl.readMessageTemplate(templateFile.getAbsolutePath());
                templateTextArea.setText(template.getTemplateText()); // Отображение исходного шаблона
            } catch (IOException e) {
                showError("Ошибка при чтении файла шаблона: " + e.getMessage());
            }
        }
    }

    /**
     * Выбор файла с получателями.
     */
    private void chooseRecipientsFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с получателями");
        recipientsFile = fileChooser.showOpenDialog(stage);
        if (recipientsFile != null) {
            try {
                List<Recipient> recipients = fileReaderServiceImpl.readRecipients(recipientsFile.getAbsolutePath());
                StringBuilder recipientsText = new StringBuilder();
                for (Recipient recipient : recipients) {
                    recipientsText.append(recipient.getEmail()).append(" - ").append(recipient.getName()).append("\n");
                }
                recipientsTextArea.setText(recipientsText.toString());
            } catch (IOException e) {
                showError("Ошибка при чтении файла с получателями: " + e.getMessage());
            }
        }
    }

    /**
     * Отправка писем всем получателям.
     */
    private void sendEmails() {
        if (templateFile == null || recipientsFile == null) {
            showError("Выберите файлы шаблона и получателей перед отправкой.");
            return;
        }

        try {
            Template template = fileReaderServiceImpl.readMessageTemplate(templateFile.getAbsolutePath());
            List<Recipient> recipients = fileReaderServiceImpl.readRecipients(recipientsFile.getAbsolutePath());

            for (Recipient recipient : recipients) {
                String personalizedMessage = template.applyPlaceholders(Map.of("name", recipient.getName()));
                emailService.sendEmail(recipient.getEmail(), "Информационное сообщение", personalizedMessage);
            }

            showInfo("Все письма успешно отправлены.");
        } catch (IOException e) {
            showError("Ошибка при чтении файлов: " + e.getMessage());
        }
    }

    /**
     * Отображает сообщение об ошибке.
     *
     * @param message текст сообщения
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Отображает информационное сообщение.
     *
     * @param message текст сообщения
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
