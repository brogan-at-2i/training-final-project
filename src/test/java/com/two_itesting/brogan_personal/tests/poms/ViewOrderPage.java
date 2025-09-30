package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class ViewOrderPage extends EdgewordsShopPage {

    private static final By productOrderedLocator = By.cssSelector("section.woocommerce-order-details > table tr:nth-of-type(1) > td:nth-of-type(1) > a");
    private static final By orderTotalLocator = By.cssSelector("section.woocommerce-order-details > table > tfoot > tr:nth-child(4) > td > span");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/view-order/";

    public ViewOrderPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public String captureSingleItemOrdered() {
        // does work with >1 item ordered, will just return the first in this case
        WebElement itemOrderedElem = this.driver.findElement(productOrderedLocator);
        return  itemOrderedElem.getText();
    }

    public BigDecimal captureOrderTotal() {
        WebElement orderTotalElem = this.driver.findElement(orderTotalLocator);
        return this.interpretPricedAsBigDecimal(orderTotalElem.getText());
    }
}
