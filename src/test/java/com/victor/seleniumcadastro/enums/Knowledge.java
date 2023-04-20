package com.victor.seleniumcadastro.enums;

public enum Knowledge {
    JAVA("java"),
    SELENIUM("selenium"),
    REACT("react"),
    JUNIT("junit"),
    JAVASCRIPT("javascript");

    private final String text;

    Knowledge(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
