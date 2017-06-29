package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ServicesScreen extends iOSHDBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	@iOSFindBy(accessibility  = "Save")
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
    private IOSElement draftalertbtn;
	
	public ServicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	

	public void clickSaveButton() {
		savebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(500);
	}
	
	public void saveWorkOrder() {
		savebtn.click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(500);
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
	}
	
	public void clickSaveAsDraft() {
		clickSaveButton();
		draftalertbtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		//Helpers.waitABit(1000);
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}

	public void clickServiceTypesButton() {
		servicetypesbtn.click();
	}

	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + defaultServiceValue + "']").isDisplayed());
	}

	public void assertServiceIsSelected(String service) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + service + "']").isDisplayed());
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
				+ service + "']").size();
	}
	
	public void assertServiceIsSelectedWithServiceValues(String servicename, String servicepriceandquantity) {
		final String labelvalue = servicename + ", " + servicepriceandquantity;
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeButton[contains(@label, '"
								+ labelvalue + "')]").isDisplayed());
		//Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
		//		+ service + "']").getAttribute("label").contains(servicepriceandquantity));
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
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeOther[@name='AvailableServiceList']/XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				servicetype + "']")).size() > 0;
	}
	
	public void searchServiceToSelect(String servicename) {
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public void selectService(String servicename) {
		TouchAction action = new TouchAction(appiumdriver);
		/*action.press(appiumdriver.findElementByAccessibilityId("AvailableServiceList")
				.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();*/
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
		Helpers.waitABit(1500);
	}
	
	public void selectPriceMatrix(String pricematrixname) {
		TouchAction action = new TouchAction(appiumdriver);
		/*action.press(appiumdriver.findElementByAccessibilityId("AvailableServiceList")
				.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
						servicename + "']"))).waitAction(1000).release().perform();*/
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + 
				pricematrixname + "']"))).waitAction(1000).release().perform();
		//action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
		Helpers.waitABit(1500);
	}
	
	public void selectServiceSubSrvice(String servicename, String servicesubsrvicename) {
		appiumdriver.findElementByAccessibilityId(servicename + " (" + servicesubsrvicename + ")").click();
	}
	
	public boolean isServiceWithSubSrviceSelected(String servicename, String servicesubsrvicename) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[@name='" + servicesubsrvicename + "']")).size() > 0;
	}

	public SelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther[@name='AvailableServiceList']/XCUIElementTypeTable/XCUIElementTypeCell[@name='" + 
				servicename + "']/XCUIElementTypeButton[@name='custom detail button']"))).waitAction(1000).release().perform();
		//appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther[@name='AvailableServiceList']/XCUIElementTypeTable/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']")).click();
		/*appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ service
						+ "\"]/UIAButton[@name=\"custom detail button\"]").click();*/
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public SelectedServiceDetailsScreen openCustomBundleServiceDetails(String servicename) {
		appiumdriver.findElementByAccessibilityId(servicename).click();
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + 
				servicename + "']/XCUIElementTypeButton[@name='custom detail button']"))).waitAction(1000).release().perform();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public SelectedServiceDetailsScreen selectBundleServiceDetails(String servicename) {
		appiumdriver.findElementByAccessibilityId(servicename).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + 
				servicename + "']/XCUIElementTypeButton[@name='unselected']"))).waitAction(1000).release().perform();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void searchAvailableService(String servicename) {
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).click();
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(servicename);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
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
		//appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
		//				+ servicepricematrices + "\"]").click();
		Helpers.waitABit(2000);
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
		Helpers.waitABit(1500);
	}

	public boolean priceMatricesPopupIsDisplayed() {
		return pricematrixespopupname.isDisplayed();
	}

	public PriceMatrixScreen selectPriceMatrices(String pricematrice) {
		Helpers.waitABit(1500);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[contains(@name, \""
						+ pricematrice + "\")]").click();
		return new PriceMatrixScreen(appiumdriver);
	}
	
	public void removeSelectedServices(String service) {
		appiumdriver.findElementByName("Delete " + service).click();
		deletebtn.click();
	}
	
	public void clickNotesButton() {
		composebtn.click();
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
		if (appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").size() > 0) {
			((IOSElement) appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView").get(1)).
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		} else {
		appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView").
			findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='"
				+ techname + "']/XCUIElementTypeButton[@name='unselected']")).click();
		}
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Service Types']/XCUIElementTypeButton[@name='Save']").click();
	}
}
