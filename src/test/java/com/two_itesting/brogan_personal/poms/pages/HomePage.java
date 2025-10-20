package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends Page<HomePage> {

    private static final By searchBarLocator = By.id("woocommerce-product-search-field-0");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/";

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void searchFor(String term) {
        this.getElementWhenVisible(searchBarLocator).sendKeys(term + Keys.ENTER);
    }
}
