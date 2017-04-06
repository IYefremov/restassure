package com.cyberiansoft.test.vnext.testcases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.utils.WebDriverInstansiator;
import com.cyberiansoft.test.vnext.builder.VNextAppiumDriverBuilder;
import com.cyberiansoft.test.vnext.screens.SwipeableWebDriver;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationScreensModalDialog;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;



/*import com.ssts.pcloudy.ConnectError;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.dto.appium.booking.BookingDtoDevice;
import com.ssts.pcloudy.dto.appium.booking.BookingDtoResult;
import com.ssts.pcloudy.dto.device.MobileDevice;
import com.ssts.pcloudy.dto.file.PDriveFileDTO;*/
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.Connection;
import io.appium.java_client.android.HasNetworkConnection;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class VNextBaseTestCase {
	
	protected static AppiumDriver<WebElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
	protected DesiredCapabilities appiumcap;
	protected static AppiumDriverLocalService service;
	protected String regcode = "";
	protected static String defaultbrowser;
	protected static String deviceofficeurl;
	protected static String deviceuser;
	protected static String devicepsw;
	protected static String devicelicensename;
	protected static String deviceplatform;
	
	@BeforeSuite
	@Parameters({ "selenium.browser", "backoffice.url", "user.name", "user.psw", "device.license", "device.platform"})
	public void startServer(String browser, String bourl, String username, String userpsw, String licensename, String devplatform) throws MalformedURLException {
		
		//AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.LOG_LEVEL, "error");
       // service = builder.build();
        //service.start();	
		deviceofficeurl = bourl;
       /* File appDir = new File("./data/");
	    File app = new File(appDir, "ReconPro.apk");
	    appiumcap = new DesiredCapabilities();

		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "mydroid19"); 
		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
		appiumcap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, false);
		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
		appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
		//appiumcap.setCapability("chromedriverExecutable", "c:\\Users\\Alex\\AppData\\Roaming\\npm\\node_modules\\appium\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
		//appiumcap.setCapability("chromedriverExecutable", "d:\\Work\\AQC\\TestAutomationProject\\browsers\\chromedriver\\chromedriver.exe");
		//appiumcap.setCapability("recreateChromeDriverSessions", true);
		//String ANDROID_DEVICE_SOCKET = "com.automobiletechnologies.ReconPro" + "_devtools_remote";
		//appiumcap.setCapability("androidDeviceSocket", ANDROID_DEVICE_SOCKET);
		//ChromeOptions chromeOptions = new ChromeOptions();
		//chromeOptions.setExperimentalOption("androidDeviceSocket", ANDROID_DEVICE_SOCKET);
		//appiumcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		//appiumcap.setCapability("appWaitPackage", "com.android.packageinstaller");
		//appiumcap.setCapability("appWaitActivity", ".permission.ui.GrantPermissionsActivity");
		appiumcap.setCapability("autoGrantPermissions", "true");
		
		
		appiumdriver = new SwipeableWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),
				appiumcap);*/
		
		//appiumdriver = new SwipeableWebDriver(service.getUrl(), appiumcap);
		deviceplatform = devplatform;
		if (deviceplatform.contains("ios"))
			appiumdriver = VNextAppiumDriverBuilder.forIOS().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
		else
			appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
		
		defaultbrowser = browser;
		deviceuser = username;
		devicepsw = userpsw;
		devicelicensename = licensename;
	}
	
	public void setUp() {
		waitABit(15000);
		//switchToWebViewContext();
	}
	
	public AppiumDriver getAppiumDriver() {
		return appiumdriver;
	}
	
	public WebDriver getWebDriver() {
		return webdriver;
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
		switchToWebViewContext();
		return testcasename + uuid + ".jpeg";
	}
	
	public void switchToWebViewContext() {
		Set<String> contextNames = appiumdriver.getContextHandles();
		List<String> handlesList = new ArrayList(contextNames);
		if (handlesList.size() > 2)
			appiumdriver.context(handlesList.get(2));
		else
			appiumdriver.context(handlesList.get(1));
		/*boolean switched = false;
		final int ITERATIONS_COUNT = 100;
		for (int i = 0; i < ITERATIONS_COUNT; i++) {
			Set<String> contextNames = appiumdriver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println("++++++" + contextName);
				//if (contextName.equals("WEBVIEW_com.automobiletechnologies.repair360")) {
				if (contextName.contains(".5")) {
					System.out.println("----------" + contextName);
					try {
						appiumdriver.context(contextName);
						System.out.println("---------- Success");
						i = ITERATIONS_COUNT;
						switched = true;
						break;
					} catch (NoSuchContextException ex) {
						System.out.println("---------- Fail");
						waitABit(1000);
					}
				} else if (!contextName.equals("NATIVE_APP")){
					System.out.println("=============" + contextName);
					waitABit(1000);
				}
			}
		}
		return switched;*/
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
	
	public void registerDevice() throws Exception {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		//wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(By.xpath("//iframe"))));
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		switchToWebViewContext();
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		String userregmail = "osmak.oksana+408@gmail.com";
		regscreen.setUserRegistrationInfoAndSend("QA", "QA", "380", "978385064", userregmail);
		regscreen.waitABit(5000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton(); 
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(60*1000);
		appiumdriver.switchTo().defaultContent();
		if (appiumdriver.findElements(By.xpath("//body/child::*")).size() < 1) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
			appiumdriver.closeApp();
			appiumdriver.launchApp();
		    switchToWebViewContext();
		} else {
			VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
			informationdlg.clickInformationDialogOKButton();
		}
	}
	
	public void restartAppAndGetNewRegCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	public void resetApp() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.resetApp();
		waitABit(30*1000);
		switchToWebViewContext();
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
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
		((HasNetworkConnection) appiumdriver).setConnection(Connection.AIRPLANE);
	    switchToWebViewContext();
	    //switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void setNetworkOn() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);	
		Connection networkConnection = ((HasNetworkConnection) appiumdriver).getConnection();
		if (networkConnection.equals(Connection.AIRPLANE) ) {
			((HasNetworkConnection) appiumdriver).setConnection(Connection.ALL);		
			waitABit(5000);
		}
		switchToWebViewContext();
	    //switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void tapHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.navigate().back();
		switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void webdriverGotoWebPage(String url) {
		webdriver.get(url);
		if (defaultbrowser.equals("ie")) {
			if (webdriver.findElements(By.id("overridelink")).size() > 0) {
				webdriver.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
	}
	
	/////////////////////////////
	//@BeforeSuite
	/*@Parameters({ "selenium.browser", "backoffice.url", "user.name", "user.psw", "device.license" })
	public void runExecutionOnPCloudy(String browser, String bourl, String username, String userpsw, String licensename) throws InterruptedException, IOException, ConnectError {
		deviceofficeurl = bourl;
		defaultbrowser = browser;
		deviceuser = username;
		devicepsw = userpsw;
		devicelicensename = licensename;
		
		
		Connector pCloudyCONNECTOR = new Connector();

		// User Authentication over pCloudy
		String authToken = pCloudyCONNECTOR.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp");
		ArrayList selectedDevices = new ArrayList<>();

		// Populate the selected Devices here
		
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS5_Android_5.0.0", 51, "GalaxyS5", "Galaxy S5", "android", "5.0.0", "Samsung"));    
		selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS4_Android_5.0.1", 44, "GalaxyS4", "Galaxy S4", "android", "5.0.1", "Samsung"));  
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyA7_Android_5.0.2", 106, "GalaxyA7", "Galaxy A7", "android", "5.0.2", "Samsung"));
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyNote5_Android_6.0.1", 91, "GalaxyNote5", "Galaxy Note5", "android", "6.0.1", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_S7Edge_Android_6.0.1", 130, "S7Edge", "S7 Edge", "android", "6.0.1", "Samsung"));
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS6_Android_6.0.1", 132, "GalaxyS6", "Galaxy S6", "android", "6.0.1", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Huawei_HuaweiHonor5X_Android_5.1.1", 129, "HuaweiHonor5X", "Honor 5X", "android", "5.1.1", "Huawei")); 
		//selectedDevices.add(MobileDevice.getNew("Motorola_Nexus6_Android_6.0.1", 201, "Nexus6", "Nexus 6", "android", "6.0.1", "Motorola")); 
		//selectedDevices.add(MobileDevice.getNew("Motorola_MotorolaXPlay_Android_6.0.1", 142, "MotorolaXPlay", "X Play", "android", "6.0.1", "Motorola")); 
		//selectedDevices.add(MobileDevice.getNew("Motorola_MotorolaMotoE2_Android_5.0.2", 122, "MotorolaMotoE2", "Moto E2", "android", "5.0.2", "Motorola")); 
		//selectedDevices.add(MobileDevice.getNew("Lg_G4Dual_Android_6.0.0", 100, "G4Dual", "G4 Dual", "android", "6.0.0", "Lg"));
		//selectedDevices.add(MobileDevice.getNew("Htc_One_Android_5.0.2", 62, "One", "One", "android", "5.0.2", "Htc"));
		//selectedDevices.add(MobileDevice.getNew("Lg_G5_Android_6.0.1", 154, "G5", "G5", "android", "6.0.1", "Lg")); 
		//selectedDevices.add(MobileDevice.getNew("Htc_10_Android_6.0.1", 155, "10", "10", "android", "6.0.1", "Htc")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS7_Android_6.0.1", 153, "GalaxyS7", "Galaxy S7", "android", "6.0.1", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyA7_Android_6.0.1", 226, "GalaxyA7", "Galaxy A7", "android", "6.0.1", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Htc_Desire630_Android_6.0.1", 209, "Desire630", "Desire 630", "android", "6.0.1", "Htc")); 
		//selectedDevices.add(MobileDevice.getNew("Lg_Nexus5X_Android_7.0.0", 215, "Nexus5X", "Nexus 5X", "android", "7.0.0", "Lg")); 
		//selectedDevices.add(MobileDevice.getNew("Htc_Desire830_Android_5.1.0", 217, "Desire830", "Desire 830", "android", "5.1.0", "Htc"));
		//selectedDevices.add(MobileDevice.getNew("Htc_Desire628_Android_5.1.0", 211, "Desire628", "Desire 628", "android", "5.1.0", "Htc"));
		
		
		BookingDtoDevice[] bookedDevicesIDs = pCloudyCONNECTOR.bookDevicesForAppium(authToken, selectedDevices, 10, "friendlySessionName");
		System.out.println("Devices booked successfully");

		// Upload apk in pCloudy
		File appDir = new File("./data/");
		File app = new File(appDir, "ReconPro.apk");
		PDriveFileDTO pDriveFile = pCloudyCONNECTOR.uploadApp(authToken, app);
		System.out.println("apk file uploaded successfully");
		pCloudyCONNECTOR.initAppiumHubForApp(authToken, pDriveFile);

		// Get the endpoint from pCloudy
		URL endpoint = pCloudyCONNECTOR.getAppiumEndpoint(authToken);
		System.out.println("Appium Endpoint:" + endpoint);

		appiumcap = new DesiredCapabilities();
		//capabilities.setCapability("newCommandTimeout", 600);
		//capabilities.setCapability("launchTimeoutv", 90000);
		appiumcap.setCapability("deviceName", bookedDevicesIDs[0].capabilities.deviceName);
		appiumcap.setCapability("browserName", bookedDevicesIDs[0].capabilities.deviceName);
		appiumcap.setCapability("platformName", "Android");
		appiumcap.setCapability("appPackage", "com.automobiletechnologies.repair360");
		appiumcap.setCapability("appActivity","com.automobiletechnologies.repair360.MainActivity");
		appiumcap.setCapability("rotatable", true);
		appiumcap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "1500");
		appiumcap.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
		
		try {
			appiumdriver = new SwipeableWebDriver(endpoint, appiumcap);
		} catch (Exception ex) {
			// this might have happened due to the remote appium server taking some more time to register with the hub
			waitABit(10000);
			// lets wait for some time and then give it a retry
			appiumdriver = new SwipeableWebDriver(endpoint, appiumcap);
		}
		
		
		
		// Create multiple driver objects in multiple threads
		
		}*/

}
