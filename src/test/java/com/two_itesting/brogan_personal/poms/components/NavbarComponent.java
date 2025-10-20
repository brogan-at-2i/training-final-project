package com.two_itesting.brogan_personal.poms.components;

import com.two_itesting.brogan_personal.poms.base.Page;
import com.two_itesting.brogan_personal.poms.pages.CartPage;
import com.two_itesting.brogan_personal.poms.pages.MyAccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavbarComponent extends Page<NavbarComponent> {

    private static final By shopNavbarButtonLocator = By.cssSelector("#menu-item-43 > a");
    private static final By cartNavbarButtonLocator = By.cssSelector("#menu-item-44 > a");
    private static final By myAccountNavbarButtonLocator = By.cssSelector("#menu-item-46 > a");


    public NavbarComponent(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url, false, true);
    }

    public com.two_itesting.brogan_personal.poms.pages.ShopPage navigateToShop() {
        this.clickWhenClickable(shopNavbarButtonLocator);
        return new com.two_itesting.brogan_personal.poms.pages.ShopPage(this.driver, this.wait);
    }

    public CartPage navigateToCart() {
        this.clickWhenClickable(cartNavbarButtonLocator);
        return new CartPage(this.driver, this.wait);
    }

    public MyAccountPage navigateToMyAccount() {
        this.clickWhenClickable(myAccountNavbarButtonLocator);
        return new MyAccountPage(this.driver, this.wait);
    }
}
