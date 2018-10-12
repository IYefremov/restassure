package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularMainScreen extends iOSBaseScreen {
	
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

	@iOSFindBy(accessibility = "Licenses")
    private IOSElement licenses;

	public RegularMainScreen() {
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Login")));
	}
	
	public void updateDatabase() {
		Helpers.setTimeOut(340);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("UpdateMainDatabaseButton")));
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

	public RegularHomeScreen userLogin(String user, String password)  {
		//Helpers.waitUntilCheckLicenseDialogDisappears();
		//Thread.sleep(3000);
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell/UIAStaticText[@name='" + user + "']");
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(user)));
		appiumdriver.findElementByAccessibilityId(user).click();
		wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Enter password here")));
		securefld.setValue(password);
		loginbtn.click();
		return new RegularHomeScreen();
	}
	
	public LicensesScreen clickLicenses() {
		licenses.click();
		return new LicensesScreen();
	}
}
