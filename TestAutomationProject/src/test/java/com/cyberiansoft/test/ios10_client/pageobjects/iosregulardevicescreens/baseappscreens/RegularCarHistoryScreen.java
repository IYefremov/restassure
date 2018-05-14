package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularCarHistoryScreen extends RegularBaseAppScreen {
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(accessibility = "Close")
    private IOSElement closesearchbtn;
	
	@iOSFindBy(accessibility = "Switch to web")
    private IOSElement switchtowebbtn;
	
	@iOSFindBy(accessibility = "Invoices")
    private IOSElement invoicesmenu;
	
	@iOSFindBy(accessibility = "Work Orders")
    private IOSElement myworkordersmenumenu;
	
	public RegularCarHistoryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn));
	}
	
	public void searchCar(String vin)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
		IOSElement searchfld = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Search' and type = 'XCUIElementTypeSearchField'"));
		searchfld.click();
		searchfld.sendKeys(vin + "\n");	
	}
	
	public void clickCancelSearchButton() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public void clickFirstCarHistoryInTable() {		
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")).click();
		//appiumdriver.tap(1, appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")), 200);
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[0]")).click();
	}
	
	public void clickCarHistoryRowByVehicleInfo(String vehicleinfo) {	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(vehicleinfo)));
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + vehicleinfo + "']")).click();
		//appiumdriver.tap(1, appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]")), 200);
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[0]")).click();
	}
	
	public String getFirstCarHistoryValueInTable() {		
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").getAttribute("value");
	}
	
	public String getFirstCarHistoryDetailsInTable() {		
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[2]").getAttribute("value");
	}
	
	public void clickFirstCar() {		
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
	}
	
	public void clickSwitchToWeb() {		
		switchtowebbtn.click();
	}
	
	public RegularMyInvoicesScreen clickCarHistoryInvoices() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(invoicesmenu)).click();
		return new RegularMyInvoicesScreen(appiumdriver);
	}
	
	public void clickCarHistoryMyWorkOrders() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(myworkordersmenumenu)).click();
	}

}
