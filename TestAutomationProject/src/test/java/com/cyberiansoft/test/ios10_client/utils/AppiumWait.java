package com.cyberiansoft.test.ios10_client.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class AppiumWait {

    public static FluentWait<AppiumDriver<MobileElement>> getGeneralFluentWait(int timeout, int pollingEvery) {
        return
                new FluentWait<>(DriverBuilder.getInstance().getAppiumDriver())
                        .withTimeout(Duration.ofSeconds(timeout))
                        .pollingEvery(Duration.ofMillis(pollingEvery))
                        .ignoring(ElementClickInterceptedException.class)
                        .ignoring(WebDriverException.class)
                        .ignoring(AssertionError.class)
                        .ignoring(StaleElementReferenceException.class)
                        .ignoring(RuntimeException.class);
    }
}
