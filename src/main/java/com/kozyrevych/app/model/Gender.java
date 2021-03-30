package com.kozyrevych.app.model;

public enum Gender{
    MALE("Мужской"), FEMALE("Женский");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
