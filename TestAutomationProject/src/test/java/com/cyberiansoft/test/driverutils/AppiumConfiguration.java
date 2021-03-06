package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;


public class AppiumConfiguration {

	public DesiredCapabilities getCapabilities(MobilePlatform mplatform) {
		DesiredCapabilities appiumcap = new DesiredCapabilities();
		DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMdd");

		Instant date = Instant.now();
		ZoneId californiaTimeZone = ZoneId.of("America/Los_Angeles");
		ZonedDateTime californiaTime = ZonedDateTime.ofInstant(date, californiaTimeZone);
		//californiaTime = californiaTime.minusDays(1);
		ZoneId kyivTimeZone = ZoneId.of("Europe/Budapest");
		ZonedDateTime kyivTime = ZonedDateTime.ofInstant(date, kyivTimeZone);
		kyivTime = kyivTime.minusDays(1);
		switch (mplatform) {
			case ANDROID:
				File appDir = new File("data/");
				try {
					BaseUtils.unpackArchive(new URL("http://amtqc.cyberiansoft.net/Uploads/Repair360AndroidTeam_" + californiaTime.format(dateFormat) + ".app.zip"), appDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
				File app = new File(appDir, "Repair360AndroidTeam.apk");
				appiumcap = new DesiredCapabilities();
				appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "myphone");
				//appiumcap.setCapability("avd", "myphone");
	    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
	    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
	    		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
	    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
	    		appiumcap.setCapability("session-override",true);
	    		appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
	    		appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.BROWSER, Level.ALL);
				appiumcap.setCapability(MobileCapabilityType.LOGGING_PREFS, logPrefs);
	    		//appiumcap.setCapability(MobileCapabilityType.APP,
	    		//		"http://amtqc.cyberiansoft.net/Uploads/Repair360AndroidTeam_0618.app.zip");
	     
				return appiumcap;
			case IOS_HD:
				appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
				appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSHDDeviceInfo.getInstance().getPlatformVersion());
				appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
				appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
				appiumcap.setCapability("nativeWebTap", true);
				appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, IOSHDDeviceInfo.getInstance().getAutomationName()); 
			
				appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
				appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, IOSHDDeviceInfo.getInstance().getNewCommandTimeout());
	
				appiumcap.setCapability("bundleId", IOSHDDeviceInfo.getInstance().getDeviceBundleId());
	    		//appiumcap.setCapability(MobileCapabilityType.UDID, IOSHDDeviceInfo.getInstance().getDeviceUDID());
	    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSHDDeviceInfo.getInstance().getDeviceName());
	    		appiumcap.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, false);
	    		appiumcap.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 8500);
				//appiumcap.setCapability("showXcodeLog", true);
	    		appiumcap.setCapability(MobileCapabilityType.APP,
	    				"http://amtqc.cyberiansoft.net/Uploads/ReconPro_HD_" + kyivTime.format(dateFormat) + ".app.zip");
				return appiumcap;
			case IOS_REGULAR:
				appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
				appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSRegularDeviceInfo.getInstance().getPlatformVersion());
				appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
				appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
				appiumcap.setCapability("session-override",true);
				appiumcap.setCapability("nativeWebTap", true);
				appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST); 
				appiumcap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
			
				appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
				appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
	
				appiumcap.setCapability("bundleId", IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
	    		//appiumcap.setCapability(MobileCapabilityType.UDID, IOSRegularDeviceInfo.getInstance().getDeviceUDID());
	    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSRegularDeviceInfo.getInstance().getDeviceName());
	    		appiumcap.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, false);
	    		//appiumcap.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 8200);
	    		appiumcap.setCapability(MobileCapabilityType.APP,
	    				"http://amtqc.cyberiansoft.net/Uploads/ReconPro_" + kyivTime.format(dateFormat) + ".app.zip");
	     
				return appiumcap;
		}
		return appiumcap;
    	
    }


	
}
