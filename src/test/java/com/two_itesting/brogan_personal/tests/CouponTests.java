package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.models.Coupon;
import com.two_itesting.brogan_personal.poms.pages.MyAccountPage;
import com.two_itesting.brogan_personal.steps.AccountSteps;
import com.two_itesting.brogan_personal.models.User;
import com.two_itesting.brogan_personal.tests.base.TestBase;
import com.two_itesting.brogan_personal.utils.CaptureHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

/**
 * Test the functionality available when logged in to the Edgewords Shop.
 */
public class CouponTests extends TestBase {

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        super.setUp(); // ensure super constructs driver etc.
    }

    @AfterEach
    public void tearDown() {
        super.tearDown(); // super handles driver teardown
    }

    @ParameterizedTest
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestCouponsApplied")
    public void testCouponsApplied(User user, String couponCode, BigDecimal couponValue, String productName) {
        try {
            this.shoppingSteps.login(user);
            this.shoppingSteps.addProductToCartWithCoupon(productName, new Coupon(couponCode, couponValue));
            // now retrieve captured (actual) values
            BigDecimal capturedProductSubtotal;
            BigDecimal capturedCartSubtotal;
            BigDecimal capturedCouponDeduction;
            BigDecimal capturedShippingCost;
            BigDecimal capturedFinalCartTotal;
            // now calculate (expected) values
//            BigDecimal calculatedCouponDeduction = capturedCartSubtotal.multiply(couponValue);
//            BigDecimal calculatedFinalCartTotal = capturedCartSubtotal.subtract(calculatedCouponDeduction).add(capturedShippingCost);
//            // now perform assertions based on these captured and calculated values
//            assertThat(capturedProductSubtotal, comparesEqualTo(capturedCartSubtotal));
//            assertThat(capturedCouponDeduction, comparesEqualTo(calculatedCouponDeduction));
//            assertThat(capturedFinalCartTotal, comparesEqualTo(calculatedFinalCartTotal));
            this.shoppingSteps.logout();
        } catch (Exception e) {
            CaptureHelper.takeAndSaveScreenshot((TakesScreenshot) driver, "testCouponsApplied");
            throw e;
        }
    }
}
