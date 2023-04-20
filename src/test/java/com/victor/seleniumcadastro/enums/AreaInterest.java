package com.victor.seleniumcadastro.enums;

public enum AreaInterest {
    FRONTEND("Frontend"),
    BACKEND("Backend"),
    TESTS("Testes"),
    DEVOPS("Devops");

    private final String text;

    AreaInterest(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
