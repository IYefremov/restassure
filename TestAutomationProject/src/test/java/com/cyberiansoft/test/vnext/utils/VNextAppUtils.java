package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;

public class VNextAppUtils {

	public static void resetApp() {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		DriverBuilder.getInstance().getAppiumDriver().resetApp();
		BaseUtils.waitABit(15*1000);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}

	public static void restartApp() {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}

}
