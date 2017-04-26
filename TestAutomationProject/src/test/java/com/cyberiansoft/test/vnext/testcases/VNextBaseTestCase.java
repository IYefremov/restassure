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
	
	//@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void startServer(String browser, String devplatform) throws MalformedURLException {
		
		//AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.LOG_LEVEL, "error");
	       // service = builder.build();
	        //service.start();	
			deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
	       
			deviceplatform = devplatform;
			if (deviceplatform.contains("ios"))
				appiumdriver = VNextAppiumDriverBuilder.forIOS().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
			else
				appiumdriver = VNextAppiumDriverBuilder.forAndroid().withEndpoint(new URL("http://127.0.0.1:4723/wd/hub")).build();
			
			defaultbrowser = browser;
			deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
			devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
	}
	
	public void setUp() {
		waitABit(15000);
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
	@BeforeSuite
	@Parameters({ "selenium.browser", "device.platform" })
	public void runExecutionOnPCloudy(String browser, String devplatform) throws InterruptedException, IOException {
		deviceofficeurl = VNextConfigInfo.getInstance().getBackOfficeCapiURL();
		defaultbrowser = browser;
		deviceuser = VNextConfigInfo.getInstance().getUserCapiUserName();
		devicepsw = VNextConfigInfo.getInstance().getUserCapiUserPassword();
		deviceplatform = devplatform;
		
		/*
		File reportsFolder = new File("Reports"); 
	     
        Connector con = new Connector("https://us.pcloudy.com/api"); 

        // User Authentication over pCloudy 
        String authToken = con.authenticateUser("olexandr.kramar@cyberiansoft.com", "kg3s78jmj7qbxs7xhs4krrhp"); 

        ArrayList selectedDevices = new ArrayList<>();
        selectedDevices.add(MobileDevice.getNew("Apple_iPhone7_Ios_10.0.2", 229, "iPhone7", "iPhone 7", "ios", "10.0.2", "Apple")); 
        //selectedDevices.add(MobileDevice.getNew("Apple_iPhone7plus_Ios_10.2.1", 133, "iPhone7plus", "iPhone 7 Plus", "ios", "10.2.1", "Apple")); 
                     
        // To select multiple devices manually, use either of these: 
        //selectedDevices.addAll(con.chooseMultipleDevicFile deviceFolder = new File(reportsFolder, aDevice.manufacturer + " " + aDevice.model + " " + aDevice.version); 
        // selectedDevices.add(con.chooseSingleDevice(authToken, "ios")); 
         
        // To select devices from a CI variable, use: 
        //selectedDevices.addAll(con.chooseMultipleDevices(authToken,"ios",CI_VariableWithDeviceFullNamesArray)); 
         
        String sessionName = "Appium " + "Apple_iPhone7plus_Ios_10.2.1"; 
        if (selectedDevices.size() > 1) 
            sessionName += " & " + (selectedDevices.size() - 1) + " others"; 
             
        // Book the selected devices in pCloudy 
        BookingDtoDevice[] bookedDevices = con.AppiumApis().bookDevicesForAppium(authToken, selectedDevices, 7, sessionName); 
        System.out.println("Devices booked successfully"); 

        // Select ipa in pCloudy Cloud Drive 
        
        
	    
        PDriveFileDTO pDriveFile = PDriveFileDTO.getNew("Repair360_JumpStart_Staging_3.1.0.1711502.ipa");  
        System.out.println("IPA file selected from CloudDrive"); 
                 
        con.AppiumApis().initAppiumHubForApp(authToken, pDriveFile); 

        // Get the endpoint from pCloudy 

        URL endpoint = con.AppiumApis().getAppiumEndpoint(authToken);
        System.out.println("Appium Endpoint: " + endpoint); 

        URL reportFolderOnPCloudy = con.AppiumApis().getAppiumReportFolder(authToken); 
        System.out.println("Report Folder: " + reportFolderOnPCloudy); 

         
        List allThreads = new ArrayList(); 
        MultipleRunReport multipleReports = new MultipleRunReport(); 
        //BookingDtoDevice aDevice = bookedDevices[i]; 
        PCloudyAppiumSession pCloudySession = new PCloudyAppiumSession(con, authToken, bookedDevices[0]); 
        SingleRunReport report = new SingleRunReport(); 
        multipleReports.add(report); 

        report.Header = bookedDevices[0].manufacturer + " " + bookedDevices[0].model + " " + bookedDevices[0].version; 
        report.Enviroment.addDetail("NetworkType", bookedDevices[0].networkType); 
        report.Enviroment.addDetail("Phone Number", bookedDevices[0].phoneNumber); 
        report.HyperLinks.addLink("Appium Endpoint", endpoint); 
        report.HyperLinks.addLink("pCloudy Result Folder", reportFolderOnPCloudy); 
        
        File deviceFolder = new File(reportsFolder, bookedDevices[0].manufacturer + " " + bookedDevices[0].model + " " + bookedDevices[0].version); 
        File snapshotsFolder = new File(deviceFolder, "Snapshots"); 
        snapshotsFolder.mkdirs(); 
        try {
        
        DesiredCapabilities capabilities = new DesiredCapabilities(); 
        capabilities.setCapability("newCommandTimeout", 600); 
        capabilities.setCapability("launchTimeout", 90000); 
        capabilities.setCapability("deviceName", bookedDevices[0].capabilities.deviceName); 
        capabilities.setCapability("browserName", bookedDevices[0].capabilities.deviceName); 
        capabilities.setCapability("platformName", "ios"); 
        capabilities.setCapability("bundleId", "com.automobiletechnologies.repair360"); 
        capabilities.setCapability("usePrebuiltWDA", true); 
        capabilities.setCapability("acceptAlerts", true); 
     
        if (bookedDevices[0].getVersion().compareTo(new Version("9.3")) >= 0) 
            capabilities.setCapability("automationName", "XCUITest"); 
        else 
            capabilities.setCapability("automationName", "Appium");
        appiumdriver = new SwipeableWebDriver(endpoint, capabilities);
        
        File snapshotTmpFile = pCloudySession.takeScreenshot(); 
        File snapshotFile = new File(snapshotsFolder, snapshotTmpFile.getName()); 
        FileUtils.moveFile(snapshotTmpFile, snapshotFile); 

        report.addStep("Take Screenshot", null, null, snapshotFile.getAbsolutePath(), ExecutionResult.Pass); 

        // release session now 
        pCloudySession.releaseSessionNow(); 

        report.addStep("Release Appium Session", null, null, ExecutionResult.Pass); 

        } catch (ConnectError | IOException e) { 
            report.addStep("Error Running TestCase", null, e.getMessage(), ExecutionResult.Fail); 
            e.printStackTrace(); 
        } finally { 
            HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
            printer.printSingleRunReport(report); 

        } 
        */
        
        // Create multiple driver objects in multiple threads 
        /*for (int i = 0; i < bookedDevices.length; i++) { 
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
            try {
            
            DesiredCapabilities capabilities = new DesiredCapabilities(); 
            capabilities.setCapability("newCommandTimeout", 600); 
            capabilities.setCapability("launchTimeout", 90000); 
            capabilities.setCapability("deviceName", aDevice.capabilities.deviceName); 
            capabilities.setCapability("browserName", aDevice.capabilities.deviceName); 
            capabilities.setCapability("platformName", "ios"); 
            capabilities.setCapability("bundleId", "com.automobiletechnologies.repair360"); 
            capabilities.setCapability("usePrebuiltWDA", true); 
            capabilities.setCapability("acceptAlerts", true); 
         
            if (aDevice.getVersion().compareTo(new Version("9.3")) >= 0) 
                capabilities.setCapability("automationName", "XCUITest"); 
            else 
                capabilities.setCapability("automationName", "Appium");
            appiumdriver = new SwipeableWebDriver(endpoint, capabilities);
            
            File snapshotTmpFile = pCloudySession.takeScreenshot(); 
            File snapshotFile = new File(snapshotsFolder, snapshotTmpFile.getName()); 
            FileUtils.moveFile(snapshotTmpFile, snapshotFile); 

            report.addStep("Take Screenshot", null, null, snapshotFile.getAbsolutePath(), ExecutionResult.Pass); 

            // release session now 
            pCloudySession.releaseSessionNow(); 

            report.addStep("Release Appium Session", null, null, ExecutionResult.Pass); 

            } catch (ConnectError | IOException e) { 
                report.addStep("Error Running TestCase", null, e.getMessage(), ExecutionResult.Fail); 
                e.printStackTrace(); 
            } finally { 
                HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
                printer.printSingleRunReport(report); 

            } 
        }*/
            
/*
            Runnable testCase = getTestCaseClass(endpoint, aDevice, pCloudySession, report,reportsFolder); 
            Thread aThread = new Thread(testCase); 
            aThread.start(); 
            allThreads.add(aThread); 
        } 
            for(Thread aThread : allThreads) { 
            aThread.join(); 
        } 
            con.revokeTokenPrivileges(authToken); 
             
            File consolidatedReport = new File(reportsFolder, "ConsolidatedReports.html"); 
            HtmlFilePrinter printer = new HtmlFilePrinter(consolidatedReport); 
            printer.printConsolidatedSingleRunReport(multipleReports); 
            System.out.println("Check the reports at : " + consolidatedReport.getAbsolutePath()); 

            System.out.println("Execution Completed..."); 
         

    } 

    private Runnable getTestCaseClass(final URL endpoint, final BookingDtoDevice aDevice,final PCloudyAppiumSession pCloudySession, final SingleRunReport report,final File reportsFolder) { 
        // this will give a Thread Safe TestScript class. 
        // You may also like to have this as a named class in a separate file 

        return new Runnable() { 

            @Override 
            public void run() { 
             
                
            try{ 
                DesiredCapabilities capabilities = new DesiredCapabilities(); 
                capabilities.setCapability("newCommandTimeout", 600); 
                capabilities.setCapability("launchTimeout", 90000); 
                capabilities.setCapability("deviceName", aDevice.capabilities.deviceName); 
                capabilities.setCapability("browserName", aDevice.capabilities.deviceName); 
                capabilities.setCapability("platformName", "ios"); 
                capabilities.setCapability("bundleId", "com.automobiletechnologies.repair360"); 
                capabilities.setCapability("usePrebuiltWDA", true); 
                capabilities.setCapability("acceptAlerts", true); 
             
                if (aDevice.getVersion().compareTo(new Version("9.3")) >= 0) 
                    capabilities.setCapability("automationName", "XCUITest"); 
                else 
                    capabilities.setCapability("automationName", "Appium"); 
                 
                IOSDriver driver = new IOSDriver(endpoint, capabilities); 
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // ########################################### 
                // Your Test Script Goes Here 
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

                } catch (ConnectError | IOException e) { 
                    report.addStep("Error Running TestCase", null, e.getMessage(), ExecutionResult.Fail); 
                    e.printStackTrace(); 
                } finally { 
                    HtmlFilePrinter printer = new HtmlFilePrinter(new File(deviceFolder, deviceFolder.getName() + ".html")); 
                    printer.printSingleRunReport(report); 

                } 
            } 

        }; */
		
		
		
		
		
		
		
		
		/*
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
		*/
		}

}
