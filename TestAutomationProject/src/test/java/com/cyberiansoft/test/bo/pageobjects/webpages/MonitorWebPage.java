package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl12_Label1")
	private WebElement activeVehiclesByPhaseLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl08_Label1")
	private WebElement productionDashboardLink;

	public MonitorWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickRepairOrdersLink() {
        Utils.clickElement(repairorderslink);
	}

	public void clickVendorsTeamsLink() {
        Utils.clickElement(vendorsteamslink);
	}

	public void clickRepairLocationsLink() {
	    Utils.clickElement(repairlocationslink);
	}

	public void clickVendorOrdersLink() {
	    Utils.clickElement(vendororderslink);
	}

	public void clickMonitorSettingsLink() {
	    Utils.clickElement(monitorsettingslink);
	}

	public void clickRepairCycleTimeLink() {
	    Utils.clickElement(repairCycleTimeLink);
	}

	public void clickVehicleTimeTrackingLink() {
	    Utils.clickElement(vehicleTimeTrackingLink);
	}

	public void clickCycleTimeTrendingLink() {
	    Utils.clickElement(cycleTimeTrendingLink);
	}

	public void clickEventsLink() {
        Utils.clickElement(eventslink);
	}

	public void clickWhiteBoardLink() {
        Utils.clickElement(whiteBoardLink);
	}

	public void clickProductionDashboardLink() {
        Utils.clickElement(productionDashboardLink);
	}

	public void clickWipSummaryLink() {
        Utils.clickElement(wipSummaryLink);
	}

	public void clickServiceCountLink() {
        Utils.clickElement(serviceCountLink);
	}

	public void clickActiveVehiclesByPhaseLink() {
        Utils.clickElement(activeVehiclesByPhaseLink);
	}
}
