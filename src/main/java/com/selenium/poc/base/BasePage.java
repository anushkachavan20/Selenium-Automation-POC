//package com.selenium.poc.base;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class BasePage {
//    protected WebDriver driver;
//    private WebDriverWait wait;
//
//    public BasePage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        PageFactory.initElements(driver, this); // ⚡ Important: initialize @FindBy elements
//    }
//
//    protected void waitForVisibility(WebElement element) {
//        wait.until(ExpectedConditions.visibilityOf(element));
//    }
//
//    protected void waitAndClick(WebElement element) {
//        if (element != null) {
//            wait.until(ExpectedConditions.visibilityOf(element));
//            wait.until(ExpectedConditions.elementToBeClickable(element));
//            element.click();
//        } else {
//            throw new RuntimeException("Element not found for click!");
//        }
//    }
//
//    protected void waitAndType(WebElement element, String text) {
//        if (element != null) {
//            wait.until(ExpectedConditions.visibilityOf(element));
//            element.clear();
//            element.sendKeys(text);
//        } else {
//            throw new RuntimeException("Element not found for typing!");
//        }
//    }
//
//    protected boolean isElementDisplayed(WebElement element) {
//        try {
//            return element != null && element.isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
package com.selenium.poc.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // ✅ required for @FindBy
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * 🔐 Firefox-safe + Chrome-safe click
     * Native click first → JS click fallback
     */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            element.click(); // Chrome works here
        } catch (Exception e) {
            // 🔥 Firefox fallback
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }


    /**
     * ⌨ Safe typing (works on all browsers)
     */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * 👁 Safe display check
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
