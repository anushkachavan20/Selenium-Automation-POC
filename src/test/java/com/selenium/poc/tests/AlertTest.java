package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlertTest extends BaseTest {

    @Test
    public void searchAlertTest() {

        SearchPage searchPage = new SearchPage(getDriver());
        String alertText = searchPage.triggerSearchAlertAndGetText();

        Assert.assertEquals(
                alertText,
                "Please enter some search keyword",
                "Unexpected alert message"
        );
    }
}
