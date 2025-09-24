package com.two_itesting.brogan_personal.tests;

import com.two_itesting.brogan_personal.tests.poms.*;
import com.two_itesting.brogan_personal.tests.utils.DriverType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

@EnumSource(DriverType.class)
@ParameterizedClass
public abstract class EdgewordsWebDriverTests {

    @Parameter(0)
    protected DriverType driverType; // parameterised

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected RootPage rootPage;
    protected MyAccountPage myAccountPage;
    protected ShopPage shopPage;
    protected CartPage cartPage;
    protected EditAccountPage editAccountPage;
    protected CheckoutPage checkoutPage;
    protected OrderReceivedPage orderReceivedPage;
    protected OrdersPage ordersPage;
    protected ViewOrderPage viewOrderPage;

    private static final int DEFAULT_TIMEOUT = 15; // 15 seconds (excruciating)

    protected static final double CURRENCY_ERR = 0.005; // still rounds to same pence

    @BeforeEach
    public void setUp() throws IOException {
        this.driver = this.createDriverInstance();
        this.driver.manage().window().maximize(); // consistent size for each test
        this.wait = new WebDriverWait(this. driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.rootPage = new RootPage(this.driver, this.wait);
        this.myAccountPage = new MyAccountPage(this.driver, this.wait);
        this.shopPage = new ShopPage(this.driver, this.wait);
        this.cartPage = new CartPage(this.driver, this.wait);
        this.editAccountPage = new EditAccountPage(this.driver, this.wait);
        this.checkoutPage = new CheckoutPage(this.driver, this.wait);
        this.orderReceivedPage = new OrderReceivedPage(this.driver, this.wait);
        this.ordersPage = new OrdersPage(this.driver, this.wait);
        this.viewOrderPage = new ViewOrderPage(this.driver, this.wait);
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    private WebDriver createDriverInstance() {
        switch (this.driverType) {
            case DriverType.CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
//                 chromeOptions.addArguments("--headless=new");
                return new ChromeDriver(chromeOptions);
            case DriverType.FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                 firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);
            default:
                return new ChromeDriver(); // ChromeDriver is the default, no options
        }
    }
}
