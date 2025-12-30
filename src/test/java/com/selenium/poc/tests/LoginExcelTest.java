package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.LoginPage;
import com.selenium.poc.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginExcelTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return ExcelUtil.getExcelData(
                "src/test/resources/testdata/loginData.xlsx",
                "Sheet1"
        );
    }

    @Test(dataProvider = "loginData")
    public void loginWithExcelData(String username, String password, String expectedResult) {

        //openApp();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin();
        loginPage.login(username, password);

        handleAlertIfPresent(); // handle Google alert if it appears

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(
                    loginPage.isLogoutDisplayed(),
                    "Valid login failed for user: " + username
            );
        } else if (expectedResult.equalsIgnoreCase("failure")) {
            String error = loginPage.getErrorMessageSafe();
            Assert.assertTrue(
                    error.contains("Login was unsuccessful") || error.contains("No customer account found"),
                    "Invalid login error not displayed for user: " + username + ". Actual: " + error
            );
        } else {
            Assert.fail("Invalid expectedResult value in Excel: " + expectedResult);
        }
    }

    // Handle alert if present
    public void handleAlertIfPresent() {
        try {
            getDriver().switchTo().alert().accept();
        } catch (Exception ignored) {
        }
    }
}
