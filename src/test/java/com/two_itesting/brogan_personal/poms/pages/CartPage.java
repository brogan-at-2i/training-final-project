package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.Page;
import com.two_itesting.brogan_personal.poms.base.HasNavbar;
import com.two_itesting.brogan_personal.poms.components.NavbarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartPage extends Page<CartPage> implements HasNavbar {

    private static final By couponEntryLocator = By.id("coupon_code");
    private static final By couponApplyButtonLocator = By.name("apply_coupon");
    private static final By removeProductButtonLocator = By.className("remove");
    private static final By removeCouponButtonLocator = By.className("woocommerce-remove-coupon");
    private static final By cartSubtotalLocator = By.cssSelector("tr.cart-subtotal > td > span > bdi");
    private static final By productSubtotalLocator = By.cssSelector("td.product-price > span > bdi");
    private static final By couponDeductionLabelLocator = By.cssSelector("div.cart_totals tbody tr:nth-of-type(2) > td > span");
    private static final By shippingLabelLocator = By.cssSelector("#shipping_method > li > label > span > bdi");
    private static final By finalCartTotalLocator = By.cssSelector("tr.order-total > td > strong > span > bdi");
    private static final By checkoutButtonLocator = By.cssSelector("div.wc-proceed-to-checkout > a");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/cart/";

    private final NavbarComponent navbar;

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this(driver, wait, false);
    }

    public CartPage(WebDriver driver, WebDriverWait wait, boolean navigateTo) {
        super(driver, wait, URL, navigateTo, false);
        this.navbar = new NavbarComponent(this.driver, this.wait, URL);
    }

    public CartPage enterCouponCode(String couponCode) {
        return this.enterTextInField(couponCode, couponEntryLocator);
    }

    public CartPage clickApplyCouponButton() {
        return this.clickWhenClickable(couponApplyButtonLocator);
    }

    public CartPage clearCart() {
        List<WebElement> productsFound = this.driver.findElements(removeProductButtonLocator);
        List<WebElement> clickedButtons = new ArrayList<>();
        while (!productsFound.isEmpty() && clickedButtons.size() < productsFound.size()) {
            this.clickWhenClickableToRemove(removeProductButtonLocator);
            clickedButtons.add(productsFound.getFirst());
            productsFound = this.driver.findElements(removeProductButtonLocator);
        }
        return this;
    }

    public CartPage clearAppliedCoupons() {
        List<WebElement> removeCouponsButtonsFound = this.driver.findElements(removeCouponButtonLocator);
        List<WebElement> clickedButtons = new ArrayList<>();
        while (!removeCouponsButtonsFound.isEmpty() && clickedButtons.size() < removeCouponsButtonsFound.size()) {
            this.clickWhenClickableToRemove(removeCouponButtonLocator);
            clickedButtons.add(removeCouponsButtonsFound.getFirst());
            removeCouponsButtonsFound = this.driver.findElements(removeCouponButtonLocator);
        }
        return this;
    }

    public String captureCartSubtotal() {
        return this.captureElementText(cartSubtotalLocator);
    }

    public String captureCouponDeduction() {
        // often experience issues with this, so utilising waits
        return this.captureElementText(couponDeductionLabelLocator);
    }

    public String captureShippingCost() {
        return this.captureElementText(shippingLabelLocator);
    }

    public String captureFinalCartTotal() {
        return this.captureElementText(finalCartTotalLocator);
    }

    public CheckoutPage clickCheckoutButton() {
        this.clickWhenClickable(checkoutButtonLocator);
        return new CheckoutPage(this.driver, this.wait);
    }

    @Override
    public NavbarComponent navbar() {
        return this.navbar;
    }

    public String captureProductSubtotal() {
        return this.captureElementText(productSubtotalLocator);
    }
}