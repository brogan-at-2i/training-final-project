package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.models.Coupon;
import com.two_itesting.brogan_personal.models.capture.PlacedOrderDetails;
import com.two_itesting.brogan_personal.poms.pages.MyAccountPage;
import com.two_itesting.brogan_personal.poms.pages.OrderReceivedPage;
import com.two_itesting.brogan_personal.test_data.EdgewordsTestDataSource;
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
import static org.hamcrest.Matchers.equalTo;

/**
 * Test the functionality available when logged in to the Edgewords Shop.
 */
public class OrderTests extends TestBase {

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
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestPlacedOrderIsTracked")
    public void testPlacedOrderIsTracked(User user, String productName) {
        try {
            this.shoppingSteps.login(user);
            this.shoppingSteps.placeOrderForProduct(productName, user);
            PlacedOrderDetails capturedPlacedOrderDetails = this.shoppingSteps.capturePlacedOrderDetails();
            this.shoppingSteps.viewAllPlacedOrders();
            PlacedOrderDetails capturedMostRecentOrderDetails = this.shoppingSteps.captureMostRecentOrderDetails();
            this.shoppingSteps.logout();
            assertThat(capturedPlacedOrderDetails, equalTo(capturedMostRecentOrderDetails));
        } catch (Exception e) {
            CaptureHelper.takeAndSaveScreenshot((TakesScreenshot) driver, "testPlacedOrderIsTracked");
            throw e;
        }
    }
}
