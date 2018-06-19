package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;

public class VNextAppUtils {
	
	public static void resetApp() {


		try {
			((AndroidDriver)DriverBuilder.getInstance().getAppiumDriver()).startRecordingScreen(
					new AndroidStartScreenRecordingOptions()
							.withTimeLimit(Duration.ofSeconds(90))
			);
		} catch (WebDriverException e) {
			if (e.getMessage().toLowerCase().contains("emulator")) {
				// screen recording only works on real devices
				return;
			}
		}


		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		DriverBuilder.getInstance().getAppiumDriver().resetApp();
		BaseUtils.waitABit(30*1000);


		String ss = ((AndroidDriver)DriverBuilder.getInstance().getAppiumDriver()).stopRecordingScreen();

		byte[] bts = Base64.getDecoder().decode(ss);
		try {
			FileUtils.writeByteArrayToFile(new File("mpg11.avi"), bts);
		} catch (IOException e) {
			e.printStackTrace();
		}

		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
	
	public static void restartApp() {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}

}
