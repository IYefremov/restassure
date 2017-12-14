package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
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
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ServicesScreen extends iOSHDBaseScreen {

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
	
	public ServicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Available Services")));
	}
	

	/*public void clickSaveButton() {
		clickSaveButton();
	}*/
	
	public void saveWorkOrder() {
		clickSaveButton();
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		appiumdriver.findElementByAccessibilityId("Final").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
	public void clickSaveAsDraft() {
		clickSaveButton();
		appiumdriver.findElementByAccessibilityId("Draft").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		//Helpers.waitABit(1000);
	}

	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}

	public void clickServiceTypesButton() {
		appiumdriver.findElementByAccessibilityId("AvailableServicesSwitchButton").click();
	}

	public void assertDefaultServiceIsSelected() {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		Assert.assertTrue(selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(defaultServiceValue).size() > 0);
	}

	public void assertServiceIsSelected(String service) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		Assert.assertTrue(selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(service).size() > 0);
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.iOSNsPredicateString("name = '" + service + "' and type = 'XCUIElementTypeCell'")).size();
	}
	
	public void assertServiceIsSelectedWithServiceValues(String servicename, String servicepriceandquantity) {
		final String labelvalue = servicename + ", " + servicepriceandquantity;
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		Assert.assertTrue(selectedservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(labelvalue).size() > 0);
	}

	public int getServiceSelectedNumber(String service) {
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".scrollViews()[0].elements()['SelectedServicesView'].tableViews()[0]cells()['" + service + "']")).size();
	}

	public void assertTotalAmauntIsCorrect(String price) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("TotalAmount")));
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), price);
	}
	
	public void assertSubTotalAmauntIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElementByName("SubtotalAmount").getAttribute("value"), price);
	}

	public boolean isServiceTypeExists(String servicetype) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");
		return availableservices.findElementByClassName("XCUIElementTypeTable").findElementsByAccessibilityId(servicetype).size() > 0;
	}
	
	public void searchServiceToSelect(String servicename) {
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public void selectService(String servicename) {
		IOSElement availableservices = null;
		if (appiumdriver.findElementsByAccessibilityId("AvailableServiceList").size() > 0)
			availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");
		else 
			availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceRequestServicesView");

		
		TouchAction action = new TouchAction(appiumdriver);
		/*action.press(appiumdriver.findElementByAccessibilityId("AvailableServiceList")
				.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();*/
		action.press(availableservices.findElementByClassName("XCUIElementTypeTable") .findElementByAccessibilityId(servicename)).waitAction(Duration.ofSeconds(1)).release().perform();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
	}
	
	public void selectGroupServiceItem(String servicename) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableGroupItemList");
		
		TouchAction action = new TouchAction(appiumdriver);
		/*action.press(appiumdriver.findElementByAccessibilityId("AvailableServiceList")
				.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();*/
		action.press(availableservices.findElementByClassName("XCUIElementTypeTable") .findElementByAccessibilityId(servicename)).waitAction(Duration.ofSeconds(1)).release().perform();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
	}
	
	public void selectPriceMatrix(String pricematrixname) {
		TouchAction action = new TouchAction(appiumdriver);
		/*action.press(appiumdriver.findElementByAccessibilityId("AvailableServiceList")
				.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();*/
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + 
				pricematrixname + "']"))).waitAction(Duration.ofSeconds(1)).release().perform();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
	}
	
	public void selectServiceSubSrvice(String servicename, String servicesubsrvicename) {
		appiumdriver.findElementByAccessibilityId(servicename + " (" + servicesubsrvicename + ")").click();
	}
	
	public boolean isServiceWithSubSrviceSelected(String servicename, String servicesubsrvicename) {
		IOSElement selectedservices = (IOSElement) appiumdriver.findElementByAccessibilityId("SelectedServicesView");
		return selectedservices.findElementByClassName("XCUIElementTypeTable").findElementByAccessibilityId(servicename).findElementsByAccessibilityId(servicesubsrvicename).size() > 0;
	}

	public SelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
		IOSElement availableservices = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList");		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(availableservices.findElementByClassName("XCUIElementTypeTable").findElementByAccessibilityId(servicename).findElementByAccessibilityId("custom detail button")).waitAction(Duration.ofSeconds(1)).release().perform();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public SelectedServiceDetailsScreen openCustomBundleServiceDetails(String servicename) {
		if (!appiumdriver.findElementByAccessibilityId(servicename).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(servicename),
					appiumdriver.findElement(MobileBy.AccessibilityId("BundleItemsView")));
			appiumdriver.findElementByAccessibilityId(servicename).click();
		}
		//appiumdriver.findElementByAccessibilityId(servicename).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.AccessibilityId(servicename))
				.findElement(MobileBy.AccessibilityId("custom detail button"))).waitAction(Duration.ofSeconds(1)).release().perform();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public SelectedServiceDetailsScreen selectBundleServiceDetails(String servicename) {
		appiumdriver.findElementByAccessibilityId(servicename).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.AccessibilityId(servicename))
				.findElement(MobileBy.AccessibilityId("unselected"))).waitAction(Duration.ofSeconds(1)).release().perform();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void searchAvailableService(String servicename) {
		IOSElement searchfld = (IOSElement) appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")); 
		searchfld.click();
		searchfld.clear();
		searchfld.setValue(servicename);
		/*((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);*/
	}
	
	public void searchSelectedService(String servicename) {
		appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.findElementByAccessibilityId("SelectedServicesView").findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
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
		return new PriceMatrixScreen(appiumdriver);
	}

	public SelectedServiceDetailsScreen openServiceDetails(String service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
				+ service + "']"))).click();
		return new SelectedServiceDetailsScreen(appiumdriver);
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
	
	public void setSelectedServiceRequestServicePrice(String service, String price) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIAElement[@name='SelectedServiceRequestServicesView']/UIATableView[1]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]").click();
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIAElement[@name='SelectedServiceRequestServicesView']/UIATableView[1]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]").click();
		((IOSElement) appiumdriver.findElementByXPath("//UIAScrollView[1]/UIAElement[@name='SelectedServiceRequestServicesView']/UIATableView[1]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]")).setValue(price);
		Helpers.keyboadrType("\n");
	}

	public void cancelOrder() {
		clickCancelButton();
		acceptAlert();
	}

	public boolean priceMatricesPopupIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Price Matrices").isDisplayed();
	}

	public PriceMatrixScreen selectPriceMatrices(String pricematrice) {
		Helpers.waitABit(1500);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[contains(@name, \""
						+ pricematrice + "\")]").click();
		return new PriceMatrixScreen(appiumdriver);
	}
	
	public void removeSelectedServices(String service) {
		appiumdriver.findElementByName("Delete " + service).click();
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
