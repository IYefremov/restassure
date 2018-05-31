package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularVehicleScreen extends RegularBaseWizardScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton")
    private IOSElement makecustombtn;
	
	/*@iOSFindBy(accessibility = "Save")
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
	
	@iOSFindBy(accessibility = "RO#")
    private IOSElement rofld;
	
	@iOSFindBy(accessibility = "PO#")
    private IOSElement pofld;*/
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    private IOSElement inspnumberlabel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[2]")
    private IOSElement regularinspnumberlabel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[1]")
    private IOSElement regularwotypelabel;
	
	/*@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	@iOSFindBy(accessibility = "Compose")
    private IOSElement composebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;*/
	
	public RegularVehicleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("VehicleInfoTable"))); 
	}

	public static String getVehicleScreenCaption() {
		return vehiclescreencapt;
	}

	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
	
	public void setVINValue(String vin)  {
		clickVINField();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void setVIN(String vin)  {
		setVINValue(vin);
		Helpers.waitABit(1000);
		List<IOSElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (IOSElement closebtn : closebtns)
			if (closebtn.isDisplayed()) {
				closebtn.click();
				break;
			}
        try {
            if (appiumdriver.findElementsByAccessibilityId("Searching on Back Office").size() > 0) {
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
			}
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
		appiumdriver.getKeyboard().sendKeys(vin + "\n");
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("No vehicle invoice history found")));
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId("No vehicle invoice history found").isDisplayed());
		appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public void verifyExistingWorkOrdersDialogAppears() throws InterruptedException {
		Assert.assertTrue(appiumdriver.findElements(MobileBy.iOSNsPredicateString("value BEGINSWITH 'Existing work orders were found'")).size() > 0);
		appiumdriver.findElementByAccessibilityId("Close")
				.click();
	}
	
	public IOSElement getInspectionNumberLabel() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return (IOSElement) toolbar.findElementByIosNsPredicate("name CONTAINS 'E-'");
		//return regularinspnumberlabel;
	}
	
	public String getWorkOrderTypeValue() {
		return regularwotypelabel.getAttribute("value");
	}
	
	public String getInspectionNumber() {
		return getInspectionNumberLabel().getText();
	}
	
	public String getWorkOrderNumber() {
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
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell[@name='Tech']/XCUIElementTypeTextField[1]")).getAttribute("value");
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
		appiumdriver.getKeyboard().sendKeys(mileage);
	}
	
	public void setFuelTankLevel(String fueltanklevel) {
		appiumdriver.findElementByAccessibilityId("Fuel Tank Level").click();
		appiumdriver.getKeyboard().sendKeys(fueltanklevel);
		//element(
			//	MobileBy.name("Fuel Tank Level")).setValue(fueltanklevel);
	}
	
	public void setLicensePlate(String licplate) {
		appiumdriver.findElementByAccessibilityId("License Plate").click();
		appiumdriver.getKeyboard().sendKeys(licplate);
	}

	public void setTech(String _tech) throws InterruptedException {
		clickTech();
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
	
	public void setType(String _type) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Type").click();
		//selectUIAPickerValue(_type);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void setYear(String year) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Year").click();
		selectUIAPickerValue(year);
		//((IOSElement) appiumdriver.findElementsByAccessibilityId("Year").get(1)).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		
		//TouchAction perform = new TouchAction(appiumdriver).tap(tapOptions().withElement(element(appiumdriver.findElementByAccessibilityId("Done")))).perform();
		
	}
	
	public void setTrim(String trimvalue) throws InterruptedException {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Trim']")));
		appiumdriver.findElementByAccessibilityId("Trim").click();
		appiumdriver.findElementByAccessibilityId("Trim").click();;
		selectUIAPickerValue(trimvalue);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void clickTech() {
		if (!appiumdriver.findElementByAccessibilityId("Tech").isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeCell[@name='Tech']")));
		appiumdriver.findElementByAccessibilityId("Tech").click();
	}

	public void setStock(String stock) {
		appiumdriver.findElementByAccessibilityId("Stock#").click();
		//appiumdriver.findElementByAccessibilityId("RO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(stock);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		
	}

	public void setRO(String ro) {
		appiumdriver.findElementByAccessibilityId("RO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ro);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setPO(String po) throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("PO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
		return new RegularNotesScreen(appiumdriver);
	}
	
	public WebElement getVehicleInfoTableParentNode(String wonumber) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='VehicleInfoTable']/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + wonumber + "']/.."));
	}

}
