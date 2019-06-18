package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
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
		
	final static String vehiclescreencapt = "Vehicle";	
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton")
    private IOSElement makecustombtn;
	
	/*@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Advisor")
    private IOSElement advisorfld;
	
	@iOSXCUITFindBy(accessibility = "Color")
    private IOSElement colorfld;
	
	@iOSXCUITFindBy(accessibility = "Mileage")
    private IOSElement mileagefld;
	
	@iOSXCUITFindBy(accessibility = "Fuel Tank Level")
    private IOSElement fueltanklevelfld;
	
	@iOSXCUITFindBy(accessibility = "License Plate")
    private IOSElement licenseplatefld;
	
	@iOSXCUITFindBy(accessibility = "Tech")
    private IOSElement techfld;
	
	@iOSXCUITFindBy(accessibility = "Location")
    private IOSElement locationfld;
	
	@iOSXCUITFindBy(accessibility = "Type")
    private IOSElement typefld;
	
	@iOSXCUITFindBy(accessibility = "Year")
    private IOSElement yearfld;
	
	@iOSXCUITFindBy(accessibility = "Trim")
    private IOSElement trimfld;
	
	@iOSXCUITFindBy(accessibility = "Stock#")
    private IOSElement stockfld;
	
	@iOSXCUITFindBy(accessibility = "RO#")
    private IOSElement rofld;
	
	@iOSXCUITFindBy(accessibility = "PO#")
    private IOSElement pofld;*/
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    private IOSElement inspnumberlabel;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[2]")
    private IOSElement regularinspnumberlabel;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[1]")
    private IOSElement regularwotypelabel;
	
	/*@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSXCUITFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;*/

	@iOSXCUITFindBy(accessibility = "VehicleInfoTable")
	private IOSElement vehicleinfotbl;
	
	public RegularVehicleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("VehicleInfoTable"))); 
	}

	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
	
	public void setVINValue(String vin)  {
		clickVINField();
		getVINField().sendKeys(vin);
		getVINField().sendKeys("\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void setVIN(String vin)  {
		setVINValue(vin);
		/*Helpers.waitABit(1000);
		List<IOSElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (IOSElement closebtn : closebtns)
			if (closebtn.isDisplayed()) {
				closebtn.click();
				break;
			}
        try {
			Helpers.waitABit(1000);
            if (elementExists(MobileBy.AccessibilityId("Searching on Back Office"))) {
                WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
                wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElementByAccessibilityId("Searching on Back Office")));
            }
        } catch (WebDriverException e) {

        }

		Helpers.waitABit(1000);
		if (elementExists("Close"))	 {
		closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (IOSElement closebtn : closebtns)
//			closebtn.click();
			if (closebtn.isDisplayed()) {
				closebtn.click();
				
				break;
			}
		}

		Helpers.waitABit(1000);
		if (elementExists("Close"))	 {
			closebtns = appiumdriver.findElementsByAccessibilityId("Close");
			for (IOSElement closebtn : closebtns)
				if (closebtn.isDisplayed()) {
					closebtn.click();
					
					break;
				}
			}*/
	}
	
	public void clearVINCode() {
		appiumdriver.findElementByAccessibilityId("VIN#").click();
		WebElement deleteBtn  = appiumdriver.findElementByClassName("XCUIElementTypeKeyboard").findElement(MobileBy.AccessibilityId("delete"));
		for (int i = 0; i < 17; i++)
			deleteBtn.click();
	}
	
	public WebElement getVINField() {
		//WebElement par = getVehicleInfoTableParentNode("VIN#");
		//return par.findElement(By.xpath("./XCUIElementTypeTextField[1]"));
		return appiumdriver.findElementByAccessibilityId("VIN#");
	}
	
	public void clickVINField() {
		getVINField().click();
	}

	public String setVINAndAndSearch(String vin) {

		getVINField().click();
		getVINField().sendKeys(vin + "\n");
		String alertText = appiumdriver.findElementByClassName("XCUIElementTypeTextView").getText();
		//WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		//wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("No vehicle invoice history found")));
		//Assert.assertTrue(appiumdriver.findElementByAccessibilityId("No vehicle invoice history found").isDisplayed());
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name CONTAINS 'O-'")));
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name CONTAINS 'O-'").getText();
	}

	public void setMakeAndModel(String make, String model) {
		
		makecustombtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(make)));
		appiumdriver.findElementByAccessibilityId(make).click();
		appiumdriver.findElementByAccessibilityId(model).click();
		clickSave();
	}

	public void seletAdvisor(String advisor) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Advisor']")));
		appiumdriver.findElementByAccessibilityId("Advisor").click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}
	
	
	public String getMake() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		IOSElement table = (IOSElement)wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("VehicleInfoTable")));
		return table.findElementByAccessibilityId("Make").findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}

	public String getModel() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Model")).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}

	public String getYear() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Year")).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}
	
	public String getTrim() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Trim")).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}
	
	public String getEst() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Est#")).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}
	
	public String getTechnician() {
		return vehicleinfotbl.findElement(MobileBy.AccessibilityId("Tech")).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
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
		appiumdriver.findElementByAccessibilityId("Color").click();
		appiumdriver.findElementByAccessibilityId(color).click();
	}
	
	public void setMileage(String mileage) {
		appiumdriver.findElementByAccessibilityId("Mileage").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Mileage' and type = 'XCUIElementTypeCell'")).sendKeys(mileage);
		//appiumdriver.getKeyboard().sendKeys(mileage);
	}
	
	public void setFuelTankLevel(String fueltanklevel) {
		appiumdriver.findElementByAccessibilityId("Fuel Tank Level").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Fuel Tank Level' and type = 'XCUIElementTypeCell'")).sendKeys(fueltanklevel);
		//appiumdriver.getKeyboard().sendKeys(fueltanklevel);
		//element(
			//	MobileBy.name("Fuel Tank Level")).setValue(fueltanklevel);
	}
	
	public void setLicensePlate(String licplate) {
		appiumdriver.findElementByAccessibilityId("License Plate").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'License Plate' and type = 'XCUIElementTypeCell'")).sendKeys(licplate);
		//appiumdriver.getKeyboard().sendKeys(licplate);
	}

	public void setTech(String _tech) {
		clickTech();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(_tech)));
		appiumdriver.findElementByAccessibilityId(_tech).click();
	}
	
	public void selectLocation(String _location) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		if (!table.findElement(MobileBy.AccessibilityId("Location")).isDisplayed()) {
			swipeToElement(table.findElement(MobileBy.AccessibilityId("Location")));
		}
		table.findElement(MobileBy.AccessibilityId("Location")).click();
		//WebElement par = getVehicleInfoTableParentNode("Location");
		//par.findElement(By.xpath(".//XCUIElementTypeTextField")).click();
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void setType(String _type) {
		appiumdriver.findElementByAccessibilityId("Type").click();
		//selectUIAPickerValue(_type);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void setYear(String year) {
		appiumdriver.findElementByAccessibilityId("Year").click();
		selectUIAPickerValue(year);
		//((IOSElement) appiumdriver.findElementsByAccessibilityId("Year").get(1)).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		
		//TouchAction perform = new TouchAction(appiumdriver).tap(tapOptions().withElement(element(appiumdriver.findElementByAccessibilityId("Done")))).perform();
		
	}
	
	public void setTrim(String trimvalue)  {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Trim']")));
		appiumdriver.findElementByAccessibilityId("Trim").click();
		appiumdriver.findElementByAccessibilityId("Trim").click();;
		selectUIAPickerValue(trimvalue);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void clickTech() {

		JavascriptExecutor js1 = (JavascriptExecutor) appiumdriver;
		HashMap<String, String> scrollObject1 = new HashMap<String, String>();
		scrollObject1.put("direction", "up");
		js1.executeScript("mobile: swipe", scrollObject1);

		//if (!appiumdriver.findElementByAccessibilityId("Tech").isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeCell[@name='Tech']")));
		appiumdriver.findElementByAccessibilityId("Tech").click();
	}

	public void setStock(String stock) {
		appiumdriver.findElementByAccessibilityId("Stock#").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Stock#' and type = 'XCUIElementTypeCell'")).sendKeys(stock + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(stock);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		
	}

	public void setRO(String ro) {
		appiumdriver.findElementByAccessibilityId("RO#").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'RO#' and type = 'XCUIElementTypeCell'")).sendKeys(ro + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(ro);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setPO(String po) {
		appiumdriver.findElementByAccessibilityId("PO#").click();
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'PO#' and type = 'XCUIElementTypeCell'")).sendKeys(po + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(po);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public String getWorkOrderCustomer() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		return wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("viewPrompt"))).getAttribute("value");
	}
	
	public String getInspectionCustomer() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		return wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("viewPrompt"))).getAttribute("value");
	}

	public RegularNotesScreen clickNotesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Compose")));
		appiumdriver.findElementByAccessibilityId("Compose").click();
		return new RegularNotesScreen();
	}

	public String getWorkOrderTypeValue() {
		return regularwotypelabel.getAttribute("value");
	}

}
