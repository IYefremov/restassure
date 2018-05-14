package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class VehicleScreen extends BaseWizardScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	//@iOSFindBy(accessibility  = "Advisor")
    //private IOSElement advisorfld;
	
	//@iOSFindBy(accessibility  = "Color")
    //private IOSElement colorfld;
	
	//@iOSFindBy(accessibility  = "Mileage")
   // private IOSElement mileagefld;
	
	//@iOSFindBy(accessibility  = "Fuel Tank Level")
    //private IOSElement fueltanklevelfld;
	
	//@iOSFindBy(accessibility  = "License Plate")
    //private IOSElement licenseplatefld;
	
	//@iOSFindBy(accessibility  = "Owner")
    //private IOSElement ownerfld;
	
	//@iOSFindBy(accessibility  = "Tech")
   // private IOSElement techfld;
	
	//@iOSFindBy(accessibility  = "Location")
    //private IOSElement locationfld;
	
	//@iOSFindBy(accessibility  = "Type")
    //private IOSElement typefld;
	
	//@iOSFindBy(accessibility  = "Year")
    //private IOSElement yearfld;
	
	//@iOSFindBy(accessibility  = "Trim")
    //private IOSElement trimfld;
	
	//@iOSFindBy(accessibility  = "Stock#")
    //private IOSElement stockfld;
	
	//@iOSFindBy(accessibility  = "RO#")
    //private IOSElement rofld;
	
	//@iOSFindBy(accessibility  = "PO#")
    //private IOSElement pofld;
	
	//@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"PO#\"]/XCUIElementTypeTextField")
   // private IOSElement pofldvalue;
	
	//@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    //private IOSElement inspnumberlabel;
	
	//@iOSFindBy(accessibility = "Done")
    //private IOSElement toolbardonebtn;

	
	//@iOSFindBy(accessibility  = "Compose")
    //private IOSElement composebtn;
	
	//@iOSFindBy(accessibility  = "Cancel")
    //private IOSElement cancelbtn;
	
	//@iOSFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[4]")
    //private IOSElement wotypelabel;
	
	public VehicleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("VehicleTable")));
	}

	public static String getVehicleScreenCaption() {
		return vehiclescreencapt;
	}

	public String clickSaveWithAlert() throws InterruptedException {
		clickSave();
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) {
	
		setVINFieldValue(vin);
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
	
	public void setVINFieldValue(String vin) {
		
		clickVINField();

		//appiumdriver.findElementByAccessibilityId("VIN#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		//Helpers.waitABit(500);
		try {
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		} catch (WebDriverException e) {
			
		}
		//((IOSDriver) appiumdriver).hideKeyboard();
	}
	
	public void clearVINCode() {
		clickVINField();
		for (int i = 0; i < 17; i++)
			((IOSDriver) appiumdriver).getKeyboard().sendKeys(Keys.DELETE);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		
	}
	
	public IOSElement getVINField() {
		//IOSElement vehicletable = (IOSElement)  appiumdriver.findElementByAccessibilityId("VehicleTable");
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
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
						.findElement(
				MobileBy.name("No vehicle invoice history found")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
	}
	
	public void verifyExistingWorkOrdersDialogAppears() {
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Existing work orders were found for this vehicle")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
	}

	public String getInspectionNumber() {
		return appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[3]")).getText();
	}

	public void setMakeAndModel(String make, String model) {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Make");
		makefld.findElementByAccessibilityId("custom detail button").click();
		//makecustombtn.click();
		appiumdriver.findElementByName(make).click();
		appiumdriver.findElementByName(model).click();
	}

	public void seletAdvisor(String advisor) {
		appiumdriver.findElementByAccessibilityId("Advisor").click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
		//appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
		//				+ advisor + "\"]").click();
	}
	
	
	public String getMake() {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Make");
		return makefld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}

	public String getModel() {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Model");
		return makefld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}

	public String getYear() {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Year");
		return makefld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}
	
	public String getTechnician() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		IOSElement techfld = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Tech"))); 
		return techfld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}
	
	public String getEst() {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Est#");
		return makefld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}
	
	public String getTrim() {
		IOSElement makefld = (IOSElement) appiumdriver.findElementByAccessibilityId("Trim");
		return makefld.findElementByClassName("XCUIElementTypeTextField").getAttribute("value");
	}
	
	public void verifyMakeModelyearValues(String exp_make, String exp_model, String exp_year) {
		Assert.assertEquals(getMake(), exp_make);
		Assert.assertEquals(getModel(), exp_model);
		Assert.assertEquals(getYear(), exp_year);
	}
	
	public void cancelOrder() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
		acceptAlert();
	}
	
	public void setColor(String color) {
		appiumdriver.findElementByAccessibilityId("Color").click();
		appiumdriver.findElementByName(color).click();
		appiumdriver.hideKeyboard();
	}
	
	public void setMileage(String mileage) {
		appiumdriver.findElementByAccessibilityId("Mileage").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(mileage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setFuelTankLevel(String fueltanklevel)  {
		appiumdriver.findElementByAccessibilityId("Fuel Tank Level").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(fueltanklevel);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setLicensePlate(String licplate)  {
		appiumdriver.findElementByAccessibilityId("License Plate").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(licplate);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void selectOwnerT(String owner)  {
		appiumdriver.findElementByAccessibilityId("Owner").click();
		try {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + owner + "']")).waitAction(Duration.ofSeconds(1)).release().perform();
		} catch (WebDriverException e) {
			appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + owner + "']").click();
		}
	}

	public void setTech(String _tech)  {
		clickTech();
		appiumdriver.findElementByName(_tech).click();
	}
	
	public void selectLocation(String _location) {
		appiumdriver.findElementByAccessibilityId("Location").click();
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setType(String _type)  {
		appiumdriver.findElementByAccessibilityId("Type").click();
		selectUIAPickerValue(_type);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void setYear(String year) {
		
		appiumdriver.findElementByAccessibilityId("Year").click();

		IOSElement picker = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePicker");
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");
		//picker.setValue(year);
		pickerwhl.setValue(year);
		
		IOSElement donebtn = (IOSElement) appiumdriver.findElementByAccessibilityId("StringPicker_Done");
		
		new TouchAction(appiumdriver).tap(tapOptions().withElement(element(donebtn))).perform();
		/*if (appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@label='Done']").size() > 1)
			donebtn = ((IOSElement) appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@label='Done']").get(1));
		else
			donebtn = (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeButton[@label='Done']");*/
		
		//IOSTouchAction iosTouchAction = new IOSTouchAction(appiumdriver);
		//iosTouchAction.tap(xx,yy).perform();
		
		/*if (appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@label='Done']").size() > 1)
			((IOSElement) appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@label='Done']").get(1)).click();
		else
			appiumdriver.findElementByXPath("//XCUIElementTypeButton[@label='Done']").click();*/
			
	}
	
	public void setTrim(String trimvalue) {
		
		appiumdriver.findElementByAccessibilityId("Trim").click();
		selectUIAPickerValue(trimvalue);
		
		if (appiumdriver.findElementsByAccessibilityId("Done").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Done").get(1)).click();
		else
			appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void clickTech() {
		appiumdriver.findElementByAccessibilityId("Tech").click();
	}

	public void setStock(String stock) {
		appiumdriver.findElementByAccessibilityId("Stock#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(stock);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void setRO(String ro)  {
		appiumdriver.findElementByAccessibilityId("RO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ro);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setPO(String po)  {
		appiumdriver.findElementByAccessibilityId("PO#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
		action.press(appiumdriver.findElementByAccessibilityId("Compose")).waitAction(Duration.ofSeconds(1)).release().perform();
		//composebtn.click();
		return new NotesScreen(appiumdriver);
	}
	
	public String getWorkOrderTypeValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[4]").getAttribute("value");
	}

	public void clickNavigationBarSaveButton() {
		appiumdriver.findElement(By.xpath("//XCUIElementTypeNavigationBar[@name='Vehicle']/XCUIElementTypeButton[@name='Save']")).click();
	}
	
	public void saveWorkOrder() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
}
