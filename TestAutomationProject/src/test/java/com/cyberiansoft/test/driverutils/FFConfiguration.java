package com.cyberiansoft.test.driverutils;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FFConfiguration {

    public DesiredCapabilities getFirefoxCapabilities(FirefoxProfile ffProfile) {
        DesiredCapabilities ffCapabilities = DesiredCapabilities.firefox();
        ffCapabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);
        ffCapabilities.setCapability("marionette", true);
        return ffCapabilities;
    }
}
