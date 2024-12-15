package com.example.mailing;

import java.util.Objects;

/**
 * Data класс для получателя писем
 */
public class Recipient {
    private final String email;
    private final String name;

    public Recipient(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient recipient)) return false;
        return Objects.equals(email, recipient.email) && Objects.equals(name, recipient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }

}
