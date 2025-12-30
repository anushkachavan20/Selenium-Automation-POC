package com.selenium.poc.pages;

import com.selenium.poc.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver); // ⚡ BasePage calls PageFactory.initElements
    }

    /* ------------------ Hover Menu: Computers → Desktops ------------------ */
    public void hoverToDesktops() {
        Actions actions = new Actions(driver);

        // Locate visible Computers menu
        List<WebElement> computersList =
                driver.findElements(By.xpath("//a[normalize-space()='Computers']"));

        WebElement computersMenu = null;
        for (WebElement el : computersList) {
            if (el.isDisplayed()) {
                computersMenu = el;
                break;
            }
        }

        if (computersMenu == null) {
            throw new RuntimeException("Visible Computers menu not found");
        }

        actions.moveToElement(computersMenu).perform();

        // Click visible Desktops menu
        List<WebElement> desktopsList =
                driver.findElements(By.xpath("//a[normalize-space()='Desktops']"));

        for (WebElement el : desktopsList) {
            if (el.isDisplayed()) {
                el.click();
                return;
            }
        }

        throw new RuntimeException("Visible Desktops menu not found");
    }

    /* ------------------ Currency Dropdown ------------------ */
    @FindBy(id = "customerCurrency")
    private WebElement currencyDropdown;

    @FindBy(css = "#customerCurrency option")
    private List<WebElement> currencyOptions;

    @FindBy(css = ".prices")
    private WebElement productPrice;

    public void selectCurrency(String currencyName) {
        waitForVisibility(currencyDropdown);

        String currencyUrl = null;
        String expectedSymbol;

        if (currencyName.equalsIgnoreCase("Euro")) {
            expectedSymbol = "€";
        } else {
            expectedSymbol = "$";
        }

        for (WebElement option : currencyOptions) {
            if (option.getText().trim().equalsIgnoreCase(currencyName)) {
                currencyUrl = option.getAttribute("value");
                break;
            }
        }

        if (currencyUrl == null) {
            throw new RuntimeException("Currency not found: " + currencyName);
        }

        driver.navigate().to(currencyUrl);
        waitForVisibility(productPrice);
    }

    public String getDisplayedPrice() {
        waitForVisibility(productPrice);
        return productPrice.getText();
    }

    /* ------------------ Window Handling: Facebook ------------------ */
    private String parentWindow;

    @FindBy(css = "a[href='https://www.facebook.com/nopCommerce']")
    private WebElement facebookLink;

    public void openFacebookAndSwitch() {
        parentWindow = driver.getWindowHandle();
        waitAndClick(facebookLink);

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void closeChildAndReturn() {
        driver.close();
        driver.switchTo().window(parentWindow);
    }

    /* ------------------ Product Search ------------------ */
    @FindBy(id = "small-searchterms")
    private WebElement searchBox;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    public void searchProduct(String productName) {
        waitAndType(searchBox, productName);
        waitAndClick(searchButton);
    }
    @FindBy(id = "userProfile") // example element visible after login
    private WebElement userProfile;
    public boolean isUserLoggedIn() {
        try {
            return userProfile.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
