package com.two_itesting.brogan_personal.poms.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class is a base class for a page of the Edgewords Shop, and
 * contains locators for the (relevant) elements common to each page.
 */
public abstract class Page<T extends Page<T>> {

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/";
    private static final int INTERCEPT_RETRIES = 2;

    private static final By overlayLocator = By.cssSelector("div[class=\"blockUI blockOverlay\"]");

    protected final WebDriver driver;
    protected final String url;
    protected final WebDriverWait wait;

    public Page(WebDriver driver, WebDriverWait wait, String url, boolean navigateTo, boolean subpage) {
        this.driver = driver;
        this.wait = wait;
        this.url = url;
        if (navigateTo) {
            this.driver.get(url);
        }
//        if (subpage) { // if need to check on subpage
//            this.waitUntilOnSubOfThisPage();
//        } else { // else just a normal page
//            this.waitUntilOnThisPage();
//        }
    }

    public Page(WebDriver driver, WebDriverWait wait, String url) {
        this(driver, wait, url, false,false);
    }

    protected T enterTextInField(String text, By locator) {
        this.getElementWhenVisible(locator).sendKeys(text);
        return (T) this;
    }

    protected T clearField(By locator) {
        this.getElementWhenVisible(locator).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        return (T) this;
    }

    protected T clickWhenClickable(By locator) throws TimeoutException, NoSuchElementException, ElementClickInterceptedException {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        return (T) this;
    }

    protected T clickWhenClickableToRemove(By locator) throws TimeoutException, NoSuchElementException, ElementClickInterceptedException {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        WebElement foundElement = this.wait.until(ExpectedConditions.elementToBeClickable(locator));
        foundElement.click();
        this.wait.until(ExpectedConditions.invisibilityOf(foundElement));
        return (T) this;
    }


    public T navigateToThisPage() {
        // check if already on this page - if so then do nothing
        if (!this.url.equalsIgnoreCase(this.driver.getCurrentUrl())) {
            this.driver.get(this.url);
        }
        return (T) this;
    }

    public T waitUntilOnThisPage() {
        this.wait.until(ExpectedConditions.urlToBe(this.url));
        return (T) this;
    }

    public T waitUntilOnSubOfThisPage() {
        this.wait.until(ExpectedConditions.urlContains(this.url));
        return (T) this;
    }

    protected WebElement getElementWhenVisible(By locator) {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String captureElementText(By locator) {
        return this.getElementWhenVisible(locator).getText();
    }

}
