package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class  OrderMonitorScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(accessibility = "Change Status")
    private IOSElement phasestatuscell;
	
	@iOSFindBy(accessibility = "Service Status")
    private IOSElement servicestatuscell;
	
	@iOSFindBy(accessibility = "Start Service")
    private IOSElement startservice;
	
	@iOSFindBy(accessibility = "Completed")
    private IOSElement completedcell;
	
	@iOSFindBy(accessibility = "Done icon")
    private IOSElement servicedetailsdonebtn;
	
	@iOSFindBy(accessibility  = "custom detail button")
    private IOSElement customservicestatusbtn;
	
	@iOSFindBy(accessibility  = "Start Service")
    private IOSElement startservicebtn;
	
	@iOSFindBy(accessibility  = "Start phase")
    private IOSElement startphasebtn;
	
	@iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	@iOSFindBy(accessibility = "Services")
    private IOSElement servicesbtn;
	
	@iOSFindBy(accessibility = "Active")
    private IOSElement activecaption;*/
	
	public OrderMonitorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Order Monitor")));
	}
	
	public void selectPanel(String panelname) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(panelname))).click(); 
	}
	
	public void verifyPanelsStatuses(String panelname, String status) {
		List<WebElement> elements = appiumdriver.findElementsByXPath("//XCUIElementTypeCell[@name=\"" + panelname + "\"]/XCUIElementTypeStaticText[3]");
		for (WebElement element : elements) {
			Assert.assertTrue(element.getAttribute("value").equals(status));
		}

	}
	
	public void verifyPanelStatus(String panelname, String status) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name=\"" + panelname + "\"]/XCUIElementTypeStaticText[3]").getAttribute("value").equals(status));
	}
	
	public void verifyPanelStatusInPopup(String panelname, String status) {
		//appiumdriver.findElementByName(panelname).click();
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Service Status']/.."));
		Assert.assertTrue(par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value").equals(status));
		appiumdriver.findElementByAccessibilityId("Done icon").click();
	}
	
	public void setCompletedServiceStatus() throws InterruptedException {
		clickServiceStatusCell();
		appiumdriver.findElementByAccessibilityId("Completed").click();
		Thread.sleep(2000);
		//clickBackButton();
	}
	
	public void setCompletedPhaseStatus() throws InterruptedException {
		//clickCustomServiceStatusButton();
		//clickPhaseStatusCell();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1][@name='Completed']").click();
		//completedcell.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public void clickChangeStatusCell() {
		appiumdriver.findElementByAccessibilityId("Change Status").click();
	}
	
	public void clickServiceStatusCell() {
		appiumdriver.findElementByAccessibilityId("Service Status").click();
	}
	
	
	public void clickStartPhase() throws InterruptedException {
		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name='Repair phase']/XCUIElementTypeButton[@name='Start phase']").click();
		Thread.sleep(3000);
	}
	
	public void clickServiceDetailsDoneButton() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Done icon").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public boolean isStartServiceButtonPresent() {
		return appiumdriver.findElementByAccessibilityId("Start Service").isDisplayed();
	}
	
	public boolean isStartPhaseButtonPresent() {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeCell[@name='Repair phase']/XCUIElementTypeButton[@name='Start phase']").size() > 0;
	}
	
	public void clickServicesButton() {
		//appiumdriver.findElement(By.xpath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeButton[@name='Services']")).click();
		appiumdriver.findElement(By.xpath("//XCUIElementTypeButton[@name='Services']")).click();
		Helpers.waitABit(1000);
	}
	
	public boolean isRepairPhaseExists() { 
		return appiumdriver.findElementsByAccessibilityId("Repair phase").size() > 0;
	}
	
	public void clicksRepairPhaseLine() { 
		appiumdriver.findElementByAccessibilityId("Repair phase").click();
		Helpers.waitABit(1000);
	}
	
	public boolean isServiceIsActive(String servicedisplayname) {
		return appiumdriver.findElementByXPath("//UIATableView[3]/UIATableCell[contains(@name, \""
						+ servicedisplayname + "\")]/UIAStaticText[3]").getAttribute("name").equals("Active");
		//Assert.assertTrue(activecaption.isDisplayed());
	}
	
	public void verifyServiceStartDateIsSet(String servicedisplayname, String startdate) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ servicedisplayname + "\")]/UIAStaticText[contains(@name, \""
						+ startdate + "\")]").isDisplayed());
	}
	
	public void verifyStartServiceDissapeared() throws InterruptedException {
		Assert.assertFalse(appiumdriver.findElementByXPath("//UIAPopover[1]/UIAButton[@name=\"Start Service\"]") .isDisplayed());
	}
	
	public void clickStartService() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Start Service").click();
	}
	
	public String getServiceStartDate() {
		return appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Start Date\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public boolean isServiceStartDateExists() { 
		return appiumdriver.findElementsByAccessibilityId("Start Date").size() > 0;
	}
	
	public TeamWorkOrdersScreen clickBackButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
		return new TeamWorkOrdersScreen(appiumdriver);
	}
	
	public boolean isServicePresent(String servicename) { 
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']").size() > 0;
	}
	
	public void checkMyWorkCheckbox() {
		if (appiumdriver.findElementsByAccessibilityId("checkbox unchecked").size() > 0)
			appiumdriver.findElementByAccessibilityId("checkbox unchecked").click();
	}
	
	public void uncheckMyWorkCheckbox() {
		if (appiumdriver.findElementsByAccessibilityId("checkbox checked").size() > 0)
			appiumdriver.findElementByAccessibilityId("checkbox checked").click();
	}

}
