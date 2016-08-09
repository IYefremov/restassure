package com.cyberiansoft.test.vnext.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVehicleInfoScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[@class='page inspections-info page-on-center']")
	private WebElement vehiclepage;
	
	@FindBy(id="my-form")
	private WebElement vehiclefieldslist;
	
	@FindBy(name="Vehicle.VIN")
	private WebElement vinfld;
	
	@FindBy(name="Vehicle.Make")
	private WebElement makefld;
	
	@FindBy(name="Vehicle.Model")
	private WebElement modelfld;
	
	@FindBy(name="Vehicle.VehicleTypeId")
	private WebElement typefld;
	
	@FindBy(name="Vehicle.PlateNo")
	private WebElement licplatefld;
	
	@FindBy(name="Vehicle.Milage")
	private WebElement milagefld;
	
	@FindBy(name="Estimations.StockNo")
	private WebElement stockfld;
	
	@FindBy(name="Estimations.RONo")
	private WebElement rofld;
	
	@FindBy(name="Vehicle.Color")
	private WebElement colorfld;
		
	@FindBy(name="Vehicle.Year")
	private WebElement yearfld;
	
	@FindBy(name="Estimations.EmployeeId")
	private WebElement techfld;
	
	public VNextVehicleInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(vehiclefieldslist));
	}
	
	public void setVIN (String vinnumber) {
		setValue(vinfld, vinnumber);
		log(LogStatus.INFO, "Set VIN: " + vinnumber);
	}
	
	public String getVINFieldValue() {
		return vinfld.getAttribute("value");
	}
	
	public boolean isVINFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.VIN")).size() > 0;
	}
	
	public boolean isMakeFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.Make")).size() > 0;
	}
	
	public boolean isModelFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.Model")).size() > 0;
	}
	
	public boolean isColorFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.Color")).size() > 0;
	}
	
	public boolean isYearFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.Year")).size() > 0;
	}
	
	public boolean isStockNumberFieldVisible() {
		return appiumdriver.findElements(By.name("Estimations.StockNo")).size() > 0;
	}
	
	public boolean isRONumberFieldVisible() {
		return appiumdriver.findElements(By.name("Estimations.RONo")).size() > 0;
	}
	
	public boolean isMilageFieldVisible() {
		return appiumdriver.findElements(By.name("Vehicle.Milage")).size() > 0;
	}
	
	public void selectType (String vehicletype) {
		tap(typefld);
		VNextVehicleTypeScreen vehicletypescreen = new VNextVehicleTypeScreen(appiumdriver);
		vehicletypescreen.selectType(vehicletype);
		log(LogStatus.INFO, "Select Vehicle Type: " + vehicletype);
	}
	
	public String getType () {
		return typefld.getAttribute("value");
	}
	
	public String getModelInfo() {
		return modelfld.getAttribute("value");
	}

	public String getMakeInfo() {
		return makefld.getAttribute("value");
	}
	
	public String getYear() {
		return yearfld.getAttribute("value");
	}
	
	public void setLicPlate (String licplate) {
		licplatefld.clear();
		licplatefld.sendKeys(licplate);
		log(LogStatus.INFO, "Set License Plate : " + licplate);
	}
	
	public String getLicPlate () {
		return licplatefld.getAttribute("value");
	}
	
	public void setMilage (String milage) {
		milagefld.clear();
		milagefld.sendKeys(milage);
		log(LogStatus.INFO, "Set Milage: " + milage);
	}
	
	public String getMilage () {
		return milagefld.getAttribute("value");
	}
	
	public void setStockNo (String stockno) {
		stockfld.clear();
		stockfld.sendKeys(stockno);
		log(LogStatus.INFO, "Set Stock Number : " + stockno);
	}
	
	public String getStockNo () {
		return stockfld.getAttribute("value");
	}
	
	public void setRoNo (String rono) {
		rofld.clear();
		rofld.sendKeys(rono);
		log(LogStatus.INFO, "Set RO Number : " + rono);
	}
	
	public String getRoNo () {
		return rofld.getAttribute("value");
	}
	
	public List<String> getDisplayedVehicleFieldsListItems() {
		List<String> fields = new ArrayList<String>();
    	List<WebElement> elementfields = vehiclefieldslist.findElements(By.xpath("./ul/li/label/div/div[@class='item-title label']"));
    	for (WebElement element : elementfields)
    		fields.add(element.getText());
    	return fields;
	}
	
	public VNextInspectionsScreen saveInspectionfromFirstScreen() {
		setVIN("1FMCU0DG4BK830800");
		return saveInspectionViaMenu();
	}
	
	public VNextInspectionServicesScreen goToInspectionServicesScreen() {
		waitABit(5000);
		swipeScreensLeft(4);
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		if (appiumdriver.findElements(By.xpath("//span[@class='client-mode' and text()='All Services']")).size() > 0)
			swipeScreenLeft();
		return new VNextInspectionServicesScreen(appiumdriver);
	}

}
