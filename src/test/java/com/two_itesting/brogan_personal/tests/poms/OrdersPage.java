package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersPage extends EdgewordsShopPage {

    private static final String viewOrderButtonLocatorStringTemplate = "#%s";

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/orders/";

    public OrdersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void clickToViewOrder(String orderNumber) {
        By orderViewButtonLocator = By.linkText(viewOrderButtonLocatorStringTemplate.formatted(orderNumber));
        this.clickWhenClickable(orderViewButtonLocator);
    }

}
