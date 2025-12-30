package com.selenium.poc.tests;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.pages.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    @DataProvider(name = "users", parallel = true)
    public Object[][] users() {
        return new Object[][]{
                {"testuser1@gmail.com", "Anu@201220"},
                {"testuser2@gmail.com", "Au@201220"}
        };
    }

    @Test(dataProvider = "users")
    public void completeEndToEndFlow(String email, String password) {

        // Initialize page objects using ThreadLocal driver
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = new SearchPage(getDriver());
        SearchResultsPage searchResultsPage = new SearchResultsPage(getDriver());
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());

        // 🔐 Login
        loginPage.navigateToLogin();
        loginPage.login(email, password);

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Login failed for user: " + email
        );

        // 🔍 Search product
        searchPage.search("Apple MacBook Pro");

        // 📦 Open product
        searchResultsPage.openFirstProduct();

        // 🛒 Add to cart
        productPage.addProductAndGoToCart();

        Assert.assertTrue(
                cartPage.isProductPresentInCart("Apple MacBook Pro"),
                "Product not present in cart"
        );

        // 💳 Checkout
        cartPage.proceedToCheckout();
        checkoutPage.completeCheckout();

        // 🎉 Validate order
        Assert.assertTrue(
                checkoutPage.isOrderPlacedSuccessfully(),
                "Order was not placed successfully"
        );
    }
}
