package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "dialogModal")
	private WebElement confirmdialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div[contains(@data-bind, 'text: text,')]")
	private WebElement confirmdialogmessage;
	
	@FindBy(xpath = "//button[@data-automation-id='modalCancelButton']")
	private WebElement nobtn;
	
	@FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
	private WebElement yesbtn;
	
	public VNextConfirmationDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(confirmdialog));
	}
	
	public void clickNoButton() {
		nobtn.click();
	}
	
	public void clickYesButton() {
		yesbtn.click();
	}
	
	public String getConfirmationDialogMessage() {
		return confirmdialogmessage.getText();
	}
	
	public String clickYesAndGetConfirmationDialogMessage() {
		final String msg = getConfirmationDialogMessage();
		clickYesButton();
		return msg;
	}
	
	public String clickNoAndGetConfirmationDialogMessage() {
		final String msg = getConfirmationDialogMessage();
		clickNoButton();
		return msg;
	}
}
