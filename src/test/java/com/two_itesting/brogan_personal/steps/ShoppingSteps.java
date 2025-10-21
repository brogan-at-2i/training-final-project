package com.two_itesting.brogan_personal.steps;

import com.two_itesting.brogan_personal.models.site.Coupon;
import com.two_itesting.brogan_personal.models.site.Product;
import com.two_itesting.brogan_personal.models.site.User;
import com.two_itesting.brogan_personal.models.capture.FullCartDetails;
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

    public void placeOrderForProductWithCoupon(Product product, User user, Coupon coupon) {
        this.addProductToCartWithCoupon(product, coupon);
        this.checkoutSteps.checkout(user);
    }

    public void placeOrderForProduct(Product product, User user) {
        this.placeOrderForProductWithCoupon(product, user, new Coupon("", null));
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

    public void addProductToCartWithCoupon(Product product, Coupon coupon) {
        this.cartSteps.addProductToCart(product.name());
        this.cartSteps.applyCouponCodeToCart(coupon.code());
    }

    public FullCartDetails captureFulLCartDetails() {
        return this.cartSteps.captureFullCartDetails();
    }
}
