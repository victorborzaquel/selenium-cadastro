package com.victor.seleniumcadastro;

import com.victor.seleniumcadastro.enums.AreaInterest;
import com.victor.seleniumcadastro.enums.Gender;
import com.victor.seleniumcadastro.enums.Knowledge;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Form {
    private final Page page;

    public Form(Page page) {
        this.page = page;
    }

    public String[] getAllSelectOptions(Select select) {
        return select.getOptions().stream().map(WebElement::getText).toArray(String[]::new);
    }

    public String[] getAllSelectedOptions(Select select) {
        return select.getAllSelectedOptions().stream().map(WebElement::getText).toArray(String[]::new);
    }

    public void submit() {
        page.submitButton.click();
    }

    public String[] resultArea() {
        String[] result;

        try {
            result = page.resultArea
                    .findElement(By.tagName("tr"))
                    .findElements(By.tagName("td"))
                    .stream().map(WebElement::getText).toArray(String[]::new);
        } catch (Exception e) {
            result = new String[0];
        }

        return result;
    }

    public static class Builder {
        private final Page page;

        public Builder(Page page) {
            this.page = page;
        }

        public Form build() {
            return new Form(page);
        }

        public Builder name(String name) {
            page.name.sendKeys(name);
            return this;
        }

        public Builder lastName(String lastName) {
            page.lastName.sendKeys(lastName);
            return this;
        }

        public Builder areaInterest(AreaInterest area) {
            page.areaInterest().selectByValue(area.toString());
            return this;
        }

        public Builder areaInterest(List<AreaInterest> areas) {
            areas.forEach(this::areaInterest);
            return this;
        }

        public Builder principalArea(AreaInterest area) {
            page.principalArea().selectByValue(area.toString());
            return this;
        }

        public Builder knowledge(Knowledge knowledge) {
            switch (knowledge) {
                case JAVA -> page.checkboxJava.click();
                case SELENIUM -> page.checkboxSelenium.click();
                case REACT -> page.checkboxReact.click();
                case JUNIT -> page.checkboxJunit.click();
                case JAVASCRIPT -> page.checkboxJavascript.click();
            }
            return this;
        }

        public Builder knowledge(List<Knowledge> knowledge) {
            knowledge.forEach(this::knowledge);
            return this;
        }

        public Builder gender(Gender gender) {
            switch (gender) {
                case MALE -> page.radioMale.click();
                case FEMALE -> page.radioFemale.click();
                case OTHER -> page.radioOther.click();
            }
            return this;
        }

        public Builder whyWantJob(String text) {
            page.whyWantJob.sendKeys(text);
            return this;
        }
    }
}
