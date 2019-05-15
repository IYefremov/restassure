package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.globalutils.NetworkUtils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.net.SocketException;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class AppiumServiceManager {
    private static ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();

    public static AppiumDriverLocalService getAppiumService() {
        return appiumService.get();
    }

    public static void startAppium() {
        String localIPAddress = null;
        try {
            localIPAddress =  NetworkUtils.getLocalIPAdress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        appiumService.set(
                new AppiumServiceBuilder()
                        .withIPAddress(localIPAddress)
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
