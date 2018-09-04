package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServicesScreen extends BaseWizardScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	/*@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility  = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(accessibility  = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSFindBy(accessibility  = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSFindBy(accessibility  = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;*/
	
	public ServicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Available Services")));
        wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Available Services")));
	}
	
	public void clickSaveAsDraft() {
		clickSave();
		appiumdriver.findElementByAccessibilityId("Draft").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
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
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(service).size() > 0;
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.iOSNsPredicateString("name = '" + service + "' and type = 'XCUIElementTypeCell'")).size();
	}
	
	public boolean checkServiceIsSelectedWithServiceValues(String servicename, String vehiclepart, String servicepriceandquantity) {
		boolean selected = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		IOSElement servicecell = (IOSElement)  selectedservices.findElementByClassName("XCUIElementTypeTable").
				findElementByXPath("XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[@name='" + vehiclepart + "']/..");
		selected = servicecell.findElementByXPath("//XCUIElementTypeStaticText[3]").getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
				servicepriceandquantity.replaceAll(" ", ""));
		return selected;
	}
	
	public boolean checkServiceIsSelectedWithServiceValues(String servicename, String servicepriceandquantity) {
		boolean selected = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		List<MobileElement> serviceCells = selectedservices.findElementByClassName("XCUIElementTypeTable").
				findElementsByXPath("//XCUIElementTypeStaticText[@name='" + servicename + "']/..");
		for (MobileElement serviceCell : serviceCells) {
			if (serviceCell.findElementByXPath("//XCUIElementTypeStaticText[3]").getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
			servicepriceandquantity.replaceAll(" ", ""))) {
				selected = true;
				break;
			}
		}
		return selected;
	}

	public int getServiceSelectedNumber(String service) {
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".scrollViews()[0].elements()['SelectedServicesView'].tableViews()[0]cells()['" + service + "']")).size();
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

	public boolean isServiceTypeExists(String servicetype) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");
		return availableservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(servicetype).size() > 0;
	}
	
	public void searchServiceToSelect(String servicename) {
		IOSElement grouplist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/.."));
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		appiumdriver.hideKeyboard();
	}

	public void selectService(String servicename) {
		IOSElement tablelist = null;
		if (typeContext.equals(TypeScreenContext.SERVICEREQUEST)) {
			if (!(elementExists("AvailableGroupItemList"))) {
				tablelist = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");
			}
			else
				tablelist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='AvailableGroupItemList']/.."));
		} else
			tablelist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='AvailableGroupItemList']/.."));
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		if (tablelist.findElementsByAccessibilityId("Clear text").size() > 0) {
			tablelist.findElementByAccessibilityId("Clear text").click();

		}
        tablelist.findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.getKeyboard().sendKeys(servicename);
		appiumdriver.hideKeyboard();

		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeTable")).findElement(MobileBy.AccessibilityId(servicename)).click();
	}
	
	public void selectGroupServiceItem(String servicename) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableGroupItemList");
		
		availableservices.findElementByClassName("XCUIElementTypeTable") .findElementByAccessibilityId(servicename).click();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
	}
	
	public void selectPriceMatrix(String pricematrixname) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" +
				pricematrixname + "']")).click();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
	}
	
	public void selectServiceSubSrvice(String servicename, String servicesubsrvicename) {
		appiumdriver.findElementByAccessibilityId(servicename + " (" + servicesubsrvicename + ")").click();
	}
	
	public boolean isServiceWithSubSrviceSelected(String servicename, String servicesubsrvicename) {
		boolean found = false;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		List<MobileElement> services = selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(servicename);
		for (MobileElement serv : services)
			if (serv.findElementsByAccessibilityId(servicesubsrvicename).size() > 0) {
				found = true;
				break;
			}				
		return found;
		
	}

	public SelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
		IOSElement grouplist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/.."));
        appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        if (grouplist.findElementsByAccessibilityId("Clear text").size() > 0) {
			grouplist.findElementByAccessibilityId("Clear text").click();

        }
		grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
        appiumdriver.getKeyboard().sendKeys(servicename);
		appiumdriver.hideKeyboard();
		((IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList")).findElementByAccessibilityId(servicename).
				findElementByAccessibilityId("custom detail button").click();
		return new SelectedServiceDetailsScreen();
	}
	
	public SelectedServiceDetailsScreen openCustomBundleServiceDetails(String servicename) {
		if (!appiumdriver.findElementByAccessibilityId(servicename).isDisplayed()) {
			scrollToElement(servicename);
		}
		appiumdriver.findElement(MobileBy.AccessibilityId(servicename))
				.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		return new SelectedServiceDetailsScreen();
	}
	
	public SelectedServiceDetailsScreen selectBundleServiceDetails(String servicename) {
		appiumdriver.findElementByAccessibilityId(servicename).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(servicename))
				.findElement(MobileBy.AccessibilityId("unselected")).click();
		return new SelectedServiceDetailsScreen();
	}
	
	public void searchAvailableService(String servicename) {
		IOSElement grouplist = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/.."));
		IOSElement searchfld = (IOSElement) grouplist.findElement(MobileBy.className("XCUIElementTypeSearchField"));
		searchfld.clear();
		searchfld.setValue(servicename);
		appiumdriver.hideKeyboard();
	}
	
	public void searchSelectedService(String servicename) {
		appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void cancelSearchAvailableService() {
		if (appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElements(MobileBy.AccessibilityId("Cancel")).size() > 0)
			appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
	
	public void cancelSearchSelectedService() {
		if (appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElements(MobileBy.AccessibilityId("Cancel")).size() > 0)
			appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElement(MobileBy.AccessibilityId("Cancel")).click();
	}

	public PriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElementByAccessibilityId(servicepricematrices).click();
		return new PriceMatrixScreen();
	}

	public SelectedServiceDetailsScreen openServiceDetails(String service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
				+ service + "']"))).click();
		return new SelectedServiceDetailsScreen();
	}
	
	public void setSelectedServiceRequestServicesQuantity(String service, String _quantity) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServiceRequestServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\""
				+ service + "\"]/XCUIElementTypeTextField[1]"))).click();
		
		((IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServiceRequestServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\""
						+ service + "\"]/XCUIElementTypeTextField[1]")).setValue("");
		((IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServiceRequestServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\""
						+ service + "\"]/XCUIElementTypeTextField[1]")).setValue(_quantity);
		Helpers.keyboadrType("\n");
	}
	
	
	public void openServiceDetailsByIndex(String service, int dervicedetailindex) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[contains(@name, '" + service + "')]");
		if (selectedservices.size() > dervicedetailindex) {
			selectedservices.get(dervicedetailindex).click();
		}
	}

	public boolean priceMatricesPopupIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Price Matrices").isDisplayed();
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
		//appiumdriver.findElementByName("Delete " + service).click();
		appiumdriver.findElementByAccessibilityId("Delete").click();
	}
	
	public void clickNotesButton() {
		appiumdriver.findElementByAccessibilityId("Compose").click();
	}

	public static String getServicesScreenCaption() {
		return servicesscreencapt;
	}
	
	public boolean isServiceApproved(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				srvname + "_SelectedServiceIconSelected" + "']")).size() > 0;
	}
	
	public boolean isServiceDeclinedSkipped(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				srvname + "_SelectedServiceIconDeclined" + "']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedService(String servicename) {
		WebElement selectedservicestable = appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[contains(@name, '" + servicename + "')]/XCUIElementTypeImage[@name='ESTIMATION_NOTES']")).size() > 0;
	}
	
	public boolean isNotesIconPresentForSelectedWorkOrderService(String servicename) {
		WebElement selectedservicestable = appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservicestable.findElements(By.xpath("//XCUIElementTypeCell[contains(@name, '" + servicename + "')]/XCUIElementTypeImage[@name='ORDER_NOTES']")).size() > 0;
	}
	
	public void clickTechnicianToolbarIcon() {
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
