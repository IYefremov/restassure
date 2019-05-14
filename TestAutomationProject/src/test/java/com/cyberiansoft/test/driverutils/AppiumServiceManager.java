package com.cyberiansoft.test.driverutils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class AppiumServiceManager {
    private static ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();

    public static AppiumDriverLocalService getAppiumService() {
        return appiumService.get();
    }

    public static void startAppium() {
        appiumService.set(
                new AppiumServiceBuilder()
                        .usingAnyFreePort()
                        .withArgument(SESSION_OVERRIDE)
                        .withArgument(LOG_LEVEL, "error")
                        .build()
        );
        appiumService.get().start();
        if (!appiumService.get().isRunning())
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
    }
}
