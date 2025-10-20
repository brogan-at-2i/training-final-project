package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class ViewOrderPage extends Page {

    private static final By productOrderedLocator = By.cssSelector("section.woocommerce-order-details > table tr:nth-of-type(1) > td:nth-of-type(1) > a");
    private static final By orderTotalLocator = By.cssSelector("section.woocommerce-order-details > table > tfoot > tr:nth-child(4) > td > span");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/view-order/";

    public ViewOrderPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL, false, true);
    }

    public ViewOrderPage captureSingleProductOrdered(HashMap<String, String> capturedValuesMap) {
        // does work with >1 item ordered, will just return the first in this case
        String productOrdered =  this.captureElementText(productOrderedLocator);
        capturedValuesMap.put("productOrdered", productOrdered);
        return this;
    }

    public ViewOrderPage captureOrderTotal(HashMap<String, String> capturedValuesMap) {
        String orderTotalFromViewOrder = this.captureElementText(orderTotalLocator);
        capturedValuesMap.put("orderTotalFromViewOrder", orderTotalFromViewOrder);
        return this;
    }
}
