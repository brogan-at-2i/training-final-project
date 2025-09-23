package com.two_itesting.brogan_personal.tests.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditAccountPage extends EdgewordsShopPage {

    private static final By firstNameFieldLocator = By.id("account_first_name");
    private static final By lastNameFieldLocator = By.id("account_last_name");
    private static final By saveChangesButtonLocator = By.name("save_account_details");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/edit-account/";

    public EditAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public void updateName(String firstName, String lastName) {
        this.clearField(firstNameFieldLocator);
        this.clearField(lastNameFieldLocator);
        this.enterTextInField(firstName, firstNameFieldLocator);
        this.enterTextInField(lastName, lastNameFieldLocator);
        this.saveChanges();
    }

    public void saveChanges() {
        this.clickWhenClickable(saveChangesButtonLocator);
    }
}
