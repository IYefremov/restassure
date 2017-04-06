package com.cyberiansoft.test.vnext.builder;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cyberiansoft.test.core.IOSRegularDeviceInfo;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public abstract class VNextAppiumDriverBuilder<SELF, DRIVER extends AppiumDriver<?>> {

    protected String apiKey;
    protected String testReportId;

    public static AndroidDriverBuilder forAndroid() {
        return new AndroidDriverBuilder();
    }

    public static class AndroidDriverBuilder extends VNextAppiumDriverBuilder<AndroidDriverBuilder, AndroidDriver<WebElement>> {

        DesiredCapabilities appiumcap = new DesiredCapabilities();

        public AndroidDriver<WebElement> build() {
        	File appDir = new File("./data/");
    	    File app = new File(appDir, "ReconPro.apk");
    	    appiumcap = new DesiredCapabilities();

    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "mydroid19"); 
    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
    		appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
    		
            return new AndroidDriver<WebElement>(endpoint, appiumcap);

        }

    }
    
    public static IOSDriverBuilder forIOS() {
        return new IOSDriverBuilder();
    }
    
    public static class IOSDriverBuilder extends VNextAppiumDriverBuilder<IOSDriverBuilder, IOSDriver<WebElement>> {

        DesiredCapabilities appiumcap = new DesiredCapabilities();

        public IOSDriver<WebElement> build() {

        	File appDir = new File("/Users/kolin/Documents");
    	    File app = new File(appDir, "Repair360_0328.app.zip");
    		appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, IOSRegularDeviceInfo.getInstance().getPlatformVersion());
    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, false);
    		appiumcap.setCapability("nativeWebTap", false);
    		appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); 
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS); 
    		appiumcap.setCapability("appiumVersion", "1.6.3");
    	
    		appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, IOSRegularDeviceInfo.getInstance().getNewCommandTimeout());

    		appiumcap.setCapability("bundleId", "com.automobiletechnologies.repair360");
    		appiumcap.setCapability(MobileCapabilityType.UDID, IOSRegularDeviceInfo.getInstance().getDeviceUDID());
    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, IOSRegularDeviceInfo.getInstance().getDeviceName());
    		
    		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

            return new IOSDriver<WebElement>(endpoint, appiumcap);

        }

    }

    protected URL endpoint;

    public SELF withEndpoint(URL endpoint) {
        this.endpoint = endpoint;

        return (SELF) this;
    }

    public abstract DRIVER build();

}