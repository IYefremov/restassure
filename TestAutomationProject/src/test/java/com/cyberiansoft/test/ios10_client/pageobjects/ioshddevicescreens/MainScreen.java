package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class MainScreen {
	
	private AppiumDriver appiumdriver;
	
	@iOSFindBy(accessibility  = "UpdateMainDatabaseButton")
    private IOSElement mainbtn;
	
	@iOSFindBy(accessibility  = "UpdateVinDatabaseButton")
    private IOSElement updatevin;
	
	@iOSFindBy(accessibility = "Enter Password")
    private IOSElement securefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton[@name='Login']")
    private IOSElement loginbtn;
	
	@iOSFindBy(accessibility  = "Licenses")
    private IOSElement licenses;
	
	//final static String mainbtnxpath = "//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[1]";
	//final static String updatevinxpath = "//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[2]";
	//final static String securefldxpath = "//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIASecureTextField[1]";
	//final static String loginbtnxpath = "//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIAButton[1]";
	//final static String licensesxpath = "//UIAToolbar[1]/UIAButton[@name=\"Licences\"]";

	public MainScreen(AppiumDriver driver) {
		appiumdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void updateDatabase() throws InterruptedException {
		Helpers.setTimeOut(340);
		Thread.sleep(2000);

		mainbtn.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public void updateVIN() throws InterruptedException {
		Helpers.setTimeOut(60);
		Helpers.waitUntilCheckLicenseDialogDisappears();
		/*
		 * MobileElement element = new MobileElement( (RemoteWebElement)
		 * driver.findElementByXPath(updatevinxpath), driver);
		 */
		updatevin.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public HomeScreen userLogin(String user, String password) throws InterruptedException {
		Helpers.waitABit(10000);
		Helpers.setTimeOut(5);
		//Helpers.waitUntilCheckLicenseDialogDisappears();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(user)));
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']")).waitAction(1000).release().perform();
		//appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").click();
		//wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.visibilityOf(securefld));
		securefld.setValue(password);
		action = new TouchAction(appiumdriver);
		action.press(loginbtn).waitAction(1000).release().perform();
		//loginbtn.click();
		return new HomeScreen(appiumdriver);
	}
	
	public LicensesScreen clickLicenses() {
		licenses.click();
		return new LicensesScreen(appiumdriver);
	}
}