package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AppiumUtils {
	
	public static void switchApplicationContext(AppContexts context) {
		Set<String> contextNames;

		try {
			contextNames = DriverBuilder.getInstance().getAppiumDriver().getContextHandles();
		} catch (WebDriverException e) {
			contextNames = DriverBuilder.getInstance().getAppiumDriver().getContextHandles();
		}

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
		ConnectionState state = ((AndroidDriver<MobileElement>)DriverBuilder.getInstance().getAppiumDriver()).getConnection();
		if (state.isWiFiEnabled()) {
			try {
				Runtime.getRuntime().exec("adb shell am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings");
				BaseUtils.waitABit(2000);
				DriverBuilder.getInstance().getAppiumDriver().findElement(By.className("android.widget.Switch")).click();
				((AndroidDriver<MobileElement>) DriverBuilder.getInstance().getAppiumDriver()).pressKey(new KeyEvent().withKey(AndroidKey.BACK));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		BaseUtils.waitABit(3000);
	}
	
	public static void setNetworkOn() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		ConnectionState state = ((AndroidDriver<MobileElement>)DriverBuilder.getInstance().getAppiumDriver()).getConnection();
		if (!state.isWiFiEnabled()) {
			try {
				Runtime.getRuntime().exec("adb shell am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings");
				BaseUtils.waitABit(2000);
				//System.out.println("===" + DriverBuilder.getInstance().getAppiumDriver().findElement(By.className("android.widget.Switch")).getAttribute("text"));
				DriverBuilder.getInstance().getAppiumDriver().findElement(By.className("android.widget.Switch")).click();
				((AndroidDriver<MobileElement>) DriverBuilder.getInstance().getAppiumDriver()).pressKey(new KeyEvent().withKey(AndroidKey.BACK));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		BaseUtils.waitABit(3000);
	}
	
	public static void clickHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		((AndroidDriver<MobileElement>) DriverBuilder.getInstance().getAppiumDriver()).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
		switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
	
	public static String createScreenshot(String reportFolder, String filename) {
		UUID uuid = UUID.randomUUID();
		File file = ((TakesScreenshot) DriverBuilder.getInstance().getAppiumDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file , new File(reportFolder + "/" + filename + uuid + ".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename + uuid + ".jpeg";
	}

	@Attachment(value = "Screen screenshot", type = "image/png")
	public static byte[] attachAllureScreenshot() {
		byte[] screenshotAs = null;
		try {
			screenshotAs = ((TakesScreenshot) DriverBuilder.getInstance().getAppiumDriver()).getScreenshotAs(OutputType.BYTES);
		} catch (Exception e) {
			failToSaveScreenshot(e);
		}
		return screenshotAs;
	}

	@Attachment(value = "Unable to save screenshot")
	public static String failToSaveScreenshot(Exception e) {
		return String.format("%s\n%s\n%s", "Failed to save screenshot",
				e.getMessage(), Arrays.toString(e.getStackTrace()));
	}

	@Attachment(value = "Log", type = "text/html")
	public static String attachAllureLog(String text) {
		return String.format("%s\n", text);
	}


	public static String createBase64Screenshot() {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		String base64Screenshot = "data:image/png;base64,"+ ((TakesScreenshot) DriverBuilder.getInstance().getAppiumDriver()).getScreenshotAs(OutputType.BASE64);
		
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		return base64Screenshot;
	}
}