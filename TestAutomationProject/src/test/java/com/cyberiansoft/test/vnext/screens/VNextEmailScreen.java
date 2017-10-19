package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextEmailScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-name='to']")
	private WebElement toemailpanel;
	
	@FindBy(xpath="//*[@action='send']")
	private WebElement sendbtn;
	
	@FindBy(xpath="//div[@data-page='email']")
	private WebElement emailscreen;
	
	final String toemailxpath = ".//input[@name='address-field']";
	final String addmorebtn = ".//*[@action='add']";
	final String removemailbtn = ".//*[@action='remove']";
	
	public VNextEmailScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(emailscreen));
		waitABit(1000);
	}
	
	public void sentToEmailAddress(String emailaddress) {
		tap(toemailpanel.findElement(By.xpath(removemailbtn)));
		waitABit(1000);
		toemailpanel.findElement(By.xpath(toemailxpath)).clear();
		toemailpanel.findElement(By.xpath(toemailxpath)).sendKeys(emailaddress);
		log(LogStatus.INFO, "Set Send Email To: " + emailaddress);
	}
	
	public void addToEmailAddress(String emailaddress) {
		clickAddMoreToEmailsButton();
		toemailpanel.findElements(By.xpath(toemailxpath)).get(1).clear();
		toemailpanel.findElements(By.xpath(toemailxpath)).get(1).sendKeys(emailaddress);
		log(LogStatus.INFO, "Set Send Email To: " + emailaddress);
	}
	
	public String getToEmailFieldValue() {
		return toemailpanel.findElement(By.xpath(toemailxpath)).getAttribute("value");
	}
	
	public void clickAddMoreToEmailsButton() {
		tap(toemailpanel.findElement(By.xpath(addmorebtn)));
		log(LogStatus.INFO, "Tap on Add More To Emails button");
	}
	
	public void clickSendEmailsButton() {
		tap(sendbtn);
		log(LogStatus.INFO, "Tap on Send Emails button");
	}

}
