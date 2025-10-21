package com.two_itesting.brogan_personal.tests.base;

import com.two_itesting.brogan_personal.utils.CaptureHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.Optional;

public class BaseTestExceptionHandler implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(@NotNull ExtensionContext context, @NotNull Throwable testException) throws Throwable {
        Optional<Object> testObject = context.getTestInstance();
        if (testObject.isEmpty()) { // failed to get test instance obj
            throw testException; // so just let the failure through no logging
        }
        TestBase testBaseObject = (TestBase) testObject.get();
        Optional<Method> testMethod = context.getTestMethod();
        if (testMethod.isEmpty()) { // failed to get method
            throw testException; // rethrow
        }
        String testName = testMethod.get().getName();
        WebDriver driver = testBaseObject.getDriver();
        CaptureHelper.takeAndSaveScreenshot((TakesScreenshot) driver, testName); // log to allure
        // and then rethrow to still mark as failed test
        throw testException;
    }
}
