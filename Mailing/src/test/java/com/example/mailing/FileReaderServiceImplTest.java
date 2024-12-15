package com.example.mailing;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class FileReaderServiceImplTest {

    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void testReadRecipients_validFile() throws IOException {
        // Подготовка тестового CSV-файла
        File testFile = new File("test_recipients.csv");
        if (!testFile.exists()) {
            testFile.createNewFile();
            // Написать данные в файл
            try (java.io.FileWriter writer = new java.io.FileWriter(testFile)) {
                writer.write("test1@example.com;Test User 1\n");
                writer.write("test2@example.com;Test User 2\n");
            }
        }

        // Чтение файла
        List<Recipient> recipients = fileReaderService.readRecipients(testFile.getAbsolutePath());

        // Проверка результатов
        assertEquals(2, recipients.size());
        assertEquals("test1@example.com", recipients.get(0).getEmail());
        assertEquals("Test User 1", recipients.get(0).getName());

        // Удалить тестовый файл
        testFile.delete();
    }

    @Test
    public void testReadTemplate_validFile() throws IOException {
        // Подготовка тестового файла шаблона
        File testFile = new File("test_template.txt");
        if (!testFile.exists()) {
            testFile.createNewFile();
            try (java.io.FileWriter writer = new java.io.FileWriter(testFile)) {
                writer.write("Hello, {name}!\nWelcome to our service.");
            }
        }

        // Чтение шаблона
        Template template = fileReaderService.readMessageTemplate(testFile.getAbsolutePath());

        // Ожидаемое значение
        String expected = "Hello, {name}!\nWelcome to our service.";

        // Нормализация строк перед сравнением
        String actual = template.applyPlaceholders(Map.of()).replace("\r\n", "\n").replace("\r", "\n");

        // Проверка содержания шаблона
        assertEquals(expected, actual);

        // Удалить тестовый файл
        testFile.delete();
    }
}