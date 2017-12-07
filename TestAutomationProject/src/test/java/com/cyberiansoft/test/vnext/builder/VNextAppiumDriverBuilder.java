package com.cyberiansoft.test.vnext.builder;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.vnext.screens.SwipeableWebDriver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public abstract class VNextAppiumDriverBuilder<SELF, DRIVER extends SwipeableWebDriver> {

	private static String PLATFORM_NAME;
	
	public static String getPlatformName() {
        return PLATFORM_NAME;
    }
	
	public static void setPlatformName() {
		PLATFORM_NAME = MobilePlatform.IOS;
    }
	
    public static AndroidDriverBuilder forAndroid() {
        return new AndroidDriverBuilder();
    }

    public static class AndroidDriverBuilder extends VNextAppiumDriverBuilder<AndroidDriverBuilder, SwipeableWebDriver> {

        DesiredCapabilities appiumcap = new DesiredCapabilities();

        public SwipeableWebDriver build() {
        	PLATFORM_NAME = MobilePlatform.ANDROID;
        	File appDir = new File("./data/");
    	    File app = new File(appDir, "Repair360Android.apk");
    	    appiumcap = new DesiredCapabilities();

    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "mydroid19"); 
    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
    		appiumcap.setCapability("session-override",true);
    		appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
    		appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
    		//appiumcap.setCapability(MobileCapabilityType.APP,
    		//		"http://amtqc.cyberiansoft.net/Uploads/Repair360Android_1003.app.zip");
            return new SwipeableWebDriver(endpoint, appiumcap);

        }
        
        public SwipeableWebDriver buildForPCloudy(String deviceName, String browserName) {
        	PLATFORM_NAME = MobilePlatform.ANDROID;
    	    appiumcap = new DesiredCapabilities();

    	    appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1500);
    	    appiumcap.setCapability("launchTimeout", 90000);
    	    appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
    	    appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
    	    appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    	    appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
    	    appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
             //capabilities.setCapability("appPackage", appPackage); 
             //capabilities.setCapability("appActivity", appActivity); 
    	    appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
             //capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
    	    appiumcap.setCapability("rotatable", true); 
             
            return new SwipeableWebDriver(endpoint, appiumcap);

        }

    }
    
    public static IOSDriverBuilder forIOS() {
        return new IOSDriverBuilder();
    }
    
    public static class IOSDriverBuilder extends VNextAppiumDriverBuilder<IOSDriverBuilder, SwipeableWebDriver> {

        DesiredCapabilities appiumcap = new DesiredCapabilities();

        public SwipeableWebDriver build() {
        	DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern("MMdd");
    		LocalDate date = LocalDate.now();
    		date = date.minusDays(1);
        	PLATFORM_NAME = MobilePlatform.IOS;
        	//File appDir = new File("/Users/kolin/Documents");
    	    //File app = new File(appDir, "Repair360_0328.app.zip");
    		appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSRegularDeviceInfo.getInstance().getPlatformVersion());
    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, false);
    		appiumcap.setCapability("nativeWebTap", false);
    		appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); 
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS); 
    	
    		appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, IOSRegularDeviceInfo.getInstance().getNewCommandTimeout());

    		appiumcap.setCapability("bundleId", "com.automobiletechnologies.repair360");
    		appiumcap.setCapability(MobileCapabilityType.UDID, IOSRegularDeviceInfo.getInstance().getDeviceUDID());
    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSRegularDeviceInfo.getInstance().getDeviceName());
    		appiumcap.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, false);
    		appiumcap.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 8100);
    		appiumcap.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, true);
    		//appiumcap.setCapability("connectHardwareKeyboard", true);
    		appiumcap.setCapability(MobileCapabilityType.APP,
    				"http://amtqc.cyberiansoft.net/Uploads/Repair360_" + date.format(dateFormat) + ".app.zip");

            return new SwipeableWebDriver(endpoint, appiumcap);

        }

    }

    protected URL endpoint;

    public SELF withEndpoint(URL endpoint) {
        this.endpoint = endpoint;

        return (SELF) this;
    }

    public abstract DRIVER build();

}
