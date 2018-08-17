package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextWorkOrderSummaryScreen extends VNextBaseWizardScreen {
	
	@FindBy(xpath="//div[@data-page='summary']")
	private WebElement wosummaryscreen;
	
	@FindBy(xpath="//input[@action='auto-invoice']")
	private WebElement autoinvoicecreateoption;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;

	public VNextWorkOrderSummaryScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(autoinvoicecreateoption));
	}
	
	public void clickCreateInvoiceOption() {
		tap(autoinvoicecreateoption);
	}
	
	public void clickWorkOrderSaveButton() {
		tap(savebtn);
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickFinalButton();
	}

}
