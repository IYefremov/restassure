package com.cyberiansoft.test.vnext.testcases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.vnext.builder.VNextAppiumDriverBuilder;
import com.cyberiansoft.test.vnext.builder.VNextAppiumDriverBuilder.AndroidDriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.SwipeableWebDriver;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationScreensModalDialog;
import com.cyberiansoft.test.vnext.screens.VNextTeamEditionVerificationScreen;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
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
import io.appium.java_client.android.Connection;
import io.appium.java_client.android.HasNetworkConnection;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;


public class VNextBaseTestCase {
	
	protected static SwipeableWebDriver appiumdriver;
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
	public void startServer(String browser, String devplatform) throws MalformedURLException {
		
		//AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.LOG_LEVEL, "error");
	       // service = builder.build();
	        //service.start();	
			deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
	       
			deviceplatform = devplatform;
			if (deviceplatform.contains("ios"))
				appiumdriver = VNextAppiumDriverBuilder.forIOS().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
			else {
				appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
				appiumdriver.removeApp("com.automobiletechnologies.repair360_devstage");
				appiumdriver.quit();
				appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
			
			}
			defaultbrowser = browser;
			deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
			devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
			if (VNextConfigInfo.getInstance().getBuildProductionAttribute().equals("true"))
				buildproduction = true;
			else
				buildproduction = false;
	}
	
	public void setUp() {
		waitABit(8000);
	   // switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public SwipeableWebDriver getAppiumDriver() {
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
		System.out.println("+++" + handlesList.size());
		for (String handles : handlesList) {
			System.out.println("+++" + handles);
			if (handles.contains("com.automobiletechnologies.repair360"))
				appiumdriver.context(handles);
		}
		/*if (handlesList.size() > 2)
			appiumdriver.context(handlesList.get(2));
		else
			appiumdriver.context(handlesList.get(1));*/
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
		String phonecountrycode = "1";
		String phonenumber = "14122264998";
		String regcode1 = VNextWebServicesUtils.getProdRegCode(phonenumber);
		
		
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
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		
		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
				phonecountrycode, phonenumber, userregmail);
		regscreen.waitABit(7000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonenumber));
		else
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));		
		verificationscreen.clickVerifyButton(); 
		
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(10*1000);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.closeApp();
		appiumdriver.launchApp();

		switchToWebViewContext();
		
		
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void registerDevice(String userMail, String userPhone) throws Exception {
		String phonecountrycode = "1";
		                      
		
		
		if (buildproduction) {
			phonecountrycode = VNextUserRegistrationInfo.getInstance().getProductionDeviceRegistrationUserPhoneCountryCode();
			/*initiateWebDriver();
			webdriver.get("http://receivefreesms.com/");
			phonenumber = webdriver.findElement(By.xpath("//strong/a[contains(text(), '+1')]")).getText();
			webdriver.quit();*/
		} 

		switchToWebViewContext();
		//String userregmail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		
		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
				phonecountrycode, userPhone, userMail);
		regscreen.waitABit(7000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonecountrycode + userPhone));
		else
			//verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonecountrycode + userPhone));
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userMail).replaceAll("\"", ""));		
		verificationscreen.clickVerifyButton(); 
		
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(10*1000);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.closeApp();
		appiumdriver.launchApp();

		switchToWebViewContext();
		
		
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public void registerTeamEdition() {
		final String searchlicensecriteria = "Anastasia_android";

		initiateWebDriver();
		webdriverGotoWebPage("https://reconpro.cyberianconcepts.com/Admin/Devices.aspx");

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(searchlicensecriteria);
		String regCode = devicespage.getFirstRegCodeInTable();

		getWebDriver().quit();
		
		switchToWebViewContext();
		VNextTeamEditionVerificationScreen verificationscreen = new VNextTeamEditionVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(regCode);
		verificationscreen.clickVerifyButton(); 
		
		verificationscreen.waitABit(10*1000);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		//appiumdriver.closeApp();
		//appiumdriver.launchApp();
		switchToWebViewContext();
		
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
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
		if (deviceplatform.contains("android")) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);	
			Connection networkConnection = ((HasNetworkConnection) appiumdriver).getConnection();
			if (networkConnection.equals(Connection.AIRPLANE) ) {
				((HasNetworkConnection) appiumdriver).setConnection(Connection.ALL);		
				waitABit(5000);
			}
			switchToWebViewContext();
		}
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
	/*@SuppressWarnings("unchecked")
	//@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void runExecutionOnPCloudy(String browser, String devplatform) throws InterruptedException, IOException, ConnectError {
		deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
		defaultbrowser = browser;
		deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
		devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
		deviceplatform = devplatform;
		int deviceBookDuration = 60;
		
		PCloudyAppiumSession pCloudySession;
		
		//Connector con = new Connector("https://device.pcloudy.com/api/");
		Connector con = new Connector("https://us.pcloudy.com/api");
		
		
		// User Authentication over pCloudy
		String authToken = con.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp");

		// Select apk in pCloudy Cloud Drive
		File appDir = new File("./data/");
	    File fileToBeUploaded = new File(appDir, "ReconPro.apk");
	    
	    //PDriveFileDTO alreadyUploadedApp = con.getAvailableAppIfUploaded(authToken, fileToBeUploaded.getName());
	    System.out.println("Uploading App: " + fileToBeUploaded.getAbsolutePath());
		PDriveFileDTO uploadedApp = con.uploadApp(authToken, fileToBeUploaded, false);
		System.out.println("App uploaded");
		//PDriveFileDTO alreadyUploadedApp = new PDriveFileDTO();
		//alreadyUploadedApp.file = uploadedApp.file;

		ArrayList<MobileDevice> selectedDevices = new ArrayList<>();
		selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS8_Android_7.0.0", 149, "GalaxyS8", "Galaxy S8", "android", "7.0.0", "Samsung")); 
		//selectedDevices.add(MobileDevice.getNew("Samsung_GalaxyS8Plus_Android_7.0.0", 148, "GalaxyS8Plus", "Galaxy S8 +", "android", "7.0.0", "Samsung")); 
		
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
                 
                appiumdriver = new SwipeableWebDriver(endpoint, capabilities);
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
