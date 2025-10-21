package com.two_itesting.brogan_personal.steps;

import com.two_itesting.brogan_personal.models.capture.PlacedOrderDetails;
import com.two_itesting.brogan_personal.poms.pages.MyAccountPage;
import com.two_itesting.brogan_personal.poms.pages.OrdersPage;
import com.two_itesting.brogan_personal.steps.base.BaseSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountSteps extends BaseSteps {

    public AccountSteps(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void loginUser(String username, String password) {
        new MyAccountPage(this.driver, this.wait).loginUser(username, password);
    }

    public void logoutUser() {
        new MyAccountPage(this.driver, this.wait).logoutUser();
    }

    public void viewAllPlacedOrders() {
        new MyAccountPage(this.driver, this.wait)
                .navigateToThisPage()
                .clickOrdersButton();
    }

    public PlacedOrderDetails captureMostRecentOrderDetails() {
        OrdersPage ordersPage = new OrdersPage(this.driver, this.wait);
        String capturedOrderNumber = ordersPage.captureMostRecentOrderNumber();
        String capturedOrderTotal = ordersPage.captureMostRecentOrderTotal();
        capturedOrderNumber = capturedOrderNumber.substring(1); // trim # off
        return new PlacedOrderDetails(capturedOrderNumber, capturedOrderTotal);
    }

    public void dismissDisclaimer() {
        new MyAccountPage(this.driver, this.wait).dismissDisclaimer();
    }
}
