package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for webpages interacted with by WebDriver, holding basic functionality
 * and attributes not specific to the Edgewords site.
 */
public abstract class Page {

    protected final WebDriver driver;
    protected final String url;
    protected final WebDriverWait wait;

    private static final int INTERCEPT_RETRIES = 2;

    public Page(WebDriver driver, WebDriverWait wait, String url) {
        this.driver = driver;
        this.wait = wait;
        this.url = url;
    }

    protected void enterTextInField(String text, By locator) {
        // wait by default
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    protected void clearField(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
    }

    public void navigateToThisPage() {
        this.driver.get(this.url);
    }

    public void waitUntilOnThisPage() {
        this.wait.until(ExpectedConditions.urlToBe(this.url));
    }

    public void waitUntilOnSubOfThisPage() {
        this.wait.until(ExpectedConditions.urlContains(this.url));
    }

    protected void clickWhenClickable(By locator) throws TimeoutException, NoSuchElementException, ElementClickInterceptedException {
        this.driver.findElement(locator);
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void clickKnownWhenClickable(By locator) throws TimeoutException {
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void clickWhenClickableWithInterceptionRetry(By locator) throws NoSuchElementException {
        int currentRetries = 0;
        while (currentRetries < INTERCEPT_RETRIES) {
            try {
                this.clickWhenClickable(locator);
                return; // exit early, click is done
            } catch (ElementClickInterceptedException e) {
                // do nothing, fall down into the loop
            }

            currentRetries++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Max retries exceeded, fail loudly");
    }

}
