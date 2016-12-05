package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.HideKeyboardStrategy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularServicesScreen extends iOSRegularBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	@iOSFindBy(accessibility = "Save")
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
    private IOSElement finalalertbtn;
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	public RegularServicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}

	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[1]/UIATableCell[@name=\""
						+ defaultServiceValue + "\"]/UIAButton[@name=\"selected\"]").isDisplayed());
	}

	public void assertServiceIsSelected(String servicename) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));
		
		Assert.assertTrue(appiumdriver.findElements(By.xpath(".//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size() > 0);
	}
	
	public int getNumberOfServiceSelectedItems(String servicename) {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size();
	}
	
	public int getNumberOfSelectedServices() {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeButton[@name='selected']")).size();
	}
	
	
	public void assertServiceIsSelectedAndVisible(String service, String pricevalue) throws InterruptedException {
		Helpers.scroolTo(service);
		
		Assert.assertTrue(appiumdriver.findElement(By.name(service)).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(By.name("selected")).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(By.name(pricevalue)).isDisplayed());
	}
	
	public void assertServiceIsSelectedWithServiceValues(String servicename, String pricevalue) {
		Assert.assertTrue(appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='selected']")).size() > 0);
		Assert.assertTrue(appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[@name='" + pricevalue + "']")).size() > 0);
		//WebElement par = getServiceTableCell(servicename);		
		//par.findElement(MobileBy.xpath(".//XCUIElementTypeButton[@name='custom detail button']")).click();
		//Assert.assertTrue(par.findElements(MobileBy.AccessibilityId("selected")).size() >0);
		//Assert.assertTrue(par.findElements(MobileBy.AccessibilityId(pricevalue)).size() >0);
	}

	public int getServiceSelectedNumber(String servicename) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(servicename)).size();
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
		Assert.assertTrue(appiumdriver.findElements(MobileBy.AccessibilityId(servicetype)).size() > 0);
	}

	public void selectService(String servicename) {
		appiumdriver.findElementByAccessibilityId(servicename).click();
		//appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
		//	+ service + "\"]").click();
	}
	
	public void selectSubService(String servicename) {
		Helpers.waitABit(300);
		appiumdriver.findElementByAccessibilityId(servicename).click();
		//appiumdriver.findElementByAccessibilityId("Search").sendKeys(servicename);
		Helpers.waitABit(500);
		
		if (appiumdriver.findElementsByAccessibilityId("Save").size() > 0) {
			//appiumdriver.findElementByAccessibilityId("Save").click();
		}
		else {
			new TouchAction(appiumdriver).tap(appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeButton[@name='unselected']")).perform();
			//WebElement par = getServiceTableCell(servicename);
			//new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("unselected"))).perform() ;
		}
		//appiumdriver.findElementByAccessibilityId("Cancel").click();
		//par.findElement(MobileBy.xpath(".//XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public WebElement getServiceTableCell(String servicename) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicename + "']/.."));
	}

	public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
		Helpers.waitABit(1000);
		appiumdriver.findElementByAccessibilityId(servicename).click();
		if (appiumdriver.findElementsByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeOther/XCUIElementTypeStaticText[@value='" + servicename + "']").size() > 0) {
			//appiumdriver.findElementByAccessibilityId("Save").click();
		}
		else {
			WebElement par = getServiceTableCell(servicename);		
			new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("custom detail button"))).perform() ;
		}
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public RegularSelectedServiceDetailsScreen clickServiceCustomDetailButton(String service) {
		//appiumdriver.findElementByXPath("//UIAScrollView[2]/UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']").click();
		
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='ServiceGroupServicesTable']/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']").click();
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']");
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public RegularSelectedServiceDetailsScreen openSelectedServiceDetails(String service) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']");
		if (selectedservices.size() > 1) {			
			Helpers.waitABit(300);
			((WebElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + service + "']/XCUIElementTypeButton[@name='custom detail button']").get(1)).click();
		} else {
			selectedservices.get(0).click();
		}
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void clickToolButton() {
		appiumdriver.findElementByAccessibilityId("services").click();
	}
	
	public void openCustomServiceDetailsByPartOfServiceName(String service) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[2]/UIATableCell[4]").click();
	}


	public RegularPriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicepricematrices + "']")).click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void setSelectedServiceRequestServicesQuantity(String servicename, String _quantity) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		Helpers.waitABit(500);
		WebElement par = getServiceTableCell(servicename);
		par.findElement(MobileBy.xpath(".//XCUIElementTypeTextField[1]")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity + "\n");
		//par.findElement(MobileBy.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_quantity + "\n");
		/*wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
				+ service + "\"]/UIATextField[1]"))).click();
		
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue("");
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue(_quantity);
		Helpers.keyboadrType("\n");*/
	}
	
	public void searchServiceByName(String service) {
		appiumdriver.findElementByAccessibilityId("Search").click();
		appiumdriver.findElementByAccessibilityId("Search").sendKeys(service);
		Helpers.waitABit(500);
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='ServiceSelectorTable']/XCUIElementTypeCell[@name='" + service + "']").click();
	}
	
	public void openServiceDetailsByIndex(String service, int servicedetailindex) {
		
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeButton[@name='custom detail button']");
		//Helpers.scroolToElement(selectedservices.get(servicedetailindex));
		//Helpers.waitABit(2000);
		
		((WebElement) appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeButton[@name='custom detail button']").get(servicedetailindex)).click();
	}
	
	public void setSelectedServiceRequestServicePrice(String servicename, String price) {
		Helpers.waitABit(500);
		WebElement par = getServiceTableCell(servicename);
		par.findElement(MobileBy.xpath(".//XCUIElementTypeTextField[1]")).clear();
		par.findElement(MobileBy.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(price + "\n");
		
		/*appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]").click();
		appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]").click();
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]")).setValue(price);
		Helpers.keyboadrType("\n");*/
	}
	
	public boolean priceMatricesPopupIsDisplayed() {
		return pricematrixespopupname.isDisplayed();
	}

	public RegularPriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ pricematrice + "\")]").click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void removeSelectedServices(String servicename) {
		WebElement par = getServiceTableCell(servicename);		
		//par.findElement(MobileBy.xpath(".//XCUIElementTypeButton[@name='custom detail button']")).click();
		new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("selected"))).perform() ;
		par = getServiceTableCell(servicename);	
		Assert.assertTrue(par.findElements(MobileBy.AccessibilityId("unselected")).size() > 0);
	}
	
	public void clickNotesButton() {
		composebtn.click();
	}
	
	public void clickVehiclePartsButton() {
		vehiclepartsbtn.click();
	}

	public static String getServicesScreenCaption() {
		return servicesscreencapt;
	}
	
	public void clickBackServicesButton() {	
		appiumdriver.findElement(MobileBy.name("Back")).click();
	}
	
	public void clickAddServicesButton() {	
		appiumdriver.findElement(MobileBy.AccessibilityId("Add")).click();
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
	}
	
	public void clickSaveAsDraft() {
		clickSaveButton();
		draftalertbtn.click();
	}
	
	public String getListOfSelectedVehicleParts() {
		WebElement par = getServiceTableCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public boolean isServiceWithVehiclePartExists(String srvname, String srvvehiclepart) {
		return Helpers.elementExists(By.xpath("//UIAScrollView[2]/UIATableView[1]/UIATableCell[@name='" + 
				srvname + "]/UIAStaticText[@name='" + srvvehiclepart + "']"));
	}

}
