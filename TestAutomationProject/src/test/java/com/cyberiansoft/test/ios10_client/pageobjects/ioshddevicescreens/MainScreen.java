package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MainScreen extends iOSHDBaseScreen {
	
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

	public MainScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void updateDatabase() {
		Helpers.setTimeOut(340);

		mainbtn.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public void updateVIN() {
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

	public HomeScreen userLogin(String user, String password) {
		Helpers.waitABit(1000);
		Helpers.setTimeOut(5);
		//Helpers.waitUntilCheckLicenseDialogDisappears();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(user)));
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']")).waitAction(Duration.ofSeconds(1)).release().perform();
		if (!appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + user + "']/.."),
					appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + user + "']/../.."));
			appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").click();
		} else 
			appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").click();

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSecureTextField")));
		((IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSecureTextField")).setValue(password);
		//securefld.setValue(password);
		loginbtn.click();
		//loginbtn.click();
		return new HomeScreen();
	}
	
	public LicensesScreen clickLicenses() {
		licenses.click();
		return new LicensesScreen();
	}
}
