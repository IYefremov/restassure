  package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularHomeScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(name = "Customers")
    private IOSElement customersbtn;
	
	@iOSFindBy(name = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSFindBy(name = "Team Inspections")
    private IOSElement teaminspectionsbtn;
	
	@iOSFindBy(name = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSFindBy(name = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSFindBy(name = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSFindBy(name = "Service Requests")
    private IOSElement servicerequestsbtn;
	
	@iOSFindBy(name = "Status")
    private IOSElement statustsbtn;
	
	@iOSFindBy(name = "Team Work Orders")
    private IOSElement temworkorderstsbtn;
	
	@iOSFindBy(name = "Settings")
    private IOSElement settingstsbtn;
	
	@iOSFindBy(name = "logout")
    private IOSElement logoutbtn;
	
	public RegularHomeScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	final static String scrollviewxpath = "//UIAScrollView[1]";

	public RegularCustomersScreen clickCustomersButton() {	
		Helpers.scroolTo("Customers");
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(customersbtn));
		customersbtn.click();
		return new RegularCustomersScreen(appiumdriver);
	}

	public RegularMyInspectionsScreen clickMyInspectionsButton() {
		Helpers.scroolTo("My Inspections");
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myinspectionsbtn));
		myinspectionsbtn.click();
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

	public RegularServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		servicerequestsbtn.click();
		return new RegularServiceRequestsScreen(appiumdriver);
	}
	
	public void clickStatusButton() {
		Helpers.scroolTo("Status");
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(statustsbtn));
		statustsbtn.click();
	}
	
	public RegularTeamWorkOrdersScreen clickTeamWorkordersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(temworkorderstsbtn));
		temworkorderstsbtn.click();
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
		MobileElement element = Helpers.element(MobileBy.xpath("//UIAToolbar[1]/UIAButton[1]"));
		element.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}
	
	public void updateVIN() {
		Helpers.setTimeOut(60);
		MobileElement element = Helpers.element(MobileBy.xpath("//UIAToolbar[1]/UIAButton[2]"));
		element.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public RegularMainScreen clickLogoutButton() {
		logoutbtn.click();
		return new RegularMainScreen(appiumdriver);
	}

}
