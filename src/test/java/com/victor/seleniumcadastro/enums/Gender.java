package com.victor.seleniumcadastro.enums;

public enum Gender {
    MALE("masculino"),
    FEMALE("feminino"),
    OTHER("outro");

    private final String text;

    Gender(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
