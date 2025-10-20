package com.two_itesting.brogan_personal.test_data;

import com.two_itesting.brogan_personal.poms.pages.ShopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EdgewordsProductListFetcher {

    private static final By productNamesLocator = By.cssSelector("h2.woocommerce-loop-product__title");

    private static final List<String> productList = fetchProductList();

    public static List<String> fetchProductList() {
        if (productList != null) {
            return productList;
        } // else
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
        driver.get(ShopPage.URL);
        List<String> productList = scrapeAllProducts(driver);
        driver.quit(); // cleanup
        return productList;
    }

    private static List<String> scrapeAllProducts(WebDriver driver) {
        List<WebElement> productNameElements = driver.findElements(productNamesLocator);
        List<String> productNames = new ArrayList<>();
        for (WebElement productNameElement : productNameElements) {
            productNames.add(productNameElement.getText());
        }
        return productNames;
    }

    public static String getRandomProductName() {
        Random rng = new Random(); // random seed
        return productList.get(rng.nextInt(0, productList.size()));
    }

}