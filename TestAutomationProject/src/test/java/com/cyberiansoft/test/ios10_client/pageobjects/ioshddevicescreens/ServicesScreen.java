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
		Helpers.waitABit(2000);
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
		Helpers.waitABit(1000);
	}
	
	public void clickSaveAsDraft() {
		clickSaveButton();
		draftalertbtn.click();
		Helpers.waitABit(1000);
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}

	public void clickServiceTypesButton() {
		servicetypesbtn.click();
	}

	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView[1]/UIAElement[@name='SelectedServicesView']/UIATableView[1]/UIATableCell[@name='" + defaultServiceValue + "']").isDisplayed());
	}

	public void assertServiceIsSelected(String service) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']").isDisplayed());
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
				+ service + "']").size();
	}
	
	public void assertServiceIsSelectedWithServiceValues(String servicename, String servicepriceandquantity) {
		final String labelvalue = servicename + ", " + servicepriceandquantity;
		Assert.assertTrue(appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@label='"
								+ labelvalue + "']").isDisplayed());
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

	public void assertServiceTypeExists(String servicetype) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ servicetype + "\"]").isDisplayed());
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
		action.press(appiumdriver.findElementByAccessibilityId(servicename)).waitAction(1000).release().perform();
		//appiumdriver.findElementByName(service).click();
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
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.className("XCUIElementTypeSearchField")).sendKeys(servicename);
	}
	
	public void cancelSearchAvailableService() {
		appiumdriver.findElementByAccessibilityId("AvailableServiceList").findElement(MobileBy.AccessibilityId("Cancel")).click();
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
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='"
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
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//XCUIElementTypeOther[@name='SelectedServicesView']/XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']");
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
		Helpers.waitABit(500);
	}

	public boolean priceMatricesPopupIsDisplayed() {
		return pricematrixespopupname.isDisplayed();
	}

	public PriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView/UIATableCell[contains(@name, \""
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
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "']/XCUIElementTypeButton[@name='selected']")).size() > 0;
	}
	
	public boolean isServiceDeclinedSkipped(String srvname) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + 
				srvname + "']/XCUIElementTypeButton[@name='declined']")).size() > 0;
	}
}
