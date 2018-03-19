package com.cyberiansoft.test.driverutils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class DriverBuilder {
	
	private static DriverBuilder instance = null;
	private static final int IMPLICIT_TIMEOUT = 0;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();
    private ThreadLocal<MobilePlatform> mobilePlatform = new ThreadLocal<MobilePlatform>();
	
	private DriverBuilder() {
	}
	
	public static DriverBuilder getInstance() {
        if ( instance == null ) {
            instance = new DriverBuilder();
        }

        return instance;
    }
	
	
	public final void setDriver(BrowserType browserType) {
		
		DesiredCapabilities webcap = null;
		switch (browserType) {
		case FIREFOX:
			FirefoxDriverManager.getInstance().setup();
			webcap = DesiredCapabilities.firefox();
			FirefoxOptions ffOpts = new FirefoxOptions();
            FirefoxProfile ffProfile = new FirefoxProfile();

            ffProfile.setPreference("browser.autofocus", true);
            ffProfile.setPreference("browser.tabs.remote.autostart.2", false);

            webcap.setCapability(FirefoxDriver.PROFILE, ffProfile);
            webcap.setCapability("marionette", true);

            webDriver.set(new FirefoxDriver(ffOpts.merge(webcap)));
			break;
		case IE:
			InternetExplorerDriverManager.getInstance().arch64().setup();
	        DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();

//			DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
//	         System.setProperty("webdriver.ie.driver", PATH_TO_IE_DRIVER);

	         IEDesiredCapabilities.setCapability("nativeEvents", false);    
	         IEDesiredCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
	         IEDesiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
	         IEDesiredCapabilities.setCapability("disable-popup-blocking", true);
	         IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	         IEDesiredCapabilities.setCapability("ignoreZoomSetting", true);
	         IEDesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	         IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	         IEDesiredCapabilities.setJavascriptEnabled(true);
	         IEDesiredCapabilities.setCapability("requireWindowFocus", false);
	         IEDesiredCapabilities.setCapability("enablePersistentHover", false);
			webcap = DesiredCapabilities.internetExplorer();
			webDriver.set(new RemoteWebDriver(IEDesiredCapabilities));
			//webdriver = new InternetExplorerDriver(IEDesiredCapabilities);
			//webdriver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
			break;
		case CHROME:
			ChromeDriverManager.getInstance().setup();
			webcap =  DesiredCapabilities.chrome();
			webDriver.set(new ChromeDriver());
			break;
		case SAFARI:
			SafariOptions safariOpts = new SafariOptions();
			webcap = DesiredCapabilities.safari();

		    safariOpts.setCapability("UseTechnologyPreview", true);
		    //safariOpts.useCleanSession(true);
		    webcap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    webcap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
		    webcap.setCapability(SafariOptions.CAPABILITY, safariOpts);
		    webcap.setBrowserName("safari");
		    webcap.setPlatform(Platform.MAC);
		    webDriver.set(new SafariDriver(safariOpts.merge(webcap)));
		    break;
		}
		sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
        sessionBrowser.set(webcap.getBrowserName());
        sessionVersion.set(webcap.getVersion());
		getDriver().manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	 }
	
	public void setDriver(WebDriver driver) {
		webDriver.set(driver);
		sessionId.set(((RemoteWebDriver) webDriver.get())
		.getSessionId().toString());
		sessionBrowser.set(((RemoteWebDriver) webDriver.get())
		.getCapabilities().getBrowserName());
	}

	public WebDriver getDriver() {
	   return webDriver.get();
	}
	
	
	public AppiumDriver<MobileElement> getAppiumDriver() {
		  return mobileDriver.get();
	}
	 
	public final void setAppiumDriver(MobilePlatform mobilePlatform) {
		initAppiumDriver(mobilePlatform, getLocalHostAppiumURL());
	}
	
	public final void setAppiumDriver(MobilePlatform mobilePlatform, URL appiumURL) {
		initAppiumDriver(mobilePlatform, appiumURL);
	}
	
	private final void initAppiumDriver(MobilePlatform mobilePlatform, URL appiumURL) {
		DesiredCapabilities appiumcap =  new AppiumConfiguration().getCapabilities(mobilePlatform);
		switch (mobilePlatform) {
			case ANDROID:
				mobileDriver.set(new AndroidDriver<MobileElement>(appiumURL,
						appiumcap));
				sessionId.set(((AndroidDriver<MobileElement>)
						mobileDriver.get()).getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;
			case IOS_HD:
				mobileDriver.set(new IOSDriver<MobileElement>(appiumURL,
						appiumcap));
				sessionId.set(((IOSDriver<MobileElement>)
						mobileDriver.get()).getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;
			case IOS_REGULAR:
				mobileDriver.set(new IOSDriver<MobileElement>(appiumURL,
						appiumcap));
				sessionId.set(((IOSDriver<MobileElement>)
						mobileDriver.get()).getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;	
		}
	}
	
	public MobilePlatform getMobilePlatform() {
		return this.mobilePlatform.get();
	}
	
	public final URL getLocalHostAppiumURL() {
		URL defaultAppiumURL = null;
		try {
			defaultAppiumURL =  new URL("http://127.0.0.1:4723/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultAppiumURL;
	}

}
