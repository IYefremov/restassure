package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.monitor.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class  RegularOrderMonitorScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(accessibility = "Phase Status")
	private IOSElement phasestatuscell;

	@iOSXCUITFindBy(accessibility = "Service Status")
	private IOSElement servicestatuscell;

	@iOSXCUITFindBy(accessibility = "Completed")
	private IOSElement completedcell;

	@iOSXCUITFindBy(accessibility = "Done icon")
	private IOSElement servicedetailsdonebtn;

	@iOSXCUITFindBy(accessibility = "custom detail button")
	private IOSElement customservicestatusbtn;

	@iOSXCUITFindBy(accessibility = "Start Service")
	private IOSElement startservicebtn;

	@iOSXCUITFindBy(accessibility = "btnStartReset")
	private IOSElement startphasebtn;

	@iOSXCUITFindBy(accessibility = "Back")
	private IOSElement backbtn;

	@iOSXCUITFindBy(accessibility = "Services")
	private IOSElement servicesbtn;

	@iOSXCUITFindBy(accessibility = "Active")
	private IOSElement activecaption;

	@iOSXCUITFindBy(accessibility = "MonitorOrderServicesList")
	private IOSElement monitorserviceslist;

	public RegularOrderMonitorScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitOrderMonitorScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("MonitorOrderServicesList")));
	}

	public void selectPanel(String panelName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("MonitorOrderServicesList")));
		wait = new WebDriverWait(appiumdriver, 15);

		wait.until(appiumdriver -> monitorserviceslist.findElementsByAccessibilityId(panelName).size() > 0);
		swipeToElement(monitorserviceslist.findElement(MobileBy.AccessibilityId(panelName)));
		monitorserviceslist.findElementByAccessibilityId(panelName).click();
	}

	public void selectPanel(ServiceData serviceData) {
		String panelName = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null) {
			panelName = panelName + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		}
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("MonitorOrderServicesList")));
		wait = new WebDriverWait(appiumdriver, 45);
		wait.until(ExpectedConditions.elementToBeClickable(monitorserviceslist));
		swipeToElement(monitorserviceslist.findElement(MobileBy.AccessibilityId(panelName)));
		monitorserviceslist.findElementByAccessibilityId(panelName).click();
	}

	public void selectPanelToChangeStatus(String panelname) {
		selectPanel(panelname);
		clickChangeStatus();
	}

	public void selectOrderPhase(String phaseName) {
		monitorserviceslist.findElementByAccessibilityId(phaseName).click();
	}

	public void clickChangeStatus() {
		appiumdriver.findElementByAccessibilityId("Change Status").click();
	}

	public void clickAssignTechnician() {
		appiumdriver.findElementByAccessibilityId("Assign Technician").click();
	}

	public void selectPhaseVendor(String vendorName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Teams")));
		appiumdriver.findElementByAccessibilityId(vendorName).click();
	}

	public String getPanelStatus(String panelname) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("lblServiceStatus")));
		return monitorserviceslist.findElementByAccessibilityId(panelname).findElementByAccessibilityId("lblServiceStatus").getAttribute("value");
	}

	public String getPanelStatus(ServiceData serviceData) {

		String panelName = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null) {
			panelName = panelName + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		}
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("lblServiceStatus")));
		return monitorserviceslist.findElementByAccessibilityId(panelName).findElementByAccessibilityId("lblServiceStatus").getAttribute("value");
	}

	public String getPanelStatus(ServiceData serviceData, VehiclePartData vehiclePartData) {

		String panelName = serviceData.getServiceName() + " (" + vehiclePartData.getVehiclePartName() + ")";
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("lblServiceStatus")));
		return monitorserviceslist.findElementByAccessibilityId(panelName).findElementByAccessibilityId("lblServiceStatus").getAttribute("value");
	}

	public String getServiceTeamValue(ServiceData serviceData) {
		String panelName = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null) {
			panelName = panelName + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		}
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		WebElement serviceTeamCell = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("lblTeamName")));
		return serviceTeamCell.getAttribute("value");
	}

	public String getServiceStatusInPopup(String panelname) {
		String status = null;
		appiumdriver.findElementByName(panelname).click();
		status = appiumdriver.findElementByAccessibilityId("MonitorDetailsCell_Status").
				findElements(MobileBy.className("XCUIElementTypeStaticText")).get(1).getAttribute("name");
		appiumdriver.findElementByAccessibilityId("Done icon").click();
		return status;
	}

	public String getServiceTechnicianValue() {
		return appiumdriver.findElementByAccessibilityId("MonitorDetailsCell_Technician").
				findElements(MobileBy.className("XCUIElementTypeStaticText")).get(1).getAttribute("name");
	}

	public void setCompletedServiceStatus() {
		clickServiceStatusCell();
		clickCompletedPhaseCell();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Order Monitor")));
	}

	public void setCompletedPhaseStatus() {
		clickPhaseStatusCell();
		clickCompletedPhaseCell();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Order Monitor")));
	}

	public void clickCompletedPhaseCell() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Completed")));
		completedcell.click();
	}

	public void clickPhaseStatusCell() {
		phasestatuscell.click();
	}

	public void clickServiceStatusCell() {
		servicestatuscell.click();
	}


	public void clickStartPhase() {
		startphasebtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 40);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Loading...")));
	}

	public RegularOrderMonitorScreen clickServiceDetailsDoneButton() {
		servicedetailsdonebtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 40);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Team Order Saving...")));
		return this;
	}

	public RegularOrderMonitorScreen clickServiceDetailsCancelButton() {
		appiumdriver.findElementByAccessibilityId("NavigationBarItemClose").click();
		return this;
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

	public String getServiceFinishDate(String servicedisplayname) {
		return monitorserviceslist.findElementByAccessibilityId(servicedisplayname).findElementByAccessibilityId("lblStartFinishDate").getAttribute("value");
	}

	public boolean isStartServiceDissapeared() {
		return !elementExists("Start Service");
	}

	public void clickStartService() {
		startservicebtn.click();
	}

	public String getServiceStartDate() {
		return appiumdriver.findElementByAccessibilityId("MonitorDetailsCell_StartDate")
				.findElements(MobileBy.className("XCUIElementTypeStaticText")).get(1).getAttribute("name");
	}

	public boolean isServiceStartDateExists() {
		return appiumdriver.findElementsByAccessibilityId("Start Date").size() > 0;
	}

	public boolean isServiceCompletedDateExists() {
		return appiumdriver.findElementsByAccessibilityId("Completed Date").size() > 0;
	}

	public boolean isServiceDurationExists() {
		return appiumdriver.findElementsByAccessibilityId("Duration").size() > 0;
	}


		public RegularTeamWorkOrdersScreen clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Back")));
		backbtn.click();
		return new RegularTeamWorkOrdersScreen();
	}

	public boolean isOrderPhaseExists(String phaseName) {
		return appiumdriver.findElementsByAccessibilityId(phaseName).size() > 0;
	}

	public boolean isRepairPhaseExists() {
		return appiumdriver.findElementsByAccessibilityId("Repair phase").size() > 0;
	}

	public void clicksRepairPhaseLine() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Repair phase"))).click();
	}

	public void clickStartPhaseButton() {
		monitorserviceslist.findElementByAccessibilityId("btnStartReset").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(monitorserviceslist));
	}

	public boolean isStartPhaseButtonExists() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(monitorserviceslist));
		return appiumdriver.
				findElementsByAccessibilityId("btnStartReset").size() > 0;
	}

	public boolean isStartOrderButtonExists() {
		waitOrderMonitorScreenLoaded();
		return appiumdriver.findElementsByAccessibilityId("Start").size() > 0;
	}

	public boolean isServicePresent(String serviceName) {
		return monitorserviceslist.findElementsByAccessibilityId(serviceName).size() > 0;
	}

	public void checkMyWorkCheckbox() {
		if (appiumdriver.findElementsByAccessibilityId("checkbox unchecked").size() > 0)
			appiumdriver.findElementByAccessibilityId("checkbox unchecked").click();
	}

	public void uncheckMyWorkCheckbox() {
		if (appiumdriver.findElementsByAccessibilityId("checkbox checked").size() > 0)
			appiumdriver.findElementByAccessibilityId("checkbox checked").click();
	}

	public void clickTech() {
		appiumdriver.findElementByAccessibilityId("Tech").click();
	}

	public void clickStartOrderButton() {
		waitOrderMonitorScreenLoaded();
		appiumdriver.findElementByAccessibilityId("Start").click();
	}

	public void openOrderPhasesList() {
	    waitOrderMonitorScreenLoaded();
		monitorserviceslist.findElementByAccessibilityId("lblPhaseName").click();
	}

	public void selectOrderPhaseStatus(OrderMonitorStatuses orderPhaseStatus) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(orderPhaseStatus.getValue())));
		appiumdriver.findElement(MobileBy.AccessibilityId(orderPhaseStatus.getValue())).click();
	}

	public void selectServiceStatus(OrderMonitorServiceStatuses orderMonitorServiceStatus) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(orderMonitorServiceStatus.getValue())));
		appiumdriver.findElement(MobileBy.AccessibilityId(orderMonitorServiceStatus.getValue())).click();
	}

	public String getOrderMonitorPhaseStatusValue() {
		waitOrderMonitorScreenLoaded();
		return monitorserviceslist.findElementByAccessibilityId("lblPhaseStatus").getAttribute("value");
	}

	public String getOrderMonitorPhaseStatusValue(String phaseName) {
		waitOrderMonitorScreenLoaded();
		return monitorserviceslist.findElementByAccessibilityId(phaseName).findElementByAccessibilityId("lblPhaseStatus").getAttribute("value");
	}

	public void changeStatusForWorkOrder(String newWOSTatus, String reason) {
		waitOrderMonitorScreenLoaded();
		MobileElement toolbar = (MobileElement) appiumdriver.findElementByAccessibilityId("Toolbar");
		toolbar.findElementsByClassName("XCUIElementTypeButton").get(2).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		IOSElement pickerwhl = (IOSElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.className("XCUIElementTypePickerWheel")));
		pickerwhl.setValue(newWOSTatus);
		MobileElement pickerToolbar = (MobileElement) appiumdriver.findElementByAccessibilityId("StringPickerVC_Status");
		IOSElement donebtn = (IOSElement) pickerToolbar.findElementByAccessibilityId("Done");

		new TouchAction(appiumdriver).tap(tapOptions().withElement(element(donebtn))).perform();

		appiumdriver.findElementByAccessibilityId(reason).click();
		int i = 2;
	}

	public String getMonitorServiceAmauntValue(ServiceData serviceData) {
		String serviceName = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null) {
			serviceName = serviceName + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		}
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("lblServiceAmount")));
		return monitorserviceslist
				.findElement(MobileBy.AccessibilityId(serviceName))
				.findElement(MobileBy.AccessibilityId("lblServiceAmount")).getAttribute("label").replaceAll("\n", " ");
	}

	public String getServiceDetailsPriceValue() {
		return appiumdriver.findElementByAccessibilityId("MonitorDetailsCell_Amount")
				.findElement(MobileBy.AccessibilityId("AmountTextControl")).getAttribute("value");
	}

	public void clickStartPhaseCheckOutButton() {
		monitorserviceslist.findElementByIosNsPredicate("name = 'Start phase' and type = 'XCUIElementTypeCell'").
				findElementByAccessibilityId("btnCheckInOut").click();
	}

	public void changeStatusForStartPhase(OrderMonitorPhases orderMonitorPhase) {

		monitorserviceslist.findElementByIosNsPredicate("name = 'Start phase' and type = 'XCUIElementTypeCell'").click();

		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(orderMonitorPhase.getrderMonitorPhaseName()))).click();
	}

	public void clickOrderStartDateButton() {
		LocalDate date = LocalDate.now();
		appiumdriver.findElementByAccessibilityId(date.format(DateTimeFormatter.ofPattern("M/d/uu"))).click();
	}

	public void setOrderStartYearValue(int year) {
		((MobileElement) appiumdriver.findElementsByClassName("XCUIElementTypePickerWheel").get(2)).setValue(Integer.toString(year));
	}

	public String getOrderStartYearValue() {
		return ((MobileElement) appiumdriver.findElementsByClassName("XCUIElementTypePickerWheel").get(2)).getAttribute("value");
	}

	public boolean isDurationLabelPresentForService(ServiceData serviceData) {
		return monitorserviceslist.findElementByAccessibilityId(serviceData.getServiceName()).findElementsByAccessibilityId("lblDuration").size() > 0;
	}

	public boolean isStartFinishDateLabelPresentForService(ServiceData serviceData) {
		return monitorserviceslist.findElementByAccessibilityId(serviceData.getServiceName()).findElementsByAccessibilityId("lblStartFinishDate").size() > 0;
	}

	public void closeSelectorderDatePicker() {
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
}
