package com.cyberiansoft.test.vnext.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVehicleInfoScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement vehiclepage;
	
	@FindBy(xpath="//*[contains(@data-autotests-id, '-vehicle-info')]")
	private WebElement vehiclefieldslist;
	
	@FindBy(name="Vehicle.VIN")
	private WebElement vinfld;
	
	@FindBy(name="Vehicle.Make")
	private WebElement makefld;
	
	@FindBy(name="Vehicle.Model")
	private WebElement modelfld;
	
	@FindBy(name="Vehicle.Color")
	private WebElement colcorfld;
	
	@FindBy(xpath="//a[@action='select-color']")
	private WebElement selectcolorbtn;
	
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
	
	@FindBy(name="Estimations.PONo")
	private WebElement pofld;
	
	@FindBy(name="Vehicle.Color")
	private WebElement colorfld;
		
	@FindBy(name="Vehicle.Year")
	private WebElement yearfld;
	
	@FindBy(name="Estimations.EmployeeId")
	private WebElement techfld;
	
	public VNextVehicleInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@data-autotests-id, '-vehicle-info')]")));
		waitABit(1000);
		if (appiumdriver.findElementsByXPath("//div[@class='help-button' and text()='OK, got it']").size() > 0)
			if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
				tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		waitABit(2000);
	}
	
	public void setVIN (String vinnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(vinfld));
		setValue(vinfld, vinnumber);
		//appiumdriver.hideKeyboard();
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
	
	public void selectModelColor (String color) {
		tap(selectcolorbtn);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(By.xpath("//*[@class='item-name' and text()='" + color + "']"))));
		tap(appiumdriver.findElement(By.xpath("//*[@class='item-name' and text()='" + color + "']")));
		log(LogStatus.INFO, "Select Vehicle Color: " + color);
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
	
	public void setYear(String yearValue) {
		tap(yearfld);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-picker-value='" + yearValue + "']")));
		WebElement elem = appiumdriver.findElement(By.xpath("//div[@data-picker-value='" + yearValue + "']"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);	
		tap(appiumdriver.findElement(By.xpath("//div[@data-picker-value='" + yearValue + "']")));
		List<WebElement> closebtns = appiumdriver.findElements(By.xpath("//div[@class='right']/a[@class='link close-picker']"));
		for (WebElement closebtn : closebtns)
			if (closebtn.isDisplayed()) {
				tap(closebtn);
				break;
			}
		log(LogStatus.INFO, "Select Vehicle Year: " + yearValue);
	}
	
	public void setLicPlate (String licplate) {
		licplatefld.clear();
		licplatefld.sendKeys(licplate);
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set License Plate : " + licplate);
	}
	
	public String getLicPlate () {
		return licplatefld.getAttribute("value");
	}
	
	public void setMilage (String milage) {
		milagefld.click();
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(milagefld.getAttribute("value"), milage);
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
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set RO Number : " + rono);
	}
	
	public String getPoNo () {
		return pofld.getAttribute("value");
	}
	
	public void setPoNo (String pono) {
		pofld.clear();
		pofld.sendKeys(pono);
		appiumdriver.hideKeyboard();
		log(LogStatus.INFO, "Set RO Number : " + pono);
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
	
	public String getCustomercontextValue() {
		return vehiclepage.findElement(By.xpath(".//span[@class='client-mode']")).getText();
	}
	
	public VNextInspectionServicesScreen goToInspectionServicesScreen() {
		waitABit(1000);
		swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		waitABit(1000);	
		visualscreen.swipeScreenLeft();
		//new VNextVisualScreen(appiumdriver);
		//swipeScreenLeft();
		//new VNextVisualScreen(appiumdriver);
		//swipeScreenLeft();
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		return new VNextInspectionServicesScreen(appiumdriver);
	}
	
	public VNextWorkOrderSummaryScreen goToWorkOrderSummaryScreen() {
		waitABit(5000);
		swipeScreensLeft(2);
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		if (appiumdriver.findElements(By.xpath("//div[@data-page='summary']")).size() > 0)
			swipeScreenLeft();
		return new VNextWorkOrderSummaryScreen(appiumdriver);
	}
	
	public void populateVehicleInfoDataOnCreateWOWizard(String VIN, String color, String mileage, 
			String stockno, String rono, String licplate, String pono, String vehicletype) {
		setVIN(VIN);
		selectModelColor(color);
		setMilage(mileage);
		setStockNo(stockno);
		setRoNo(rono);
		setLicPlate(licplate);
		setPoNo(pono);
		selectType(vehicletype);
	}
	
	public void populateVehicleInfoDataOnCreateWOWizard(String VIN, String color) {
		setVIN(VIN);
		selectModelColor(color);
	}

}
