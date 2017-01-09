package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class EmailScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Send")
    private IOSElement toolbarsendbtn;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addemailbtn;
	
	public EmailScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void sendInvoiceOnEmailAddress(String emailaddr) {
		clickAddEmailButton();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(emailaddr);
		Helpers.acceptAlert();
		clickSendButton();
		Helpers.waitABit(500);
	}
	
	public void clickAddEmailButton() {
		addemailbtn.click();
	}
	
	public void clickSendButton() {
		toolbarsendbtn.click();
	}

}
