package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RegularServicesScreen extends RegularBaseServicesScreen {

	final static String servicesscreencapt = "Services";
	
	/*@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSXCUITFindBy(accessibility = "Delete")
    private IOSElement deletebtn;
	
	@iOSXCUITFindBy(accessibility = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSXCUITFindBy(accessibility = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSXCUITFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSXCUITFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartsbtn;
	
	@iOSXCUITFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;*/

	@iOSXCUITFindBy(accessibility = "AvailableServiceList")
	private IOSElement availableservicestbl;
	
	public RegularServicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitServicesScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("label = 'Services' and type = 'XCUIElementTypeButton'")));
	}

	public void clickCancelButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}

	public String getServicePriceValue(String servicename) {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[3]")).getAttribute("value").replaceAll("[^a-zA-Z0-9$.%]", "");
	}
	
	public String getTotalAmaunt() {
		String totalAmaunt = "";
		if (appiumdriver.findElementsByName("TotalAmount").size() > 0)
			totalAmaunt = appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
		else
			totalAmaunt = appiumdriver.findElementByAccessibilityId("AmountTotal").getAttribute("value");
		return totalAmaunt;
	}
	
	public String getSubTotalAmaunt() {
		String subtotalAmaunt = "";
		if (appiumdriver.findElementsByName("SubtotalAmount").size() > 0)
			subtotalAmaunt = appiumdriver.findElementByName("SubtotalAmount").getAttribute("value");
		else
			subtotalAmaunt = appiumdriver.findElementByName("AmountSubtotal").getAttribute("value");
		return subtotalAmaunt;
	}

	public boolean isServiceExists(String servicetype) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(servicetype)).size() > 0;
	}

	public void selectServicePanel(String servicePanel) {
		MobileElement panelTable = (MobileElement) appiumdriver.findElementByAccessibilityId("AvailableGroupItemList");
		if (!panelTable.findElement(MobileBy.AccessibilityId(servicePanel)).isDisplayed()) {
			swipeToElement(panelTable.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + servicePanel + "']/..")));
			if (!panelTable.findElement(MobileBy.AccessibilityId(servicePanel)).isDisplayed())
				swipeScreenUp();
		}
		panelTable.findElement(MobileBy.AccessibilityId(servicePanel)).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("ServiceGroupServicePartsTable"), 1));
	}
	
	public void selectService(String servicename) {
		waitServicesScreenLoaded();
		WebElement searchFild = appiumdriver.findElementByClassName("XCUIElementTypeSearchField");
		searchFild.clear();

		IOSElement servicecell = (IOSElement) availableservicestbl.
				findElement(MobileBy.AccessibilityId(servicename));
		if (!servicecell.isDisplayed()) {
			searchFild.sendKeys(servicename + "\n");
		}
		availableservicestbl.findElement(MobileBy.AccessibilityId(servicename)).click();
	}

	public void selectServices(List<ServiceData> servicesData) {
		WebElement searchFild = appiumdriver.findElement(MobileBy.className("XCUIElementTypeSearchField"));
		for (ServiceData serviceData : servicesData) {
			searchFild.clear();
			searchFild.sendKeys(serviceData.getServiceName() + "\n");
			availableservicestbl.findElement(MobileBy.AccessibilityId(serviceData.getServiceName())).click();
		}
	}
	
	public void selectServiceSubSrvice(String servicesubsrvicename) {
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(servicesubsrvicename)).click();
	}

	public void openCustomServiceDetails(String serviceName) {

		WebElement searchFild = appiumdriver.findElementByClassName("XCUIElementTypeSearchField");
		if (!(searchFild.getAttribute("value") == null))
			searchFild.clear();

		IOSElement servicecell = (IOSElement) availableservicestbl.
				findElement(MobileBy.AccessibilityId(serviceName));
		if (!servicecell.isDisplayed()) {
			searchFild.sendKeys(serviceName + "\n");
		}
		IOSElement el = (IOSElement) availableservicestbl.findElement(MobileBy.AccessibilityId(serviceName))
				.findElementByAccessibilityId("custom detail button");
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(PointOption.point(el.getLocation().getX()+2, el.getLocation().getY()+2)).perform();
	}

	public void selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByAccessibilityId(pricematrice).click();
	}

	public void clickVehiclePartsButton() {
		appiumdriver.findElement(MobileBy.name("Vehicle Parts")).click();
	}

	public void clickBackServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Service Types")));
		appiumdriver.findElement(MobileBy.name("Service Types")).click();
	}

	public String getListOfSelectedVehicleParts() {
		return appiumdriver.findElementByAccessibilityId("ClarificationBox_VehiclePartSelector")
				.findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
	}
	
	public void clickTechnicianToolbarIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("technician")));
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("technician")).click();
	}
	
	public void changeTechnician(String servicetype, String techname) {
		appiumdriver.findElementByAccessibilityId(servicetype).click();
		appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView").
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Save']").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Service Types']/XCUIElementTypeButton[@name='Save']").click();
	}

}
