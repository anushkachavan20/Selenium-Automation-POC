package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MouseHoverTest extends BaseTest {

    @Test
    public void mouseHoverTest() {


        HomePage homePage = new HomePage(getDriver());
        homePage.hoverToDesktops();

        // Correct validation
        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("/desktops"),
                "Hover navigation to Desktops page failed"
        );
    }
}
