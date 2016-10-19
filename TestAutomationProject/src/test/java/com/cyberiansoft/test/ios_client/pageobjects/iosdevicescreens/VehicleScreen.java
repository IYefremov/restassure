package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class VehicleScreen extends iOSHDBaseScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"VIN#\"]/UIAStaticText")
    private IOSElement vinfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Make\"]/UIATextField")
    private IOSElement makefld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Model\"]/UIATextField")
    private IOSElement modelfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Year\"]/UIATextField")
    private IOSElement yearfldvalue;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Tech\"]/UIATextField")
    private IOSElement techfldvalue;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Make\"]/UIAButton[@name=\"custom detail button\"]")
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
	
	@iOSFindBy(accessibility  = "Stock#")
    private IOSElement stockfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Stock#\"]/UIATextField")
    private IOSElement stockfldvalue;
	
	@iOSFindBy(accessibility  = "RO#")
    private IOSElement rofld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"RO#\"]/UIATextField[1]")
    private IOSElement rofldvalue;
	
	@iOSFindBy(accessibility  = "PO#")
    private IOSElement pofld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"PO#\"]/UIATextField")
    private IOSElement pofldvalue;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAStaticText[3]")
    private IOSElement inspnumberlabel;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAStaticText[2]")
    private IOSElement regularinspnumberlabel;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Done\"]")
    private IOSElement toolbardonebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"OrderVehicleForm\"]/UIAStaticText[1]")
	private IOSElement navbarworkordercustomercaption;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"VehicleForm\"]/UIAStaticText[1]")
			private IOSElement navbarinspectioncustomercaption;
	
	@iOSFindBy(accessibility  = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	public VehicleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static String getVehicleScreenCaption() {
		return vehiclescreencapt;
	}

	public String clickSaveWithAlert() throws InterruptedException {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) throws InterruptedException {
		Helpers.setVIN(vin);
	}
	
	public IOSElement getVINField() {
		return vinfld;
	}

	public void setVINAndAndSearch(String vin)
			throws InterruptedException {

		vinfld.click();

		Helpers.keyboadrType(vin);
		Helpers.hideKeyboard();
		
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
	
	public String getRegularInspectionNumber() {
		return regularinspnumberlabel.getText();
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
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ advisor + "\"]").click();
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
		return techfldvalue.getAttribute("value");
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
	
	public void selectOwnerT(String owner) throws InterruptedException {
		ownerfld.click();
		appiumdriver.findElementByName(owner).click();
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
	
	public void setYear(String year) throws InterruptedException {
		yearfld.click();
		Thread.sleep(1000);
		selectUIAPickerValue(year);
		toolbardonebtn.click();
	}

	public void clickTech() {
		techfld.click();
	}

	public void setStock(String stock) throws InterruptedException {
		stockfld.click();
		stockfldvalue.setValue(stock);
		Helpers.keyboadrType("\n");
	}

	public void setRO(String ro) throws InterruptedException {
		rofld.click();
		Thread.sleep(1000);
		//rofldvalue.setValue(ro);
		Helpers.keyboadrType(ro + "\n");
	}
	
	public void setPO(String po) throws InterruptedException {
		pofld.click();
		pofldvalue.setValue(po);
		Helpers.keyboadrType("\n");
	}
	
	public String getWorkOrderCustomer() {
		return navbarworkordercustomercaption.getAttribute("name");
	}
	
	public String getInspectionCustomer() {
		return navbarinspectioncustomercaption.getAttribute("name");
	}

	public NotesScreen clickNotesButton() {
		composebtn.click();
		return new NotesScreen(appiumdriver);
	}

}
