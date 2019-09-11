  package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

  import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
  import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
  import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
  import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
  import com.cyberiansoft.test.ios10_client.utils.Helpers;
  import com.cyberiansoft.test.vnext.utils.WaitUtils;
  import io.appium.java_client.MobileBy;
  import io.appium.java_client.ios.IOSElement;
  import io.appium.java_client.pagefactory.AppiumFieldDecorator;
  import io.appium.java_client.pagefactory.iOSXCUITFindBy;
  import org.openqa.selenium.support.PageFactory;
  import org.openqa.selenium.support.ui.ExpectedConditions;
  import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularHomeScreen extends iOSRegularBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "Customers")
    private IOSElement customersbtn;
	
	/*@iOSXCUITFindBy(accessibility = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Team Inspections")
    private IOSElement teaminspectionsbtn;
	
	@iOSXCUITFindBy(accessibility = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSXCUITFindBy(accessibility = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSXCUITFindBy(accessibility = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSXCUITFindBy(accessibility = "Team Invoices")
    private IOSElement teaminvoicesbtn;
	
	@iOSXCUITFindBy(accessibility = "Service Requests")
    private IOSElement servicerequestsbtn;
	
	@iOSXCUITFindBy(accessibility = "Status")
    private IOSElement statustsbtn;
	
	@iOSXCUITFindBy(accessibility = "Team Work Orders")
    private IOSElement temworkorderstsbtn;
	
	@iOSXCUITFindBy(accessibility = "Settings")
    private IOSElement settingstsbtn;*/
	
	@iOSXCUITFindBy(accessibility = "logout")
    private IOSElement logoutbtn;

	@iOSXCUITFindBy(accessibility = "viewPrompt")
	private IOSElement activecustomer;
	
	public RegularHomeScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitHomeScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(MobileBy.AccessibilityId("Home")));
	}

	public RegularCustomersScreen clickCustomersButton() {
		WaitUtils.waitUntilElementIsClickable(customersbtn);
		customersbtn.click();
		return new RegularCustomersScreen();
	}

	public RegularMyInspectionsScreen clickMyInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Inspections")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.switchToMyView();
		return myinspectionsscreen;
	}
	
	public RegularTeamInspectionsScreen clickTeamInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Inspections")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.switchToTeamView();
		return new RegularTeamInspectionsScreen();
	}

	public RegularMyWorkOrdersScreen clickMyWorkOrdersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Work Orders"))).click();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.switchToMyView();
		return myworkordersscreen;
	}
	
	public RegularCarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Car History")));
		appiumdriver.findElementByAccessibilityId("Car History").click();
		return new RegularCarHistoryScreen();
	}

	public RegularMyInvoicesScreen clickMyInvoicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		return new RegularMyInvoicesScreen();
	}
	
	public RegularTeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		RegularMyInvoicesScreen invoicesscreen = new RegularMyInvoicesScreen();
		invoicesscreen.switchToTeamView();
		return new RegularTeamInvoicesScreen();
	}

	public RegularServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Requests")));
		appiumdriver.findElementByAccessibilityId("Service Requests").click();
		return new RegularServiceRequestsScreen();
	}
	
	public void clickStatusButton() {
		appiumdriver.findElementByAccessibilityId("Status").click();
	}
	
	public RegularTeamWorkOrdersScreen clickTeamWorkordersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Work Orders"))).click();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.switchToTeamView();
		return new RegularTeamWorkOrdersScreen();
	}

	public RegularSettingsScreen clickSettingsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Service Requests")));
		swipeScreenUp();
		appiumdriver.findElementByAccessibilityId("Settings").click();
		return new RegularSettingsScreen();
	}	
	
	public void updateDatabase() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		toolbar.findElementsByClassName("XCUIElementTypeButton").get(0).click();
		Helpers.acceptAlert();
	}
	
	public void updateVIN() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		toolbar.findElementsByClassName("XCUIElementTypeButton").get(1).click();
		Helpers.acceptAlert();
	}

	public RegularMainScreen clickLogoutButton() {
		logoutbtn.click();
		return new RegularMainScreen();
	}

	public String getActiveCustomerValue() {
		return activecustomer.getAttribute("value");
	}

}
