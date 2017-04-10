package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextWorkOrderSummaryScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[@data-page='summary']")
	private WebElement wosummaryscreen;
	
	@FindBy(xpath="//input[@action='auto-invoice']")
	private WebElement autoinvoicecreateoption;
	
	@FindBy(xpath="//div[@class='right']/i[@action='save']")
	private WebElement savebtn;

	public VNextWorkOrderSummaryScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(autoinvoicecreateoption));
	}
	
	public void clickCreateInvoiceOption() {
		tap(autoinvoicecreateoption);
		log(LogStatus.INFO, "Select Create Invoice Option");
	}
	
	public void clickWorkOrderSaveButton() {
		tap(savebtn);
		log(LogStatus.INFO, "Click Save work order button");
	}

}
