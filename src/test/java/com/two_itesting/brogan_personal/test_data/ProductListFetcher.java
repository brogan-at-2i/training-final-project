package com.two_itesting.brogan_personal.test_data;

import com.two_itesting.brogan_personal.models.site.Product;
import com.two_itesting.brogan_personal.poms.pages.ShopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductListFetcher {

    private static final By productNamesLocator = By.cssSelector("h2.woocommerce-loop-product__title");

    private static List<Product> productList;

    public static List<Product> fetchProductList() {
        if (productList == null) {
            WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
            driver.get(ShopPage.URL);
            productList = scrapeAllProducts(driver);
            driver.quit(); // cleanup
        }
        return productList;
    }

    private static List<Product> scrapeAllProducts(WebDriver driver) {
        List<WebElement> productNameElements = driver.findElements(productNamesLocator);
        List<Product> products = new ArrayList<>();
        for (WebElement productNameElement : productNameElements) {
            products.add(new Product(productNameElement.getText()));
        }
        return products;
    }

    public static Product getRandomProduct() {
        Random rng = new Random(); // random seed
        return fetchProductList().get(rng.nextInt(0, productList.size()));
    }

}