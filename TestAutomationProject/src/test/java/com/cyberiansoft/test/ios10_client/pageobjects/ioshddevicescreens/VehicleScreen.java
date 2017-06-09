package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import static com.cyberiansoft.test.ios10_client.utils.Helpers.element;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class VehicleScreen extends iOSHDBaseScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='VIN#']/XCUIElementTypeTextField")
    private IOSElement vinfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Make']/XCUIElementTypeTextField")
    private IOSElement makefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Model']/XCUIElementTypeTextField")
    private IOSElement modelfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Year']/XCUIElementTypeTextField")
    private IOSElement yearfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Tech']/XCUIElementTypeTextField")
    private IOSElement techfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Est#']/XCUIElementTypeTextField")
    private IOSElement estfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Trim']/XCUIElementTypeTextField")
    private IOSElement trimfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Make']/XCUIElementTypeButton[@name='custom detail button']")
    private IOSElement makecustombtn;
	
	@iOSFindBy(accessibility  = "Advisor")
    private IOSElement advisorfld;
	
	@iOSFindBy(accessibility  = "Color")
    private IOSElement colorfld;
	
	@iOSFindBy(accessibility  = "Mileage")
    private IOSElement mileagefld;
	
	@iOSFindBy(accessibility  = "Fuel Tank Level")
    private IOSElement fueltanklevelfld;
	
	@iOSFindBy(accessibility  = "License Plate")
    private IOSElement licenseplatefld;
	
	@iOSFindBy(accessibility  = "Owner")
    private IOSElement ownerfld;
	
	@iOSFindBy(accessibility  = "Tech")
    private IOSElement techfld;
	
	@iOSFindBy(accessibility  = "Location")
    private IOSElement locationfld;
	
	@iOSFindBy(accessibility  = "Type")
    private IOSElement typefld;
	
	@iOSFindBy(accessibility  = "Year")
    private IOSElement yearfld;
	
	@iOSFindBy(accessibility  = "Trim")
    private IOSElement trimfld;
	
	@iOSFindBy(accessibility  = "Stock#")
    private IOSElement stockfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"Stock#\"]/XCUIElementTypeTextField")
    private IOSElement stockfldvalue;
	
	@iOSFindBy(accessibility  = "RO#")
    private IOSElement rofld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"RO#\"]/XCUIElementTypeTextField[1]")
    private IOSElement rofldvalue;
	
	@iOSFindBy(accessibility  = "PO#")
    private IOSElement pofld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"PO#\"]/XCUIElementTypeTextField")
    private IOSElement pofldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    private IOSElement inspnumberlabel;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"OrderVehicleForm\"]/UIAStaticText[1]")
	private IOSElement navbarworkordercustomercaption;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"VehicleForm\"]/UIAStaticText[1]")
			private IOSElement navbarinspectioncustomercaption;
	
	@iOSFindBy(accessibility  = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[4]")
    private IOSElement wotypelabel;
	
	public VehicleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static String getVehicleScreenCaption() {
		return vehiclescreencapt;
	}

	public String clickSaveWithAlert() throws InterruptedException {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) {
		
		setVINFieldValue(vin);
		if (appiumdriver.findElementsByAccessibilityId("Close").size() > 0)
			appiumdriver.findElementByAccessibilityId("Close").click();
		if (appiumdriver.findElementsByAccessibilityId("Close").size() > 0)
			appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public void setVINFieldValue(String vin) {
		
		clickVINField();

		//appiumdriver.findElementByAccessibilityId("VIN#").click();
		Helpers.waitABit(500);
		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		((IOSDriver) appiumdriver).hideKeyboard();
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void clearVINCode() {
		clickVINField();
		for (int i = 0; i < 17; i++)
			((IOSDriver) appiumdriver).getKeyboard().sendKeys(Keys.DELETE);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		
	}
	
	public IOSElement getVINField() {
		return (IOSElement) appiumdriver.findElementByAccessibilityId("VIN#");
		//return vinfld;
	}
	
	public void clickVINField() {
		getVINField().click();
	}

	public void setVINAndAndSearch(String vin)
			throws InterruptedException {

		appiumdriver.findElementByAccessibilityId("VIN#").click();

		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
		
		Assert.assertTrue(element(
				MobileBy.name("No vehicle invoice history found")).isDisplayed());
		element(
				MobileBy.name("Close"))
				.click();
	}
	
	public void verifyExistingWorkOrdersDialogAppears() throws InterruptedException {
		
		Assert.assertTrue(element(
				MobileBy.name("Existing work orders were found for this vehicle")).isDisplayed());
		element(
				MobileBy.name("Close"))
				.click();
	}

	public String getInspectionNumber() {
		return inspnumberlabel.getText();
	}

	public void setMakeAndModel(String make, String model) {
		
		makecustombtn.click();
		appiumdriver.findElementByName(make).click();
		appiumdriver.findElementByName(model).click();
		/*element(
				MobileBy.name(make)).click();
		element(
				MobileBy.name(model)).click();*/
	}

	public void seletAdvisor(String advisor) {
		advisorfld.click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
		//appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
		//				+ advisor + "\"]").click();
	}
	
	
	public String getMake() {
		return makefld.getAttribute("value");
	}

	public String getModel() {
		return modelfld.getAttribute("value");
	}

	public String getYear() {
		return yearfldvalue.getAttribute("value");
	}
	
	public String getTechnician() {
		System.out.println("+++" + techfldvalue.getText());
		return techfldvalue.getText();
	}
	
	public String getEst() {
		return estfldvalue.getText();
	}
	
	public String getTrim() {
		return trimfldvalue.getAttribute("value");
	}
	
	public void verifyMakeModelyearValues(String exp_make, String exp_model, String exp_year) {
		Assert.assertEquals(getMake(), exp_make);
		Assert.assertEquals(getModel(), exp_model);
		Assert.assertEquals(getYear(), exp_year);
	}
	
	public void cancelOrder() {
		cancelbtn.click();
		acceptAlert();
	}
	
	public void setColor(String color) {
		colorfld.click();
		appiumdriver.findElementByName(color).click();
		appiumdriver.hideKeyboard();
	}
	
	public void setMileage(String mileage) {
		mileagefld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(mileage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setFuelTankLevel(String fueltanklevel)  {
		fueltanklevelfld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(fueltanklevel);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setLicensePlate(String licplate)  {
		licenseplatefld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(licplate);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void selectOwnerT(String owner) throws InterruptedException {
		ownerfld.click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + owner + "']")).waitAction(300).release().perform();
	}

	public void setTech(String _tech) throws InterruptedException {
		clickTech();
		appiumdriver.findElementByName(_tech).click();
	}
	
	public void selectLocation(String _location) throws InterruptedException {
		locationfld.click();
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setType(String _type) throws InterruptedException {
		typefld.click();
		selectUIAPickerValue(_type);
		toolbardonebtn.click();
	}
	
	public void setYear(String year) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		
		yearfld.click();
		selectUIAPickerValue(year);
		
		if (appiumdriver.findElementsByAccessibilityId("Done").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Done").get(1)).click();
		else
			toolbardonebtn.click();
	}
	
	public void setTrim(String trimvalue) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		
		trimfld.click();
		selectUIAPickerValue(trimvalue);
		
		if (appiumdriver.findElementsByAccessibilityId("Done").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Done").get(1)).click();
		else
			toolbardonebtn.click();
	}

	public void clickTech() {
		techfld.click();
	}

	public void setStock(String stock) {
		stockfld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(stock);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public void setRO(String ro)  {
		rofld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ro);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setPO(String po)  {
		pofld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public String getWorkOrderCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
		//return navbarworkordercustomercaption.getAttribute("name");
	}
	
	public String getInspectionCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
		//return navbarinspectioncustomercaption.getAttribute("name");
	}

	public NotesScreen clickNotesButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(composebtn).waitAction(300).release().perform();
		//composebtn.click();
		return new NotesScreen(appiumdriver);
	}
	
	public String getWorkOrderTypeValue() {
		Helpers.waitABit(1000);
		return wotypelabel.getAttribute("value");
	}

	public void clickNavigationBarSaveButton() {
		appiumdriver.findElement(By.xpath("//XCUIElementTypeNavigationBar[@name='Vehicle']/XCUIElementTypeButton[@name='Save']")).click();
	}
	
}
