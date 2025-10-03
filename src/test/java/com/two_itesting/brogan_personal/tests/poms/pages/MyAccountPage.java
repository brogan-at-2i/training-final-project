package com.two_itesting.brogan_personal.tests.poms.pages;

import com.two_itesting.brogan_personal.tests.poms.base.EdgewordsShopPage;
import com.two_itesting.brogan_personal.tests.poms.base.HasNavbar;
import com.two_itesting.brogan_personal.tests.poms.components.DisclaimerComponent;
import com.two_itesting.brogan_personal.tests.poms.components.NavbarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends EdgewordsShopPage<MyAccountPage> implements HasNavbar {

    private static final By usernameLoginFieldLocator = By.id("username");
    private static final By passwordLoginFieldLocator = By.id("password");
    private static final By loginButtonLocator = By.name("login");
    private static final By logoutButtonLocator = By.linkText("Logout");
    private static final By accountDetailsButtonLocator = By.linkText("Account details");
    private static final By ordersButtonLocator = By.linkText("Orders");

    private final NavbarComponent navbar;
    private final DisclaimerComponent disclaimer;

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/";

    public MyAccountPage(WebDriver driver, WebDriverWait wait, boolean navigateTo) {
        super(driver, wait, URL, navigateTo, false);
        this.navbar = new NavbarComponent(driver, wait, URL);
        this.disclaimer = new DisclaimerComponent(driver, wait, URL);
    }

    public MyAccountPage(WebDriver driver, WebDriverWait wait) {
        this(driver, wait, false);
    }

    public MyAccountPage enterUsernameForLogin(String username) {
        return this.enterTextInField(username, usernameLoginFieldLocator);
    }

    public MyAccountPage enterPasswordForLogin(String password) {
        return this.enterTextInField(password, passwordLoginFieldLocator);
    }

    public MyAccountPage clickLoginButton() {
        return this.clickWhenClickable(loginButtonLocator);
    }

    public MyAccountPage loginUser(String username, String password) {
        return this.enterUsernameForLogin(username)
                .enterPasswordForLogin(password).
                clickLoginButton();
    }

    public MyAccountPage logoutUser() {
        return this.clickWhenClickable(logoutButtonLocator);
    }

    public MyAccountPage clickAccountDetailsButton() {
        return this.clickWhenClickable(accountDetailsButtonLocator);
    }

    public OrdersPage clickOrdersButton() {
        this.clickWhenClickable(ordersButtonLocator);
        return new OrdersPage(this.driver, this.wait);
    }

    @Override
    public NavbarComponent navbar() {
        return this.navbar;
    }

    public MyAccountPage dismissDisclaimer() {
        this.disclaimer.dismiss();
        return this;
    }
}
