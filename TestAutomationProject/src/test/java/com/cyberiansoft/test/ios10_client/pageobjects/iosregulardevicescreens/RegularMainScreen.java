package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularMainScreen {
	
	private AppiumDriver appiumdriver;
	
	@iOSFindBy(accessibility = "UpdateMainDatabaseButton")
    private IOSElement mainbtn;
	
	@iOSFindBy(accessibility = "UpdateVinDatabaseButton")
    private IOSElement updatevin;
	
	@iOSFindBy(accessibility = "Enter password here")
    private IOSElement securefld;
	
	@iOSFindBy(accessibility = "OK")
    private IOSElement loginbtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@name='Search']")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@name='Licenses']")
    private IOSElement licenses;
	
	@iOSFindBy(xpath = "//UIASearchBar[@name='Search']")
    private IOSElement searchbar;
	
	//final static String mainbtnxpath = "//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[1]";
	//final static String updatevinxpath = "//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[2]";
	//final static String securefldxpath = "//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIASecureTextField[1]";
	//final static String loginbtnxpath = "//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIAButton[1]";
	//final static String licensesxpath = "//UIAToolbar[1]/UIAButton[@name=\"Licences\"]";

	public RegularMainScreen(AppiumDriver driver) {
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

	public RegularHomeScreen userLogin(String user, String password) throws InterruptedException {
		Thread.sleep(1000);
		//Helpers.waitUntilCheckLicenseDialogDisappears();
		//Thread.sleep(3000);
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell/UIAStaticText[@name='" + user + "']");
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(user))).click(); 
		//appiumdriver.findElementByAccessibilityId(user).click();
		securefld.setValue(password);
		loginbtn.click();
		return new RegularHomeScreen(appiumdriver);
	}
	
	public LicensesScreen clickLicenses() {
		licenses.click();
		return new LicensesScreen(appiumdriver);
	}
}
