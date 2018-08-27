package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularServicesScreen extends RegularBaseServicesScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	/*@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(accessibility = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSFindBy(accessibility = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartsbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;*/

	@iOSFindBy(accessibility = "AvailableServiceList")
	private IOSElement availableservicestbl;
	
	public RegularServicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Services")));
		appiumdriver.findElementByAccessibilityId("Available").click();
	}

	public void clickCancelButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}

	public boolean isDefaultServiceIsSelected() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
						+ defaultServiceValue + "']/XCUIElementTypeButton[@name='selected']").isDisplayed();
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

	public boolean isServiceTypeExists(String servicetype) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(servicetype)).size() > 0;
	}

	public void clearSearchServiceParameters() {
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
	}

	public void searchServiceByName(String servicename) {
		clearSearchServiceParameters();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").click();
		appiumdriver.getKeyboard().sendKeys(servicename + "\n");

	}

	public void selectServicePanel(String servicePanel) {
		MobileElement panelTable = (MobileElement) appiumdriver.findElementByAccessibilityId("AvailableGroupItemList");
		if (!panelTable.findElement(MobileBy.AccessibilityId(servicePanel)).isDisplayed())
			swipeToElement(panelTable.
					findElement(By.xpath("//XCUIElementTypeCell[@name='" + servicePanel + "']/XCUIElementTypeStaticText[@name='" + servicePanel + "']/..")));
		panelTable.findElement(MobileBy.AccessibilityId(servicePanel)).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("ServiceGroupServicePartsTable"), 1));
	}
	
	public void selectService(String servicename) {
		if (elementExists("Clear text"))
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();


		IOSElement servicecell = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList").
				findElement(MobileBy.AccessibilityId(servicename));
		if (!servicecell.isDisplayed()) {
			if (appiumdriver.findElementsByClassName("XCUIElementTypeSearchField").size() > 0)
				searchServiceByName(servicename);
		}

		if (appiumdriver.findElementByAccessibilityId("AvailableServiceList").
				findElement(MobileBy.AccessibilityId(servicename)).findElements(MobileBy.AccessibilityId("unselected")).size() > 0)
			appiumdriver.findElementByAccessibilityId("AvailableServiceList").
				findElement(MobileBy.AccessibilityId(servicename)).findElement(MobileBy.AccessibilityId("unselected")).click();
		else

			appiumdriver.findElementByAccessibilityId("AvailableServiceList").
					findElement(MobileBy.AccessibilityId(servicename)).findElement(MobileBy.className("XCUIElementTypeImage")).click();
	}
	
	public void selectServiceSubSrvice(String servicesubsrvicename) {
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(servicesubsrvicename)).click();
	}

	public void selectSubService(String servicename) {
		if (elementExists(MobileBy.AccessibilityId("Clear text")))
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));
        IOSElement servicecell = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList").
                findElement(MobileBy.AccessibilityId(servicename));
        if (!servicecell.isDisplayed()) {
            if (appiumdriver.findElementsByClassName("XCUIElementTypeSearchField").size() > 0)
                searchServiceByName(servicename);
        }
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").
				findElement(MobileBy.AccessibilityId(servicename)).click();
	}
	
	public WebElement getServiceTableCell(String servicename) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicename + "']/.."));
	}

	public RegularSelectedServiceDetailsScreen  openBundleServiceDetails(String servicename) {

		IOSElement el = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"))
				.findElement(MobileBy.AccessibilityId(servicename)).findElement(MobileBy.AccessibilityId("custom detail button"));
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(PointOption.point(el.getLocation().getX()+2, el.getLocation().getY()+2)).perform();

		return new RegularSelectedServiceDetailsScreen();
	}

	public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {

		if (elementExists("Clear text"))
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		if (elementExists(MobileBy.className("XCUIElementTypeSearchField")))
		    if (!availableservicestbl.findElementByAccessibilityId(servicename ).isDisplayed())
				searchServiceByName(servicename);
		IOSElement el = (IOSElement) availableservicestbl.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='custom detail button']"));
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(PointOption.point(el.getLocation().getX()+2, el.getLocation().getY()+2)).perform();

		return new RegularSelectedServiceDetailsScreen();
	}
	
	public RegularSelectedServiceDetailsScreen clickServiceCustomDetailButton(String service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(service)));
		availableservicestbl.findElementByAccessibilityId(service).findElementByAccessibilityId("custom detail button").click();
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']");
		return new RegularSelectedServiceDetailsScreen();
	}

	public RegularSelectedServiceDetailsScreen openGroupServiceDetails(String serviceName) {
		appiumdriver.findElementByAccessibilityId("ServiceGroupServicesTable").findElement(MobileBy.AccessibilityId(serviceName))
				.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		return new RegularSelectedServiceDetailsScreen();
	}
	
	public void clickToolButton() {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("services")));
		//appiumdriver.findElementByAccessibilityId("services").click();
	}
	
	public void openCustomServiceDetailsByPartOfServiceName(String service) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[2]/UIATableCell[4]").click();
	}


	public RegularPriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicepricematrices + "']")).click();
		return new RegularPriceMatrixScreen();
	}
	
	public void setSelectedServiceRequestServicesQuantity(String servicename, String _quantity) throws InterruptedException {
		WebElement par = getServiceTableCell(servicename);
		par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity + "\n");
	}
	
	public void setSelectedServiceRequestServicePrice(String servicename, String price) {
		//Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").clear();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[2]").sendKeys(price + "\n");
	}
	
	public boolean priceMatricesPopupIsDisplayed() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("Price Matrices")).isDisplayed();
	}

	public RegularPriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByAccessibilityId(pricematrice).click();
		return new RegularPriceMatrixScreen();
	}
	
	public void clickNotesButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Compose")).click();
	}
	
	public void clickVehiclePartsButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Vehicle Part")).click();
	}

	public static String getServicesScreenCaption() {
		return servicesscreencapt;
	}
	
	public RegularServicesScreen clickBackServicesButton() {
		BaseUtils.waitABit(500);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Service Types")));
		appiumdriver.findElement(MobileBy.name("Service Types")).click();
		BaseUtils.waitABit(500);
		return this;
	}
	
	public void clickAddServicesButton() {
		/*WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Add")));
		IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
		navbar.findElement(MobileBy.AccessibilityId("Add")).click();
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ServiceGroupServicesTable")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("ServiceGroupServicesTable")));
		return this;*/
	}

	
	public String getListOfSelectedVehicleParts() {
		WebElement par = getServiceTableCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public boolean isServiceWithVehiclePartExists(String srvname, String srvvehiclepart) {
		return Helpers.elementExists(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "]/XCUIElementTypeStaticText[@name='" + srvvehiclepart + "']"));
	}
	
	public void clickTechnicianToolbarIcon() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("technician")).click();
	}
	
	public void changeTechnician(String servicetype, String techname) {
		appiumdriver.findElementByAccessibilityId(servicetype).click();
		/*if (appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").size() > 0) {
			((IOSElement) appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").get(1)).
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		} else {*/
		appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView").
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		//}
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Save']").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Service Types']/XCUIElementTypeButton[@name='Save']").click();
	}

}
