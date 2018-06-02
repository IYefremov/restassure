package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CarHistoryScreen extends BaseAppScreen {
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeSearchBar[1]")
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
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void searchCar(String car) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Search")));
		searchbtn.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(car);
		closesearchbtn.click();
		//wait = new WebDriverWait(appiumdriver, 60);
		//wait.until(ExpectedConditions.elementToBeClickable(closesearchbtn)).click();
	}
	
	public void clickFirstCarHistoryInTable() {		
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
	}
	
	public String getFirstCarHistoryValueInTable() {		
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[1]").getAttribute("name");
	}
	
	public String getFirstCarHistoryDetailsInTable() {		
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[2]").getAttribute("name");
	}
	
	public void clickFirstCar() {		
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
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
		Helpers.waitABit(1000);
	}

}
