package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextHomeScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='title' and text()='Customers']")
	private WebElement customerslist;
	
	@FindBy(xpath="//div[@class='title' and text()='Inspections']")
	private WebElement inspectionslist;
	
	@FindBy(xpath="//div[@class='title' and text()='Settings']")
	private WebElement settingslist;
	
	@FindBy(xpath="//span[@class='letter-number']")
	private WebElement queuemessage;
	
	@FindBy(xpath="//i[@action='messager-send']")
	private WebElement queuemessageicon;
	
	@FindBy(xpath="//a[@action='logout']/i")
	private WebElement logoutbtn;
	
	public VNextHomeScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(customerslist));
	}
	
	public VNextInspectionsScreen clickInspectionsMenuItem() {
		tap(inspectionslist);
		testReporter.log(LogStatus.INFO, "Tap Inspections menu item");
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextSettingsScreen clickSettingsMenuItem() {
		tap(settingslist);
		testReporter.log(LogStatus.INFO, "Tap Settings menu item");
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public void clickQueueMessageIcon() {
		tap(queuemessageicon);
		testReporter.log(LogStatus.INFO, "Tap Queue Message Icon");
		waitABit(500);
	}
	
	public String getQueueMessageValue() {
		waitABit(500);
		return queuemessage.getText();
	}
	
	public VNextLoginScreen clickLogoutButton() {
		tap(logoutbtn);
		testReporter.log(LogStatus.INFO, "Tap Logout button");
		return new VNextLoginScreen(appiumdriver);
	}

}
