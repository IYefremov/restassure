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
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
/*import com.ssts.pcloudy.ConnectError;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.Version;
import com.ssts.pcloudy.appium.PCloudyAppiumSession;
import com.ssts.pcloudy.dto.appium.booking.BookingDtoDevice;
import com.ssts.pcloudy.dto.device.MobileDevice;
import com.ssts.pcloudy.dto.file.PDriveFileDTO;
import com.ssts.util.reporting.ExecutionResult;
import com.ssts.util.reporting.MultipleRunReport;
import com.ssts.util.reporting.SingleRunReport;
import com.ssts.util.reporting.printers.HtmlFilePrinter;
*/
import io.appium.java_client.android.Connection;
import io.appium.java_client.android.HasNetworkConnection;
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
		if (handlesList.size() > 2)
			appiumdriver.context(handlesList.get(2));
		else
			appiumdriver.context(handlesList.get(1));
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
		
		/*final String searchlicensecriteria = "VNext Automation";

		initiateWebDriver();
		webdriverGotoWebPage("https://reconpro.qc.cyberianconcepts.com/Admin/Devices.aspx");

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(searchlicensecriteria);
		String regCode = devicespage.getFirstRegCodeInTable();

		getWebDriver().quit();*/
		
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		if (buildproduction) 
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phonenumber));
		else
			verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userregmail).replaceAll("\"", ""));
		//verificationscreen.setDeviceRegistrationCode(regCode);		
		verificationscreen.clickVerifyButton(); 
		
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		registrationinformationdlg.waitABit(5*1000);
		//registrationinformationdlg.waitABit(70*1000);
		
		
		/*appiumdriver.switchTo().defaultContent();
		if (appiumdriver.findElements(By.xpath("//body/child::*")).size() < 1) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
			appiumdriver.closeApp();
			appiumdriver.launchApp();
		    switchToWebViewContext();
		} else {
			VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
			informationdlg.clickInformationDialogOKButton();
		}*/
		//System.out.println("++++" + appiumdriver.findElements(By.xpath("//body/child::*")).size());
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.closeApp();
		appiumdriver.launchApp();
		//registrationinformationdlg.waitABit(15*1000);
	    //switchToWebViewContext();

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
	@SuppressWarnings("unchecked")
	//@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void runExecutionOnPCloudy(String browser, String devplatform) throws InterruptedException, IOException {
		deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
		defaultbrowser = browser;
		deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
		devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
		deviceplatform = devplatform;
		
		
		 /*File reportsFolder = new File("Reports"); 
         
	        Connector con = new Connector("https://device.pcloudy.com/api"); 

	        // User Authentication over pCloudy 
	        String authToken = con.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp"); 

	        ArrayList selectedDevices = new ArrayList(); 
	        // Populate the selected Devices here 
	         
	        selectedDevices.add(MobileDevice.getNew("Huawei_Honor4X_Android_4.4.4", 239, "Honor4X", "Honor 4X", "android", "4.4.4", "Huawei")); 

	                     
	        // To select multiple devices manually, use either of these: 
	        //selectedDevices.addAll(con.chooseMultipleDevices(authToken, "android")); 
	        // selectedDevices.add(con.chooseSingleDevice(authToken, "android")); 
	         
	        // To select devices from a CI variable, use: 
	        //selectedDevices.addAll(con.chooseMultipleDevices(authToken,"android",CI_VariableWithDeviceFullNamesArray)); 
	         
	         
	        String sessionName = "Appium " + "Android 4.4"; 
	        if (selectedDevices.size() > 1) 
	            sessionName += " & " + (selectedDevices.size() - 1) + " others"; 
	             
	             
	        // Book the selected devices in pCloudy 
	        BookingDtoDevice[] bookedDevices = con.AppiumApis().bookDevicesForAppium(authToken, selectedDevices, 10, sessionName); 
	        System.out.println("Devices booked successfully"); 

	        // Select apk in pCloudy Cloud Drive 
	        PDriveFileDTO pDriveFile = PDriveFileDTO.getNew("ReconPro-1494675141.apk"); 
	        System.out.println("Apk file selected from CloudDrive"); 
	         
	        con.AppiumApis().initAppiumHubForApp(authToken, pDriveFile); 

	        // Get the endpoint from pCloudy 
	        URL endpoint = con.AppiumApis().getAppiumEndpoint(authToken); 
	        System.out.println("Appium Endpoint: " + endpoint); 
	         
	        URL reportFolderOnPCloudy = con.AppiumApis().getAppiumReportFolder(authToken); 
	        System.out.println("Report Folder: " + reportFolderOnPCloudy); 

	             
	        List allThreads = new ArrayList(); 
	        MultipleRunReport multipleReports = new MultipleRunReport(); 
	         
	        // Create multiple driver objects in multiple threads 
	         
	        for (int i = 0; i < bookedDevices.length; i++) { 
	            BookingDtoDevice aDevice = bookedDevices[i]; 
	            PCloudyAppiumSession pCloudySession = new PCloudyAppiumSession(con, authToken, aDevice); 
	            SingleRunReport report = new SingleRunReport(); 
	            multipleReports.add(report); 

	            report.Header = aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version; 
	            report.Enviroment.addDetail("NetworkType", aDevice.networkType); 
	            report.Enviroment.addDetail("Phone Number", aDevice.phoneNumber); 
	            report.HyperLinks.addLink("Appium Endpoint", endpoint); 
	            report.HyperLinks.addLink("pCloudy Result Folder", reportFolderOnPCloudy); 

	            File deviceFolder = new File(reportsFolder, aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version); 
                File snapshotsFolder = new File(deviceFolder, "Snapshots"); 
                snapshotsFolder.mkdirs(); 
                 
            try{ 
                DesiredCapabilities capabilities = new DesiredCapabilities(); 
                capabilities.setCapability("newCommandTimeout", 600); 
                capabilities.setCapability("launchTimeout", 90000); 
                capabilities.setCapability("deviceName", aDevice.capabilities.deviceName); 
                capabilities.setCapability("browserName", aDevice.capabilities.deviceName); 
                capabilities.setCapability("platformName", "Android"); 
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                //capabilities.setCapability("appPackage", appPackage); 
                //capabilities.setCapability("appActivity", appActivity); 
                capabilities.setCapability("rotatable", true); 
                appiumdriver = new SwipeableWebDriver(endpoint, capabilities); 
               
                } finally { 
                    HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
                    printer.printSingleRunReport(report); 

                } 
	                     
	        File consolidatedReport = new File(reportsFolder, "ConsolidatedReports.html"); 
	        HtmlFilePrinter printer = new HtmlFilePrinter(consolidatedReport); 
	        //printer.printConsolidatedSingleRunReport(multipleReports); 
	        System.out.println("Check the reports at : " + consolidatedReport.getAbsolutePath()); 

	        System.out.println("Execution Completed..."); 
	     
	    } 
		*/
		
		
		
		/*File reportsFolder = new File("Reports"); 
	     
        Connector con = new Connector("https://device.pcloudy.com/api"); 

        // User Authentication over pCloudy 
        String authToken = con.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp"); 

        ArrayList selectedDevices = new ArrayList<>(); 
        // Populate the selected Devices here 
         
        selectedDevices.add(MobileDevice.getNew("Apple_iPhone6S_Ios_10.1.1", 237, "iPhone6S", "iPhone 6S", "ios", "10.1.1", "Apple")); 
        //selectedDevices.add(MobileDevice.getNew("Apple_iPhone5_Ios_10.2.0", 256, "iPhone5", "iPhone 5", "ios", "10.2.0", "Apple")); 
        //selectedDevices.add(MobileDevice.getNew("Apple_iPhone6sPlus_Ios_10.2.1", 314, "iPhone6sPlus", "iPhone 6s Plus", "ios", "10.2.1", "Apple"));
                     
        // To select multiple devices manually, use either of these: 
        //selectedDevices.addAll(con.chooseMultipleDevicFile deviceFolder = new File(reportsFolder, aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version); 
        // selectedDevices.add(con.chooseSingleDevice(authToken, "ios")); 
         
        // To select devices from a CI variable, use: 
        //selectedDevices.addAll(con.chooseMultipleDevices(authToken,"ios",CI_VariableWithDeviceFullNamesArray)); 
         
        String sessionName = "Appium " + "iOS 10"; 
        if (selectedDevices.size() > 1) 
            sessionName += " & " + (selectedDevices.size() - 1) + " others"; 
             
        // Book the selected devices in pCloudy 
        BookingDtoDevice[] bookedDevices = con.AppiumApis().bookDevicesForAppium(authToken, selectedDevices, 30, sessionName); 
        System.out.println("Devices booked successfully"); 

        // Select ipa in pCloudy Cloud Drive 
        PDriveFileDTO pDriveFile = PDriveFileDTO.getNew("Repair360_JumpStart_Staging_3.1.0.1712302.ipa"); 
        System.out.println("IPA file selected from CloudDrive"); 
                 
         
        con.AppiumApis().initAppiumHubForApp(authToken, pDriveFile); 

        // Get the endpoint from pCloudy 

        URL endpoint = con.AppiumApis().getAppiumEndpoint(authToken); 
        System.out.println("Appium Endpoint: " + endpoint); 

        URL reportFolderOnPCloudy = con.AppiumApis().getAppiumReportFolder(authToken); 
        System.out.println("Report Folder: " + reportFolderOnPCloudy); 

         
        List<Thread> allThreads = new ArrayList(); 
        MultipleRunReport multipleReports = new MultipleRunReport(); 
         
        // Create multiple driver objects in multiple threads 
        for (int i = 0; i < bookedDevices.length; i++) { 
            BookingDtoDevice aDevice = bookedDevices[i]; 
            PCloudyAppiumSession pCloudySession = new PCloudyAppiumSession(con, authToken, aDevice); 
            SingleRunReport report = new SingleRunReport(); 
            multipleReports.add(report); 

            report.Header = aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version; 
            report.Enviroment.addDetail("NetworkType", aDevice.networkType); 
            report.Enviroment.addDetail("Phone Number", aDevice.phoneNumber); 
            report.HyperLinks.addLink("Appium Endpoint", endpoint); 
            report.HyperLinks.addLink("pCloudy Result Folder", reportFolderOnPCloudy); 
            
            
            
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
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			
            if (aDevice.getVersion().compareTo(new Version("9.3")) >= 0) 
                capabilities.setCapability("automationName", "XCUITest"); 
            else 
                capabilities.setCapability("automationName", "Appium"); 
             
            appiumdriver = new SwipeableWebDriver(endpoint, capabilities);
            AndroidDriverBuilder.setPlatformName();
            
            } finally { 
                HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
                printer.printSingleRunReport(report); 
            } 
            


        }
            File consolidatedReport = new File(reportsFolder, "ConsolidatedReports.html"); 
            HtmlFilePrinter printer = new HtmlFilePrinter(consolidatedReport); 
            //printer.printConsolidatedSingleRunReport(multipleReports); 
            System.out.println("Check the reports at : " + consolidatedReport.getAbsolutePath()); 

            System.out.println("Execution Completed..."); 
         */

    } 

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
