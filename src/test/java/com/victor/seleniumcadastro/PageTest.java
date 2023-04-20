package com.victor.seleniumcadastro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.victor.seleniumcadastro.enums.AreaInterest.*;
import static com.victor.seleniumcadastro.enums.Gender.*;
import static com.victor.seleniumcadastro.enums.Knowledge.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

class PageTest {
    private WebDriver driver;
    private Page page;
    private Wait<WebDriver> wait;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get("https://igorsmasc.github.io/fomulario_cadastro_selenium/");

        page = new Page(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private void completeAlert() {
        Alert alert = driver.switchTo().alert();
        assertEquals("Você preencheu tudo corretamente e é sua última resposta?", alert.getText());
        alert.accept();
    }

    private void notCompleteAlert() {
        Alert alert = driver.switchTo().alert();
        assertEquals("Por favor, preencha todos os campos.", alert.getText());
        alert.dismiss();
    }

    private void switchToAbout() {
        page.aboutButton.click();
        driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
    }

    @Test
    void allFormIsCorrect() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT, JUNIT, REACT, SELENIUM))
                .areaInterest(List.of(FRONTEND, BACKEND, TESTS, DEVOPS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "masculino", "Frontend, Backend, Testes, Devops", "Backend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void createFrontEndRegister() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(FEMALE)
                .knowledge(List.of(JAVASCRIPT, REACT))
                .areaInterest(FRONTEND)
                .principalArea(FRONTEND)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "feminino", "Frontend", "Frontend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void createDevopsRegister() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(FEMALE)
                .knowledge(List.of(JAVASCRIPT, JAVA))
                .areaInterest(DEVOPS)
                .principalArea(DEVOPS)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "feminino", "Devops", "Devops"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void createBackendRegister() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(FEMALE)
                .knowledge(List.of(JAVASCRIPT, JAVA))
                .areaInterest(BACKEND)
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "feminino", "Backend", "Backend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void notRegisterTagHTMLInName() {
        Form form = new Form.Builder(page)
                .name("</td><h1>Victor</h1><td>")
                .lastName("Borzaquel")
                .gender(FEMALE)
                .knowledge(List.of(JAVASCRIPT, JAVA))
                .areaInterest(BACKEND)
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "feminino", "Backend", "Backend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void notRegisterTagHTMLInLastName() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("</td><h1>Borzaquel</h1><td>")
                .gender(FEMALE)
                .knowledge(List.of(JAVASCRIPT, JAVA))
                .areaInterest(BACKEND)
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "feminino", "Backend", "Backend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void nameIsSpace() {
        Form form = new Form.Builder(page)
                .name(" ")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void lastNameIsSpace() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName(" ")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void whyWantJobIsSpace() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob(" ")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void selectMultiplesGender() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .gender(FEMALE)
                .gender(OTHER)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        assertFalse(page.radioMale.isSelected());
        assertFalse(page.radioFemale.isSelected());
        assertTrue(page.radioOther.isSelected());
        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "outro", "Backend, Testes", "Backend"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void selectMultiplesPrincipalArea() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .principalArea(TESTS)
                .whyWantJob("I want to work")
                .build();

        assertArrayEquals(new String[]{"Testes"}, form.getAllSelectedOptions(page.principalArea()));
        assertArrayEquals(new String[]{}, form.resultArea());

        form.submit();

        completeAlert();

        String[] expected = new String[] {"Victor", "Borzaquel", "masculino", "Backend, Testes", "Testes"};
        assertArrayEquals(expected, form.resultArea());
    }

    @Test
    void nameIsNull() {
        Form form = new Form.Builder(page)
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void lastNameIsNull() {
        Form form = new Form.Builder(page)
                .name("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void genderIsNull() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void knowledgeIsNull() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void areaInterestIsNull() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .principalArea(BACKEND)
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void principalAreaIsNull() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .whyWantJob("I want to work")
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void whyWantJobIsNull() {
        Form form = new Form.Builder(page)
                .name("Victor")
                .lastName("Borzaquel")
                .gender(MALE)
                .knowledge(List.of(JAVA, JAVASCRIPT))
                .areaInterest(List.of(BACKEND, TESTS))
                .principalArea(BACKEND)
                .build();

        form.submit();

        notCompleteAlert();

        assertArrayEquals(new String[]{}, form.resultArea());
    }

    @Test
    void moreInformation() {
        page.infoButton.click();
        String expected = "Este formulário é para cadastro de candidatos interessados em vagas de emprego. Por favor, preencha todos os campos obrigatórios e forneça informações precisas e atualizadas. Obrigado!";
        assertEquals(expected, driver.switchTo().alert().getText());
    }

    @Test
    void aboutText() {
        switchToAbout();

        wait.until(presenceOfElementLocated(By.tagName("p")));
        String expected = "Bem-vindo a melhor empresa do mundo, uma empresa de software comprometida em fornecer soluções inovadoras e personalizadas para seus clientes. Fundada em 2023, temos sido líderes em tecnologia desde então, ajudando empresas de todos os setores a alcançarem seus objetivos.";
        assertEquals(expected, driver.findElement(By.tagName("p")).getText());
    }

    @Test
    void aboutTitle() {
        switchToAbout();

        wait.until(presenceOfElementLocated(By.tagName("h1")));
        assertEquals("A melhor empresa do mundo", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    void aboutButtonCandidate() {
        switchToAbout();

        wait.until(elementToBeClickable(By.linkText("Se Candidate")));
        driver.findElement(By.linkText("Se Candidate")).click();

        wait.until(presenceOfElementLocated(By.tagName("h1")));
        assertEquals("Desafio - Cadastro", driver.findElement(By.tagName("h1")).getText());
    }
}
