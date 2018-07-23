package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
	
	@iOSFindBy(accessibility = "Services")
    private IOSElement servicesbtn;
	
	@iOSFindBy(accessibility = "Active")
    private IOSElement activecaption;*/

	@iOSFindBy(accessibility = "MonitorOrderServicesList")
	private IOSElement monitorservicestable;

    @iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	public OrderMonitorScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Order Monitor")));
	}
	
	public void selectPanel(String panelname) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name(panelname))).click();
	}
	
	public List<String> getPanelsStatuses(String panelname) {
		List<MobileElement> elements = monitorservicestable.findElementsByIosNsPredicate ("name = '" +
				panelname + "' and type = 'XCUIElementTypeCell'");
		return elements.stream().map(element -> element.
				findElementByAccessibilityId("lblServiceStatus").getAttribute("value"))
				.collect(Collectors.toList());
	}
	
	public String getPanelStatus(String panelname) {
		return monitorservicestable.findElementByAccessibilityId (panelname).findElementByAccessibilityId("lblServiceStatus").getAttribute("value");
	}
	
	public String getPanelStatusInPopup() {
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='Service Status']/.."));
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}

	public void clickDoneIcon() {
		appiumdriver.findElementByAccessibilityId("Done icon").click();
	}
	
	public void setCompletedServiceStatus() {
		clickServiceStatusCell();
		appiumdriver.findElementByAccessibilityId("Completed").click();
	}
	
	public void setCompletedPhaseStatus() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1][@name='Completed']").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}
	
	public void clickChangeStatusCell() {
		appiumdriver.findElementByAccessibilityId("Change Status").click();
	}
	
	public void clickServiceStatusCell() {
		appiumdriver.findElementByAccessibilityId("Service Status").click();
	}
	
	
	public void clickStartPhase() {
		monitorservicestable.findElementByIosNsPredicate("name = 'Repair phase' and type = 'XCUIElementTypeCell'")
				.findElementByAccessibilityId("btnStartReset").click();
	}
	
	public OrderMonitorScreen clickServiceDetailsDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done icon").click();
		return this;
	}
	
	public boolean isStartServiceButtonPresent() {
		return appiumdriver.findElementByAccessibilityId("Start Service").isDisplayed();
	}

	
	public boolean isStartPhaseButtonPresent() {
		return monitorservicestable.findElementByIosNsPredicate("name = 'Repair phase' and type = 'XCUIElementTypeCell'")
				.findElementsByAccessibilityId("btnStartReset").size() > 0;
	}
	
	public void clickServicesButton() {
		//appiumdriver.findElement(By.xpath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeButton[@name='Services']")).click();
		appiumdriver.findElement(By.xpath("//XCUIElementTypeButton[@name='Services']")).click();
	}
	
	public boolean isRepairPhaseExists() { 
		return appiumdriver.findElementsByAccessibilityId("Repair phase").size() > 0;
	}
	
	public void clicksRepairPhaseLine() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Repair phase"))).click();
	}
	
	public void clickStartService() {
		appiumdriver.findElementByAccessibilityId("Start Service").click();
	}
	
	public boolean isServiceStartDateExists() { 
		return appiumdriver.findElementsByAccessibilityId("Start Date").size() > 0;
	}
	
	public TeamWorkOrdersScreen clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Back")));
		backbtn.click();
		return new TeamWorkOrdersScreen();
	}
	
	public boolean isServicePresent(String servicename) {
		return monitorservicestable.findElementsByAccessibilityId (servicename ).size() > 0;
	}
	
	public void checkMyWorkCheckbox() {
		if (appiumdriver.findElementsByAccessibilityId("checkbox unchecked").size() > 0)
			appiumdriver.findElementByAccessibilityId("checkbox unchecked").click();
	}

}
