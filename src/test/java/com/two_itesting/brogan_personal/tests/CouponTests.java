package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.models.site.Coupon;
import com.two_itesting.brogan_personal.models.capture.FullCartDetails;
import com.two_itesting.brogan_personal.models.site.Product;
import com.two_itesting.brogan_personal.models.site.User;
import com.two_itesting.brogan_personal.tests.base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class CouponTests extends TestBase {

    @ParameterizedTest
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestCouponsApplied")
    public void testCouponsApplied(User user, Coupon coupon, Product product) {
        this.shoppingSteps.login(user);
        this.shoppingSteps.addProductToCartWithCoupon(product, coupon);
        FullCartDetails capturedCart = this.shoppingSteps.captureFulLCartDetails();
        this.shoppingSteps.logout();
        BigDecimal calculatedCouponDeduction = capturedCart.subtotal().multiply(coupon.discountValue());
        BigDecimal calculatedFinalCartTotal = capturedCart.subtotal().subtract(calculatedCouponDeduction).add(capturedCart.shippingCost());
        assertThat(capturedCart.firstProductSubtotal(), comparesEqualTo(capturedCart.subtotal()));
        assertThat(capturedCart.couponDeduction(), comparesEqualTo(calculatedCouponDeduction));
        assertThat(capturedCart.finalTotal(), comparesEqualTo(calculatedFinalCartTotal));
    }
}
