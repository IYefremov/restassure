package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class VNextBOConfirmPasswordWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='loginForm']")
	private WebElement loginform;
	
	@FindBy(id = "newPassword")
	private TextField newpasswordfld;
	
	@FindBy(id = "newPassword2")
	private TextField confirmpswfld;
	
	@FindBy(xpath = "//div[@class='errorMessege']/p/span")
	private WebElement errormsg;
	
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitbtn;
	
	public VNextBOConfirmPasswordWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(submitbtn));
	}

	public VNextBOLoginScreenWebPage confirmNewUserPassword(String newpassword) {
		setUserPasswordFieldValue(newpassword);
		setUserConfirmPasswordFieldValue(newpassword);
		clickSubmitButton();
		waitABit(2000);
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public void setUserPasswordFieldValue(String userpassword) {
		newpasswordfld.clearAndType(userpassword);
	}
	
	public void setUserConfirmPasswordFieldValue(String userpassword) {
		confirmpswfld.clearAndType(userpassword);
	}
	
	public void clickSubmitButton() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(submitbtn));
		Actions act = new Actions(driver);
		act.click(submitbtn).perform();
		waitABit(300);
		//if (submitbtn.isDisplayed()) {
		//	submitbtn.click();
		//}
		//
	}
	
	public String geterrorMessageValue() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@data-bind, 'newPasswordError.visible')]/p/span"))));
		return driver.findElement(By.xpath("//div[contains(@data-bind, 'newPasswordError.visible')]/p/span")).getText();
	}
}
