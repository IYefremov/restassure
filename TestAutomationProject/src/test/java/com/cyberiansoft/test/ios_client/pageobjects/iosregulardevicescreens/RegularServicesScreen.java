package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularServicesScreen extends iOSRegularBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String servicesscreencapt = "Services";
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(name = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(name = "Delete")
    private IOSElement deletebtn;
	
	@iOSFindBy(name = "AvailableServicesSwitchButton")
    private IOSElement servicetypesbtn;
	
	@iOSFindBy(name = "Price Matrices")
    private IOSElement pricematrixespopupname;
	
	@iOSFindBy(name = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[1][@name=\"Vehicle Part\"]")
    private IOSElement vehiclepartsbtn;
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Final\"]")
    private IOSElement finalalertbtn;
	
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

	public void assertServiceIsSelected(String service) {
		Assert.assertTrue(Helpers.elementExists("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIAButton[@name=\"selected\"]"));
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		return appiumdriver.findElementsByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIAButton[@name=\"selected\"]").size();
	}
	
	public void assertServiceIsSelectedAndVisible(String service, String pricevalue) throws InterruptedException {
		Helpers.scroolTo(service);
		
		Assert.assertTrue(appiumdriver.findElement(By.name(service)).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(By.name("selected")).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(By.name(pricevalue)).isDisplayed());
	}
	
	public void assertServiceIsSelectedWithServiceValues(String service, String pricevalue) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[1]/UIATableCell[@name=\""
				+ service + "\"]/UIAButton[@name=\"selected\"]").isEnabled());

		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[1]/UIATableCell[@name=\""
				+ service + "\"]/UIAStaticText[@name=\"" + pricevalue + "\"]").isEnabled());
	}

	public int getServiceSelectedNumber(String service) {
		return appiumdriver.findElementsByXPath("//UIAScrollView[1]/UIATableView[4]/UIATableCell[@name=\""
								+ service + "\"]").size();
	}
	
	public void assertTotalAmauntIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElementByName("AmountTotal").getAttribute("value"), price);
	}

	public void assertServiceTypeExists(String servicetype) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ servicetype + "\"]").isDisplayed());
	}

	public void selectService(String service) {
		appiumdriver.findElementByName(service).click();
	}
	
	public void selectSubService(String service) {
		if (Helpers.elementExists(MobileBy.IosUIAutomation(".scrollViews()[1]"))) {
			appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].tableViews()[0].cells()['"
						+ service
						+ "'].buttons()['unselected']")).click();
		} else {
			Helpers.scroolTo(service);
			appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['"
					+ service
					+ "'].buttons()['unselected']")).click();
		}
	}

	public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String service) {
		Helpers.scroolTo(service);
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']").click();
		//Helpers.scroolToByXpath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']");
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public RegularSelectedServiceDetailsScreen openSelectedServiceDetails(String service) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']");
		if (selectedservices.size() > 1) {			
			Helpers.scroolToElement(selectedservices.get(1));
			Helpers.waitABit(300);
			((WebElement) appiumdriver.findElementsByXPath("//UIATableView[1]/UIATableCell[@name='" + service + "']/UIAButton[@name='custom detail button']").get(1)).click();
		} else {
			selectedservices.get(0).click();
		}
		return new RegularSelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void clickToolButton() {
		appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAButton[@name=\"services\"]").click();
	}
	
	public void openCustomServiceDetailsByPartOfServiceName(String service) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[2]/UIATableCell[4]").click();
	}


	public RegularPriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ servicepricematrices + "\"]").click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void setSelectedServiceRequestServicesQuantity(String service, String _quantity) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
				+ service + "\"]/UIATextField[1]"))).click();
		
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue("");
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue(_quantity);
		Helpers.keyboadrType("\n");
	}
	
	
	public void openServiceDetailsByIndex(String service, int servicedetailindex) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//UIAScrollView[2]/UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
				+ service + "\"]/UIAButton[@name=\"custom detail button\"]");
		Helpers.scroolToElement(selectedservices.get(servicedetailindex));
		Helpers.waitABit(2000);
		
		((WebElement) appiumdriver.findElementsByXPath("//UIAScrollView[2]/UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
				+ service + "\"]/UIAButton[@name=\"custom detail button\"]").get(servicedetailindex)).click();
	}
	
	public void setSelectedServiceRequestServicePrice(String service, String price) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]").click();
		appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]").click();
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[@name=\"ServiceGroupServicesTable\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]")).setValue(price);
		Helpers.keyboadrType("\n");
	}
	
	public boolean priceMatricesPopupIsDisplayed() {
		return pricematrixespopupname.isDisplayed();
	}

	public RegularPriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ pricematrice + "\")]").click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}
	
	public void removeSelectedServices(String service) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].tableViews()[0].cells()['"
				+ service
				+ "'].buttons()['selected']")).click();
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
		appiumdriver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()['Add']")).click();
	}
	
	public void clickSaveAsFinal() {
		savebtn.click();
		finalalertbtn.click();
	}

}
