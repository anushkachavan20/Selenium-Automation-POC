package com.selenium.poc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---------- Locators ----------
    private By firstNameInput = By.id("FirstName");
    private By lastNameInput = By.id("LastName");
    private By emailInput = By.id("Email");
    private By passwordInput = By.id("Password");
    private By confirmPasswordInput = By.id("ConfirmPassword");
    private By registerBtn = By.id("register-button");
    private By registrationSuccessMsg = By.cssSelector(".result");

    // Navigate link
    private By registerLink = By.cssSelector("a.ico-register");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==================== PUBLIC METHODS ====================

    public void navigateToRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void registerUser(String firstName, String lastName, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);

        driver.findElement(confirmPasswordInput).clear();
        driver.findElement(confirmPasswordInput).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(registerBtn)).click();

        // Wait for registration success message
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationSuccessMsg));
    }

    public boolean isRegistrationSuccessful() {
        try {
            String msg = driver.findElement(registrationSuccessMsg).getText();
            return msg.contains("Your registration completed");
        } catch (Exception e) {
            return false;
        }
    }
}
