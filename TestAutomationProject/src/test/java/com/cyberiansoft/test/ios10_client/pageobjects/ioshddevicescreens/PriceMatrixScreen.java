package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class PriceMatrixScreen extends iOSHDBaseScreen {
	
	//Sizes
	public final static String DIME_SIZE = "DIME";
	public final static String NKL_SIZE = "NKL";
	public final static String QTR_SIZE = "QTR";
	public final static String HLF_SIZE = "HLF";
	public final static String QUICK_QUOTE_SIZE = "Quick Quote";
	
	//Severities
	public final static String VERYLIGHT_SEVERITY = "Very Light (1-5 Dents)";
	public final static String LIGHT_SEVERITY = "Light (6-15 Dents)";
	public final static String HEAVY_SEVERITY = "Heavy (51-75 Dents)";
	public final static String MEDIUM_SEVERITY = "Medium (31-50 Dents)";
	public final static String MODERATE_SEVERITY = "Moderate (16-30 Dents)";
	public final static String SEVERE_SEVERITY = "Severe (76-100 Dents)";
	public final static String QUICK_QUOTE_SEVERITY = "Quick Quote";
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Price\"]")
    private IOSElement pricecell;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Time\"]")
    private IOSElement timecell;
	
	@iOSFindBy(uiAutomator = "//XCUIElementTypeCell[@name=\"Price\"]/XCUIElementTypeTextField[1]")
    private IOSElement pricevaluefld;
	
	@iOSFindBy(accessibility = "Notes")
    private IOSElement notescell;
	
	@iOSFindBy(accessibility = "Technicians")
    private IOSElement technicianscell;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[contains(@name,\"Technicians\")]/XCUIElementTypeStaticText[2]")
    private IOSElement technicianscellvalue;
	
	@iOSFindBy(accessibility  = "Compose")
    private IOSElement composecell;
	
	@iOSFindBy(accessibility = "Clear")
    private IOSElement clearvehiclepartdatabtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	public PriceMatrixScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPriceMatrix(String pricematrix) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId(pricematrix)).waitAction(1000).release().perform();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + pricematrix + "']").click();

	}

	public void setSizeAndSeverity(String size, String severity) {
		appiumdriver.findElementByAccessibilityId("Size").click();
		appiumdriver.findElementByAccessibilityId(size).click();
		appiumdriver.findElementByAccessibilityId(severity).click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Size & Severity']/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(500);
	}

	public void setPrice(String price) {
		pricecell.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(price + "\n");
		Helpers.waitABit(500);
	}
	
	public void setTime(String timevalue) {
		timecell.click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(timevalue + "\n");
		Helpers.waitABit(500);
	}

	/*public void setSeverity(String severity) {
		Helpers.text_exact("Severity").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ severity + "\"]").click();
	}*/

	public void assertPriceCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElement(
				MobileBy.xpath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='PriceMatrixItemDetailsCellPrice']/XCUIElementTypeTextField[1]")).
				getAttribute("value"),price);
	}

	public void selectDiscaunt(String discaunt) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='"
				+ discaunt + "']/XCUIElementTypeButton[@name='unselected']").click();
	}

	public void clickDiscaunt(String discaunt) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId(discaunt)).waitAction(1000).release().perform();
		//appiumdriver.findElementByAccessibilityId(discaunt).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[contains(@name,\""
		//				+ discaunt + "\")]").click();
	}
	
	public void switchOffOption(String optionname) {
		if (appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname + "']").getAttribute("value").equals("true"))
			((IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname + "']")).click();
		Helpers.waitABit(1000);
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='"
						+ discaunt + "']").getAttribute("label");
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='"
						+ discaunt + "']").isDisplayed();
	}
	
	public boolean isPriceMatrixSelected(String pricematrix) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixVehiclePartList']/XCUIElementTypeCell[@name='"
						+ pricematrix + "']/XCUIElementTypeButton[@name='selected']").isDisplayed();
	}

	public void assertNotesExists() {
		Assert.assertTrue(notescell.isDisplayed());
	}

	public void assertTechniciansExists() {
		Assert.assertTrue(technicianscell.isDisplayed());
	}

	public String getTechniciansValue() {
		return technicianscellvalue.getAttribute("name");
	}

	public void clickOnTechnicians() {
		technicianscell.click();
	}
	
	public void clickNotesButton() {
		composecell.click();
	}

	public void clickSaveButton() {
		savebtn.click();
		Helpers.waitABit(2000);
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void clearVehicleData() {
		clearvehiclepartdatabtn.click();
		Helpers.waitABit(1000);
		String msg = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(msg, AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getPriceMatrixVehiclePartTotalPrice() {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeStaticText[1]")).getAttribute("value");
	}


}
