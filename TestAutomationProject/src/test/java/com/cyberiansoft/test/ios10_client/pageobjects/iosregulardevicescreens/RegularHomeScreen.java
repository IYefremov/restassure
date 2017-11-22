  package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularTeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularHomeScreen extends iOSRegularBaseScreen {
	
	/*@iOSFindBy(accessibility = "Customers")
    private IOSElement customersbtn;
	
	@iOSFindBy(accessibility = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSFindBy(accessibility  = "Team Inspections")
    private IOSElement teaminspectionsbtn;
	
	@iOSFindBy(accessibility = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSFindBy(accessibility = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSFindBy(accessibility = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSFindBy(accessibility = "Team Invoices")
    private IOSElement teaminvoicesbtn;
	
	@iOSFindBy(accessibility = "Service Requests")
    private IOSElement servicerequestsbtn;
	
	@iOSFindBy(accessibility = "Status")
    private IOSElement statustsbtn;
	
	@iOSFindBy(accessibility = "Team Work Orders")
    private IOSElement temworkorderstsbtn;
	
	@iOSFindBy(accessibility = "Settings")
    private IOSElement settingstsbtn;
	
	@iOSFindBy(accessibility = "logout")
    private IOSElement logoutbtn;*/
	
	public RegularHomeScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), RegularHomeScreen.class);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public RegularCustomersScreen clickCustomersButton() {	
		if(!appiumdriver.findElementByAccessibilityId("Customers").isDisplayed()) {
			TouchAction action = new TouchAction(appiumdriver);
			action.press(appiumdriver.findElementByAccessibilityId("Customers")).waitAction(Duration.ofSeconds(1)).release().perform();
		}
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Customers")).waitAction(Duration.ofSeconds(1)).release().perform();
		return new RegularCustomersScreen(appiumdriver);
	}

	public RegularMyInspectionsScreen clickMyInspectionsButton() {
		if(!appiumdriver.findElementByAccessibilityId("My Inspections").isDisplayed()) {
			TouchAction action = new TouchAction(appiumdriver);
			action.press(appiumdriver.findElementByAccessibilityId("My Inspections")).waitAction(Duration.ofSeconds(1)).release().perform();
		}
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("My Inspections")).waitAction(Duration.ofSeconds(1)).release().perform();
		return new RegularMyInspectionsScreen(appiumdriver);
	}
	
	public RegularTeamInspectionsScreen clickTeamInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Team Inspections")));
		appiumdriver.findElementByAccessibilityId("Team Inspections").click();
		return new RegularTeamInspectionsScreen(appiumdriver);
	}

	public RegularMyWorkOrdersScreen clickMyWorkOrdersButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("My Work Orders")));
		appiumdriver.findElementByAccessibilityId("My Work Orders").click();
		return new RegularMyWorkOrdersScreen(appiumdriver);
	}
	
	public RegularCarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Car History")));
		appiumdriver.findElementByAccessibilityId("Car History").click();
		return new RegularCarHistoryScreen(appiumdriver);
	}

	public RegularMyInvoicesScreen clickMyInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("My Invoices")));
		appiumdriver.findElementByAccessibilityId("My Invoices").click();
		return new RegularMyInvoicesScreen(appiumdriver);
	}
	
	public RegularTeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Team Invoices")));
		appiumdriver.findElementByAccessibilityId("Team Invoices").click();
		return new RegularTeamInvoicesScreen(appiumdriver);
	}

	public RegularServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Requests")));
		appiumdriver.findElementByAccessibilityId("Service Requests").click();
		return new RegularServiceRequestsScreen(appiumdriver);
	}
	
	public void clickStatusButton() {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(statustsbtn));
		appiumdriver.findElementByAccessibilityId("Status").click();
	}
	
	public RegularTeamWorkOrdersScreen clickTeamWorkordersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Team Work Orders")));
		appiumdriver.findElementByAccessibilityId("Team Work Orders").click();
		Helpers.waitABit(1000);
		return new RegularTeamWorkOrdersScreen(appiumdriver);
	}

	public RegularSettingsScreen clickSettingsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Requests")));
		swipeScreenUp();
		appiumdriver.findElementByAccessibilityId("Settings").click();
		return new RegularSettingsScreen(appiumdriver);
	}	
	
	public void updateDatabase() {
		Helpers.setTimeOut(180);
		appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeButton[1]").click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}
	
	public void updateVIN() {
		Helpers.setTimeOut(60);
		appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeButton[2]").click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public RegularMainScreen clickLogoutButton() {
		appiumdriver.findElementByAccessibilityId("logout").click();
		return new RegularMainScreen(appiumdriver);
	}

}
