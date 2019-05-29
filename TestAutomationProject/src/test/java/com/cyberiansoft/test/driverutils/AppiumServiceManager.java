package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.globalutils.NetworkUtils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
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
                new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
                        .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                        .withIPAddress("127.0.0.1")
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
