package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
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

import java.util.List;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class VehicleScreen extends BaseWizardScreen {
		
	final static String vehiclescreencapt = "Vehicle";	
	
	//@iOSXCUITFindBy(accessibility  = "Advisor")
    //private IOSElement advisorfld;
	
	//@iOSXCUITFindBy(accessibility  = "Color")
    //private IOSElement colorfld;
	
	//@iOSXCUITFindBy(accessibility  = "Mileage")
   // private IOSElement mileagefld;
	
	//@iOSXCUITFindBy(accessibility  = "Fuel Tank Level")
    //private IOSElement fueltanklevelfld;
	
	//@iOSXCUITFindBy(accessibility  = "License Plate")
    //private IOSElement licenseplatefld;
	
	//@iOSXCUITFindBy(accessibility  = "Owner")
    //private IOSElement ownerfld;
	
	//@iOSXCUITFindBy(accessibility  = "Tech")
   // private IOSElement techfld;
	
	//@iOSXCUITFindBy(accessibility  = "Location")
    //private IOSElement locationfld;
	
	//@iOSXCUITFindBy(accessibility  = "Type")
    //private IOSElement typefld;
	
	//@iOSXCUITFindBy(accessibility  = "Year")
    //private IOSElement yearfld;
	
	//@iOSXCUITFindBy(accessibility  = "Trim")
    //private IOSElement trimfld;
	
	//@iOSXCUITFindBy(accessibility  = "Stock#")
    //private IOSElement stockfld;
	
	//@iOSXCUITFindBy(accessibility  = "RO#")
    //private IOSElement rofld;
	
	//@iOSXCUITFindBy(accessibility  = "PO#")
    //private IOSElement pofld;
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[@name=\"PO#\"]/XCUIElementTypeTextField")
   // private IOSElement pofldvalue;
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]")
    //private IOSElement inspnumberlabel;
	
	//@iOSXCUITFindBy(accessibility = "Done")
    //private IOSElement toolbardonebtn;

	
	//@iOSXCUITFindBy(accessibility  = "Compose")
    //private IOSElement composebtn;
	
	//@iOSXCUITFindBy(accessibility  = "Cancel")
    //private IOSElement cancelbtn;
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[4]")
    //private IOSElement wotypelabel;
	
	public VehicleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("VehicleTable")));
	}

	public String clickSaveWithAlert() {
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

		appiumdriver.findElementByAccessibilityId("VIN#").sendKeys(vin);
		((IOSDriver) appiumdriver).hideKeyboard();
	}
	
	public void clearVINCode() {
		clickVINField();
		BaseUtils.waitABit(1000);
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

	public void setVINAndAndSearch(String vin) {

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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(make)));
		appiumdriver.findElementByName(make).click();
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(model)));
		appiumdriver.findElementByName(model).click();
	}

	public void seletAdvisor(String advisor) {
		appiumdriver.findElementByAccessibilityId("Advisor").click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
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
		appiumdriver.findElementByAccessibilityId("Mileage").sendKeys(mileage + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setFuelTankLevel(String fueltanklevel)  {
		appiumdriver.findElementByAccessibilityId("Fuel Tank Level").click();
		appiumdriver.findElementByAccessibilityId("Fuel Tank Level").sendKeys(fueltanklevel + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setLicensePlate(String licplate)  {
		appiumdriver.findElementByAccessibilityId("License Plate").click();
		appiumdriver.findElementByAccessibilityId("License Plate").sendKeys(licplate + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void selectOwnerT(String owner)  {
		appiumdriver.findElementByAccessibilityId("Owner").click();
		try {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + owner + "']").click();
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

	public TechniciansPopup clickTech() {
		MobileElement vehicleTable = (MobileElement) appiumdriver.findElementByAccessibilityId("VehicleTable");
		if (!vehicleTable.findElementByAccessibilityId("Tech").isDisplayed())
			scrollToElement(vehicleTable.findElementByAccessibilityId("Tech"));
		vehicleTable.findElementByAccessibilityId("Tech").click();
		return new TechniciansPopup();
	}

	public void setStock(String stock) {
		appiumdriver.findElementByAccessibilityId("Stock#").click();
		appiumdriver.findElementByAccessibilityId("Stock#").sendKeys(stock + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void setRO(String ro)  {
		appiumdriver.findElementByAccessibilityId("RO#").click();
		appiumdriver.findElementByAccessibilityId("RO#").sendKeys(ro + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setPO(String po)  {
		appiumdriver.findElementByAccessibilityId("PO#").click();
		appiumdriver.findElementByAccessibilityId("PO#").sendKeys(po + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
		appiumdriver.findElementByAccessibilityId("Compose").click();
		//composebtn.click();
		return new NotesScreen();
	}
	
	public String getWorkOrderTypeValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[4]").getAttribute("value");
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
