  package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class HomeScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(name = "Customers")
    private IOSElement customersbtn;
	
	@iOSFindBy(name = "My Inspections")
    private IOSElement myinspectionsbtn;
	
	@iOSFindBy(name = "My Work Orders")
    private IOSElement myworkordersbtn;
	
	@iOSFindBy(name = "Car History")
    private IOSElement carhistorybtn;
	
	@iOSFindBy(name = "My Invoices")
    private IOSElement myinvoicesbtn;
	
	@iOSFindBy(name = "Team Invoices")
    private IOSElement teaminvoicesbtn;
	
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
	
	public HomeScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	final static String scrollviewxpath = "//UIAScrollView[1]";

	public CustomersScreen clickCustomersButton() {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(customersbtn));
		customersbtn.click();
		return new CustomersScreen(appiumdriver);
	}

	public MyInspectionsScreen clickMyInspectionsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myinspectionsbtn));
		myinspectionsbtn.click();
		return new MyInspectionsScreen(appiumdriver);
	}

	public MyWorkOrdersScreen clickMyWorkOrdersButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myworkordersbtn));
		myworkordersbtn.click();
		return new MyWorkOrdersScreen(appiumdriver);
	}
	
	public CarHistoryScreen clickCarHistoryButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(carhistorybtn));
		carhistorybtn.click();
		return new CarHistoryScreen(appiumdriver);
	}

	public MyInvoicesScreen clickMyInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(myinvoicesbtn));
		myinvoicesbtn.click();
		return new MyInvoicesScreen(appiumdriver);
	}
	
	public TeamInvoicesScreen clickTeamInvoices() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(teaminvoicesbtn));
		teaminvoicesbtn.click();
		return new TeamInvoicesScreen(appiumdriver);
	}

	public ServiceRequestsScreen clickServiceRequestsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		servicerequestsbtn.click();
		Helpers.waitABit(2000);
		return new ServiceRequestsScreen(appiumdriver);
	}
	
	public void clickStatusButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(statustsbtn));
		statustsbtn.click();
	}
	
	public TeamWorkOrdersScreen clickTeamWorkordersButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(temworkorderstsbtn));
		temworkorderstsbtn.click();
		return new TeamWorkOrdersScreen(appiumdriver);
	}

	public SettingsScreen clickSettingsButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestsbtn));
		Helpers.swipeScreenUp();
		settingstsbtn.click();
		return new SettingsScreen(appiumdriver);
	}	
	
	public void updateDatabase() {
		Helpers.setTimeOut(180);
		MobileElement element = Helpers.element(MobileBy.xpath("//UIAPopover[1]/UIAToolbar[1]/UIAButton[1]"));
		element.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}
	
	public void updateVIN() {
		Helpers.setTimeOut(60);
		MobileElement element = Helpers.element(MobileBy.xpath("//UIAPopover[1]/UIAToolbar[1]/UIAButton[2]"));
		element.click();
		Helpers.acceptAlert();
		Helpers.setDefaultTimeOut();
	}

	public MainScreen clickLogoutButton() {
		logoutbtn.click();
		return new MainScreen(appiumdriver);
	}

}
