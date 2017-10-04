package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

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
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Average Repair Time Report']")
	private WebElement averagerepairtimereportlink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Repair Location Time Tracking']")
	private WebElement repairlocationtimetrackinglink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Trending Report']")
	private WebElement trendingreportlink;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl04_Label1")
	private WebElement eventslink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl02_Label1")
	private WebElement kanbanlink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl14_Label1")
	private WebElement serviceCountlink;
	
	@FindBy(id="ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl13_Label1")
	private WebElement activeVehiclesByPhaselink;
	
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
		wait.until(ExpectedConditions.elementToBeClickable(vendororderslink)).click();
		return PageFactory.initElements(
				driver, VendorOrdersWebPage.class);
	}
	
	public MonitorSettingsWebPage clickMonitorSettingsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(monitorsettingslink)).click();
		return PageFactory.initElements(
				driver, MonitorSettingsWebPage.class);
	}
	
	public AverageRepairTimeReportWebPage clickAverageRepairTimeReportLink() {
		wait.until(ExpectedConditions.elementToBeClickable(averagerepairtimereportlink)).click();
		return PageFactory.initElements(
				driver, AverageRepairTimeReportWebPage.class);
	}
	
	public RepairLocationTimeTrackingWebPage clickRepairLocationTimeTrackingLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairlocationtimetrackinglink)).click();
		return PageFactory.initElements(
				driver, RepairLocationTimeTrackingWebPage.class);
	}
	
	public TrendingReportWebPage clickTrendingReportLink() {
		wait.until(ExpectedConditions.elementToBeClickable(trendingreportlink)).click();
		return PageFactory.initElements(
				driver, TrendingReportWebPage.class);
	}
	
	public EventsWebPage clickEventsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(eventslink)).click();
		return PageFactory.initElements(
				driver, EventsWebPage.class);
	}

	public KanbanWebPage clickKanbanLink() {
		wait.until(ExpectedConditions.elementToBeClickable(kanbanlink)).click();
		return PageFactory.initElements(
				driver, KanbanWebPage.class);
	}

	public ServiceCountWebPage clickServiceCountLink() {
		wait.until(ExpectedConditions.elementToBeClickable(serviceCountlink)).click();
		return PageFactory.initElements(
				driver, ServiceCountWebPage.class);
	}
	
	public ActiveVechicleByPhaseWebPage clickActiveVehiclesByPhaseLink() {
		wait.until(ExpectedConditions.elementToBeClickable(activeVehiclesByPhaselink)).click();
		return PageFactory.initElements(
				driver, ActiveVechicleByPhaseWebPage.class);
	}
	
}
