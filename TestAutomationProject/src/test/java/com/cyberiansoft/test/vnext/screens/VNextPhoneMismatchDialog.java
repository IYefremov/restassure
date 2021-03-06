package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPhoneMismatchDialog extends VNextBaseScreen {
	
	@FindBy(id="phone-mismatch")
	private WebElement modaldlg;
	
	@FindBy(xpath="//button[text()='Email me my phone number']")
	private WebElement emailmephonebtn;

    public VNextPhoneMismatchDialog(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(modaldlg));
	}
	
	public String getInformationDialogBodyMessage() {
		return  modaldlg.findElement(By.xpath(".//div[@class='modal-body']")).getText();
	}
	
	public void clickEmailMeMyPhoneButton() {
		tap(emailmephonebtn);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("phone-mismatch")));
	}

}
