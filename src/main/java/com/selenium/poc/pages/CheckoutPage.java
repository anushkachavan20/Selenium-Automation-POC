package com.selenium.poc.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ================== BILLING ==================
    private By billingContinueBtn =
            By.cssSelector(".new-address-next-step-button");

    private By countryDropdown =
            By.id("BillingNewAddress_CountryId");
    private By stateDropdown =
            By.id("BillingNewAddress_StateProvinceId");
    private By cityInput =
            By.id("BillingNewAddress_City");
    private By address1Input =
            By.id("BillingNewAddress_Address1");
    private By zipInput =
            By.id("BillingNewAddress_ZipPostalCode");
    private By phoneInput =
            By.id("BillingNewAddress_PhoneNumber");

    private By billingDeleteBtn =
            By.id("delete-billing-address-button");

    // ================== SHIPPING / PAYMENT ==================
    private By shippingContinueBtn =
            By.cssSelector("#shipping-method-buttons-container button");
    private By paymentMethodContinueBtn =
            By.cssSelector("#payment-method-buttons-container button");
    private By paymentInfoContinueBtn =
            By.cssSelector("#payment-info-buttons-container button");

    // ================== CONFIRM ==================
    private By confirmOrderBtn =
            By.cssSelector("#confirm-order-buttons-container button");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ================== PUBLIC FLOW ==================
    public void completeCheckout() {

        handleBillingAddress();

        safeClick(shippingContinueBtn);
        safeClick(paymentMethodContinueBtn);
        safeClick(paymentInfoContinueBtn);
        safeClick(confirmOrderBtn);
    }

    // ================== BILLING ==================
    private void handleBillingAddress() {

        if (isElementPresent(billingDeleteBtn)) {
            clickDeleteBillingAddress();
            wait.until(ExpectedConditions.presenceOfElementLocated(countryDropdown));
            fillBillingAddress();
        } else {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(countryDropdown));
                fillBillingAddress();
            } catch (Exception ignored) {
            }
        }

        safeClick(billingContinueBtn);
    }

    private void clickDeleteBillingAddress() {

        WebElement deleteBtn =
                wait.until(ExpectedConditions.visibilityOfElementLocated(billingDeleteBtn));

        // 🔑 Scroll into view
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", deleteBtn);

        // 🔑 JavaScript click (THIS IS THE FIX)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", deleteBtn);

        // wait for deletion to complete
        wait.until(ExpectedConditions.invisibilityOf(deleteBtn));
    }

    private void fillBillingAddress() {

        Select country = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)));
        country.selectByVisibleText("India");

        wait.until(ExpectedConditions.presenceOfElementLocated(stateDropdown));
        selectStateSafely("Maharashtra");

        driver.findElement(cityInput).clear();
        driver.findElement(cityInput).sendKeys("Mumbai");

        driver.findElement(address1Input).clear();
        driver.findElement(address1Input).sendKeys("Andheri East");

        driver.findElement(zipInput).clear();
        driver.findElement(zipInput).sendKeys("400069");

        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys("9876543210");
    }

    private void selectStateSafely(String stateName) {

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                Select state = new Select(
                        wait.until(ExpectedConditions.elementToBeClickable(stateDropdown)));

                for (WebElement option : state.getOptions()) {
                    if (option.getText().trim().equalsIgnoreCase(stateName)) {
                        option.click();
                        return;
                    }
                }
            } catch (StaleElementReferenceException ignored) {
            }
        }

        throw new RuntimeException("State not found: " + stateName);
    }

    private void safeClick(By locator) {

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement element =
                        wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            } catch (StaleElementReferenceException ignored) {
            }
        }

        throw new RuntimeException("Unable to click element: " + locator);
    }

    private boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }
    public boolean isOrderPlacedSuccessfully() {
        try {
            WebElement successTitle = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".order-completed .title"))
            );

            return successTitle.getText()
                    .toLowerCase()
                    .contains("successfully");
        } catch (Exception e) {
            return false;
        }
    }

}
