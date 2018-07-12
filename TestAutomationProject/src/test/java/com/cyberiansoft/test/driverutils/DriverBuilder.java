package com.cyberiansoft.test.driverutils;

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
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class DriverBuilder {
	
	private static DriverBuilder instance = null;
	private static final int IMPLICIT_TIMEOUT = 15;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<>();
	private ThreadLocal<String> sessionId = new ThreadLocal<>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<>();
    private ThreadLocal<MobilePlatform> mobilePlatform = new ThreadLocal<>();


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

			FirefoxOptions ffOpts = new FirefoxOptions();
			FirefoxProfile ffProfile = new FirefoxProfile();
			webcap = new FFConfiguration().getFirefoxCapabilities(ffProfile);
            ffProfile.setPreference("browser.autofocus", true);
            ffProfile.setPreference("browser.tabs.remote.autostart.2", false);
            webDriver.set(new FirefoxDriver(ffOpts.merge(webcap)));
			break;
		case IE:
			InternetExplorerDriverManager.getInstance().arch64().setup();
			webcap = new IEConfiguration().getInternetExplorerCapabilities();
			webDriver.set(new RemoteWebDriver(webcap));
			break;
		case CHROME:
			ChromeDriverManager.getInstance().setup();
			webcap =  DesiredCapabilities.chrome();
			try {
                webDriver.set(new ChromeDriver());
            } catch (SessionNotCreatedException ignored) {
                new ThreadLocal<WebDriver>().set(new ChromeDriver());
            }
			break;
		case SAFARI:
			SafariOptions safariOpts = new SafariOptions();
			webcap = new SafariConfiguration().getSafariCapabilities(safariOpts);
			safariOpts.setCapability("UseTechnologyPreview", true);

		    webDriver.set(new SafariDriver(safariOpts.merge(webcap)));
		    break;
		}
		sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
        if (webcap != null) {
            sessionBrowser.set(webcap.getBrowserName());
            sessionVersion.set(webcap.getVersion());
        }
		getDriver().manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		try {
            getDriver().manage().window().maximize();
        } catch (WebDriverException e) {
            e.printStackTrace();
        } finally {
            getDriver().manage().window().maximize();
        }
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
				mobileDriver.set(new AndroidDriver<>(appiumURL, appiumcap));
				sessionId.set(mobileDriver.get().getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;
			case IOS_HD:
				mobileDriver.set(new IOSDriver<>(appiumURL, appiumcap));
				sessionId.set(mobileDriver.get().getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;
			case IOS_REGULAR:
				mobileDriver.set(new IOSDriver<>(appiumURL, appiumcap));
				sessionId.set(mobileDriver.get().getSessionId().toString());
				sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
				this.mobilePlatform.set(mobilePlatform);
				break;
		}
	}
	
	public MobilePlatform getMobilePlatform() {
		return this.mobilePlatform.get();
	}
	
	public String getBrowser() {
		return this.sessionBrowser.get();
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

    public void quitDriver() {
        try {
            if (getDriver() != null) {
                getDriver().quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            await().atMost(5, TimeUnit.SECONDS);
        } finally {
            if (getDriver() != null) {
                getDriver().quit();
            }
        }
    }
}
