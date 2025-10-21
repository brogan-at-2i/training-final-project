package com.two_itesting.brogan_personal.utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class CaptureHelper {

    public static BigDecimal interpretPricedAsBigDecimal(String priceString) {
        if (priceString.charAt(0) == '£') {
            return new BigDecimal(priceString.substring(1));
        } else if (priceString.charAt(0) == '-' && priceString.charAt(1) == '£') {
            return new BigDecimal("-" + priceString.substring(2));
        }
        throw new RuntimeException("Data not parseable");
    }

    public static void takeAndSaveScreenshot(TakesScreenshot driver, String filename) {
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get("./target/screenshots/" + filename + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".png");
        try {
            Files.createDirectories(Paths.get("./target/screenshots"));
            Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Screenshot saved at: " + destination);
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Debug Logging", type = "text/plain")
    public static String logToAllure(String message) {
        System.out.println(message); // print to console too
        return message;
    }

}
