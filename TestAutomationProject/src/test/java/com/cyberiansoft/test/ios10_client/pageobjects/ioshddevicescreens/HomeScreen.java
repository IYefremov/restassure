package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeScreen extends iOSHDBaseScreen {
	
	/*@iOSXCUITFindBy(accessibility  = "Customers")
    private IOSElement customersbtn;
	
	@iOSXCUITFindBy(accessibility  = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Team Inspections")
    private IOSElement teaminspectionsbtn;
	
	@iOSXCUITFindBy(accessibility  = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSXCUITFindBy(accessibility  = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSXCUITFindBy(accessibility  = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSXCUITFindBy(accessibility  = "Team Invoices")
    private IOSElement teaminvoicesbtn;
	
	@iOSXCUITFindBy(accessibility  = "Service Requests")
    private IOSElement servicerequestsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Status")
    private IOSElement statustsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Team Work Orders")
    private IOSElement temworkorderstsbtn;
	
	@iOSXCUITFindBy(accessibility  = "Settings")
    private IOSElement settingstsbtn;
	
	@iOSXCUITFindBy(accessibility  = "logout")
    private IOSElement logoutbtn;*/

	@iOSXCUITFindBy(accessibility = "viewPrompt")
	private IOSElement activecustomer;
	
	public HomeScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public CustomersScreen clickCustomersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Home")));
		appiumdriver.findElementByAccessibilityId("Customers").click();
		return new CustomersScreen();
	}

	public MyInspectionsScreen clickMyInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Home")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		MyInspectionsScreen myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.switchToMyView();
		return myInspectionsScreen;
	}
	
	public TeamInspectionsScreen clickTeamInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Inspections")));
		appiumdriver.findElementByAccessibilityId("Inspections").click();
		MyInspectionsScreen myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.waitInspectionsScreenLoaded();
		myInspectionsScreen.switchToTeamView();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		return new TeamInspectionsScreen();
	}

	public MyWorkOrdersScreen clickMyWorkOrdersButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Work Orders"))).click(); 
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.switchToMyView();
		return myworkordersscreen;		
	}
	
	public TeamWorkOrdersScreen clickTeamWorkordersButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Work Orders"))).click(); 
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.switchToTeamView();
		return new TeamWorkOrdersScreen();
	}
	
	public CarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Car History")));
		appiumdriver.findElementByAccessibilityId("Car History").click();
		return new CarHistoryScreen();
	}

	public MyInvoicesScreen clickMyInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen();
		myinvoicesscreen.switchToMyView();
		return myinvoicesscreen;
	}
	
	public TeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Invoices")));
		appiumdriver.findElementByAccessibilityId("Invoices").click();
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen();
		myinvoicesscreen.switchToTeamView();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		return new TeamInvoicesScreen();
	}

	public ServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Home")));
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Requests"))).click();
		//appiumdriver.findElementByAccessibilityId("Service Requests").click();
		/*if (appiumdriver.findElementsByAccessibilityId("Loading service requests").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading service requests")));
		}
		Helpers.waitABit(500);*/
		return new ServiceRequestsScreen();
	}
	
	public void clickStatusButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Status")));
		appiumdriver.findElementByAccessibilityId("Status").click();
	}

	public SettingsScreen clickSettingsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Home")));
		appiumdriver.findElementByAccessibilityId("Settings").click();
		return new SettingsScreen();
	}	
	
	public void updateDatabase() {
        MobileElement toolbar = null;
	    List<MobileElement> toolbars = appiumdriver.findElementsByAccessibilityId("Toolbar");
	    for (MobileElement tlb : toolbars)
	        if (tlb.isDisplayed()) {
                toolbar = tlb;
                break;
            }
		BaseUtils.waitABit(1000);
        toolbar.findElementByClassName("XCUIElementTypeButton").click();
		Helpers.acceptAlert();
	}
	
	public void updateVIN() {
        MobileElement toolbar = null;
        List<MobileElement> toolbars = appiumdriver.findElementsByAccessibilityId("Toolbar");
        for (MobileElement tlb : toolbars)
            if (tlb.isDisplayed()) {
                toolbar = tlb;
                break;
            }
        toolbar.findElementsByClassName("XCUIElementTypeButton").get(1).click();
		Helpers.acceptAlert();
	}

	public MainScreen clickLogoutButton() {
		appiumdriver.findElementByAccessibilityId("logout").click();
		return new MainScreen();
	}

	public String getActiveCustomerValue() {
		return activecustomer.getAttribute("value");
	}

}
