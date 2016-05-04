package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ServicesScreen extends iOSHDBaseScreen {

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
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Final\"]")
    private IOSElement finalalertbtn;
	
	public ServicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	

	public void clickSaveButton() {
		savebtn.click();
	}
	
	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}

	public void clickServiceTypesButton() {
		servicetypesbtn.click();
	}

	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[3]/UIATableCell[@name=\""
						+ defaultServiceValue + "\"]").isDisplayed());
	}

	public void assertServiceIsSelected(String service) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[3]/UIATableCell[@name=\""
						+ service + "\"]").isDisplayed());
	}
	
	public int getNumberOfServiceSelectedItems(String service) {
		return appiumdriver.findElementsByXPath("//UIAScrollView/UIATableView[3]/UIATableCell[@name=\""
						+ service + "\"]").size();
	}
	
	public void assertServiceIsSelectedWithServiceValues(String service, String servicepriceandquantity) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[@name=\"SelectedServicesView\"]/UIATableCell[@name=\""
								+ service + "\"]").isDisplayed());
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[@name=\"SelectedServicesView\"]/UIATableCell[@name=\""
				+ service + "\"]/UIAStaticText[@name=\""
				+ servicepriceandquantity + "\"]").isDisplayed());
	}

	public int getServiceSelectedNumber(String service) {
		return appiumdriver.findElementsByXPath("//UIAScrollView[1]/UIATableView[4]/UIATableCell[@name=\""
								+ service + "\"]").size();
	}

	public void assertPriceIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[0].toolbars()[0].staticTexts()[7]"))
						.getText(), price);
	}

	public void assertServiceTypeExists(String servicetype) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ servicetype + "\"]").isDisplayed());
	}

	public void selectService(String service) {
		appiumdriver.findElementByName(service).click();
	}

	public SelectedServiceDetailsScreen openCustomServiceDetails(String service) {
		Helpers.scroolTo(service);
		appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[0].tableViews()[1].cells()['" + service + "'].buttons()['custom detail button']")).click();
		/*appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ service
						+ "\"]/UIAButton[@name=\"custom detail button\"]").click();*/
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void openCustomServiceDetailsByPartOfServiceName(String service) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[2]/UIATableCell[4]").click();
	}


	public PriceMatrixScreen selectServicePriceMatrices(String servicepricematrices) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ servicepricematrices + "\"]").click();
		return new PriceMatrixScreen(appiumdriver);
	}

	public SelectedServiceDetailsScreen openServiceDetails(String service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[3]/UIATableCell[@name=\""
				+ service + "\"]"))).click();
		return new SelectedServiceDetailsScreen(appiumdriver);
	}
	
	public void setSelectedServiceRequestServicesQuantity(String service, String _quantity) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
				+ service + "\"]/UIATextField[1]"))).click();
		
		((IOSElement) appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue("");
		((IOSElement) appiumdriver.findElementByXPath("//UIAScrollView/UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
						+ service + "\"]/UIATextField[1]")).setValue(_quantity);
		Helpers.keyboadrType("\n");
	}
	
	
	public void openServiceDetailsByIndex(String service, int dervicedetailindex) {
		List<WebElement> selectedservices = appiumdriver.findElementsByXPath("//UIAScrollView/UIATableView[3]/UIATableCell[@name=\""
						+ service + "\"]");
		if (selectedservices.size() > dervicedetailindex) {
			selectedservices.get(dervicedetailindex).click();
		}
	}
	
	public void setSelectedServiceRequestServicePrice(String service, String price) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]").click();
		appiumdriver.findElementByXPath("//UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]").click();
		((IOSElement) appiumdriver.findElementByXPath("//UIATableView[@name=\"SelectedServiceRequestServicesView\"]/UIATableCell[@name=\""
						+ service
						+ "\"]/UIATextField[1]")).setValue(price);
		Helpers.keyboadrType("\n");
	}

	public void cancelOrder() {
		clickCancelButton();
		acceptAlert();
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
}
