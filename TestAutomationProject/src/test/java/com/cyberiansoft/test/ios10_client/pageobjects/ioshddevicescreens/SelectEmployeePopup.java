package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class SelectEmployeePopup extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//UIASecureTextField[@value=\"Enter password here\"]")
    private IOSElement securefld;
	
	public SelectEmployeePopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee(employee);
		securefld.setValue(password);
		Helpers.acceptAlert();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['" + employee + "']")).click();
	}

}
