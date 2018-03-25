package com.cyberiansoft.test.driverutils;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

import java.io.File;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumDriverServiceBuilder {
	
	private static AppiumDriverServiceBuilder instance = null;
	private ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<AppiumDriverLocalService>();
	
	private AppiumDriverServiceBuilder() {
	}
	
	public static AppiumDriverServiceBuilder getInstance() {
        if ( instance == null ) {
            instance = new AppiumDriverServiceBuilder();
        }

        return instance;
    }
	
	public final void buildAppiumService() {
		service.set(new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				 .usingAnyFreePort().withArgument(SESSION_OVERRIDE)
				 .withArgument(LOG_LEVEL, "error")
				 .build());
	        service.get().start();

	        if (service.get() == null || !service.get().isRunning()) {
	            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
	        }
	}
	
	public AppiumDriverLocalService getAppiumService() {
		   return service.get();
		}

}
