package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInvoiceMenuScreen extends VNextBaseScreen {

	@FindBy(xpath="//a[@action='email']/i")
	private WebElement emailinvoicebtn;
	
	@FindBy(xpath="//div[@data-page='menu']")
	private WebElement invoicemenuscreen;
	
	
	public VNextInvoiceMenuScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicemenuscreen));
	}
	
	public VNextEmailScreen clickEmailInvoiceMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(emailinvoicebtn));
		tap(emailinvoicebtn);
		log(LogStatus.INFO, "Tap on Email Invoice Menu");
		return new VNextEmailScreen(appiumdriver);
	}

}
