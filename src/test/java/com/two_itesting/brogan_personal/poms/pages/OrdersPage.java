package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersPage extends Page<OrdersPage> {

    // e.g. order number is #1234
    private static final String viewOrderButtonLocatorStringTemplate = "#%s";

    private static final By mostRecentOrderNumberLocator = By.cssSelector("table tr:nth-of-type(1) > td[data-title=\"Order\"] > a");
    private static final By mostRecentOrderTotalLocator = By.cssSelector("table tr:nth-of-type(1) > td[data-title=\"Total\"] > span");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/orders/";

    public OrdersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public ViewOrderPage clickToViewOrder(String orderNumber) {
        By orderViewButtonLocator = By.linkText(viewOrderButtonLocatorStringTemplate.formatted(orderNumber));
        this.clickWhenClickable(orderViewButtonLocator);
        return new ViewOrderPage(this.driver, this.wait);
    }

    public String captureMostRecentOrderNumber() {
        return this.captureElementText(mostRecentOrderNumberLocator);
    }

    public String captureMostRecentOrderTotal() {
        return this.captureElementText(mostRecentOrderTotalLocator);
    }
}
