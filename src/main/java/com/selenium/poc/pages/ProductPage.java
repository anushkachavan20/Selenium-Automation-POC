package com.selenium.poc.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Add to cart (dynamic id)
    private By addToCartBtn =
            By.cssSelector("button[id^='add-to-cart-button']");

    // Success notification
    private By successBar =
            By.cssSelector(".bar-notification.success");

    // Cart link inside success notification
    private By cartLinkInNotification =
            By.cssSelector(".bar-notification.success a[href='/cart']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // =======================
    // PUBLIC METHOD
    // =======================
    public void addProductAndGoToCart() {

        selectAllRequiredAttributes();

        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(successBar));

        wait.until(ExpectedConditions.elementToBeClickable(cartLinkInNotification))
                .click();
    }

    // =======================
    // PRIVATE HELPERS
    // =======================

    private void selectAllRequiredAttributes() {

        selectRadioButtonsSafely();
        selectDropdownsSafely();
    }

    // Handle radio buttons (RAM, HDD, CPU)
    private void selectRadioButtonsSafely() {

        List<WebElement> radios =
                driver.findElements(By.cssSelector("input[type='radio']"));

        for (WebElement radio : radios) {
            try {
                if (!radio.isSelected()) {
                    wait.until(ExpectedConditions.elementToBeClickable(radio))
                            .click();
                }
            } catch (StaleElementReferenceException e) {
                // Re-locate and retry
                WebElement freshRadio =
                        wait.until(ExpectedConditions.elementToBeClickable(
                                By.id(radio.getAttribute("id"))
                        ));
                freshRadio.click();
            }
        }
    }

    // Handle dropdown attributes
    private void selectDropdownsSafely() {

        List<WebElement> dropdowns =
                driver.findElements(By.cssSelector("select"));

        for (WebElement dropdown : dropdowns) {
            try {
                Select select = new Select(dropdown);
                if (select.getOptions().size() > 1) {
                    select.selectByIndex(1);
                }
            } catch (StaleElementReferenceException e) {
                // Re-locate dropdown
                WebElement freshDropdown =
                        wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.id(dropdown.getAttribute("id"))
                        ));
                Select select = new Select(freshDropdown);
                if (select.getOptions().size() > 1) {
                    select.selectByIndex(1);
                }
            }
        }
    }
}
