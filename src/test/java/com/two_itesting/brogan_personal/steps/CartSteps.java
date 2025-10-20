package com.two_itesting.brogan_personal.steps;

import com.two_itesting.brogan_personal.poms.pages.CartPage;
import com.two_itesting.brogan_personal.poms.pages.ShopPage;
import com.two_itesting.brogan_personal.steps.base.BaseSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartSteps extends BaseSteps {

    public CartSteps(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void addProductToCart(String product) {
        new ShopPage(this.driver, this.wait)
                .navigateToThisPage()
                .clickAddToCartForProduct(product);
    }

    public void applyCouponCodeToCart(String couponCode) {
        new CartPage(this.driver, this.wait)
                .navigateToThisPage()
                .enterCouponCode(couponCode)
                .clickApplyCouponButton();
    }

    public void clearAppliedCoupons() {
        new CartPage(this.driver, this.wait)
                .navigateToThisPage()
                .clearAppliedCoupons();
    }

    public void emptyCart() {
        new CartPage(this.driver, this.wait)
                .navigateToThisPage()
                .clearCart();
    }

}
