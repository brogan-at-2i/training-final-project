package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends EdgewordsShopPage {

    private static final By usernameLoginFieldLocator = By.id("username");
    private static final By passwordLoginFieldLocator = By.id("password");
    private static final By loginButtonLocator = By.name("login");
    // private static final By logoutButtonLocator = By.cssSelector("div.woocommerce-MyAccount-content > p:nth-of-type(1) > a");
    private static final By logoutButtonLocator = By.linkText("Logout");
    private static final By accountDetailsButtonLocator = By.linkText("Account details");
    private static final By ordersButtonLocator = By.linkText("Orders");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/";

    public MyAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void enterUsernameForLogin(String username) {
        this.enterTextInField(username, usernameLoginFieldLocator);
    }

    public void enterPasswordForLogin(String password) {
        this.enterTextInField(password, passwordLoginFieldLocator);
    }

    public void clickLoginButton() {
        this.clickWhenClickable(loginButtonLocator);
    }

    public void loginUser(String username, String password) {
        this.enterUsernameForLogin(username);
        this.enterPasswordForLogin(password);
        this.clickLoginButton();
    }

    public void logoutUser() {
        this.clickKnownWhenClickable(logoutButtonLocator);
        // as presence should be guaranteed, fail if couldn't click it
    }

    public void clickAccountDetailsButton() {
        this.clickWhenClickable(accountDetailsButtonLocator);
    }

    public void clickOrdersButton() {
        this.clickWhenClickable(ordersButtonLocator);
    }
}
