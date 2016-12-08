package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;


import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class  RegularOrderMonitorScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Phase Status\"]")
    private IOSElement phasestatuscell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Service Status\"]")
    private IOSElement servicestatuscell;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"Start Service\"]]")
    private IOSElement startservice;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Completed\"]")
    private IOSElement completedcell;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"Login\"]/UIAButton[@name=\"Done icon\"]")
    private IOSElement servicedetailsdonebtn;
	
	@iOSFindBy(accessibility  = "custom detail button")
    private IOSElement customservicestatusbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Start Service\"]")
    private IOSElement startservicebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@name=\"Back\"]")
    private IOSElement backbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[2]/UIAButton[@name= \"Services\"]")
    private IOSElement servicesbtn;
	
	@iOSFindBy(xpath = "//UIAStaticText[@name= \"Active\"]")
    private IOSElement activecaption;
	
	public RegularOrderMonitorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPanel(String panelname) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name='" + panelname + "']/UIAButton[@name='customizedDetails']").click();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIATableView/UIATableCell[contains(@name, \""
		//		+ panelname
		//		+ "\")]")));
		//element.click();
		//return element.getAttribute("name");
	}
	
	public String getPanelsStatuses(String panelname) {
		//Helpers.scroolTo(panelname);
		return appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + panelname + "\"]/UIAStaticText[3]").getAttribute("name");
	}
	
	public void verifyPanelStatus(String panelname, String status) {
		Assert.assertTrue(appiumdriver.findElementsByXPath("//UIATableCell[@name=\"" + panelname + "\"]/UIAStaticText[@name='" + status + "']").size() > 0);
	}
	
	public void verifyPanelStatusInPopup(String panelname, String status) {
		appiumdriver.findElementByName(panelname).click();
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"Phase Status\"]/UIAStaticText[2]").getAttribute("name").equals(status));
		appiumdriver.findElementByXPath("//UIANavigationBar[1]/UIAButton[@name=\"Done icon\"]").click();
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
		Helpers.waitABit(3000);
	}
	
	public void clickPhaseStatusCell() {
		phasestatuscell.click();
	}
	
	public void clickServiceStatusCell() {
		servicestatuscell.click();
	}
	
	public void clickServiceDetailsDoneButton() throws InterruptedException {
		servicedetailsdonebtn.click();
		Thread.sleep(3000);
	}
	
	public boolean isStartServiceButtonPresent() {
		return startservicebtn.isDisplayed();
	}
	
	public void clickServicesButton() {
		servicesbtn.click();
	}
	
	public boolean isServiceIsActive(String servicedisplayname) {
		Helpers.scroolTo(servicedisplayname);
		return appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ servicedisplayname + "\")]/UIAStaticText[3]").getAttribute("name").equals("Active");
		//Assert.assertTrue(activecaption.isDisplayed());
	}
	
	public void verifyServiceStartDateIsSet(String servicedisplayname, String startdate) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ servicedisplayname + "\")]/UIAStaticText[contains(@name, \""
						+ startdate + "\")]").isDisplayed());
	}
	
	public void verifyStartServiceDissapeared() throws InterruptedException {
		Assert.assertFalse(Helpers.elementExists("//UIAToolbar[1]/UIAButton[@name=\"Start Service\"]"));
	}
	
	public void clickStartService() throws InterruptedException {		
		startservicebtn.click();
		Thread.sleep(3000);
	}
	
	public String getServiceStartDate() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\"Start Date\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public boolean isServiceStartDateExists() { 
		return Helpers.elementExists(By.xpath("//UIATableView[1]/UIATableCell[@name=\"Start Date\"]"));
	}
	
	public RegularTeamWorkOrdersScreen clickBackButton() {
		backbtn.click();
		return new RegularTeamWorkOrdersScreen(appiumdriver);
	}
	
	public boolean isRepairPhaseExists() { 
		return appiumdriver.findElementsByAccessibilityId("Repair phase").size() > 0;
	}
	
	public void clicksRepairPhaseLine() { 
		appiumdriver.findElementByAccessibilityId("Repair phase").click();
	}
	
	public boolean isStartPhaseButtonExists() { 
		return appiumdriver.findElementsByXPath("//UIAButton[@name='Start phase']").size() > 0;
	}
	
	public void clickStartPhaseButton() { 
		appiumdriver.findElementByXPath("//UIAButton[@name='Start phase']").click();
		Helpers.waitABit(2000);
	}

}
