package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
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
	
	//@iOSFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Price\"]")
    //private IOSElement pricecell;
	
	//@iOSFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Time\"]")
    //private IOSElement timecell;
	
	//@iOSFindBy(uiAutomator = "//XCUIElementTypeCell[@name=\"Price\"]/XCUIElementTypeTextField[1]")
    //private IOSElement pricevaluefld;
	
	//@iOSFindBy(accessibility = "Notes")
    //private IOSElement notescell;
	
	//@iOSFindBy(accessibility = "Technicians")
    //private IOSElement technicianscell;
	
	//@iOSFindBy(xpath = "//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[contains(@name,\"Technicians\")]/XCUIElementTypeStaticText[2]")
   // private IOSElement technicianscellvalue;
	
	/*@iOSFindBy(accessibility  = "Compose")
    private IOSElement composecell;
	
	@iOSFindBy(accessibility = "Clear")
    private IOSElement clearvehiclepartdatabtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;*/
	
	public PriceMatrixScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPriceMatrix(String pricematrix) {
		if (!appiumdriver.findElementByAccessibilityId(pricematrix).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(pricematrix),
					appiumdriver.findElementByAccessibilityId("PriceMatrixVehiclePartList"));
			//appiumdriver.findElementByAccessibilityId(wotype).click();
		}
		appiumdriver.findElementByAccessibilityId(pricematrix).click();
		Helpers.waitABit(1000);
		
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByAccessibilityId(pricematrix)).waitAction(Duration.ofSeconds(1)).release().perform();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + pricematrix + "']").click();

	}

	public void setSizeAndSeverity(String size, String severity) {
		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(MobileBy.AccessibilityId("Size")).click();
		appiumdriver.findElementByAccessibilityId("tableSize").findElement(MobileBy.AccessibilityId(size)).click();
		appiumdriver.findElementByAccessibilityId("tableSeverity").findElement(MobileBy.AccessibilityId(severity)).click();
		appiumdriver.findElementByAccessibilityId("Size & Severity").findElement(By.name("Save")).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Size & Severity']/XCUIElementTypeButton[@name='Save']").click();
	}

	public void setPrice(String price) {
		appiumdriver.findElementByAccessibilityId("Price").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(price + "\n");
		Helpers.waitABit(500);
	}
	
	public void setTime(String timevalue) {
		appiumdriver.findElementByAccessibilityId("Time").click();
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
		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(By.name(discaunt)).findElement(By.name("unselected")).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='"
		//		+ discaunt + "']/XCUIElementTypeButton[@name='unselected']").click();
	}

	public void clickDiscaunt(String discaunt) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId(discaunt)).waitAction(Duration.ofSeconds(1)).release().perform();
		//appiumdriver.findElementByAccessibilityId(discaunt).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[contains(@name,\""
		//				+ discaunt + "\")]").click();
	}
	
	public void switchOffOption(String optionname) {
		IOSElement switcher = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = '" + optionname + "' and type = 'XCUIElementTypeSwitch'"));
		if (switcher.getAttribute("value").equals("1"))
		//if (appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname + "']").getAttribute("value").equals("1"))
			switcher.click();
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		return appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(By.name(discaunt)).getAttribute("label");
		//return appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='"
		//				+ discaunt + "']").getAttribute("label");
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='"
						+ discaunt + "']").isDisplayed();
	}
	
	public boolean isPriceMatrixSelected(String pricematrix) {
		IOSElement pricematrixesVPList = (IOSElement) appiumdriver.findElementByAccessibilityId("PriceMatrixVehiclePartList");
		return pricematrixesVPList.findElementByAccessibilityId(pricematrix).findElementByAccessibilityId("selected").isDisplayed();
	}

	public void assertNotesExists() {
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId("Notes").isDisplayed());
	}

	public void assertTechniciansExists() {
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId("Technicians").isDisplayed());
	}

	public String getTechniciansValue() {
		return appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails")
		.findElement(By.xpath("//XCUIElementTypeCell[contains(@name,\"Technicians\")]/XCUIElementTypeStaticText[2]"))
		.getAttribute("name");
	}

	public void clickOnTechnicians() {
		appiumdriver.findElementByAccessibilityId("Technicians").click();
	}
	
	public void clickNotesButton() {
		appiumdriver.findElementByAccessibilityId("Compose").click();
	}

	public void clickSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		Helpers.waitABit(2000);
	}

	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Camcel").click();
	}
	
	public void clearVehicleData() {
		appiumdriver.findElementByAccessibilityId("Clear").click();
		String msg = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(msg, AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getPriceMatrixVehiclePartTotalPrice() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}


}
