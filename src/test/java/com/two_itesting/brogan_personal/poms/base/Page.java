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
    }

    public Page(WebDriver driver, WebDriverWait wait, String url) {
        this(driver, wait, url, false, false);
    }

    protected T enterTextInField(String text, By locator) {
        this.getElementWhenVisible(locator).sendKeys(text);
        return self();
    }

    protected T clearField(By locator) {
        this.getElementWhenVisible(locator).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        return self();
    }

    protected T clickWhenClickable(By locator) throws TimeoutException, NoSuchElementException, ElementClickInterceptedException {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        return self();
    }

    protected T clickWhenClickableToRemove(By locator) throws TimeoutException, NoSuchElementException, ElementClickInterceptedException {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        WebElement foundElement = this.wait.until(ExpectedConditions.elementToBeClickable(locator));
        foundElement.click();
        this.wait.until(ExpectedConditions.invisibilityOf(foundElement));
        return self();
    }


    public T navigateToThisPage() {
        // check if already on this page - if so then do nothing
        if (!this.url.equalsIgnoreCase(this.driver.getCurrentUrl())) {
            this.driver.get(this.url);
        }
        return self();
    }

    public T waitUntilOnThisPage() {
        this.wait.until(ExpectedConditions.urlToBe(this.url));
        return self();
    }

    public T waitUntilOnSubOfThisPage() {
        this.wait.until(ExpectedConditions.urlContains(this.url));
        return self();
    }

    protected WebElement getElementWhenVisible(By locator) {
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String captureElementText(By locator) {
        return this.getElementWhenVisible(locator).getText();
    }

    @SuppressWarnings("unchecked")
    public T self() {
        //noinspection unchecked
        return (T) this;
    }

}
