package com.two_itesting.brogan_personal.tests.poms.pages;

import com.two_itesting.brogan_personal.tests.poms.base.EdgewordsShopPage;
import com.two_itesting.brogan_personal.tests.poms.base.HasNavbar;
import com.two_itesting.brogan_personal.tests.poms.components.NavbarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class OrderReceivedPage extends EdgewordsShopPage<OrderReceivedPage> implements HasNavbar {

    private static final By orderNumberLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(1) > strong");
    private static final By orderDateLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(2) > strong");
    private static final By orderEmailLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(3) > strong");
    private static final By orderTotalLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(4) > strong");
    private static final By orderPaymentMethodLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(5) > strong");

    // stem, as any order received is <URL>/<ordernumber>/?<wc-key>
    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/checkout/order-received/";

    private final NavbarComponent navbar;

    public OrderReceivedPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL, false, true); // will always need subpage match
        this.navbar = new NavbarComponent(driver, wait, URL);
    }

    public OrderReceivedPage captureOrderNumber(HashMap<String, String> capturedValuesMap) {
        String orderNumber = this.captureElementText(orderNumberLocator);
        capturedValuesMap.put("orderNumber", orderNumber);
        return this;
    }

    public OrderReceivedPage captureOrderDate(HashMap<String, String> capturedValuesMap) {
        String orderDate = this.captureElementText(orderDateLocator);
        capturedValuesMap.put("orderDate", orderDate);
        return this;
    }

    public OrderReceivedPage captureOrderEmail(HashMap<String, String> capturedValuesMap) {
        String orderEmail = this.captureElementText(orderEmailLocator);
        capturedValuesMap.put("orderEmail", orderEmail);
        return this;
    }

    public OrderReceivedPage captureOrderTotal(HashMap<String, String> capturedValuesMap) {
        String orderTotal = this.captureElementText(orderTotalLocator);
        capturedValuesMap.put("orderTotal", orderTotal);
        return this;
    }

    public OrderReceivedPage captureOrderPaymentMethod(HashMap<String, String> capturedValuesMap) {
        String orderPaymentMethod = this.captureElementText(orderPaymentMethodLocator);
        capturedValuesMap.put("orderPaymentMethod", orderPaymentMethod);
        return this;
    }

    public NavbarComponent navbar() {
        return this.navbar;
    }

}
