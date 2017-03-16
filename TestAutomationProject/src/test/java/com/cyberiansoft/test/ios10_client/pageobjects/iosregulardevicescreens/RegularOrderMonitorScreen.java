package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class  RegularOrderMonitorScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Phase Status")
    private IOSElement phasestatuscell;
	
	@iOSFindBy(accessibility = "Service Status")
    private IOSElement servicestatuscell;
	
	@iOSFindBy(accessibility = "Start Service")
    private IOSElement startservice;
	
	@iOSFindBy(accessibility = "Completed")
    private IOSElement completedcell;
	
	@iOSFindBy(accessibility = "Done icon")
    private IOSElement servicedetailsdonebtn;
	
	@iOSFindBy(accessibility = "custom detail button")
    private IOSElement customservicestatusbtn;
	
	@iOSFindBy(accessibility = "Start Service")
    private IOSElement startservicebtn;
	
	@iOSFindBy(accessibility = "Start phase")
    private IOSElement startphasebtn;
	
	@iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	@iOSFindBy(accessibility = "Services")
    private IOSElement servicesbtn;
	
	@iOSFindBy(accessibility = "Active")
    private IOSElement activecaption;
	
	public RegularOrderMonitorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPanel(String panelname) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + panelname + "']").click();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIATableView/UIATableCell[contains(@name, \""
		//		+ panelname
		//		+ "\")]")));
		//element.click();
		//return element.getAttribute("name");
	}
	
	/*public void verifyPanelsStatuses(String panelname, String status)  {
		//appiumdriver.findElementByAccessibilityId(panelname).click();
		WebElement par = getTableParentCell(panelname);
		System.out.print("++++" + );
		Assert.assertTrue(par.findElement(By.xpath(".//XCUIElementTypeStaticText[@name='" + status + "']")).isDisplayed());
	}*/
	
	public void verifyPanelStatus(String panelname, String status) {
		WebElement par = getTableParentCell(panelname);
		Assert.assertTrue(par.findElements(By.xpath(".//XCUIElementTypeStaticText[@name='" + status + "']")).size() > 0);
	}
	
	public void verifyPanelStatusInPopup(String panelname, String status) {
		appiumdriver.findElementByName(panelname).click();
		WebElement par = getTableParentCell("Phase Status");
		Assert.assertTrue(par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("name").equals(status));
		appiumdriver.findElementByAccessibilityId("Done icon").click();
	}
	
	public void verifyServiceStatusInPopup(String panelname, String status) {
		appiumdriver.findElementByName(panelname).click();
		WebElement par = getTableParentCell("Service Status");
		Assert.assertTrue(par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("name").equals(status));
		appiumdriver.findElementByAccessibilityId("Done icon").click();
	}
	
	public void setCompletedServiceStatus() {
		clickServiceStatusCell();
		clickCompletedPhaseCell();
	}
	
	public void setCompletedPhaseStatus() {
		//clickCustomServiceStatusButton();
		clickPhaseStatusCell();
		clickCompletedPhaseCell();
	}
	
	public void clickCompletedPhaseCell() {
		completedcell.click();
		Helpers.waitABit(2000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public void clickPhaseStatusCell() {
		phasestatuscell.click();
	}
	
	public void clickServiceStatusCell() {
		servicestatuscell.click();
	}
	
	
	public void clickStartPhase() throws InterruptedException {
		startphasebtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 40);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public void clickServiceDetailsDoneButton() throws InterruptedException {
		servicedetailsdonebtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 40);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public boolean isStartServiceButtonPresent() {
		return startservicebtn.isDisplayed();
	}
	
	public boolean isStartPhaseButtonPresent() {
		return startphasebtn.isDisplayed();
	}
	
	public void clickServicesButton() {
		servicesbtn.click();
	}
	
	public boolean isServiceIsActive(String servicedisplayname) {
		WebElement par = getTableParentCell(servicedisplayname);
		return par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("name").equals("Active");
	}
	
	public void verifyServiceStartDateIsSet(String servicedisplayname, String startdate) {
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[contains(@label='" + servicedisplayname + "')]/.."));
		Assert.assertTrue(par.findElement(By.xpath(".//XCUIElementTypeStaticText[contains(@name, \""
						+ startdate + "\")]")).isDisplayed());
	}
	
	public void verifyStartServiceDissapeared() {
		Assert.assertFalse(appiumdriver.findElementsByAccessibilityId("Start Service").size() > 0);
	}
	
	public void clickStartService() throws InterruptedException {		
		startservicebtn.click();
		Thread.sleep(3000);
	}
	
	public String getServiceStartDate() {
		WebElement par = getTableParentCell("Start Date");
		return par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("name");
	}
	
	public boolean isServiceStartDateExists() { 
		return appiumdriver.findElementsByAccessibilityId("Start Date").size() > 0;
	}
	
	public RegularTeamWorkOrdersScreen clickBackButton() {
		backbtn.click();
		return new RegularTeamWorkOrdersScreen(appiumdriver);
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

	public boolean isRepairPhaseExists() { 
		return appiumdriver.findElementsByAccessibilityId("Repair phase").size() > 0;
	}
	
	public void clicksRepairPhaseLine() { 
		appiumdriver.findElementByAccessibilityId("Repair phase").click();
	}
	
	public void clickStartPhaseButton() { 
		appiumdriver.findElementByXPath("//XCUIElementTypeButton[@name='Start phase']").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public boolean isStartPhaseButtonExists() { 
		return appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@name='Start phase']").size() > 0;
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
