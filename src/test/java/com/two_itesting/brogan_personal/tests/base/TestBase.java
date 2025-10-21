package com.two_itesting.brogan_personal.tests.base;

import com.two_itesting.brogan_personal.models.site.User;
import com.two_itesting.brogan_personal.steps.ShoppingSteps;
import com.two_itesting.brogan_personal.test_data.DriverType;
import com.two_itesting.brogan_personal.test_data.LocalTestDataFetcher;
import com.two_itesting.brogan_personal.utils.CaptureHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@EnumSource(DriverType.class)
@ExtendWith(BaseTestExceptionHandler.class)
@ParameterizedClass
public abstract class TestBase {

    protected static final String TEST_DATA_SOURCE = "com.two_itesting.brogan_personal.test_data.TestDataSource";
    private static final int DEFAULT_TIMEOUT = 7;
    @Parameter(0)
    protected DriverType driverType; // parameterised
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ShoppingSteps shoppingSteps;

    @BeforeEach
    public void setUp() {
        this.driver = this.createDriverInstance();
        this.driver.manage().window().maximize(); // consistent size for each test
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(DEFAULT_TIMEOUT));

        this.shoppingSteps = new ShoppingSteps(this.driver, this.wait);

        for (User user : LocalTestDataFetcher.fetchUserList()) {
            this.shoppingSteps.login(user);
            this.shoppingSteps.dismissDisclaimer();
            this.shoppingSteps.resetCart();
            this.shoppingSteps.logout();
        }
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    private WebDriver createDriverInstance() {
        return switch (this.driverType) {
            case DriverType.CHROME -> {
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("--headless=new");
                yield new ChromeDriver(chromeOptions);
            }
            case DriverType.FIREFOX -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                firefoxOptions.addArguments("--headless");
                yield new FirefoxDriver(firefoxOptions);
            }
            default -> {
                CaptureHelper.logToAllure("Invalid driver type " + this.driverType + ", defaulting to ChromeDriver.");
                yield new ChromeDriver();
            } // ChromeDriver is the default, no options
        };
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
