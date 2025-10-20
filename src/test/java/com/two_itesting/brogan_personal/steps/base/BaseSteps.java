package com.two_itesting.brogan_personal.steps.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseSteps {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BaseSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

}
