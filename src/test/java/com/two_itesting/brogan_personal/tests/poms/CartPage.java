package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends EdgewordsShopPage {

    private static final By couponEntryLocator = By.id("coupon_code");
    private static final By couponApplyButtonLocator = By.name("apply_coupon");
    private static final By removeItemButtonLocator = By.className("remove");
    private static final By removeCouponButtonLocator = By.className("woocommerce-remove-coupon");
    private static final By cartSubtotalLocator = By.cssSelector("tr.cart-subtotal > td > span > bdi");
    private static final By couponDeductionLabelLocator = By.cssSelector("div.cart_totals tbody tr:nth-of-type(2) > td > span");
    private static final By shippingLabelLocator = By.cssSelector("#shipping_method > li > label > span > bdi");
    private static final By finalCartTotalLocator = By.cssSelector("tr.order-total > td > strong > span > bdi");
    private static final By checkoutButtonLocator = By.cssSelector("div.wc-proceed-to-checkout > a");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/cart/";

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void enterCouponCode(String couponCode) {
        this.enterTextInField(couponCode, couponEntryLocator);
    }

    public void clickApplyCouponButton() {
        this.clickWhenClickable(couponApplyButtonLocator);
    }

    public void clearCart() {
        boolean isItemsToRemove = true;
        while (isItemsToRemove) {
            try {
                this.clickWhenClickable(removeItemButtonLocator);
            } catch (NoSuchElementException e) {
                isItemsToRemove = false;
            } catch (StaleElementReferenceException s) {
                // WebDriver a little too fast still, ignore and try again
            } catch (ElementClickInterceptedException c) {
                // again it's too fast, so just wait again
            } catch (TimeoutException t) {
                // element gone stale so no longer clickable
            }
        }
    }

    public void clearAppliedCoupons() {
        boolean isCouponsToRemove = true;
        while (isCouponsToRemove) {
            try {
                this.clickWhenClickable(removeCouponButtonLocator);
            } catch (NoSuchElementException e) {
                isCouponsToRemove = false;
            } catch (StaleElementReferenceException s) {
                // WebDriver a little too fast still, ignore and try again
            } catch (ElementClickInterceptedException c) {
                // again it's too fast, so just wait again
            }
        }
    }

    public BigDecimal captureCartSubtotal() {
        WebElement cartSubtotalElem = this.wait.until(ExpectedConditions.visibilityOfElementLocated(cartSubtotalLocator));
        return this.interpretPricedAsBigDecimal(cartSubtotalElem.getText());
    }

    public BigDecimal captureCouponDeduction() {
        // often experience issues with this, so utilising waits
        WebElement couponDeductionElem = this.wait.until(ExpectedConditions.visibilityOfElementLocated(couponDeductionLabelLocator));
        return this.interpretPricedAsBigDecimal(couponDeductionElem.getText());
    }

    public BigDecimal captureShippingCost() {
        WebElement shippingCostElem = this.driver.findElement(shippingLabelLocator);
        return this.interpretPricedAsBigDecimal(shippingCostElem.getText());
    }

    public BigDecimal captureFinalCartTotal() {
        WebElement finalCartTotalElem = this.driver.findElement(finalCartTotalLocator);
        return this.interpretPricedAsBigDecimal(finalCartTotalElem.getText());
    }

    public void clickCheckoutButton() {
        this.clickWhenClickable(checkoutButtonLocator);
    }

    public void waitUntilProductInCart() {
        int retries = 0;
        List<WebElement> productsFound;
        while (retries < 5) { // 5 is arb number
            productsFound = this.driver.findElements(removeItemButtonLocator);
            if (!productsFound.isEmpty()) return;
            // else...
            this.driver.navigate().refresh(); // to await updated data
            try {
                Thread.sleep(1000); // tacky!
            } catch (InterruptedException ignored) {}
            retries++;
        }
        // if here, throw an error
        throw new RuntimeException("No item present in cart after " + retries + " attempts");
    }
}