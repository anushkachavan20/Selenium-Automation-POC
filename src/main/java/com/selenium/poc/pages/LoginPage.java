package com.selenium.poc.pages;

import com.selenium.poc.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    // ✅ Correct locator
    @FindBy(css = "button.button-1.login-button")
    private WebElement loginButton;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutLink;

    @FindBy(css = "div.message-error.validation-summary-errors")
    private WebElement errorMessage;

    public void navigateToLogin() {
        driver.get("https://demo.nopcommerce.com/login");
        waitForVisibility(emailInput);
    }
    public boolean isLogoutDisplayed() {
        return isElementDisplayed(logoutLink);
    }


    public void login(String email, String password) {
        waitAndType(emailInput, email);
        waitAndType(passwordInput, password);
        waitAndClick(loginButton);
    }

    public boolean isLoginSuccessful() {
        try {
            waitForVisibility(logoutLink);
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageSafe() {
        try {
            waitForVisibility(errorMessage);
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
