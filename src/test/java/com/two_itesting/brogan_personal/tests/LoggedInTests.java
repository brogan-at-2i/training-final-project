package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.tests.arg_providers.LoggedInTestsArgProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

/**
 * Test the functionality available when logged in to the Edgewords Shop.
 */
public class LoggedInTests extends EdgewordsWebDriverTests {

    private static Properties properties;

    @BeforeAll
    public static void initProperties() throws IOException {
        // static, get props
        properties = new Properties();
        properties.load(LoggedInTestsArgProvider.loadResourceAsStream(LoggedInTestsArgProvider.TEST_PROPERTIES_FILENAME));
    }

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        super.setUp(); // ensure super constructs driver etc.
        this.myAccountPage.navigateToThisPage();
        this.myAccountPage.dismissDisclaimer();
        this.myAccountPage.loginUser(properties.getProperty("username"), properties.getProperty("password"));
        this.myAccountPage.clickCartInNavbar();
        this.cartPage.waitUntilOnThisPage();
        this.cartPage.clearCart();
        this.cartPage.clearAppliedCoupons();
        this.cartPage.clickMyAccountInNavbar();
        this.myAccountPage.waitUntilOnThisPage();
        this.myAccountPage.clickAccountDetailsButton();
        this.editAccountPage.waitUntilOnThisPage();
        this.editAccountPage.updateName(properties.getProperty("firstName", ""), properties.getProperty("lastName", ""));

        this.myAccountPage.logoutUser();
    }

    @AfterEach
    public void tearDown() {
        this.myAccountPage.navigateToThisPage();
        this.myAccountPage.logoutUser();
        super.tearDown(); // super handles driver teardown
    }

    @ParameterizedTest
    @ArgumentsSource(LoggedInTestsArgProvider.class)
    public void testCouponsApplied(String username, String password, String discountCode, double discountValue, String productCode) {
        try {
            this.myAccountPage.navigateToThisPage();
            this.myAccountPage.loginUser(username, password);
            this.myAccountPage.clickShopInNavbar();
            // now moving onto shop page
            this.shopPage.waitUntilOnThisPage();
            // product price captured for checking once basket reached
            double productPrice = this.shopPage.captureProductPrice(productCode);
            this.shopPage.clickAddToCartForProduct(productCode);
            this.shopPage.clickCartInNavbar();
            // now moving onto cart page
            this.cartPage.waitUntilOnThisPage();
            this.cartPage.waitUntilProductInCart();
            this.cartPage.enterCouponCode(discountCode);
            this.cartPage.clickApplyCouponButton();
            double cartSubtotal = this.cartPage.captureCartSubtotal();
            assertThat(cartSubtotal, closeTo(productPrice, CURRENCY_ERR));
            // assert here cart subtotal = captured price from earlier
            // then assert that the money removed is subtotal * discountValue
            // then assert that subtotal - discount value + shipping = total
            double couponDeduction = this.cartPage.captureCouponDeduction();
            assertThat(couponDeduction, closeTo(cartSubtotal * discountValue, CURRENCY_ERR));
            double shippingCost = this.cartPage.captureShippingCost();
            double finalCartTotal = this.cartPage.captureFinalCartTotal();
            assertThat(finalCartTotal, closeTo(cartSubtotal - couponDeduction + shippingCost, CURRENCY_ERR));
        } catch (Exception e) {
            this.takeAndSaveScreenshot("testCouponsApplied");
            throw e;
        }
    }

    @ParameterizedTest
    @ArgumentsSource(LoggedInTestsArgProvider.class)
    public void testPlacedOrderIsTracked(String username, String password, String streetAddress, String townCity, String postcode, String phoneNumber, String productCode) {
        try {
            this.myAccountPage.navigateToThisPage();
            this.myAccountPage.loginUser(username, password);
            this.myAccountPage.clickShopInNavbar();
            // now moving onto shop page
            this.shopPage.waitUntilOnThisPage();
            // product price captured for checking once basket reached
            double productPrice = this.shopPage.captureProductPrice(productCode);
            String productName = this.shopPage.captureProductName(productCode);
            this.shopPage.clickAddToCartForProduct(productCode);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException i) {
                // do nothing
            }
            this.shopPage.clickCartInNavbar();
            // now moving onto cart page
            this.cartPage.waitUntilOnThisPage();
            this.cartPage.waitUntilProductInCart();
            double cartSubtotal = this.cartPage.captureCartSubtotal();
            assertThat(cartSubtotal, closeTo(productPrice, CURRENCY_ERR));
            // still only need one -- can just update the billing address form
            // and gets shipped there
            this.cartPage.clickCheckoutButton();
            this.checkoutPage.waitUntilOnThisPage();
            this.checkoutPage.enterAddressDetails(streetAddress, townCity, postcode, phoneNumber);
            this.checkoutPage.clickCheckPaymentsOption();
            this.checkoutPage.placeOrder();
            this.orderReceivedPage.waitUntilOnSubOfThisPage();
            String capturedOrderNumber = this.orderReceivedPage.captureOrderNumber();
            String capturedDate = this.orderReceivedPage.captureOrderDate();
            String capturedEmail = this.orderReceivedPage.captureOrderEmail();
            double capturedTotal = this.orderReceivedPage.captureOrderTotal();
            String capturedPaymentMethod = this.orderReceivedPage.captureOrderPaymentMethod();
            this.orderReceivedPage.clickMyAccountInNavbar();
            this.myAccountPage.waitUntilOnThisPage();
            this.myAccountPage.clickOrdersButton();
            this.ordersPage.clickToViewOrder(capturedOrderNumber);
            this.viewOrderPage.waitUntilOnSubOfThisPage();
            String capturedOrderProductName = this.viewOrderPage.captureSingleItemOrdered();
            double capturedOrderTotal = this.viewOrderPage.captureOrderTotal();
            assertThat(capturedOrderTotal, closeTo(capturedTotal, CURRENCY_ERR));
        } catch (Exception e) {
            this.takeAndSaveScreenshot("testPlacedOrderIsTracked");
            throw e;
        }
    }

    private void takeAndSaveScreenshot(String filename) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get("./target/screenshots/" + filename + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".png");
        try {
            Files.createDirectories(Paths.get("./target/screenshots"));
            Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);  // Move the screenshot file to the destination
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Screenshot saved at: " + destination);
    }
}
