package com.example.mailing;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateTest {

    @Test
    public void testApplyPlaceholders() {
        // Создаем шаблон
       Template template = new Template("Hello, {name}! Your email is {email}.");

        // Подставляем значения
        String result = template.applyPlaceholders(Map.of(
                "name", "John Doe",
                "email", "john.doe@example.com"
        ));

        // Проверяем результат
        assertEquals("Hello, John Doe! Your email is john.doe@example.com.", result);
    }

    @Test
    public void testApplyPlaceholders_missingValues() {
        // Создаем шаблон
        Template template = new Template("Hello, {name}! Your email is {email}.");

        // Подставляем неполные значения
        String result = template.applyPlaceholders(Map.of("name", "John Doe"));

        // Проверяем результат (незаполненные значения остаются в шаблоне)
        assertEquals("Hello, John Doe! Your email is {email}.", result);
    }
}