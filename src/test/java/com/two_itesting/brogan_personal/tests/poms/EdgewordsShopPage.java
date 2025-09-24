package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class is a base class for a page of the Edgewords Shop, and
 * contains locators for the (relevant) elements common to each page.
 * An alternative to this base class would have been to use component
 * POM objects and composition, rather than inheritance.
 */
public abstract class EdgewordsShopPage extends Page {

    // hone in on id then move from there
    private static final By shopNavbarButtonLocator = By.cssSelector("#menu-item-43 > a");
    private static final By cartNavbarButtonLocator = By.cssSelector("#menu-item-44 > a");
    private static final By myAccountNavbarButtonLocator = By.cssSelector("#menu-item-46 > a");
    private static final By bottomDisclaimerLocator = By.className("woocommerce-store-notice__dismiss-link");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/";

    public EdgewordsShopPage(WebDriver driver, WebDriverWait wait) {
        this(driver, wait, URL);
    }

    public EdgewordsShopPage(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
    }

    public void clickShopInNavbar() {
        this.clickKnownWhenClickable(shopNavbarButtonLocator);
    }

    public void clickCartInNavbar() {
        this.clickKnownWhenClickable(cartNavbarButtonLocator);
    }

    public void dismissDisclaimer() {
        this.clickWhenClickable(bottomDisclaimerLocator);
    }

    public void clickMyAccountInNavbar() {
        // account for fact that it may be intercepted
        this.clickWhenClickableWithInterceptionRetry(myAccountNavbarButtonLocator);
    }

    public double interpretPricedAsDouble(String priceString) {
        if (priceString.charAt(0) == '£') { // assume GBP
            return Double.parseDouble(priceString.substring(1)); // parse from 2nd char
        } else if (priceString.charAt(0) == '-' && priceString.charAt(1) == '£') {
            return Double.parseDouble("-" + priceString.substring(2));
        } // else ...
        throw new RuntimeException("Data not parseable");
    }
}
