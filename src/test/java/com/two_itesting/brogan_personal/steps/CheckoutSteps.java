package com.two_itesting.brogan_personal.steps;

import com.two_itesting.brogan_personal.models.capture.PlacedOrderDetails;
import com.two_itesting.brogan_personal.models.site.User;
import com.two_itesting.brogan_personal.poms.pages.CheckoutPage;
import com.two_itesting.brogan_personal.poms.pages.OrderReceivedPage;
import com.two_itesting.brogan_personal.steps.base.BaseSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutSteps extends BaseSteps {

    public CheckoutSteps(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void checkout(User user) {
        new CheckoutPage(this.driver, this.wait).navigateToThisPage()
                .enterName(user.firstName(), user.lastName())
                .enterAddressDetails(user.streetAddress(), user.townCity(), user.postcode(), user.phoneNumber())
                .clickCheckPaymentsOption()
                .placeOrder();
    }

    public PlacedOrderDetails capturePlacedOrderDetails() {
        OrderReceivedPage orderReceivedPage = new OrderReceivedPage(this.driver, this.wait);
        String capturedOrderNumber = orderReceivedPage.captureOrderNumber();
        String capturedOrderTotal = orderReceivedPage.captureOrderTotal();
        return new PlacedOrderDetails(capturedOrderNumber, capturedOrderTotal);
    }
}
