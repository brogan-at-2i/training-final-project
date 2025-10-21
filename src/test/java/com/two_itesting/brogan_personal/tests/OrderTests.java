package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.models.capture.PlacedOrderDetails;
import com.two_itesting.brogan_personal.models.site.Product;
import com.two_itesting.brogan_personal.models.site.User;
import com.two_itesting.brogan_personal.tests.base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test the functionality available when logged in to the Edgewords Shop.
 */
public class OrderTests extends TestBase {

    @ParameterizedTest
    @MethodSource(TEST_DATA_SOURCE + "#provideForTestPlacedOrderIsTracked")
    public void testPlacedOrderIsTracked(User user, Product product) {
        this.shoppingSteps.login(user);
        this.shoppingSteps.placeOrderForProduct(product, user);
        PlacedOrderDetails capturedPlacedOrderDetails = this.shoppingSteps.capturePlacedOrderDetails();
        this.shoppingSteps.viewAllPlacedOrders();
        PlacedOrderDetails capturedMostRecentOrderDetails = this.shoppingSteps.captureMostRecentOrderDetails();
        this.shoppingSteps.logout();
        assertThat(capturedPlacedOrderDetails, equalTo(capturedMostRecentOrderDetails));
    }
}
