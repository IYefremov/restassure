package com.cyberiansoft.test.core;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by grago on 02/07/15.
 */
public abstract class AppiumDriverBuilder<SELF, DRIVER> {

    public static AndroidDriverBuilder forAndroid() { return new AndroidDriverBuilder(); }

    public static class AndroidDriverBuilder extends AppiumDriverBuilder<AndroidDriverBuilder, AndroidDriver>{

        private Optional<String> bundleId = Optional.empty();
        private Optional<String> udid = Optional.empty();
        
        public AndroidDriverBuilder app(String bundleId){

            this.bundleId = Optional.of(bundleId);
            return this;

        }

        public AndroidDriver newInstance() {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformName", "Android");

            return new AndroidDriver(endpoint, capabilities);

        }

    }

    public static IOSDriverBuilder forIOS(){
        return new IOSDriverBuilder();
    }

    public static class IOSDriverBuilder extends AppiumDriverBuilder<IOSDriverBuilder, IOSDriver>{

        private Optional<String> bundleId = Optional.empty();
        private Optional<String> udid = Optional.empty();
        private String appDir = null;
        private String buildFileName = null;
        private String deviceName = null;
        
        public IOSDriverBuilder regularVersion(){
            this.bundleId = Optional.of(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
            this.udid = Optional.of(IOSRegularDeviceInfo.getInstance().getDeviceUDID());
            this.appDir = IOSRegularDeviceInfo.getInstance().getAppDir();
            this.buildFileName = IOSRegularDeviceInfo.getInstance().getBuildFileName();
            this.deviceName = IOSRegularDeviceInfo.getInstance().getDeviceName();
            return this;

        }
        
        public IOSDriverBuilder hdVersion(){
            this.bundleId = Optional.of(IOSHDDeviceInfo.getInstance().getDeviceBundleId());
            this.udid = Optional.of(IOSHDDeviceInfo.getInstance().getDeviceUDID());
            this.appDir = IOSHDDeviceInfo.getInstance().getAppDir();
            this.buildFileName = IOSHDDeviceInfo.getInstance().getBuildFileName();
            this.deviceName = IOSHDDeviceInfo.getInstance().getDeviceName();
            return this;

        }

        public IOSDriver newInstance(){
            DesiredCapabilities appiumcap = new DesiredCapabilities();
            appiumcap = new DesiredCapabilities();
    		appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
    		appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, AppiumIOSConfInfo.getInstance().getPlatformVersion());
    		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, true);
    		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
    		appiumcap.setCapability("nativeWebTap", true);
    		appiumcap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AppiumIOSConfInfo.getInstance().getAutomationName()); 
    		appiumcap.setCapability("appiumVersion", "1.6.1");
    		if(bundleId.isPresent()){
    			appiumcap.setCapability("bundleId", bundleId.get());
            }
    		if(udid.isPresent()){
    			appiumcap.setCapability(MobileCapabilityType.UDID, udid.get());
            }
    		appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
    		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, AppiumIOSConfInfo.getInstance().getNewCommandTimeout());
    		
    		File appDirectory = new File(appDir);

    		File app = new File(appDirectory, buildFileName);
    		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

            
            return new IOSDriver<MobileElement>(endpoint, appiumcap);
        }
    }

    protected URL endpoint;


    public SELF againstLocalhost(){
        try {
            this.endpoint = new URL("http://0.0.0.0:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return (SELF) this;
    }

    public SELF againstHost(String host, int port){
        try {
            this.endpoint = new URL("http://" + host  +  ":" + port + "/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return (SELF) this;
    }

    public abstract DRIVER newInstance();

}
