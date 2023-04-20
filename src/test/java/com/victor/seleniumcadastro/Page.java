package com.victor.seleniumcadastro;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

// page_url = https://www.jetbrains.com/
public class Page {

    public Page(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

//    Text fields
    @FindBy(id = "nome")
    public WebElement name;

    @FindBy(id = "sobrenome")
    public WebElement lastName;

//    Radio buttons
    @FindBy(id = "masculino")
    public WebElement radioMale;

    @FindBy(id = "feminino")
    public WebElement radioFemale;

    @FindBy(id = "outro")
    public WebElement radioOther;

//    Checkboxes
    @FindBy(id = "java")
    public WebElement checkboxJava;

    @FindBy(id = "selenium")
    public WebElement checkboxSelenium;

    @FindBy(id = "react")
    public WebElement checkboxReact;

    @FindBy(id = "junit")
    public WebElement checkboxJunit;

    @FindBy(id = "javascript")
    public WebElement checkboxJavascript;

//    Multi-select
    @FindBy(id = "area-interesse")
    public WebElement areaInterestElement;

    public Select areaInterest() {
        return new Select(areaInterestElement);
    }

//    Select
    @FindBy(id = "motivacao")
    public WebElement principalAreaElement;

    public Select principalArea() {
        return new Select(principalAreaElement);
    }

//    Text Area
    @FindBy(id = "porque")
    public WebElement whyWantJob;

//    Button
    @FindBy(xpath = "/html/body/div[1]/form/div[8]/button[2]")
    public WebElement submitButton;

    @FindBy(id = "btn-info")
    public WebElement infoButton;

    @FindBy(linkText = "Sobre a empresa")
    public WebElement aboutButton;

//    Areas
    @FindBy(id = "table-cadastros-body")
    public WebElement resultArea;
}
