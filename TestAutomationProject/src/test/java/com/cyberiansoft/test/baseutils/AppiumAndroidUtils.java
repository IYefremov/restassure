package com.cyberiansoft.test.baseutils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.AppContexts;


import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class AppiumAndroidUtils {
	
	public static void switchApplicationContext(AppContexts context) {
		Set<String> contextNames = DriverBuilder.getInstance().getAppiumDriver().getContextHandles();
		switch (context) {
			case NATIVE_CONTEXT:
				for (String contextName : contextNames) {
					if (contextName.contains("NATIVE_APP")) {
						DriverBuilder.getInstance().getAppiumDriver().context(contextName);
					}
				}				
				break;
			case WEBVIEW_CONTEXT:			
				List<String> handlesList = new ArrayList<String>(contextNames);
				if (handlesList.size() > 2)
					DriverBuilder.getInstance().getAppiumDriver().context(handlesList.get(2));
				else
					DriverBuilder.getInstance().getAppiumDriver().context(handlesList.get(1));
				break;
		}
	}
	
	public static void setNetworkOff() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		try {
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus disable");
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.data_connection --es setstatus disable");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
	
	public static void setNetworkOn() {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			try {
				Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus enable");
				Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.data_connection --es setstatus enable");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
	
	public static void clickHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		((AndroidDriver<MobileElement>) DriverBuilder.getInstance().getAppiumDriver()).pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
		//DriverBuilder.getInstance().getAppiumDriver().navigate().back();
		switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
}