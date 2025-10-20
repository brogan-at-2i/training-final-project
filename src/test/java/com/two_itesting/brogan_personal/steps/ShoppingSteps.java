package com.two_itesting.brogan_personal.steps;

import com.two_itesting.brogan_personal.models.Coupon;
import com.two_itesting.brogan_personal.models.User;
import com.two_itesting.brogan_personal.models.capture.PlacedOrderDetails;
import com.two_itesting.brogan_personal.poms.pages.CartPage;
import com.two_itesting.brogan_personal.poms.pages.MyAccountPage;
import com.two_itesting.brogan_personal.steps.base.BaseSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingSteps extends BaseSteps {

    protected AccountSteps accountSteps;
    protected CartSteps cartSteps;
    protected CheckoutSteps checkoutSteps;

    public ShoppingSteps(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.accountSteps = new AccountSteps(driver, wait);
        this.cartSteps = new CartSteps(driver, wait);
        this.checkoutSteps = new CheckoutSteps(driver, wait);
    }

    public void login(User user) {
        new MyAccountPage(this.driver, this.wait, true);
        this.accountSteps.loginUser(user.username(), user.password());
    }

    public void placeOrderForProductWithCoupon(String productName, User user, Coupon coupon) {
        this.addProductToCartWithCoupon(productName, coupon);
        this.checkoutSteps.checkout(user);
    }

    public void placeOrderForProduct(String productName, User user) {
        this.placeOrderForProductWithCoupon(productName, user, new Coupon("", null));
    }

    public PlacedOrderDetails capturePlacedOrderDetails() {
        return this.checkoutSteps.capturePlacedOrderDetails();
    }

    public void resetCart() {
        new CartPage(this.driver, this.wait).navigateToThisPage();
        this.cartSteps.clearAppliedCoupons();
        this.cartSteps.emptyCart();
    }

    public void logout() {
        new MyAccountPage(this.driver, this.wait).navigateToThisPage();
        this.accountSteps.logoutUser();
    }

    public void viewAllPlacedOrders() {
        this.accountSteps.viewAllPlacedOrders();
    }

    public PlacedOrderDetails captureMostRecentOrderDetails() {
        return this.accountSteps.captureMostRecentOrderDetails();
    }

    public void dismissDisclaimer() {
        this.accountSteps.dismissDisclaimer();
    }

    public void addProductToCartWithCoupon(String productName, Coupon coupon) {
        this.cartSteps.addProductToCart(productName);
        this.cartSteps.applyCouponCodeToCart(coupon.couponCode());
    }
}
