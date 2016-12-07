package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CarHistoryScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "btnSearch")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(accessibility = "Close")
    private IOSElement closesearchbtn;
	
	@iOSFindBy(accessibility  = "Switch to web")
    private IOSElement switchtowebbtn;
	
	@iOSFindBy(accessibility  = "Invoices")
    private IOSElement invoicesmenu;
	
	@iOSFindBy(accessibility  = "Work Orders")
    private IOSElement myworkordersmenumenu;
	
	public CarHistoryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void searchCar(String car)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
		searchbar.setValue(car);
		
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(closesearchbtn)).click();
	}
	
	public void clickFirstCarHistoryInTable() {		
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]").click();
	}
	
	public String getFirstCarHistoryValueInTable() {		
		return appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]").getAttribute("name");
	}
	
	public String getFirstCarHistoryDetailsInTable() {		
		return appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAStaticText[2]").getAttribute("name");
	}
	
	public void clickFirstCar() {		
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]").click();
	}
	
	public void clickSwitchToWeb() {		
		switchtowebbtn.click();
	}
	
	public MyInvoicesScreen clickCarHistoryInvoices() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(invoicesmenu)).click();
		return new MyInvoicesScreen(appiumdriver);
	}
	
	public void clickCarHistoryMyWorkOrders() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(myworkordersmenumenu)).click();
	}

}
