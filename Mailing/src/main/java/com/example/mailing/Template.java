package com.example.mailing;

import java.util.Map;
import java.util.Objects;

/**
 * Data класс шаблона
 */
public class Template {
    private final String templateText;

    public Template(String templateText) {
        this.templateText = templateText;
    }

    /**
     * Заменяет плейсхолдеры вида {key} на значения из переданной Map.
     *
     * @param placeholders карта значений для замены
     * @return текст сообщения с подставленными значениями
     */
    public String applyPlaceholders(Map<String, String> placeholders) {
        String result = templateText;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            result = result.replace(placeholder, entry.getValue());
        }
        return result;
    }

    public String getTemplateText() {
        return templateText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Template template)) return false;
        return Objects.equals(templateText, template.templateText);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateText);
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateText='" + templateText + '\'' +
                '}';
    }
}
