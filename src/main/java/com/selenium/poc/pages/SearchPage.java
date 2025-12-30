package com.selenium.poc.pages;

import com.selenium.poc.base.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "small-searchterms")
    private WebElement searchBox;

    @FindBy(css = "button.search-box-button")
    private WebElement searchBtn;

    // 🔍 Normal product search
    public void search(String productName) {
        waitAndType(searchBox, productName);
        waitAndClick(searchBtn);
    }

    // ⚠ Alert validation (empty search)
    public String triggerSearchAlertAndGetText() {
        searchBox.clear();
        waitAndClick(searchBtn);

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        return alertText;
    }
}
