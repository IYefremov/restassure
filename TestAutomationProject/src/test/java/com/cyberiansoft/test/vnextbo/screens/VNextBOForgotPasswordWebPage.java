package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class VNextBOForgotPasswordWebPage extends BaseWebPage {
	
	@FindBy(id = "confirmEmail")
	private TextField confirmmailfld;
	
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitbtn;
	
	@FindBy(xpath = "//button[@class='btn btn-autofocus' and text()='OK']")
	private WebElement alertOKbtn;
	
	public VNextBOForgotPasswordWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(submitbtn));
	}
	
	public VNextBOLoginScreenWebPage sendConfirmationMail(String usermail) {
		setConfirmationMailFieldValue(usermail);
		clickSubmitButton();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(alertOKbtn)).click();
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public void setConfirmationMailFieldValue(String usermail) {
		confirmmailfld.clearAndType(usermail);
	}
	
	public boolean isConfirmationMailFieldDisplayed() {
		return confirmmailfld.isDisplayed();
	}
	
	public void clickSubmitButton() {
		submitbtn.click();
	}
	
	public String geterrorMessageValue() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@data-bind, 'confirmEmail.hasError')]/p"))));
		return driver.findElement(By.xpath("//div[contains(@data-bind, 'confirmEmail.hasError')]/p")).getText();
	}

}
