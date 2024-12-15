package com.example.mailing;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для сервисов чтения данных.
 */
public interface FileReaderService {

    /**
     * Считывает список получателей из файла.
     *
     * @param filePath путь к файлу
     * @return список объектов Recipient
     * @throws IOException если произошла ошибка при чтении файла
     */
    List<Recipient> readRecipients(String filePath) throws IOException;

    /**
     * Считывает шаблон сообщения из файла.
     *
     * @param filePath путь к файлу
     * @return объект Template
     * @throws IOException если произошла ошибка при чтении файла
     */
    Template readMessageTemplate(String filePath) throws IOException;
}
