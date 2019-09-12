package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularVehicleScreen extends RegularBaseWizardScreen {


	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton")
    private IOSElement makecustombtn;

	@iOSXCUITFindBy(accessibility = "VIN#")
	private IOSElement vinfld;

	@iOSXCUITFindBy(accessibility = "VehicleInfoTable")
	private IOSElement vehicleinfotbl;
	
	public RegularVehicleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitVehicleScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("VehicleInfoTable")));
	}

	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
	
	public void setVINValue(String vin)  {
		waitVehicleScreenLoaded();
		vinfld.click();
		vinfld.sendKeys(vin + "\n");
	}

	public void setVIN(String vin)  {
		setVINValue(vin);
	}
	
	public void clearVINCode() {
		vinfld.click();
		WebElement deleteBtn  = appiumdriver.findElementByClassName("XCUIElementTypeKeyboard").findElement(MobileBy.AccessibilityId("delete"));
		for (int i = 0; i < 17; i++)
			deleteBtn.click();
	}
	
	public WebElement getVINField() {
		return appiumdriver.findElementByAccessibilityId("VIN#");
	}
	
	public void clickVINField() {
		vinfld.click();
	}

	public String setVINAndAndSearch(String vin) {

		vinfld.click();
		vinfld.sendKeys(vin + "\n");
		String alertText = appiumdriver.findElementByClassName("XCUIElementTypeTextView").getText();
		appiumdriver.findElementByAccessibilityId("Close").click();
		return alertText;
	}
	
	public String getExistingWorkOrdersDialogMessage() {
		String alertText = appiumdriver.findElementByClassName("XCUIElementTypeTextView").getText();
		appiumdriver.findElementByAccessibilityId("Close")
				.click();
		return alertText;
	}
	
	public String getWorkOrderNumber() {
		waitVehicleScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name CONTAINS 'O-'")));
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name CONTAINS 'O-'").getText();
	}

	public void setMakeAndModel(String make, String model) {
		
		makecustombtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(make)));
		if (!appiumdriver.findElementByAccessibilityId(make).isDisplayed())
			SwipeUtils.swipeToElement(make);
		appiumdriver.findElementByAccessibilityId(make).click();
		if (!appiumdriver.findElementByAccessibilityId(model).isDisplayed())
			SwipeUtils.swipeToElement(model);
		appiumdriver.findElementByAccessibilityId(model).click();
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

	public void seletAdvisor(String advisor) {
		swipeToElement(vehicleinfotbl.findElement(By.xpath("//XCUIElementTypeCell[@name='Advisor']")));
		vehicleinfotbl.findElementByAccessibilityId("Advisor").click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}
	
	
	public String getMake() {
		waitVehicleScreenLoaded();
		return vehicleinfotbl.findElementByAccessibilityId("Make").findElementByClassName("XCUIElementTypeTextField").getText();
	}

	public String getModel() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Model")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getColor() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Color")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getYear() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Year")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getMileage() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Mileage")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getStock() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Stock#")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getRO() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("RO#")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getPO() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("PO#")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}
	
	public String getTrim() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Trim")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}
	
	public String getEst() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Est#")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public String getLicensePlate() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("License\nPlate")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}
	
	public String getTechnician() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Tech")).findElement(MobileBy.className("XCUIElementTypeTextField")).getText();
	}

	public void verifyMakeModelyearValues(String exp_make, String exp_model, String exp_year) {
		Assert.assertEquals(getMake(), exp_make);
		Assert.assertEquals(getModel(), exp_model);
		Assert.assertEquals(getYear(), exp_year);
	}
	
	public void cancelOrder() {
		clickChangeScreen();
		appiumdriver.findElementByAccessibilityId("Cancel").click();
		acceptAlert();
	}
	
	public void setColor(String color) {
		vehicleinfotbl.findElementByAccessibilityId("Color").click();
		appiumdriver.findElementByAccessibilityId(color).click();
	}
	
	public void setMileage(String mileage) {
		vehicleinfotbl.findElementByAccessibilityId("Mileage").click();
		vehicleinfotbl.findElement(MobileBy.iOSNsPredicateString("name = 'Mileage' and type = 'XCUIElementTypeCell'")).sendKeys(mileage);
		//appiumdriver.getKeyboard().sendKeys(mileage);
	}
	
	public void setFuelTankLevel(String fueltanklevel) {
		vehicleinfotbl.findElementByAccessibilityId("Fuel Tank Level").click();
		vehicleinfotbl.findElement(MobileBy.iOSNsPredicateString("name = 'Fuel Tank Level' and type = 'XCUIElementTypeCell'")).sendKeys(fueltanklevel);
	}
	
	public void setLicensePlate(String licplate) {
		vehicleinfotbl.findElementByAccessibilityId("License\nPlate").click();
		vehicleinfotbl.findElementByAccessibilityId("License\nPlate").sendKeys(licplate);
	}

	public void setTech(String _tech) {
		clickTech();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(_tech)));
		appiumdriver.findElementByAccessibilityId(_tech).click();
	}
	
	public void selectLocation(String _location) {
		if (!vehicleinfotbl.findElement(MobileBy.AccessibilityId("Location")).isDisplayed()) {
			swipeToElement(vehicleinfotbl.findElement(MobileBy.AccessibilityId("Location")));
		}
		vehicleinfotbl.findElement(MobileBy.AccessibilityId("Location")).click();
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void setType(String _type) {
		vehicleinfotbl.findElementByAccessibilityId("Type").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void setYear(String year) {
		vehicleinfotbl.findElementByAccessibilityId("Year").click();
		selectUIAPickerValue(year);
		appiumdriver.findElementByAccessibilityId("Done").click();
		
	}
	
	public void setTrim(String trimvalue)  {
		swipeToElement(vehicleinfotbl.findElement(By.xpath("//XCUIElementTypeCell[@name='Trim']")));
		vehicleinfotbl.findElementByAccessibilityId("Trim").click();
		selectUIAPickerValue(trimvalue);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void clickTech() {

		JavascriptExecutor js1 = (JavascriptExecutor) appiumdriver;
		HashMap<String, String> scrollObject1 = new HashMap<String, String>();
		scrollObject1.put("direction", "up");
		js1.executeScript("mobile: swipe", scrollObject1);
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeCell[@name='Tech']")));
		appiumdriver.findElementByAccessibilityId("Tech").click();
	}

	public void setStock(String stock) {
		vehicleinfotbl.findElementByAccessibilityId("Stock#").click();
		vehicleinfotbl.findElement(MobileBy.iOSNsPredicateString("name = 'Stock#' and type = 'XCUIElementTypeCell'")).sendKeys(stock + "\n");
		
	}

	public void setRO(String ro) {
		vehicleinfotbl.findElement(MobileBy.iOSNsPredicateString("name = 'RO#' and type = 'XCUIElementTypeCell'")).sendKeys(ro + "\n");
	}
	
	public void setPO(String po) {
		vehicleinfotbl.findElement(MobileBy.iOSNsPredicateString("name = 'PO#' and type = 'XCUIElementTypeCell'")).sendKeys(po + "\n");
	}
	
	public String getWorkOrderCustomer() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		return wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("viewPrompt"))).getAttribute("value");
	}
	
	public String getInspectionCustomer() {
		waitVehicleScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		return wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("viewPrompt"))).getAttribute("value");
	}

	public void clickSaveAsFinal() {
		clickSave();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Final")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Final")).click();
	}

}
