package com.two_itesting.brogan_personal.poms.pages;

import com.two_itesting.brogan_personal.poms.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditAccountPage extends Page<EditAccountPage> {

    private static final By firstNameFieldLocator = By.id("account_first_name");
    private static final By lastNameFieldLocator = By.id("account_last_name");
    private static final By saveChangesButtonLocator = By.name("save_account_details");

    private static final String URL = "https://www.edgewordstraining.co.uk/demo-site/my-account/edit-account/";

    public EditAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, URL);
    }

    public EditAccountPage updateName(String firstName, String lastName) {
        // convenience function to encapsulate many different actions
        return this
                .clearField(firstNameFieldLocator)
                .clearField(lastNameFieldLocator)
                .enterTextInField(firstName, firstNameFieldLocator)
                .enterTextInField(lastName, lastNameFieldLocator)
                .saveChanges();
    }

    public EditAccountPage saveChanges() {
        return this.clickWhenClickable(saveChangesButtonLocator);
    }
}
