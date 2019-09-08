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

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl02_Label1")
	private WebElement whiteBoardLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl14_Label1")
	private WebElement wipSummaryLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl13_Label1")
	private WebElement serviceCountLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl08_Label1")
	private WebElement productionDashboardLink;

	public MonitorWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickRepairOrdersLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairorderslink)).click();
	}

	public void clickVendorsTeamsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(vendorsteamslink)).click();
	}

	public void clickRepairLocationsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairlocationslink)).click();
	}

	public void clickVendorOrdersLink() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(vendororderslink)).click();
		} catch (TimeoutException e) {
			vendororderslink.click();
		}
	}

	public void clickMonitorSettingsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(monitorsettingslink)).click();
	}

	public void clickRepairCycleTimeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(repairCycleTimeLink)).click();
	}

	public void clickVehicleTimeTrackingLink() {
		wait.until(ExpectedConditions.elementToBeClickable(vehicleTimeTrackingLink)).click();
	}

	public void clickCycleTimeTrendingLink() {
		wait.until(ExpectedConditions.elementToBeClickable(cycleTimeTrendingLink)).click();
	}

	public void clickEventsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(eventslink)).click();
	}

	public void clickWhiteBoardLink() {
		wait.until(ExpectedConditions.elementToBeClickable(whiteBoardLink)).click();
	}

	public void clickProductionDashboardLink() {
		wait.until(ExpectedConditions.elementToBeClickable(productionDashboardLink)).click();
	}

	public void clickWipSummaryLink() {
		wait.until(ExpectedConditions.elementToBeClickable(wipSummaryLink)).click();
	}

	public void clickServiceCountLink() {
		wait.until(ExpectedConditions.elementToBeClickable(serviceCountLink)).click();
	}

}
