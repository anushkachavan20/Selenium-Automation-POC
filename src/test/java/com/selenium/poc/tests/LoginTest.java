package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {


        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin();
        loginPage.login("testuser1@gmail.com", "Anu@201220");

        handleAlertIfPresent(); // handle any Google alert after login

        Assert.assertTrue(
                loginPage.isLogoutDisplayed(),
                "Logout link not displayed. Login failed."
        );
    }

    @Test
    public void invalidLoginTest() {


        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin();
        loginPage.login("wrong@mail.com", "wrong123");

        handleAlertIfPresent(); // just in case any alert appears

        String actualError = loginPage.getErrorMessageSafe();

        Assert.assertTrue(
                actualError.contains("Login was unsuccessful") || actualError.contains("No customer account found"),
                "Incorrect error message displayed. Actual: " + actualError
        );
    }

    // Handle alert if present
    public void handleAlertIfPresent() {
        try {
            getDriver().switchTo().alert().accept();
        } catch (Exception ignored) {
        }
    }
}
