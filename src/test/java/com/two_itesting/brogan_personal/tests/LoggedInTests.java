package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.tests.poms.pages.MyAccountPage;
import com.two_itesting.brogan_personal.tests.poms.pages.OrderReceivedPage;
import com.two_itesting.brogan_personal.tests.test_data.EdgewordsTestDataSource;
import com.two_itesting.brogan_personal.tests.test_data.User;
import com.two_itesting.brogan_personal.tests.utils.CaptureHelper;
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
public class LoggedInTests extends EdgewordsWebDriverTests {

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        super.setUp(); // ensure super constructs driver etc.
        User user = EdgewordsTestDataSource.DEFAULT_USER;
        new MyAccountPage(this.driver, this.wait, true)
                .dismissDisclaimer()
                .navbar().navigateToMyAccount()
                .loginUser(user.username(), user.password())
                .navbar().navigateToCart()
                .clearCart()
                .clearAppliedCoupons()
                .navbar().navigateToMyAccount();
        // leaves user in logged in state for following tests
    }

    @AfterEach
    public void tearDown() {
        new MyAccountPage(this.driver, this.wait, true)
                .logoutUser();
        super.tearDown(); // super handles driver teardown
    }

    @ParameterizedTest
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestCouponsApplied")
    public void testCouponsApplied(User user, String couponCode, BigDecimal couponValue, String productName) {
        try {
            // user is already logged in here, owing to setUp()
            new MyAccountPage(this.driver, this.wait)
                    .navbar().navigateToShop()
                    .clickAddToCartForProduct(productName)
                    .navbar().navigateToCart()
                    .enterCouponCode(couponCode)
                    .clickApplyCouponButton()
                    .captureProductSubtotal(this.capturedValuesMap)
                    .captureCartSubtotal(this.capturedValuesMap)
                    .captureCouponDeduction(this.capturedValuesMap)
                    .captureShippingCost(this.capturedValuesMap)
                    .captureFinalCartTotal(this.capturedValuesMap);
            // now retrieve captured (actual) values
            BigDecimal capturedProductSubtotal = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("productSubtotal"));
            BigDecimal capturedCartSubtotal = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("cartSubtotal"));
            BigDecimal capturedCouponDeduction = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("couponDeduction"));
            BigDecimal capturedShippingCost = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("shippingCost"));
            BigDecimal capturedFinalCartTotal = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("finalCartTotal"));
            // now calculate (expected) values
            BigDecimal calculatedCouponDeduction = capturedCartSubtotal.multiply(couponValue);
            BigDecimal calculatedFinalCartTotal = capturedCartSubtotal.subtract(calculatedCouponDeduction).add(capturedShippingCost);
            // now perform assertions based on these captured and calculated values
            assertThat(capturedProductSubtotal, comparesEqualTo(capturedCartSubtotal));
            assertThat(capturedCouponDeduction, comparesEqualTo(calculatedCouponDeduction));
            assertThat(capturedFinalCartTotal, comparesEqualTo(calculatedFinalCartTotal));
        } catch (Exception e) {
            CaptureHelper.takeAndSaveScreenshot((TakesScreenshot) driver, "testCouponsApplied");
            throw e;
        }
    }

    @ParameterizedTest
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestPlacedOrderIsTracked")
    public void testPlacedOrderIsTracked(User user, String productName) {
        try {
            // user is already logged in here, owing to setUp()
            OrderReceivedPage orderReceivedPage = new MyAccountPage(this.driver, this.wait)
                    .navbar().navigateToShop()
                    .clickAddToCartForProduct(productName)
                    .navbar().navigateToCart()
                    .clickCheckoutButton()
                    .enterName(user.firstName(), user.lastName())
                    .enterAddressDetails(user.streetAddress(), user.townCity(), user.postcode(), user.phoneNumber())
                    .clickCheckPaymentsOption()
                    .placeOrder()
                    .captureOrderNumber(this.capturedValuesMap)
                    .captureOrderTotal(this.capturedValuesMap);

            // order is now placed, save all the info captured so far as
            // needed for next part of test
            String capturedOrderNumber = this.capturedValuesMap.get("orderNumber");
            BigDecimal capturedOrderTotal = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("orderTotal"));

            // resume here
            orderReceivedPage
                    .navbar().navigateToMyAccount()
                    .clickOrdersButton()
                    .clickToViewOrder(capturedOrderNumber)
                    .captureOrderTotal(this.capturedValuesMap);

            BigDecimal capturedOrderTotalFromViewOrder = CaptureHelper.interpretPricedAsBigDecimal(this.capturedValuesMap.get("orderTotalFromViewOrder"));

            // now begin assertions
            assertThat(capturedOrderTotal, comparesEqualTo(capturedOrderTotalFromViewOrder));
        } catch (Exception e) {
            CaptureHelper.takeAndSaveScreenshot((TakesScreenshot) driver, "testPlacedOrderIsTracked");
            throw e;
        }
    }
}
