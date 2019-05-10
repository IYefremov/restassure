package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.core.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.net.URL;

public class AppiumInicializator {

    private static AppiumInicializator instance = null;

    private AppiumInicializator() {
    }

    public static AppiumInicializator getInstance() {
        if (instance == null)
            instance = new AppiumInicializator();
        return instance;
    }

    public AppiumDriver<MobileElement> initAppium(MobilePlatform mobilePlatform) {
        if (AppiumDriverServiceBuilder.getInstance().getAppiumService() != null) {
            initAppium(mobilePlatform, AppiumDriverServiceBuilder.getInstance().getAppiumService().getUrl());
        } else
            DriverBuilder.getInstance().setAppiumDriver(mobilePlatform);
        return DriverBuilder.getInstance().getAppiumDriver();
    }

    public AppiumDriver<MobileElement> initAppium(MobilePlatform mobilePlatform, URL appiumURL) {
        DriverBuilder.getInstance().setAppiumDriver(mobilePlatform, appiumURL);
        return DriverBuilder.getInstance().getAppiumDriver();
    }
}
