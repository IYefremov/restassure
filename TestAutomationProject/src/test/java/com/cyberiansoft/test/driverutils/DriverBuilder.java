package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.awaitility.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    private BrowserType browserType = BrowserType.CHROME;
    private String remoteWebDriverURL;

    private DriverBuilder() {
    }

    public static DriverBuilder getInstance() {
        if (instance == null) {
            instance = new DriverBuilder();
        }
        return instance;
    }

    public DriverBuilder setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
        return this;
    }

    public DriverBuilder setRemoteWebDriverURL(String remoteWebDriverURL) {
        this.remoteWebDriverURL = remoteWebDriverURL;
        return this;
    }

    @SneakyThrows
    public final void setDriver() {
        DesiredCapabilities webcap = null;
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webcap = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("enable-automation");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-browser-side-navigation");
                options.addArguments("--disable-gpu");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    webDriver.set(new ChromeDriver(options));
                } catch (SessionNotCreatedException ignored) {
                    new ThreadLocal<WebDriver>().set(new ChromeDriver(options));
                }
                break;
            case SELENOID_CHROME:
                ChromeOptions selenoidChromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                //1-Allow, 2-Block, 0-default
                prefs.put("profile.default_content_setting_values.notifications", 1);
                selenoidChromeOptions.setExperimentalOption("prefs", prefs);
                selenoidChromeOptions.addArguments("--window-size=1800,1000");
                DesiredCapabilities capabilities = new SelenoidConfiguration().getCapabilities(selenoidChromeOptions);
                webcap = capabilities;
                RemoteWebDriver driver = new RemoteWebDriver(
                        URI.create(remoteWebDriverURL).toURL(),
                        capabilities
                );
                driver.setFileDetector(new LocalFileDetector());
                webDriver.set(driver);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webcap = DesiredCapabilities.firefox();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("browser.autofocus", true);
                firefoxOptions.setCapability("browser.tabs.remote.autostart.2", false);
                webDriver.set(new FirefoxDriver(firefoxOptions.merge(webcap)));
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
            try {
                getDriver().manage().window().setPosition(new Point(0, 0));
                getDriver().manage().window().setSize(new Dimension(1920, 1080));
            } catch (Exception e) {
                setWindowSizeAfterDelay();
            }
        }
    }

    //todo: remove. Use Builder methods!
    @SneakyThrows
    public final void setDriver(BrowserType browserType) {

        DesiredCapabilities webcap = null;
        switch (browserType) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOpts = new FirefoxOptions();
                FirefoxProfile ffProfile = new FirefoxProfile();
                webcap = new FFConfiguration().getFirefoxCapabilities(ffProfile);
                ffProfile.setPreference("browser.autofocus", true);
                ffProfile.setPreference("browser.tabs.remote.autostart.2", false);
                webDriver.set(new FirefoxDriver(ffOpts.merge(webcap)));
                break;
            case IE:
                WebDriverManager.iedriver().arch64().setup();
                webcap = new IEConfiguration().getInternetExplorerCapabilities();
                webDriver.set(new RemoteWebDriver(webcap));
                break;
            case EDGE:
                WebDriverManager.edgedriver().arch64().setup();
                webcap = DesiredCapabilities.edge();
                try {
                    webDriver.set(new EdgeDriver());
                } catch (WebDriverException ignored) {
                    new ThreadLocal<WebDriver>().set(new EdgeDriver());
                }
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webcap = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("enable-automation");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-browser-side-navigation");
                options.addArguments("--disable-gpu");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    webDriver.set(new ChromeDriver(options));
                } catch (SessionNotCreatedException ignored) {
                    new ThreadLocal<WebDriver>().set(new ChromeDriver(options));
                }
                break;
            case SELENOID_CHROME:
                ChromeOptions selenoidChromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                //1-Allow, 2-Block, 0-default
                prefs.put("profile.default_content_setting_values.notifications", 1);
                selenoidChromeOptions.setExperimentalOption("prefs", prefs);
                selenoidChromeOptions.addArguments("--window-size=1800,1000");
                DesiredCapabilities capabilities = new SelenoidConfiguration().getCapabilities(selenoidChromeOptions);
                webcap = capabilities;
                RemoteWebDriver driver = new RemoteWebDriver(
                        URI.create(remoteWebDriverURL).toURL(),
                        capabilities
                );
                driver.setFileDetector(new LocalFileDetector());
                webDriver.set(driver);
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
            try {
                getDriver().manage().window().setPosition(new Point(0, 0));
                getDriver().manage().window().setSize(new Dimension(1920, 1080));
            } catch (Exception e) {
                setWindowSizeAfterDelay();
            }
        }
    }

    private void setWindowSizeAfterDelay() {
        int i = 0;
        do {
            try {
                System.out.println("Retrying to set the window size after delay");
                await().atMost(Duration.FIVE_SECONDS);
                getDriver().manage().window().setPosition(new Point(0, 0));
                getDriver().manage().window().setSize(new Dimension(1920, 1080));
                break;
            } catch (Exception ee) {
                i++;
            }
        }
        while (i != 5);
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
        DesiredCapabilities appiumcap = new AppiumConfiguration().getCapabilities(mobilePlatform);
        switch (mobilePlatform) {
            case ANDROID:
                mobileDriver.set(new AndroidDriver<>(appiumURL, appiumcap));
                sessionId.set(mobileDriver.get().getSessionId().toString());
                sessionBrowser.set(appiumcap.getCapability(MobileCapabilityType.DEVICE_NAME).toString());
                this.mobilePlatform.set(mobilePlatform);
                break;
            case IOS_HD:
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
            defaultAppiumURL = new URL("http://127.0.0.1:4723/wd/hub");
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
