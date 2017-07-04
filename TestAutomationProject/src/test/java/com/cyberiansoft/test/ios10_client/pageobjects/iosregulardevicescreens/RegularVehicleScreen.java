package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularVehicleScreen extends iOSRegularBaseScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton")
    private IOSElement makecustombtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Advisor")
    private IOSElement advisorfld;
	
	@iOSFindBy(accessibility = "Color")
    private IOSElement colorfld;
	
	@iOSFindBy(accessibility = "Mileage")
    private IOSElement mileagefld;
	
	@iOSFindBy(accessibility = "Fuel Tank Level")
    private IOSElement fueltanklevelfld;
	
	@iOSFindBy(accessibility = "License Plate")
    private IOSElement licenseplatefld;
	
	@iOSFindBy(accessibility = "Tech")
    private IOSElement techfld;
	
	@iOSFindBy(accessibility = "Location")
    private IOSElement locationfld;
	
	@iOSFindBy(accessibility = "Type")
    private IOSElement typefld;
	
	@iOSFindBy(accessibility = "Year")
    private IOSElement yearfld;
	
	@iOSFindBy(accessibility = "Trim")
    private IOSElement trimfld;
	
	@iOSFindBy(accessibility = "Stock#")
    private IOSElement stockfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Stock#\"]/UIATextField")
    private IOSElement stockfldvalue;
	
	@iOSFindBy(accessibility = "RO#")
    private IOSElement rofld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"RO#\"]/UIATextField")
    private IOSElement rofldvalue;
	
	@iOSFindBy(accessibility = "PO#")
    private IOSElement pofld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"PO#\"]/UIATextField")
    private IOSElement pofldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    private IOSElement inspnumberlabel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[2]")
    private IOSElement regularinspnumberlabel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[1]")
    private IOSElement regularwotypelabel;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeOther/XCUIElementTypeStaticText")
	private IOSElement navbarworkordercustomercaption;
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='VehicleInfoView']/XCUIElementTypeOther/XCUIElementTypeStaticText[1]")
	private IOSElement navbarinspectioncustomercaption;
	
	@iOSFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	public RegularVehicleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static String getVehicleScreenCaption() {
		return vehiclescreencapt;
	}

	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin)  {
		clickVINField();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		if (appiumdriver.findElementsByAccessibilityId("Close").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Close").get(1)).click();
		if (appiumdriver.findElementsByAccessibilityId("Close").size() > 0)
			appiumdriver.findElementByAccessibilityId("Close").click();
		Helpers.waitABit(500);
	}
	
	public void clearVINCode() {
		appiumdriver.findElementByAccessibilityId("VIN#").click();
		for (int i = 0; i < 17; i++)
			((IOSDriver) appiumdriver).getKeyboard().sendKeys(Keys.DELETE);
		
	}
	
	public WebElement getVINField() {
		//WebElement par = getVehicleInfoTableParentNode("VIN#");
		//return par.findElement(By.xpath("./XCUIElementTypeTextField[1]"));
		return appiumdriver.findElementByAccessibilityId("VIN#");
	}
	
	public void clickVINField() {
		getVINField().click();
	}

	public void setVINAndAndSearch(String vin)
			throws InterruptedException {

		getVINField().click();

		Helpers.keyboadrType(vin + "\n");		
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId("No vehicle invoice history found").isDisplayed());
		appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public void verifyExistingWorkOrdersDialogAppears() throws InterruptedException {
		
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId("Existing work orders were found for this vehicle").isDisplayed());
		appiumdriver.findElementByAccessibilityId("Close")
				.click();
	}
	
	public IOSElement getInspectionNumberLabel() {
		return regularinspnumberlabel;
	}
	
	public String getWorkOrderTypeValue() {
		Helpers.waitABit(1000);
		return regularwotypelabel.getAttribute("value");
	}
	
	public String getInspectionNumber() {
		Helpers.waitABit(500);
		return getInspectionNumberLabel().getText();
	}

	public void setMakeAndModel(String make, String model) {
		
		makecustombtn.click();
		appiumdriver.findElementByAccessibilityId(make).click();
		appiumdriver.findElementByAccessibilityId(model).click();
		clickSaveButton();
	}

	public void seletAdvisor(String advisor) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Advisor']")));
		advisorfld.click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}
	
	
	public String getMake() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("VehicleInfoTable")));
		IOSElement table = (IOSElement) appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		return table.findElement(By.xpath("//XCUIElementTypeCell[@name='Make']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public String getModel() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Model']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public String getYear() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Year']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public String getTrim() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Trim']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public String getEst() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Est#']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public String getTechnician() {
		Helpers.waitABit(1000);
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Tech']/XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void verifyMakeModelyearValues(String exp_make, String exp_model, String exp_year) {
		Assert.assertEquals(getMake(), exp_make);
		Assert.assertEquals(getModel(), exp_model);
		Assert.assertEquals(getYear(), exp_year);
	}
	
	public void cancelOrder() {
		clickChangeScreen();
		cancelbtn.click();
		acceptAlert();
	}
	
	public void setColor(String color) {
		colorfld.click();
		appiumdriver.findElementByAccessibilityId(color).click();
		Helpers.waitABit(1000);
	}
	
	public void setMileage(String mileage) throws InterruptedException {
		mileagefld.click();
		Helpers.keyboadrType(mileage);
	}
	
	public void setFuelTankLevel(String fueltanklevel) throws InterruptedException {
		fueltanklevelfld.click();
		Helpers.keyboadrType(fueltanklevel);
		//element(
			//	MobileBy.name("Fuel Tank Level")).setValue(fueltanklevel);
	}
	
	public void setLicensePlate(String licplate) throws InterruptedException {
		licenseplatefld.click();
		Helpers.keyboadrType(licplate);
	}

	public void setTech(String _tech) throws InterruptedException {
		clickTech();
		appiumdriver.findElementByAccessibilityId(_tech).click();
		Helpers.waitABit(500);
	}
	
	public void selectLocation(String _location) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Location']")));
		appiumdriver.findElementByAccessibilityId("Location").click();
		//WebElement par = getVehicleInfoTableParentNode("Location");
		//par.findElement(By.xpath(".//XCUIElementTypeTextField")).click();
		Helpers.waitABit(1000);
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void setType(String _type) throws InterruptedException {
		typefld.click();
		//selectUIAPickerValue(_type);
		toolbardonebtn.click();
	}
	
	public void setYear(String year) throws InterruptedException {
		yearfld.click();
		Thread.sleep(1000);
		selectUIAPickerValue(year);
		toolbardonebtn.click();
	}
	
	public void setTrim(String trimvalue) throws InterruptedException {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Trim']")));
		trimfld.click();
		Thread.sleep(1000);
		selectUIAPickerValue(trimvalue);
		toolbardonebtn.click();
	}

	public void clickTech() {
		if (!techfld.isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeCell[@name='Tech']")));
		techfld.click();
	}

	public void setStock(String stock) {
		appiumdriver.findElementByAccessibilityId("Stock#").click();
		//appiumdriver.findElementByAccessibilityId("RO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(stock);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
		
	}

	public void setRO(String ro) {
		appiumdriver.findElementByAccessibilityId("RO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ro);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setPO(String po) throws InterruptedException {
		pofld.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public String getWorkOrderCustomer() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(navbarinspectioncustomercaption));
		return navbarinspectioncustomercaption.getAttribute("value");
	}
	
	public String getInspectionCustomer() {
		Helpers.waitABit(500);
		return navbarinspectioncustomercaption.getAttribute("value");
	}

	public RegularNotesScreen clickNotesButton() {
		composebtn.click();
		return new RegularNotesScreen(appiumdriver);
	}
	
	public WebElement getVehicleInfoTableParentNode(String wonumber) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + wonumber + "']/.."));
	}
	
	public void saveWorkOrder() {
		clickSaveButton();
		Helpers.waitABit(500);
		savebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(500);
	}

}
