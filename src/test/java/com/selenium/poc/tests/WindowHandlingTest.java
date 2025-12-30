package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WindowHandlingTest extends BaseTest {

    @Test
    public void multipleWindowTest() {

        getDriver().get("https://demo.nopcommerce.com");

        HomePage homePage = new HomePage(getDriver());
        homePage.openFacebookAndSwitch();

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("facebook.com"),
                "Expected Facebook URL but got: " + currentUrl);


        // Close child and switch back
        homePage.closeChildAndReturn();

        Assert.assertTrue(getDriver().getTitle().contains("nopCommerce"));
    }
}
