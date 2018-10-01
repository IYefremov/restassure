package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextToolsInfo;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

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


public class VNextBaseTestCase {
	
	protected static AppiumDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected String regcode = "";
	protected static String deviceofficeurl;
	protected static String deviceuser;
	protected static String devicepsw;
	protected static boolean buildproduction;
	protected static BrowserType browsertype;
	protected static MobilePlatform mobilePlatform;
	protected static Employee employee;


	protected static String deviceID;
	protected static String licenseID;
	protected static String appID;
	protected static String appLicenseEntity;

	private ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<AppiumDriverLocalService>();
	
	@BeforeSuite
	public void startServer()  {
		
		browsertype = BaseUtils.getBrowserType(VNextToolsInfo.getInstance().getDefaultBrowser());
		mobilePlatform = BaseUtils.getMobilePlatform(VNextToolsInfo.getInstance().getDefaultPlatform());
		deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();

		service.set(new AppiumServiceBuilder().withAppiumJS(new File(System.getenv("Appium") + "\\build\\lib\\main.js"))
				.usingAnyFreePort().withArgument(SESSION_OVERRIDE)
				.withArgument(LOG_LEVEL, "error")
				.build());
		service.get().start();

		if (service.get() == null || !service.get().isRunning()) {
			throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
		}


		if (mobilePlatform.getMobilePlatformString().contains("ios"))
			DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
		else {
			AppiumInicializator.getInstance().initAppium(MobilePlatform.ANDROID, service.get().getUrl());
			//DriverBuilder.getInstance().setAppiumDriver(mobilePlatform);
			if (VNextConfigInfo.getInstance().installNewBuild()) {
				DriverBuilder.getInstance().getAppiumDriver().removeApp("com.automobiletechnologies.ReconProClient");
				DriverBuilder.getInstance().getAppiumDriver().quit();
				//DriverBuilder.getInstance().setAppiumDriver(mobilePlatform);
				AppiumInicializator.getInstance().initAppium(MobilePlatform.ANDROID, service.get().getUrl());

			}
			appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
			appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

		deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
		devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
		if (VNextConfigInfo.getInstance().getBuildProductionAttribute().equals("true"))
			buildproduction = true;
		else
			buildproduction = false;
	}

	public Employee getDeviceEmployee() {
		return employee;
	}
	
	public void setUp() {
		AppiumUtils.setNetworkOn();
	}
	
	@AfterSuite
	public void tearDown() throws Exception {
		if (DriverBuilder.getInstance().getDriver() != null)
			DriverBuilder.getInstance().getDriver().quit();
		if (DriverBuilder.getInstance().getAppiumDriver() != null)
			DriverBuilder.getInstance().getAppiumDriver().quit();
		if (service.get() != null)
            service.get().stop();
	}
	
	public String getDeviceRegistrationCode(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		DriverBuilder.getInstance().setDriver(browsertype);
		webdriver = DriverBuilder.getInstance().getDriver();
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
		DriverBuilder.getInstance().getDriver().quit();
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

		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		String userregmail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();

		VNextEditionsScreen editionsScreen = new VNextEditionsScreen(appiumdriver);
		VNextEnvironmentSelectionScreen environmentSelectionScreen = editionsScreen.selectEdition("Repair360");
		environmentSelectionScreen.selectEnvironment("Development");
		BaseUtils.waitABit(15*1000);
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);

		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		
		regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
				phonecountrycode, phonenumber, userregmail);
		BaseUtils.waitABit(15000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonenumber));
		else
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));		
		verificationscreen.clickVerifyButton();
		
		//VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(DriverBuilder.getInstance().getAppiumDriver());
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		BaseUtils.waitABit(15*1000);
		if (DriverBuilder.getInstance().getMobilePlatform().equals(MobilePlatform.ANDROID)) {
			AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);	
			DriverBuilder.getInstance().getAppiumDriver().closeApp();
			DriverBuilder.getInstance().getAppiumDriver().launchApp();
			AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		}
		
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 340);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationdlg.clickInformationDialogOKButton();
	}

	public void registerTeamEdition(String licensename) {


		DriverBuilder.getInstance().setDriver(browsertype);
		webdriver = DriverBuilder.getInstance().getDriver();
		WebDriverUtils.webdriverGotoWebPage(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ActiveDevicesWebPage devicespage = companypage.clickManageDevicesLink();;

		devicespage.setSearchCriteriaByName(licensename);
		final String regCode = devicespage.getFirstRegCodeInTable();
		webdriver.quit();
		
		//AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		
		/*VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.clickIHaveRigistrationCodeLink();
		regscreen.waitABit(5*1000);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.closeApp();
		appiumdriver.launchApp();

		switchToWebViewContext();*/

		VNextEditionsScreen editionsScreen = new VNextEditionsScreen(appiumdriver);
		VNextEnvironmentSelectionScreen environmentSelectionScreen = editionsScreen.selectEdition("ReconPro Starter");
		environmentSelectionScreen.selectEnvironment("Development");

		VNextTeamEditionVerificationScreen verificationscreen = new VNextTeamEditionVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
		verificationscreen.setDeviceRegistrationCode(regCode);
		BaseUtils.waitABit(5000);
		verificationscreen.clickVerifyButton();

		BaseUtils.waitABit(20*1000);
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		VNextDownloadDataScreen downloaddatascreen = new VNextDownloadDataScreen(appiumdriver);


        DriverBuilder.getInstance().setDriver(browsertype);
        webdriver = DriverBuilder.getInstance().getDriver();
        WebDriverUtils.webdriverGotoWebPage(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingURL());

        loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.UserLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        companypage = backofficeheader.clickCompanyLink();
        devicespage = companypage.clickManageDevicesLink();;

        devicespage.setSearchCriteriaByName(licensename);

		deviceID = devicespage.getDeviceID();
		licenseID = devicespage.getLicenseID();
        appID = devicespage.getApplicationID();
        appLicenseEntity = devicespage.getLicenseEntityName();
        webdriver.quit();


		downloaddatascreen.waitUntilDatabasesDownloaded();

		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationdlg.clickInformationDialogOKButton();
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
