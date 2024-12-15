package com.example.mailing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация ReaderService для чтения данных из файлов.
 */
public class FileReaderServiceImpl implements FileReaderService {

    private static final Logger log = LogManager.getLogger(FileReaderServiceImpl.class);

    @Override
    public List<Recipient> readRecipients(String filePath) throws IOException {
        List<Recipient> recipients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String email = parts[0].trim();
                    String name = parts[1].trim();
                    recipients.add(new Recipient(email, name));
                } else {
                    log.error("Неверный формат строки: " + line);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка при чтении файла с получателями: " + filePath, e);
            throw e;
        }
        log.info("Успешно считано " + recipients.size() + " получателей из файла " + filePath);
        return recipients;
    }

    @Override
    public Template readMessageTemplate(String filePath) throws IOException {
        StringBuilder templateText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                templateText.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            log.error("Ошибка при чтении файла шаблона: " + filePath, e);
            throw e;
        }
        log.info("Шаблон успешно считан из файла " + filePath);
        return new Template(templateText.toString().trim());
    }

}
