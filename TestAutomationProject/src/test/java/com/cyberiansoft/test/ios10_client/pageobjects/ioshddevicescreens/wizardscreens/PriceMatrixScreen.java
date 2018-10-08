package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PriceMatrixScreen extends BaseWizardScreen {
	
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

	@iOSFindBy(accessibility = "Technicians")
	private IOSElement technicianscell;

	@iOSFindBy(accessibility = "Notes")
	private IOSElement notescell;
	
	public PriceMatrixScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void selectPriceMatrix(String pricematrix) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(pricematrix)));
		if (!appiumdriver.findElementByAccessibilityId(pricematrix).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(pricematrix),
					appiumdriver.findElementByAccessibilityId("PriceMatrixVehiclePartList"));
			//appiumdriver.findElementByAccessibilityId(wotype).click();
		}
		appiumdriver.findElementByAccessibilityId(pricematrix).click();
		
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByAccessibilityId(pricematrix)).waitAction(Duration.ofSeconds(1)).release().perform();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + pricematrix + "']").click();

	}

	public void setSizeAndSeverity(String size, String severity) {
		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(MobileBy.AccessibilityId("Size")).click();
		appiumdriver.findElementByAccessibilityId("tableSize").findElement(MobileBy.AccessibilityId(size)).click();
		appiumdriver.findElementByAccessibilityId("tableSeverity").findElement(MobileBy.AccessibilityId(severity)).click();
		appiumdriver.findElementByAccessibilityId("Size & Severity").findElement(By.name("Save")).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan (MobileBy.AccessibilityId("Size & Severity"), 1));
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Size & Severity']/XCUIElementTypeButton[@name='Save']").click();
	}

	public void setPrice(String price) {
		appiumdriver.findElementByAccessibilityId("Price").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(price + "\n");
	}
	
	public void setTime(String timevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Time")));
		appiumdriver.findElementByAccessibilityId("Time").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(timevalue + "\n");
	}

	/*public void setSeverity(String severity) {
		Helpers.text_exact("Severity").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ severity + "\"]").click();
	}*/

	public String getPrice() {
		return appiumdriver.findElement(
				MobileBy.xpath("//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[@name='PriceMatrixItemDetailsCellPrice']/XCUIElementTypeTextField[1]")).
				getAttribute("value");
	}

	public void selectDiscaunt(String discaunt) {
		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(By.name(discaunt)).findElement(By.name("unselected")).click();
	}

	public void clickDiscaunt(String discaunt) {
		IOSElement table = (IOSElement) appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails");
		if (!table.findElementByAccessibilityId(discaunt).isDisplayed()) {
		    scrollToElement(discaunt);
			//swipeTableUp(table.findElementByAccessibilityId(discaunt),
			//		table);
		}
		if (!table.findElementByAccessibilityId(discaunt).isDisplayed())
			swipeTableUp(table.findElementByAccessibilityId(discaunt), table);

		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(MobileBy.AccessibilityId(discaunt)).click();
	}
	
	public void switchOffOption(String optionname) {
		IOSElement switcher = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = '" + optionname + "' and type = 'XCUIElementTypeSwitch'"));
		if (switcher.getAttribute("value").equals("1"))
			switcher.click();
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		return appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(By.name(discaunt)).getAttribute("label");
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='"
						+ discaunt + "']").isDisplayed();
	}
	
	public boolean isPriceMatrixSelected(String pricematrix) {
		BaseUtils.waitABit(1000);
		IOSElement pricematrixesVPList = (IOSElement) new WebDriverWait(appiumdriver, 10).
					until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PriceMatrixVehiclePartList")));
		IOSElement vehiclePart = (IOSElement) pricematrixesVPList.findElementByAccessibilityId(pricematrix);
		//IOSElement vehiclePart = (IOSElement) new WebDriverWait(appiumdriver, 10).
		//	until(ExpectedConditions.elementToBeClickable(pricematrixesVPList.findElementByAccessibilityId(pricematrix)));

		return vehiclePart.findElementsByAccessibilityId("selected").size() > 0;
	}

	public boolean isNotesExists() {
		return notescell.isDisplayed();
	}

	public boolean isTechniciansExists() {
		return technicianscell.isDisplayed();
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
	}

	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Camcel").click();
	}
	
	public String clearVehicleData() {
		appiumdriver.findElementByAccessibilityId("Clear").click();
		return Helpers.getAlertTextAndAccept();
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getPriceMatrixVehiclePartTotalPrice() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}


}
