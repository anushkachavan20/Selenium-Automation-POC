package com.selenium.poc.pages;

import com.selenium.poc.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IFramePage extends BasePage {

    public IFramePage(WebDriver driver) {
        super(driver);
    }

    // Example iframe (admin editor style)
    private By iframe = By.cssSelector("iframe");

    private By body = By.tagName("body");

    public void switchToIframeAndType(String text) {
        driver.switchTo().frame(driver.findElement(iframe));
        driver.findElement(body).clear();
        driver.findElement(body).sendKeys(text);
        driver.switchTo().defaultContent();
    }
}
