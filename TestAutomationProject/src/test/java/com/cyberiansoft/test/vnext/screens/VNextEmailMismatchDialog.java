package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextEmailMismatchDialog extends VNextBaseScreen {
	
	@FindBy(id="email-mismatch")
	private WebElement modaldlg;
	
	@FindBy(xpath="//button[text()='Text me my email address']")
	private WebElement textmailbtn;
	
	public VNextEmailMismatchDialog(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(modaldlg));
	}
	
	public String getInformationDialogBodyMessage() {
		return  modaldlg.findElement(By.xpath(".//div[@class='modal-body']")).getText();
	}
	
	public void clickTextEmailAddressButton() {
		tap(textmailbtn);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("phone-mismatch")));
		log(LogStatus.INFO, "Tap 'Email me my phone number' Button");
	}

}
