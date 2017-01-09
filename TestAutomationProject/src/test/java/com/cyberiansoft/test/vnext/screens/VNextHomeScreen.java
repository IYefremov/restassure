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
	
	@FindBy(xpath="//div[@class='title' and text()='Work Orders']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//div[@class='title' and text()='Settings']")
	private WebElement settingslist;
	
	@FindBy(xpath="//div[@class='title' and text()='Status']")
	private WebElement statuslist;
	
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
		if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='Got It']").isDisplayed())
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='Got It']"));
	}
	
	public VNextCustomersScreen clickCustomersMenuItem() {
		tap(customerslist);
		log(LogStatus.INFO, "Tap Customers menu item");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public VNextWorkOrdersScreen clickWorkOrdersMenuItem() {
		tap(workorderslist);
		log(LogStatus.INFO, "Tap Work Orders menu item");
		return new VNextWorkOrdersScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen clickInspectionsMenuItem() {
		tap(inspectionslist);
		log(LogStatus.INFO, "Tap Inspections menu item");
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextSettingsScreen clickSettingsMenuItem() {
		tap(settingslist);
		log(LogStatus.INFO, "Tap Settings menu item");
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextStatusScreen clickStatusMenuItem() {
		tap(statuslist);
		log(LogStatus.INFO, "Tap Status menu item");
		return new VNextStatusScreen(appiumdriver);
	}
	
	public void clickQueueMessageIcon() {
		tap(queuemessageicon);
		log(LogStatus.INFO, "Tap Queue Message Icon");
		waitABit(500);
	}
	
	public String getQueueMessageValue() {
		waitABit(1000);
		return queuemessage.getText();
	}
	
	public VNextLoginScreen clickLogoutButton() {
		tap(logoutbtn);
		testReporter.log(LogStatus.INFO, "Tap Logout button");
		return new VNextLoginScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen openCreateWOWizard(String testcustomer) {
		VNextWorkOrdersScreen workordersscreen = clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		return new VNextVehicleInfoScreen(appiumdriver);
	}

}
