package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextRegistrationScreensModalDialog extends VNextBaseScreen {

	@FindBy(id="dialogModal")
	private WebElement modaldlg;
	
	@FindBy(xpath="//*[@class='modal-body__content']")
	private WebElement modaldlgmsg;
	
	public VNextRegistrationScreensModalDialog(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.visibilityOf(modaldlg));
	}
	
	public String getInformationDialogMessage() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(modaldlgmsg));
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
