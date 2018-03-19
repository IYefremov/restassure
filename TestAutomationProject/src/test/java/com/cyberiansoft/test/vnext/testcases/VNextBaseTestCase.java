package com.cyberiansoft.test.vnext.testcases;

import java.io.File;
import java.io.IOException;
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
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.utils.WebDriverInstansiator;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationScreensModalDialog;
import com.cyberiansoft.test.vnext.screens.VNextTeamEditionVerificationScreen;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
/*import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.appium.PCloudyAppiumSession;
import com.ssts.pcloudy.dto.appium.booking.BookingDtoDevice;
import com.ssts.pcloudy.dto.device.MobileDevice;
import com.ssts.pcloudy.dto.file.PDriveFileDTO;
import com.ssts.pcloudy.exception.ConnectError;
import com.ssts.util.reporting.ExecutionResult;
import com.ssts.util.reporting.MultipleRunReport;
import com.ssts.util.reporting.SingleRunReport;
import com.ssts.util.reporting.printers.HtmlFilePrinter;
*/

import io.appium.java_client.service.local.AppiumDriverLocalService;


public class VNextBaseTestCase {
	
	protected static AppiumDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
	protected DesiredCapabilities appiumcap;
	protected static AppiumDriverLocalService service;
	protected String regcode = "";
	protected static String defaultbrowser;
	protected static String deviceofficeurl;
	protected static String deviceuser;
	protected static String devicepsw;
	protected static String deviceplatform;
	protected static boolean buildproduction;
	
	@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void startServer(String browser, String devplatform) throws IOException {
		
		//AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.LOG_LEVEL, "error");
	       // service = builder.build();
	        //service.start();	
		//AppDownloader.unpackArchive(new URL("http://amtqc.cyberiansoft.net/Uploads/Repair360Android_1003.app.zip"), new File("./data/"));
		//waitABit(4000);
		
		
			deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
	       
			deviceplatform = devplatform;
			if (deviceplatform.contains("ios"))
				//appiumdriver = VNextAppiumDriverBuilder.forIOS().withEndpoint(new URL("http://127.0.0.1:4900/wd/hub")).build();
				DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
			else {
				DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.ANDROID);
				DriverBuilder.getInstance().getAppiumDriver().removeApp("com.automobiletechnologies.ReconProClient");
				DriverBuilder.getInstance().getAppiumDriver().quit();
				DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.ANDROID);
				appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
				
