package com.two_itesting.brogan_personal.poms.components;

import com.two_itesting.brogan_personal.poms.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DisclaimerComponent extends Page<DisclaimerComponent> {

    private static final By bottomDisclaimerLocator = By.className("woocommerce-store-notice__dismiss-link");

    public DisclaimerComponent(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
    }

    public DisclaimerComponent dismiss() {
        return this.clickWhenClickable(bottomDisclaimerLocator);
    }


}
