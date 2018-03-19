package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextChangeInvoicePONumberDialog extends VNextBaseScreen {
	
	@FindBy(id="InvoicesChangePO")
	private WebElement changeponumberfld;
	
	@FindBy(xpath="//span[contains(@class, 'modal-button') and text()='Save']")
	private WebElement savebtn;
	
	public VNextChangeInvoicePONumberDialog(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(changeponumberfld));
	}
	
	public VNextInvoicesScreen changeInvoicePONumber(String poNumber) {
		setInvoicePONumber(poNumber);
		clickSaveButton();
		return new VNextInvoicesScreen(appiumdriver);
	}

	
	public void setInvoicePONumber(String poNumber) {
		changeponumberfld.clear();
		changeponumberfld.sendKeys(poNumber);
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set Invoice PO Number: " + poNumber);
	}
	
	public void clickSaveButton() {
		tap(savebtn);
		log(LogStatus.INFO, "Click Change Invoice PO Number dialog Save button");
	}
	
	public void clickDontSaveButton() {
		tap(appiumdriver.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
		log(LogStatus.INFO, "Click Change Invoice PO Number dialog Don't Save button");
	}
	
	public boolean isPONUmberShouldntBeEmptyErrorAppears() {
		return appiumdriver.findElement(By.xpath("//div[@class='modal-validation']")).isDisplayed();
	}
}
