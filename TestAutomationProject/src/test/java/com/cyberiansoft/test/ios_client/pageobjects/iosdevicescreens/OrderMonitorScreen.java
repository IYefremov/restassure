package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class  OrderMonitorScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIATableView/UIATableCell[@name=\"Phase Status\"]")
    private IOSElement phasestatuscell;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIATableView/UIATableCell[@name=\"Service Status\"]")
    private IOSElement servicestatuscell;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIAButton[@name=\"Start Service\"]]")
    private IOSElement startservice;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIATableView/UIATableCell[@name=\"Completed\"]")
    private IOSElement completedcell;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIANavigationBar[@name=\"Service Details\"]/UIAButton[@name=\"Done icon\"]")
    private IOSElement servicedetailsdonebtn;
	
	@iOSFindBy(name = "custom detail button")
    private IOSElement customservicestatusbtn;
	
	@iOSFindBy(name = "Start Service")
    private IOSElement startservicebtn;
	
	@iOSFindBy(name = "Start Phase")
    private IOSElement startphasebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[2]")
    private IOSElement backbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name= \"Services\"]")
    private IOSElement servicesbtn;
	
	@iOSFindBy(xpath = "//UIAStaticText[@name= \"Active\"]")
    private IOSElement activecaption;
	
	public OrderMonitorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPanel(String panelname) {
		appiumdriver.findElementByName(panelname).click();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		//MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIATableView/UIATableCell[contains(@name, \""
		//		+ panelname
		//		+ "\")]")));
		//element.click();
		//return element.getAttribute("name");
	}
	
	public void verifyPanelsStatuses(String panelname, String status) {
		List<WebElement> elements = appiumdriver.findElementsByXPath("//UIATableCell[@name=\"" + panelname + "\"]/UIAStaticText[2]");
		for (WebElement element : elements) {
			Assert.assertTrue(element.getAttribute("name").equals(status));
		}

	}
	
	public void verifyPanelStatus(String panelname, String status) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + panelname + "\"]/UIAStaticText[2]").getAttribute("name").equals(status));
	}
	
	public void verifyPanelStatusInPopup(String panelname, String status) {
		appiumdriver.findElementByName(panelname).click();
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Phase Status\"]/UIAStaticText[2]").getAttribute("name").equals(status));
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIANavigationBar[1]/UIAButton[@name=\"Done icon\"]").click();
	}
	
	public void setCompletedServiceStatus() throws InterruptedException {
		clickServiceStatusCell();
		completedcell.click();
		Thread.sleep(2000);
		//clickBackButton();
	}
	
	public void setCompletedPhaseStatus() throws InterruptedException {
		//clickCustomServiceStatusButton();
		clickPhaseStatusCell();
		completedcell.click();
		Thread.sleep(3000);
	}
	
	public void clickPhaseStatusCell() {
		phasestatuscell.click();
	}
	
	public void clickServiceStatusCell() {
		servicestatuscell.click();
	}
	
	
	public void clickStartPhase() throws InterruptedException {
		startphasebtn.click();
		Thread.sleep(3000);
	}
	
	public void clickServiceDetailsDoneButton() throws InterruptedException {
		servicedetailsdonebtn.click();
		Thread.sleep(3000);
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
		return appiumdriver.findElementByXPath("//UIATableView[3]/UIATableCell[contains(@name, \""
						+ servicedisplayname + "\")]/UIAStaticText[2]").getAttribute("name").equals("Active");
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
		startservicebtn.click();
		Thread.sleep(3000);
	}
	
	public String getServiceStartDate() {
		return appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Start Date\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public boolean isServiceStartDateExists() { 
		return Helpers.elementExists(By.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Start Date\"]"));
	}
	
	public TeamWorkOrdersScreen clickBackButton() {
		backbtn.click();
		return new TeamWorkOrdersScreen(appiumdriver);
	}

}
