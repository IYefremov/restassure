package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainScreen extends iOSHDBaseScreen {
	
	@iOSXCUITFindBy(accessibility  = "UpdateMainDatabaseButton")
    private IOSElement mainbtn;
	
	@iOSXCUITFindBy(accessibility  = "UpdateVinDatabaseButton")
    private IOSElement updatevin;
	
	@iOSXCUITFindBy(accessibility = "Enter Password")
    private IOSElement securefld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton[@name='Login']")
    private IOSElement loginbtn;
	
	@iOSXCUITFindBy(accessibility  = "Licenses")
    private IOSElement licenses;

	public MainScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void updateDatabase() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(mainbtn));
		mainbtn.click();
		Helpers.acceptAlert();
	}

	public void updateVIN() {
		Helpers.waitUntilCheckLicenseDialogDisappears();
		updatevin.click();
		Helpers.acceptAlert();
	}

	public HomeScreen userLogin(String user, String password) {
		Helpers.waitABit(1000);
		if (!appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + user + "']/.."),
					appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + user + "']/../.."));
			appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").click();
		} else 
			appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='" + user + "']").click();

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSecureTextField")));
		((IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeSecureTextField")).setValue(password);
		loginbtn.click();
		return new HomeScreen();
	}
	
	public LicensesScreen clickLicenses() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(licenses));
		licenses.click();
		return new LicensesScreen();
	}
}
