package com.cyberiansoft.test.core;

import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.remote.DesiredCapabilities;


public class AppiumConfiguration {

	public DesiredCapabilities getCapabilities(MobilePlatform mplatform) {
		DesiredCapabilities appiumcap = new DesiredCapabilities();
		switch (mplatform) {
			case ANDROID:
				appiumcap.setCapability("bundleId", IOSHDDeviceInfo.getInstance().getDeviceBundleId());
				appiumcap.setCapability(MobileCapabilityType.UDID, IOSHDDeviceInfo.getInstance().getDeviceUDID());
				appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSHDDeviceInfo.getInstance().getDeviceName());
				File appDirectory = new File(IOSHDDeviceInfo.getInstance().getAppDir());

				File app = new File(appDirectory,  IOSHDDeviceInfo.getInstance().getBuildFileName());
				appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	     
				return appiumcap;
			case IOS:
			
				appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
				appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSHDDeviceInfo.getInstance().getPlatformVersion());
				appiumcap.setCapability(MobileCapabilityType.FULL_RESET, true);
				appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
				appiumcap.setCapability("nativeWebTap", true);
				appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, IOSHDDeviceInfo.getInstance().getAutomationName()); 
				appiumcap.setCapability("appiumVersion", "1.6.1");
			
				appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
				appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, IOSHDDeviceInfo.getInstance().getNewCommandTimeout());
	
				appiumcap.setCapability("bundleId", IOSHDDeviceInfo.getInstance().getDeviceBundleId());
	    		appiumcap.setCapability(MobileCapabilityType.UDID, IOSHDDeviceInfo.getInstance().getDeviceUDID());
	    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSHDDeviceInfo.getInstance().getDeviceName());
	    		
	    		DateTimeFormatter dateFormat =
	                    DateTimeFormatter.ofPattern("MMdd");
	    		LocalDate date = LocalDate.now(ZoneOffset.of("-08:00"));
	    		appiumcap.setCapability(MobileCapabilityType.APP,
	    				"http://amtqc.cyberiansoft.net/Uploads/ReconPro_HD_" + date.format(dateFormat) + ".app.zip");
	     
				return appiumcap;
		}
		return appiumcap;
    	
    }


	
}
