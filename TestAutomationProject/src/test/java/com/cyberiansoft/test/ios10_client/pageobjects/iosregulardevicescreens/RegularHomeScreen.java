  package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

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
	
	@iOSFindBy(accessibility = "Customers")
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
    private IOSElement logoutbtn;
	
	public RegularHomeScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	final static String scrollviewxpath = "//UIAScrollView[1]";

	public RegularCustomersScreen clickCustomersButton() {	
		if(!customersbtn.isDisplayed()) {
			TouchAction action = new TouchAction(appiumdriver);
			action.press(customersbtn).waitAction(300).release().perform();
		}
		TouchAction action = new TouchAction(appiumdriver);
		action.press(customersbtn).waitAction(300).release().perform();
		return new RegularCustomersScreen(appiumdriver);
	}

	public RegularMyInspectionsScreen clickMyInspectionsButton() {
		if(!myinspectionsbtn.isDisplayed()) {
			TouchAction action = new TouchAction(appiumdriver);
			action.press(myinspectionsbtn).waitAction(300).release().perform();
		}
		TouchAction action = new TouchAction(appiumdriver);
		action.press(myinspectionsbtn).waitAction(300).release().perform();
		return new RegularMyInspectionsScreen(appiumdriver);
	}
	
	public RegularTeamInspectionsScreen clickTeamInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(teaminspectionsbtn));
		teaminspectionsbtn.click();
		return new RegularTeamInspectionsScreen(appiumdriver);
	}

	public RegularMyWorkOrdersScreen clickMyWorkOrdersButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myworkordersbtn));
		myworkordersbtn.click();
		Helpers.waitABit(1000);
		return new RegularMyWorkOrdersScreen(appiumdriver);
	}
	
	public RegularCarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(carhistorybtn));
		carhistorybtn.click();
		return new RegularCarHistoryScreen(appiumdriver);
	}

	public RegularMyInvoicesScreen clickMyInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myinvoicesbtn));
		myinvoicesbtn.click();
		return new RegularMyInvoicesScreen(appiumdriver);
	}
	
	public RegularTeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(teaminvoicesbtn));
		teaminvoicesbtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		return new RegularTeamInvoicesScreen(appiumdriver);
	}

	public RegularServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		servicerequestsbtn.click();
		Helpers.waitABit(500);
		if (appiumdriver.findElementsByAccessibilityId("Loading service requests").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		}
		return new RegularServiceRequestsScreen(appiumdriver);
	}
	
	public void clickStatusButton() {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(statustsbtn));
		statustsbtn.click();
	}
	
	public RegularTeamWorkOrdersScreen clickTeamWorkordersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(temworkorderstsbtn));
		temworkorderstsbtn.click();
		Helpers.waitABit(1000);
		return new RegularTeamWorkOrdersScreen(appiumdriver);
	}

	public RegularSettingsScreen clickSettingsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		swipeScreenUp();
		settingstsbtn.click();
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
		logoutbtn.click();
		return new RegularMainScreen(appiumdriver);
	}

}
