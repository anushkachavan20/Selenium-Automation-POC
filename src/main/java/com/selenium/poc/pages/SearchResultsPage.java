package com.selenium.poc.pages;

import com.selenium.poc.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product-item h2.product-title a")
    private WebElement firstProduct;

    public void openFirstProduct() {
        waitAndClick(firstProduct);
    }
}
