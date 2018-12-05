package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class EmailScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Send")
    private IOSElement toolbarsendbtn;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addemailbtn;
	
	public EmailScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void sendInvoiceOnEmailAddress(String emailaddr) {
		clickAddEmailButton();
		appiumdriver.findElementByClassName("XCUIElementTypeTextField").sendKeys(emailaddr);
		Helpers.acceptAlert();
		clickSendButton();
		Helpers.waitABit(500);
	}
	
	public void clickAddEmailButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.visibilityOf(addemailbtn));
		addemailbtn.click();
	}
	
	public void clickSendButton() {
		toolbarsendbtn.click();
	}

}
