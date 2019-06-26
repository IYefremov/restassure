package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SelectEmployeePopup extends iOSHDBaseScreen {
	
	//@iOSXCUITFindBy(accessibility = "Enter password here")
    //private IOSElement securefld;
	
	public SelectEmployeePopup() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee(employee);
		((IOSElement) appiumdriver.findElement(MobileBy.AccessibilityId("Enter password here"))).setValue(password);
		Helpers.acceptAlert();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
	public void selectEmployee(String employee) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(employee)));
		appiumdriver.findElement(MobileBy.AccessibilityId(employee)).click();
	}

}
