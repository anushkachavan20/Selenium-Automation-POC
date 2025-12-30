package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropdownTest extends BaseTest {

    @Test
    public void currencyDropdownTest() {

        // Step 1: Open application


        HomePage homePage = new HomePage(getDriver());

        // Step 2: Change currency to US Dollar
        homePage.selectCurrency("US Dollar");

        // Step 3: Validate $ symbol
        String priceText = homePage.getDisplayedPrice();

        Assert.assertTrue(
                priceText.contains("$"),
                "Currency not changed to US Dollar. Price: " + priceText
        );
    }
}
