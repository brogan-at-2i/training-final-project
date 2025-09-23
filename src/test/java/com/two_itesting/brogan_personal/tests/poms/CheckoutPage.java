package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends EdgewordsShopPage {

    private static final By streetAddressFieldLocator = By.id("billing_address_1");
    private static final By addressLineTwoFieldLocator = By.id("billing_address_2");
    private static final By townCityFieldLocator = By.id("billing_city");
    private static final By countyFieldLocator = By.id("billing_state");
    private static final By postcodeFieldLocator = By.id("billing_postcode");
    private static final By phoneNumberFieldLocator = By.id("billing_phone");
    private static final By checkPaymentsButtonLocator = By.cssSelector("#payment > ul > li:nth-of-type(1) > label");
    private static final By placeOrderButtonLocator = By.id("place_order");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/checkout/";

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void enterAddressDetails(String streetAddress, String townCity, String postcode, String phoneNumber) {
        this.clearField(streetAddressFieldLocator);
        this.clearField(addressLineTwoFieldLocator); // clear but don't repopulate
        this.clearField(townCityFieldLocator);
        this.clearField(countyFieldLocator); // clear but don't repopulate
        this.clearField(postcodeFieldLocator);
        this.clearField(phoneNumberFieldLocator);
        this.enterTextInField(streetAddress, streetAddressFieldLocator);
        this.enterTextInField(townCity, townCityFieldLocator);
        this.enterTextInField(postcode, postcodeFieldLocator);
        this.enterTextInField(phoneNumber, phoneNumberFieldLocator);
    }

    public void clickCheckPaymentsOption() {
        this.clickWhenClickableWithInterceptionRetry(checkPaymentsButtonLocator);
    }

    public void placeOrder() {
        this.clickWhenClickableWithInterceptionRetry(placeOrderButtonLocator);
    }



}
