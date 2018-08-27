package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MonitorWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Repair Orders']")
	private WebElement repairorderslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Vendors/Teams']")
	private WebElement vendorsteamslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Repair Locations']")
	private WebElement repairlocationslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Order Assignments']")
	private WebElement vendororderslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Monitor Settings']")
	private WebElement monitorsettingslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Repair Cycle Time']")
	private WebElement repairCycleTimeLink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Vehicle Time Tracking']")
	private WebElement vehicleTimeTrackingLink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Cycle Time Trending']")
	private WebElement cycleTimeTrendingLink;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl04_Label1")
	private WebElement eventslink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl02_Label1")
	private WebElement whiteBoardLink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl14_Label1")
	private WebElement serviceCountLink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl13_Label1")
	private WebElement activeVehiclesByPhaseLink;

	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl08_Label1")
	private WebElement productionDashboardLink;
	
	public MonitorWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public RepairOrdersWebPage clickRepairOrdersLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairorderslink)).click();
		return PageFactory.initElements(
				driver, RepairOrdersWebPage.class);
	}
	
	public VendorsTeamsWebPage clickVendorsTeamsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(vendorsteamslink)).click();
		return PageFactory.initElements(
				driver, VendorsTeamsWebPage.class);
	}
	
	public RepairLocationsWebPage clickRepairLocationsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairlocationslink)).click();
		return PageFactory.initElements(
				driver, RepairLocationsWebPage.class);
	}
	
	public VendorOrdersWebPage clickVendorOrdersLink() {
	    try {
            wait.until(ExpectedConditions.elementToBeClickable(vendororderslink)).click();
        } catch (TimeoutException e) {
            vendororderslink.click();
        }
		return PageFactory.initElements(
				driver, VendorOrdersWebPage.class);
	}
	
	public MonitorSettingsWebPage clickMonitorSettingsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(monitorsettingslink)).click();
		return PageFactory.initElements(
				driver, MonitorSettingsWebPage.class);
	}
	
	public AverageRepairTimeReportWebPage clickRepairCycleTimeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairCycleTimeLink)).click();
		return PageFactory.initElements(
				driver, AverageRepairTimeReportWebPage.class);
	}
	
	public RepairLocationTimeTrackingWebPage clickVehicleTimeTrackingLink() {
		wait.until(ExpectedConditions.elementToBeClickable(vehicleTimeTrackingLink)).click();
		return PageFactory.initElements(
				driver, RepairLocationTimeTrackingWebPage.class);
	}
	
	public TrendingReportWebPage clickCycleTimeTrendingLink() {
		wait.until(ExpectedConditions.elementToBeClickable(cycleTimeTrendingLink)).click();
		return PageFactory.initElements(
				driver, TrendingReportWebPage.class);
	}
	
	public EventsWebPage clickEventsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(eventslink)).click();
		return PageFactory.initElements(
				driver, EventsWebPage.class);
	}

	public WhiteBoardWebPage clickWhiteBoardLink() {
		wait.until(ExpectedConditions.elementToBeClickable(whiteBoardLink)).click();
		return PageFactory.initElements(
				driver, WhiteBoardWebPage.class);
	}

	public ProductionDashboardWebPage clickProductionDashboardLink() {
		wait.until(ExpectedConditions.elementToBeClickable(productionDashboardLink)).click();
		return PageFactory.initElements(
				driver, ProductionDashboardWebPage.class);
	}

	public ServiceCountWebPage clickServiceCountLink() {
		wait.until(ExpectedConditions.elementToBeClickable(serviceCountLink)).click();
		return PageFactory.initElements(
				driver, ServiceCountWebPage.class);
	}
	
	public ActiveVechicleByPhaseWebPage clickActiveVehiclesByPhaseLink() {
		wait.until(ExpectedConditions.elementToBeClickable(activeVehiclesByPhaseLink)).click();
		return PageFactory.initElements(
				driver, ActiveVechicleByPhaseWebPage.class);
	}
	
}
