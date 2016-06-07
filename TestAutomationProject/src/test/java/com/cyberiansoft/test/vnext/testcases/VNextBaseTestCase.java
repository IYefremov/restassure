package com.cyberiansoft.test.vnext.testcases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.utils.WebDriverInstansiator;
import com.cyberiansoft.test.vnext.screens.SwipeableWebDriver;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class VNextBaseTestCase {
	
	protected static SwipeableWebDriver appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
	protected DesiredCapabilities appiumcap;
	protected static AppiumDriverLocalService service;
	protected String regcode = "";
	protected static String defaultbrowser;
	protected static String deviceofficeurl;
	
	@BeforeSuite
	@Parameters({ "selenium.browser", "backoffice.url" })
	public void startServer(String browser, String bourl) throws MalformedURLException {
		
		//AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.LOG_LEVEL, "error");
       // service = builder.build();
        //service.start();	
		deviceofficeurl = bourl;
        File appDir = new File("./data/");
	    File app = new File(appDir, "ReconPro.apk");
	    appiumcap = new DesiredCapabilities();

		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "mydroid19"); 
		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		appiumcap.setCapability(MobileCapabilityType.APP_PACKAGE,
						"com.automobiletechnologies.reconpro2");
		appiumcap.setCapability(MobileCapabilityType.APP_ACTIVITY, "com.automobiletechnologies.reconpro2.MainActivity");
		//appiumcap.setCapability("chromedriverExecutable", "d:\\Work\\AQC\\TestAutomationProject\\browsers\\chromedriver\\chromedriver.exe");
		appiumdriver = new SwipeableWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),
				appiumcap);
		//appiumdriver = new SwipeableWebDriver(service.getUrl(), appiumcap);
		defaultbrowser = browser;
	}
	
	public void setUp() {
		waitABit(8000);
	    switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public SwipeableWebDriver getAppiumDriver() {
		return appiumdriver;
	}
	
	public String createScreenshot(WebDriver driver, String loggerdir, String testcasename) {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		WebDriver driver1 = new Augmenter().augment(driver);
		UUID uuid = UUID.randomUUID();
		File file = ((TakesScreenshot) driver1).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file , new File(loggerdir + "\\" + testcasename + uuid + ".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switchApplicationContext(AppContexts.WEB_CONTEXT);
		return testcasename + uuid + ".jpeg";
	}

	public void switchApplicationContext(String appcontext) {
		Set<String> contextNames = appiumdriver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains(appcontext)) {
				appiumdriver.context(contextName);
			}
		}
	}
	
	@AfterSuite
	public void tearDown() throws Exception {
		if (webdriver != null)
			webdriver.quit();
		if (appiumdriver != null)
			appiumdriver.quit();
		if (service != null)
            service.stop();
	}
	
	public String getDeviceRegistrationCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ActiveDevicesWebPage devicespage = companypage.clickManageDevicesLink();
		devicespage.setSearchCriteriaByName(licensename);
		regcode = devicespage.getFirstRegCodeInTable();
		webdriver.quit();
		return regcode;
	}
	
	public void initiateWebDriver() {
		WebDriverInstansiator.setDriver(defaultbrowser);
		webdriver = WebDriverInstansiator.getDriver();
	}
	
	public void registerDevice(String deviceuser, String devicepsw, String licensename) throws Exception {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename));
		verificationscreen.clickVerifyButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void restartAppAndGetNewRegCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	public void resetApp() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.resetApp();
		waitABit(8*1000);
		switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void waitABit(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setNetworkOff() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		NetworkConnectionSetting networkConnection = new NetworkConnectionSetting(true, false, false);
	    ((AndroidDriver)appiumdriver).setNetworkConnection(networkConnection);
	    switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void setNetworkOn() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		NetworkConnectionSetting networkConnection = new NetworkConnectionSetting(false, true, true);
	    ((AndroidDriver)appiumdriver).setNetworkConnection(networkConnection);
	    waitABit(1000);
	    switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void tapHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.navigate().back();
		switchApplicationContext(AppContexts.WEB_CONTEXT);
	}

}
