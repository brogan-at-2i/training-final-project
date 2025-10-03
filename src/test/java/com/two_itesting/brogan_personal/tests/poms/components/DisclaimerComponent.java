package com.two_itesting.brogan_personal.tests.poms.components;

import com.two_itesting.brogan_personal.tests.poms.base.EdgewordsShopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DisclaimerComponent extends EdgewordsShopPage<DisclaimerComponent> {

    private static final By bottomDisclaimerLocator = By.className("woocommerce-store-notice__dismiss-link");


    public DisclaimerComponent(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
    }

    public DisclaimerComponent dismiss() {
        return this.clickWhenClickable(bottomDisclaimerLocator);
    }


}