				/*appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
				appiumdriver.removeApp("com.automobiletechnologies.ReconProClient");
				appiumdriver.quit();
				appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();*/
			
			}
			defaultbrowser = browser;
			deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
			devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
			if (VNextConfigInfo.getInstance().getBuildProductionAttribute().equals("true"))
				buildproduction = true;
			else
				buildproduction = false;
	}
	
	public void initiateWebDriver() {
		WebDriverInstansiator.setDriver(defaultbrowser);
		webdriver = WebDriverInstansiator.getDriver();
	}
	
	public void setUp() {
		waitABit(8000);
		setNetworkOn();
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
		Set<String> contextNames = DriverBuilder.getInstance().getAppiumDriver().getContextHandles();
		List<String> handlesList = new ArrayList<String>(contextNames);
		/*for (String handles : handlesList) {
			System.out.println("+++" + handles);
			if (handles.contains("com.automobiletechnologies.repair360"))
				appiumdriver.context(handles);
		}*/
		if (handlesList.size() > 2)
			DriverBuilder.getInstance().getAppiumDriver().context(handlesList.get(2));
		else
			DriverBuilder.getInstance().getAppiumDriver().context(handlesList.get(1));
	}

	public void switchApplicationContext(String appcontext) {
		Set<String> contextNames = DriverBuilder.getInstance().getAppiumDriver().getContextHandles();		
		for (String contextName : contextNames) {
			if (contextName.contains(appcontext)) {
				DriverBuilder.getInstance().getAppiumDriver().context(contextName);
			}
		}
	}
	
	@AfterSuite
	public void tearDown() throws Exception {
		if (webdriver != null)
			webdriver.quit();
		if (DriverBuilder.getInstance().getAppiumDriver() != null)
			DriverBuilder.getInstance().getAppiumDriver().quit();
		if (service != null)
            service.stop();
	}
	
	public String getDeviceRegistrationCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		WebDriverInstansiator.setDriver(defaultbrowser);
		webdriver = WebDriverInstansiator.getDriver();
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

	
	public void registerDevice() throws Exception {
		String phonecountrycode = "1";
		String phonenumber = "14122264998";
	
		if (buildproduction) {
			phonecountrycode = VNextUserRegistrationInfo.getInstance().getProductionDeviceRegistrationUserPhoneCountryCode();
			/*initiateWebDriver();
			webdriver.get("http://receivefreesms.com/");
			phonenumber = webdriver.findElement(By.xpath("//strong/a[contains(text(), '+1')]")).getText();
			webdriver.quit();*/
		} else {
			phonecountrycode = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneCountryCode();
			phonenumber = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneNumber();
		}

		switchToWebViewContext();
		String userregmail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
				phonecountrycode, phonenumber, userregmail);
		regscreen.waitABit(15000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonenumber));
		else
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));		
		verificationscreen.clickVerifyButton();
		
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(15*1000);
		if (DriverBuilder.getInstance().getMobilePlatform().equals(MobilePlatform.ANDROID)) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
			DriverBuilder.getInstance().getAppiumDriver().closeApp();
			DriverBuilder.getInstance().getAppiumDriver().launchApp();
			switchToWebViewContext();
		}
		
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 340);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void registerDevice(String userMail, String userPhone) throws Exception {
		String phonecountrycode = "380";
		                      
		
		
		if (buildproduction) {
			phonecountrycode = VNextUserRegistrationInfo.getInstance().getProductionDeviceRegistrationUserPhoneCountryCode();
			/*initiateWebDriver();
			webdriver.get("http://receivefreesms.com/");
			phonenumber = webdriver.findElement(By.xpath("//strong/a[contains(text(), '+1')]")).getText();
			webdriver.quit();*/
		} 

		switchToWebViewContext();
		//String userregmail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
				phonecountrycode, userPhone, userMail);
		regscreen.waitABit(7000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonecountrycode + userPhone));
		else
			//verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonecountrycode + userPhone));
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userMail).replaceAll("\"", ""));		
		verificationscreen.clickVerifyButton(); 
		
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(10*1000);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();

		switchToWebViewContext();
		
		
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void registerTeamEdition(String licenseName) {
		//final String searchlicensecriteria = "Automation_android";
		//final String searchlicensecriteria = "AutoTests";

		WebDriverInstansiator.setDriver(defaultbrowser);
		webdriver = WebDriverInstansiator.getDriver();
		webdriverGotoWebPage("https://reconpro.cyberianconcepts.com/Admin/Devices.aspx");

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(licenseName);
		String regCode = devicespage.getFirstRegCodeInTable();

		webdriver.quit();
		
		switchToWebViewContext();
		
		/*VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.clickIHaveRigistrationCodeLink();
		regscreen.waitABit(5*1000);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.closeApp();
		appiumdriver.launchApp();

		switchToWebViewContext();*/
		
		System.out.println("Registration code:" + regCode);
		
		VNextTeamEditionVerificationScreen verificationscreen = new VNextTeamEditionVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
		verificationscreen.setDeviceRegistrationCode(regCode);
		verificationscreen.clickVerifyButton(); 
		

		verificationscreen.waitABit(20*1000);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		switchToWebViewContext();
		
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 240);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void restartAppAndGetNewRegCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	public void resetApp() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		DriverBuilder.getInstance().getAppiumDriver().resetApp();
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
		try {
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus disable");
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.data_connection --es setstatus disable");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//((HasNetworkConnection) appiumdriver).setConnection(Connection.AIRPLANE);
		//appiumdriver.setConnection(Connection.NONE);
		//appiumdriver.setConnection(Connection.AIRPLANE);
	    switchToWebViewContext();
	    //switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void setNetworkOn() {
		if (deviceplatform.contains("android")) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			try {
				Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus enable");
				Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.data_connection --es setstatus enable");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			/*Connection networkConnection = ((HasNetworkConnection) appiumdriver).getConnection();
			if (networkConnection.equals(Connection.AIRPLANE) ) {
				try {
				((HasNetworkConnection) appiumdriver).setConnection(Connection.ALL);		
				waitABit(5000);
				} catch (WebDriverException e) {
				//todo
				}
				
			}*/
			switchToWebViewContext();
		}
	}
	
	public void tapHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		DriverBuilder.getInstance().getAppiumDriver().navigate().back();
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
	/*@SuppressWarnings("unchecked")
	//@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void runExecutionOnPCloudy(String browser, String devplatform) throws InterruptedException, IOException, ConnectError {
		deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
		defaultbrowser = browser;
		deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
		devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
		deviceplatform = devplatform;
		int deviceBookDuration = 10;
		
		PCloudyAppiumSession pCloudySession;
		
		Connector con = new Connector("https://device.pcloudy.com/api/");
		//Connector con = new Connector("https://us.pcloudy.com/api");
		
		
		// User Authentication over pCloudy
		String authToken = con.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp");

		// Select apk in pCloudy Cloud Drive
		File appDir = new File("./data/");
	    File fileToBeUploaded = new File(appDir, "Repair360Android.apk");
	    
	    //PDriveFileDTO alreadyUploadedApp = con.getAvailableAppIfUploaded(authToken, fileToBeUploaded.getName());
	    System.out.println("Uploading App: " + fileToBeUploaded.getAbsolutePath());
		PDriveFileDTO uploadedApp = con.uploadApp(authToken, fileToBeUploaded, false);
		System.out.println("App uploaded");
		//PDriveFileDTO alreadyUploadedApp = new PDriveFileDTO();
		//alreadyUploadedApp.file = uploadedApp.file;

		ArrayList<MobileDevice> selectedDevices = new ArrayList<>();
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS8_Android_7.0.0", 149, "GalaxyS8", "Galaxy S8", "android", "7.0.0", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS8Plus_Android_7.0.0", 148, "GalaxyS8Plus", "Galaxy S8 +", "android", "7.0.0", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS6Edge_Android_6.0.1", 151, "GalaxyS6Edge", "S6 Edge", "android", "6.0.1", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Lg_G5_Android_7.0.0", 162, "G5", "G5", "android", "7.0.0", "Lg")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyA5_Android_7.0.0", 355, "GalaxyA5", "Galaxy A5", "android", "7.0.0", "Samsung")); 
		selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS6_Android_7.0.0", 348, "GalaxyS6", "Galaxy S6", "android", "7.0.0", "Samsung"));
		// Book the selected devices in pCloudy
		String sessionName = "Appium Session " + new Date();
		BookingDtoDevice bookedDevice = con.AppiumApis().bookDevicesForAppium(authToken, selectedDevices, deviceBookDuration, sessionName)[0];
		System.out.println("Devices booked successfully");

		con.AppiumApis().initAppiumHubForApp(authToken, uploadedApp);

		pCloudySession = new PCloudyAppiumSession(con, authToken, bookedDevice);
		
		URL appiumEndpoint = pCloudySession.getConnector().AppiumApis().getAppiumEndpoint(pCloudySession.getAuthToken());
		appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(appiumEndpoint).buildForPCloudy(pCloudySession.getDto().capabilities.deviceName, pCloudySession.getDto().capabilities.deviceName);
		
		
    } */

    /*private Runnable getTestCaseClass(final URL endpoint, final BookingDtoDevice aDevice,final PCloudyAppiumSession pCloudySession, final SingleRunReport report,final File reportsFolder) { 
        // this will give a Thread Safe TestScript class. 
        // You may also like to have this as a named class in a separate file 

        return new Runnable() { 

            @Override 
            public void run() { 
             
                File deviceFolder = new File(reportsFolder, aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version); 
                File snapshotsFolder = new File(deviceFolder, "Snapshots"); 
                snapshotsFolder.mkdirs(); 
            try{ 
                DesiredCapabilities capabilities = new DesiredCapabilities(); 
                capabilities.setCapability("newCommandTimeout", 600); 
                capabilities.setCapability("launchTimeout", 90000); 
                capabilities.setCapability("deviceName", aDevice.capabilities.deviceName); 
                capabilities.setCapability("browserName", aDevice.capabilities.deviceName); 
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios"); 
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"10.2"); 
                capabilities.setCapability("bundleId", "com.automobiletechnologies.repair360"); 
                capabilities.setCapability("usePrebuiltWDA", false); 
                capabilities.setCapability("acceptAlerts", true); 
             
                if (aDevice.getVersion().compareTo(new Version("9.3")) >= 0) 
                    capabilities.setCapability("automationName", "XCUITest"); 
                else 
                    capabilities.setCapability("automationName", "Appium"); 
                 
                appiumdriver = new AppiumDriver<MobileElement>(endpoint, capabilities);
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // Your Test Script Goes Here 
               switchToWebViewContext();
        		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
        		String userregmail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();
        		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
        				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
        				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneCountryCode(), 
        				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneNumber(), userregmail);
        		regscreen.waitABit(7000);
        		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
        		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));
        		verificationscreen.clickVerifyButton(); 
               
                
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // ########################################### 
                 
                File snapshotTmpFile = pCloudySession.takeScreenshot(); 
                File snapshotFile = new File(snapshotsFolder, snapshotTmpFile.getName()); 
                FileUtils.moveFile(snapshotTmpFile, snapshotFile); 

                report.addStep("Take Screenshot", null, null, snapshotFile.getAbsolutePath(), ExecutionResult.Pass); 

                // release session now 
                pCloudySession.releaseSessionNow(); 

                report.addStep("Release Appium Session", null, null, ExecutionResult.Pass); 

                } catch (ConnectError | IOException | InterruptedException e) { 
                    report.addStep("Error Running TestCase", null, e.getMessage(), ExecutionResult.Fail); 
                    e.printStackTrace(); 
                } finally { 
                    HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
                    printer.printSingleRunReport(report); 

                } 
            } 

        }; 
		
		}*/

}
