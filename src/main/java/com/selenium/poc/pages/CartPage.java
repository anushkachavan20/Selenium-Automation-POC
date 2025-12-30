package com.selenium.poc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By cartTable = By.cssSelector("table.cart");
    private By termsCheckbox = By.id("termsofservice");
    private By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ✅ Product presence
    public boolean isProductPresentInCart(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
        return driver.findElements(
                By.xpath("//table[contains(@class,'cart')]//a[text()='" + productName + "']")
        ).size() > 0;
    }

    // ✅ Quantity validation (Apple MacBook Pro = 2)
    public int getProductQuantity(String productName) {
        String qtyXpath =
                "//a[text()='" + productName + "']/ancestor::tr//input[contains(@class,'qty-input')]";

        return Integer.parseInt(
                driver.findElement(By.xpath(qtyXpath)).getAttribute("value")
        );
    }

    // ✅ Proceed to checkout
    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }
}
