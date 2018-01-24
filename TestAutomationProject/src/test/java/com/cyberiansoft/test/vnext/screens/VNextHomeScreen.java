package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
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
	@FindBy(xpath="//div[@class='tiles-block']/a[@class='tile-link tile-item inspections-tile']")
	private WebElement inspectionslist;
	
	//@FindBy(xpath="//div[@class='title' and text()='Work Orders']")
	@FindBy(xpath="//div[@class='tiles-block']/a[@class='tile-link tile-item work-orders-tile']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//div[@class='tiles-block']/a[@class='tile-link tile-item invoices-tile']")
	private WebElement invoiceslist;
	
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
	
	@FindBy(xpath="//div[@class='speed-dial']/a[@class='floating-button color-red']")
	private WebElement addbtn;
	
	@FindBy(xpath="//a[@action='new_order']")
	private WebElement newworkorderbtn;
	
	@FindBy(xpath="//a[@action='new_inspection']")
	private WebElement newinspectionbtn;
	
	public VNextHomeScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='null']")));
		if (appiumdriver.findElementsByXPath("//div[@class='intercom-chat-dismiss-button-mobile']").size() > 0)
			tap(appiumdriver.findElementByXPath("//div[@class='intercom-chat-dismiss-button-mobile']"));
		//if (appiumdriver.findElementsByXPath("//div[@class='help-button' and text()='OK, got it']").size() > 0) {
			if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed()) {
				tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
				//waitABit(10000);
			//}
		}
		
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.visibilityOf(customerslist));
		
	}
	
	public VNextCustomersScreen clickCustomersMenuItem() {
		tap(customerslist);
		log(LogStatus.INFO, "Tap Customers menu item");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public VNextWorkOrdersScreen clickWorkOrdersMenuItem() {
		//waitABit(2000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(workorderslist));
		tap(workorderslist);
		log(LogStatus.INFO, "Tap Work Orders menu item");
		return new VNextWorkOrdersScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen clickInspectionsMenuItem() {
		tap(inspectionslist);
		waitABit(2000);
		log(LogStatus.INFO, "Tap Inspections menu item");
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextInvoicesScreen clickInvoicesMenuItem() {
		tap(invoiceslist);
		waitABit(2000);
		log(LogStatus.INFO, "Tap Inspections menu item");
		return new VNextInvoicesScreen(appiumdriver);
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
		//System.out.println("+++" + appiumdriver.findElementByXPath(quemessagexpath).isDisplayed());
		System.out.println("+++" + appiumdriver.findElementByXPath("//*[@action='messager-send']").isDisplayed());
		
		return appiumdriver.findElementByXPath("//*[@action='messager-send']").isDisplayed();
	}
	
	public void waitUntilQueueMessageInvisible() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElementByXPath("//*[@action='messager-send']")));
	}
	
	public VNextLoginScreen clickLogoutButton() {
		tap(logoutbtn);
		log(LogStatus.INFO, "Tap Logout button");
		return new VNextLoginScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen openCreateWOWizard(String testcustomer) {
		VNextWorkOrdersScreen workordersscreen = clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public void clickUpgrateToProBanner() {
		tap(appiumdriver.findElement(By.xpath("//div[@class='upgrade-image' and @action='ad']")));
		log(LogStatus.INFO, "Tap Upgrate To Pro Banner");
	}
	
	public boolean isUpgrateToProBannerVisible() {
		return appiumdriver.findElement(By.xpath("//div[@class='upgrade-image' and @action='ad']")).isDisplayed();
	}

	public void clickAddButton() {
		tap(addbtn);
		log(LogStatus.INFO, "Tap Home screen Add button");
	}
	
	public VNextCustomersScreen clickNewWorkOrderPopupMenu() {
		clickAddButton();
		tap(newworkorderbtn);
		log(LogStatus.INFO, "Tap New Work Order menu button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public VNextCustomersScreen clickNewInspectionPopupMenu() {
		clickAddButton();
		tap(newinspectionbtn);
		log(LogStatus.INFO, "Tap New Inspection menu button");
		return new VNextCustomersScreen(appiumdriver);
	}
}
