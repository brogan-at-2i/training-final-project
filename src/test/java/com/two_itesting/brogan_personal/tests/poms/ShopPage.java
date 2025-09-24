package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ShopPage extends EdgewordsShopPage {

    private static final By addToCartLocator = By.linkText("Add to cart");
    private static final String addToCartLocatorStringTemplate = "a[data-product_id=\"%s\"]";

    private static final String saleProductPriceLocatorStringTemplate = "li.post-%s > a > span.price ins";
    private static final String productPriceLocatorStringTemplate = "li.post-%s > a > span.price bdi";
    private static final String productNameLocatorStringTemplate = "li.post-%s > a > h2";

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/shop/";

    public ShopPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void clickAddToCartForProduct(String productId) {
        this.clickWhenClickable(By.cssSelector(addToCartLocatorStringTemplate.formatted(productId)));
    }

    public double captureProductPrice(String productId) {
        By onSalePriceLocator = By.cssSelector(saleProductPriceLocatorStringTemplate.formatted(productId));
        List<WebElement> prices = this.driver.findElements(onSalePriceLocator);
        WebElement priceElement;
        if (!prices.isEmpty()) {
            // then found a price, return it
            priceElement = prices.getFirst();
            return this.interpretPricedAsDouble(priceElement.getText());
        } // else, try assuming it isn't on sale
        By productPriceLocator = By.cssSelector(productPriceLocatorStringTemplate.formatted(productId));
        prices = this.driver.findElements(productPriceLocator);
        if (!prices.isEmpty()) {
            // then found a normal price, return it
            priceElement = prices.getFirst();
            return this.interpretPricedAsDouble(priceElement.getText());
        }
        throw new RuntimeException("Unable to capture price for product " + productId);
    }

    public String captureProductName(String productId) {
        By productNameLocator = By.cssSelector(productNameLocatorStringTemplate.formatted(productId));
        WebElement productNameElem = this.driver.findElement(productNameLocator);
        return productNameElem.getText();
    }

}
