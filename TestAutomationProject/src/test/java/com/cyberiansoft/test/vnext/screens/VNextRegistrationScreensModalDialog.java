package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextRegistrationScreensModalDialog extends VNextBaseScreen {

	@FindBy(id="dialogModal")
	private WebElement modaldlg;
	
	@FindBy(xpath="//span[@data-bind='html: text']")
	private WebElement modaldlgmsg;
	
	public VNextRegistrationScreensModalDialog(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.visibilityOf(modaldlg));
	}
	
	public String getInformationDialogMessage() {
		return modaldlgmsg.getText();
	}
	
	public void clickInformationDialogOKButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//button[contains(@data-bind, 'confirm')]"))));
		tap(modaldlg.findElement(By.xpath(".//button[contains(@data-bind, 'confirm')]")));
		log(LogStatus.INFO, "Tap Information Dialog OK Button");
	}
	
	public void clickInformationDialogYesButton() {
		tap(modaldlg.findElement(By.xpath(".//button[contains(@data-bind, 'confirm')]")));
		log(LogStatus.INFO, "Tap Information Dialog Yes Button");
	}
	
	public void clickInformationDialogCancelButton() {
		tap(modaldlg.findElement(By.xpath(".//button[contains(@data-bind, 'cancel')]")));
		log(LogStatus.INFO, "Tap Information Dialog Cancel Button");
	}
	
	public String clickInformationDialogOKButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogOKButton();
		return msg;
	}
	
	public String clickInformationDialogYesButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogYesButton();
		return msg;
	}
	
	public String clickInformationDialogCancelButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogCancelButton();
		return msg;
	}

}
