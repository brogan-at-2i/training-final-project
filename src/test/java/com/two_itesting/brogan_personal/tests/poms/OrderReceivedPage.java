package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends EdgewordsShopPage {

    private static final By orderNumberLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(1) > strong");
    private static final By orderDateLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(2) > strong");
    private static final By orderEmailLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(3) > strong");
    private static final By orderTotalLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(4) > strong");
    private static final By orderPaymentMethodLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(5) > strong");

    // stem, as any order received is <URL>/<ordernumber>/?<wc-key>
    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/checkout/order-received/";

    public OrderReceivedPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public String captureOrderNumber() {

        WebElement orderNumberElem = this.wait.until(ExpectedConditions.presenceOfElementLocated(orderNumberLocator));
        return orderNumberElem.getText();
    }

    public String captureOrderDate() {
        WebElement orderDateElem = this.driver.findElement(orderDateLocator);
        return orderDateElem.getText();
    }

    public String captureOrderEmail() {
        WebElement orderEmailElem = this.driver.findElement(orderEmailLocator);
        return orderEmailElem.getText();
    }

    public double captureOrderTotal() {
        WebElement orderTotalElem = this.driver.findElement(orderTotalLocator);
        return this.interpretPricedAsDouble(orderTotalElem.getText());
    }

    public String captureOrderPaymentMethod() {
        WebElement orderPaymentMethod = this.driver.findElement(orderPaymentMethodLocator);
        return orderPaymentMethod.getText();
    }

}
