package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextHomeScreen extends VNextBaseScreen {
	
	final String quemessagexpath = "//span[@class='letter-number']";
	
	//@FindBy(xpath="//div[@class='title' and text()='Customers']")
	@FindBy(xpath="//a[@class='tile-link tile-item customers-tile']")
	private WebElement customerslist;
	
	//@FindBy(xpath="//div[@class='title' and text()='Inspections']")
	@FindBy(xpath="//a[@class='tile-link tile-item inspections-tile']")
	private WebElement inspectionslist;
	
	//@FindBy(xpath="//div[@class='title' and text()='Work Orders']")
	@FindBy(xpath="//a[@class='tile-link tile-item work-orders-tile']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//a[@class='tile-link tile-item more-tile']")
	private WebElement morelist;
	
	//@FindBy(xpath="//div[@class='title' and text()='Settings']")
	@FindBy(xpath="//a[@class='tile-link tile-item settings-tile']")
	private WebElement settingslist;
	
	//@FindBy(xpath="//div[@class='title' and text()='Status']")
	@FindBy(xpath="//a[@class='tile-link tile-item status-tile']")
	private WebElement statuslist;
	
	@FindBy(xpath=quemessagexpath)
	private WebElement queuemessage;
	
	@FindBy(xpath="//*[@action='messager-send']")
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
		if (!settingslist.isDisplayed())
			tap(morelist);
		tap(settingslist);
		log(LogStatus.INFO, "Tap Settings menu item");
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextStatusScreen clickStatusMenuItem() {
		if (!statuslist.isDisplayed())
			tap(morelist);
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
		return queuemessage.getText();
	}
	
	public boolean isQueueMessageVisible() {
		return appiumdriver.findElementsByXPath(quemessagexpath).size() > 0;
	}
	
	public void waitUntilQueueMessageInvisible() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOf(queuemessage));
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
