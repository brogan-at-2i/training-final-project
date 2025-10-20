package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.HasNavbar;
import com.two_itesting.brogan_personal.poms.base.Page;
import com.two_itesting.brogan_personal.poms.components.NavbarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShopPage extends Page<ShopPage> implements HasNavbar {

    private static final By loadingStatusLocator = By.className("loading");

    private static final String addToCartLocatorStringTemplate = "a[aria-label*=\"“%s”\"]"; // wildcard for contains
    private static final String saleProductPriceLocatorStringTemplate = "li.post-%s > a > span.price ins";
    private static final String productPriceLocatorStringTemplate = "li.post-%s > a > span.price bdi";
    private static final String productNameLocatorStringTemplate = "li.post-%s > a > h2";

    public static final String URL = "https://www.edgewordstraining.co.uk/demo-site/shop/";

    private final NavbarComponent navbar;

    public ShopPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
        this.navbar = new NavbarComponent(driver, wait, URL);
    }

    public ShopPage clickAddToCartForProduct(String productName) {
        this.clickWhenClickable(By.cssSelector(addToCartLocatorStringTemplate.formatted(productName)));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingStatusLocator));
        return this;
    }

    @Override
    public NavbarComponent navbar() {
        return this.navbar;
    }
}
