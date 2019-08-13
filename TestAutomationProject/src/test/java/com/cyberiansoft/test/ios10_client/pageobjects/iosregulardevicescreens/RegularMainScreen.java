package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularMainScreen extends iOSBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "UpdateMainDatabaseButton")
    private IOSElement mainbtn;
	
	@iOSXCUITFindBy(accessibility = "UpdateVinDatabaseButton")
    private IOSElement updatevin;
	
	@iOSXCUITFindBy(accessibility = "Enter password here")
    private IOSElement securefld;
	
	@iOSXCUITFindBy(accessibility = "OK")
    private IOSElement loginbtn;

	@iOSXCUITFindBy(accessibility = "Licenses")
    private IOSElement licenses;

	@iOSXCUITFindBy(accessibility = "LoginView")
	private IOSElement loginView;

	public RegularMainScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void updateDatabase() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("UpdateMainDatabaseButton")));
		mainbtn.click();
		Helpers.acceptAlert();
	}

	public void updateVIN() {
		Helpers.waitUntilCheckLicenseDialogDisappears();
		updatevin.click();
		Helpers.acceptAlert();
	}

	public RegularHomeScreen userLogin(String employeeName, String password)  {
		selectEmployee(employeeName);
		enterEmployeePassword(password);
		return new RegularHomeScreen();
	}

	public void selectEmployee(String employeeName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("LoginView")));
		WaitUtils.waitUntilElementIsClickable(loginView);
		if (!loginView.findElementByAccessibilityId(employeeName).isDisplayed()) {
			SwipeUtils.swipeToElement(loginView.findElementByAccessibilityId(employeeName));
		}
		loginView.findElementByAccessibilityId(employeeName).click();
	}

	public void enterEmployeePassword(String employeePassword) {
		WaitUtils.waitUntilElementIsClickable(securefld);
		securefld.setValue(employeePassword);
		loginbtn.click();
	}

	public LicensesScreen clickLicenses() {
		licenses.click();
		return new LicensesScreen();
	}
}
