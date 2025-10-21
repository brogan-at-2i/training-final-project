package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.HasNavbar;
import com.two_itesting.brogan_personal.poms.base.Page;
import com.two_itesting.brogan_personal.poms.components.NavbarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends Page<OrderReceivedPage> implements HasNavbar {

    private static final By orderNumberLocator = By.cssSelector("div.woocommerce-order > ul > li:nth-of-type(1) > strong");
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

    public String captureOrderNumber() {
        return this.captureElementText(orderNumberLocator);
    }

    public String captureOrderEmail() {
        return this.captureElementText(orderEmailLocator);
    }

    public String captureOrderTotal() {
        return this.captureElementText(orderTotalLocator);
    }

    public String captureOrderPaymentMethod() {
        return this.captureElementText(orderPaymentMethodLocator);
    }

    public NavbarComponent navbar() {
        return this.navbar;
    }

}
