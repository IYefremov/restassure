package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ServicesScreen extends BaseWizardScreen {

	private final static String defaultServiceValue = "Test Tax";
	
	/*@iOSXCUITFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSXCUITFindBy(accessibility  = "Delete")
    private IOSElement deletebtn;
	
	@iOSXCUITFindBy(accessibility  = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSXCUITFindBy(accessibility  = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSXCUITFindBy(accessibility  = "Compose")
    private IOSElement composebtn;
	
	@iOSXCUITFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;
	
	@iOSXCUITFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;*/

	@iOSXCUITFindBy(accessibility  = "SelectedServicesView")
	private IOSElement selectedServicesView;
	
	public ServicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitServicesScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Available Services")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Available Services")));
	}
	
	public void clickSaveAsDraft() {
		clickSave();
		appiumdriver.findElementByAccessibilityId("Draft").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}

	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}

	public void clickServiceTypesButton() {
		appiumdriver.findElementByAccessibilityId("AvailableServicesSwitchButton").click();
	}

	public boolean checkDefaultServiceIsSelected() {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(defaultServiceValue).size() > 0;
	}

	public boolean checkServiceIsSelected(String service) {
		waitServicesScreenLoaded();
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(service).size() > 0;
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.iOSNsPredicateString("name = '" + service + "' and type = 'XCUIElementTypeCell'")).size();
	}
	
	public boolean checkServiceIsSelectedWithServiceValues(String serviceName, String vehiclepart, String servicepriceandquantity) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		IOSElement servicecell = (IOSElement)  selectedservices.findElementByClassName("XCUIElementTypeTable").
				findElementByXPath("XCUIElementTypeCell[@name='" + serviceName + "']/XCUIElementTypeStaticText[@name='" + vehiclepart + "']/..");
		return servicecell.findElementByXPath("//XCUIElementTypeStaticText[3]").getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
				servicepriceandquantity.replaceAll(" ", ""));
	}
	
	public boolean checkServiceIsSelectedWithServiceValues(String serviceName, String servicepriceandquantity) {
		boolean selected = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeOther' and name CONTAINS 'SelectedService'"));
		List<MobileElement> serviceCells = selectedservices.findElementByClassName("XCUIElementTypeTable").
				findElementsByAccessibilityId(serviceName);
		for (MobileElement serviceCell : serviceCells) {
			if (serviceCell.findElementByXPath("//XCUIElementTypeStaticText[3]").getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
			servicepriceandquantity.replaceAll(" ", ""))) {
				selected = true;
				break;
			}
		}
		return selected;
	}

	public boolean checkServiceIsSelectedWithServiceValues(ServiceData serviceData) {
		boolean selected = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		List<MobileElement> serviceCells = selectedservices.findElementByClassName("XCUIElementTypeTable").
				findElementsByXPath("//XCUIElementTypeStaticText[@name='" + serviceData.getServiceName() + "']/..");
		String priceValue = PricesCalculations.getPriceRepresentation(serviceData.getServicePrice()) + " x " + serviceData.getServiceQuantity();
		for (MobileElement serviceCell : serviceCells) {
			if (serviceCell.findElementByXPath("//XCUIElementTypeStaticText[3]").getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
					priceValue.replaceAll(" ", ""))) {
				selected = true;
				break;
			}
		}
		return selected;
	}

	public String getTotalAmaunt() {
		//appiumdriver.hideKeyboard();
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("TotalAmount")));
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public String getSubTotalAmaunt() {
		return appiumdriver.findElementByName("SubtotalAmount").getAttribute("value");
	}

	public boolean isServiceExists(String servicetype) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");
		return availableservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(servicetype).size() > 0;
	}
	
	public void searchServiceToSelect(String serviceName) {
		IOSElement grouplist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/.."));
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).setValue(serviceName);
		appiumdriver.hideKeyboard();
	}

	public void selectService(String serviceName) {
		waitServicesScreenLoaded();
		MobileElement searchFld =  null;
		String tablelistName = "";
		if (appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").getAttribute("name").equals("ServiceRequestServicesForm"))
			tablelistName = "AvailableServicesView";
		else
			tablelistName = "AvailableServiceList";
		IOSElement tablelist = (IOSElement) appiumdriver.findElementByAccessibilityId(tablelistName);

		if (tablelist.getAttribute("type").equals("XCUIElementTypeOther"))
			searchFld = ((MobileElement) tablelist.findElementByClassName("XCUIElementTypeSearchField"));
		else
			searchFld = ((MobileElement) appiumdriver.findElementsByClassName("XCUIElementTypeSearchField").get(1));
		searchFld.click();
		searchFld.clear();
		searchFld.setValue(serviceName);
		appiumdriver.hideKeyboard();
		appiumdriver.findElementByAccessibilityId(tablelistName).findElement(MobileBy.className("XCUIElementTypeTable")).findElement(MobileBy.AccessibilityId(serviceName)).click();
	}

	public SelectedServiceBundleScreen openSelectBundleServiceDetails(String serviceName) {
		waitServicesScreenLoaded();
		IOSElement tablelist = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		tablelist.findElement(MobileBy.AccessibilityId(serviceName)).click();
		return new SelectedServiceBundleScreen();
	}

	public void clickOnSelectService(String serviceName) {
		IOSElement tablelist = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		tablelist.findElement(MobileBy.AccessibilityId(serviceName)).click();
	}
	
	public void selectGroupServiceItem(String serviceName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("AvailableGroupItemList")));
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableGroupItemList");
		availableservices.findElementByClassName("XCUIElementTypeTable") .findElementByAccessibilityId(serviceName).click();
	}
	
	public void selectPriceMatrix(String pricematrixname) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" +
				pricematrixname + "']")).click();
	}
	
	public void selectServiceSubSrvice(String serviceName, String servicesubsrvicename) {
		appiumdriver.findElementByAccessibilityId(serviceName + " (" + servicesubsrvicename + ")").click();
	}
	
	public boolean isServiceWithSubSrviceSelected(String serviceName, String servicesubsrvicename) {
		boolean found = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		List<MobileElement> services = selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(serviceName);
		for (MobileElement serv : services)
			if (serv.findElementsByAccessibilityId(servicesubsrvicename).size() > 0) {
				found = true;
				break;
			}				
		return found;
		
	}

	public SelectedServiceDetailsScreen openCustomServiceDetails(String serviceName) {

		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("AvailableServiceList")));
		WebElement searchFld = (WebElement) appiumdriver.findElements(MobileBy.className("XCUIElementTypeSearchField")).get(1);
		searchFld.findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		searchFld.findElement(MobileBy.className("XCUIElementTypeSearchField")).sendKeys(serviceName + "\n");
		//appiumdriver.hideKeyboard();
		((IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList")).findElementByAccessibilityId(serviceName).
				findElementByAccessibilityId("custom detail button").click();
		return new SelectedServiceDetailsScreen();
	}

	public SelectedServiceDetailsScreen openCustomBundleServiceDetails(String serviceName) {
		if (!appiumdriver.findElementByClassName("XCUIElementTypePopover").
				findElement(MobileBy.AccessibilityId(serviceName)).isDisplayed()) {
			//scrollToElement(serviceName);
			SwipeUtils.swipeToElement(appiumdriver.findElementByClassName("XCUIElementTypePopover").
					findElement(MobileBy.AccessibilityId(serviceName)));
			appiumdriver.findElementByClassName("XCUIElementTypePopover").
					findElement(MobileBy.AccessibilityId(serviceName))
					.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		}
		appiumdriver.findElementByClassName("XCUIElementTypePopover").
				findElement(MobileBy.AccessibilityId(serviceName))
				.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		return new SelectedServiceDetailsScreen();
	}
	
	public void cancelSearchAvailableService() {
		if (appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElements(MobileBy.AccessibilityId("Cancel")).size() > 0)
			appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.AccessibilityId("Cancel")).click();
	}

	public PriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElementByAccessibilityId(servicepricematrices).click();
		return new PriceMatrixScreen();
	}

	public SelectedServiceDetailsScreen openServiceDetails(String serviceName) {
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeOther' and name CONTAINS 'SelectedService'")).findElement(MobileBy.AccessibilityId(serviceName)).click();
		return new SelectedServiceDetailsScreen();
	}

	public void openServiceDetailsByIndex(String service, int dervicedetailindex) {
		List<WebElement> selectedServices = appiumdriver.findElementsByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[contains(@name, '" + service + "')]");
		if (selectedServices.size() > dervicedetailindex) {
			selectedServices.get(dervicedetailindex).click();
		}
	}

	public void openServiceDetails(String serviceName, VehiclePartData vehiclePartData) {
		selectedServicesView.findElementByXPath("//XCUIElementTypeCell[@name='" + serviceName +
				"']/XCUIElementTypeStaticText[@name='" + vehiclePartData.getVehiclePartName() +"']").click();
	}

	public PriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[contains(@name, \""
						+ pricematrice + "\")]").click();
		return new PriceMatrixScreen();
	}
	
	public void removeSelectedServices(String serviceName) {
		IOSElement selectedServices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		IOSElement serviceCell = (IOSElement) selectedServices.findElementByAccessibilityId(serviceName);
		serviceCell.findElementByIosNsPredicate("name CONTAINS 'Delete'").click();
		appiumdriver.findElementByAccessibilityId("Delete").click();
	}
	
	public void clickNotesButton() {
		appiumdriver.findElementByAccessibilityId("Compose").click();
	}
	
	public boolean isServiceApproved(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				srvname + "_SelectedServiceIconSelected" + "']")).size() > 0;
	}
	
	public boolean isServiceDeclinedSkipped(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				srvname + "_SelectedServiceIconDeclined" + "']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedService(String serviceName) {
		WebElement selectedservicestable = appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[contains(@name, '" + serviceName + "')]/XCUIElementTypeImage[@name='ESTIMATION_NOTES']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedWorkOrderService(String serviceName) {
		WebElement selectedservicestable = appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[contains(@name, '" + serviceName + "')]/XCUIElementTypeImage[@name='ORDER_NOTES']")).size() > 0;
	}
	
	public void clickTechnicianToolbarIcon() {
		waitServicesScreenLoaded();
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("technician")).click();
	}
	
	public void changeTechnician(String servicetype, String techname) {
		appiumdriver.findElementByAccessibilityId(servicetype).click();
		Helpers.waitABit(500);
		
		IOSElement techsplittable =  null;
		if (appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").size() > 1) {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		} else {
			techsplittable = (IOSElement) appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView");
		}
		
		techsplittable.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Service Types']/XCUIElementTypeButton[@name='Save']").click();
	}
}
