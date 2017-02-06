package com.cyberiansoft.test.core;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

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
    	 
    	//DesiredCapabilities appiumcap = new DesiredCapabilities();
    	DesiredCapabilities appiumcap =  new AppiumConfiguration().getCapabilities(MobilePlatform.IOS);
        public IOSDriver newInstance(){
        	
            return new IOSDriver<MobileElement>(endpoint, appiumcap);
        }
    }

    protected URL endpoint;
    
    public SELF againstLocalhost(){
        try {
            this.endpoint = new URL("http://127.0.0.1:4723/wd/hub");
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
