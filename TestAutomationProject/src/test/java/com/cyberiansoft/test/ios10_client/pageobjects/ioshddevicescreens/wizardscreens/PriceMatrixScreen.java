package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Price\"]")
    //private IOSElement pricecell;
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[@name=\"Time\"]")
    //private IOSElement timecell;
	
	//@iOSXCUITFindByuiAutomator = "//XCUIElementTypeCell[@name=\"Price\"]/XCUIElementTypeTextField[1]")
    //private IOSElement pricevaluefld;
	
	//@iOSXCUITFindBy(accessibility = "Technicians")
    //private IOSElement technicianscell;
	
	//@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable[@name='PriceMatrixItemDetails']/XCUIElementTypeCell[contains(@name,\"Technicians\")]/XCUIElementTypeStaticText[2]")
   // private IOSElement technicianscellvalue;

	/*@iOSXCUITFindBy(accessibility  = "Compose")
    private IOSElement composecell;

	@iOSXCUITFindBy(accessibility = "Clear")
    private IOSElement clearvehiclepartdatabtn;

	@iOSXCUITFindBy(accessibility  = "Save")
    private IOSElement savebtn;

	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;*/

	@iOSXCUITFindBy(accessibility = "PriceMatrixVehiclePartList")
	private IOSElement priceMatrixVehiclePartList;

	@iOSXCUITFindBy(accessibility = "Technicians")
	private IOSElement technicianscell;

	@iOSXCUITFindBy(accessibility = "Notes")
	private IOSElement notescell;

	private static String viewMode = "PdrView";

	public PriceMatrixScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		//viewMode = "PdrView";
	}

	public void selectPriceMatrix(String pricematrix) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(pricematrix)));
		if (!appiumdriver.findElementByAccessibilityId(pricematrix).isDisplayed()) {
			swipeTableUp(appiumdriver.findElementByAccessibilityId(pricematrix),
					priceMatrixVehiclePartList);
		}
		appiumdriver.findElementByAccessibilityId(pricematrix).click();
	}

	public String getPriceMatrixVehiclePartPriceValue(String pricematrix) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(pricematrix)));
		return priceMatrixVehiclePartList.findElementByAccessibilityId(pricematrix).findElements(By.className("XCUIElementTypeStaticText")).get(1).getAttribute("value");
	}

	public void setSizeAndSeverity(String size, String severity) {

		appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetailsCellSize").click();
		appiumdriver.findElementByAccessibilityId("tableSize").findElement(MobileBy.AccessibilityId(size)).click();
		appiumdriver.findElementByAccessibilityId("tableSeverity").findElement(MobileBy.AccessibilityId(severity)).click();
		appiumdriver.findElementByAccessibilityId("Size & Severity").findElement(By.name("Save")).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan (MobileBy.AccessibilityId("Size & Severity"), 1));
		viewMode = "PdrView";
	}

	public void setPrice(String price) {
		MobileElement priceCell = (MobileElement) appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetailsCellPrice");
		priceCell.click();
		priceCell.findElementByClassName("XCUIElementTypeTextField").clear();
		priceCell.findElementByClassName("XCUIElementTypeTextField").setValue(price + "\n");
	}

	public void setTime(String timevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Time")));
		appiumdriver.findElementByAccessibilityId("Time").click();
		appiumdriver.findElementByAccessibilityId("Time").sendKeys(timevalue + "\n");
	}

	public String getPrice() {
		return getMaqtrixPanel().findElement(
				MobileBy.xpath("//XCUIElementTypeCell[@name='PriceMatrixItemDetailsCellPrice']/XCUIElementTypeTextField[1]")).
				getAttribute("value");
	}

	public void selectDiscaunt(String discaunt) {
		MobileElement table = getMaqtrixPanel();
		if (!table.findElementByAccessibilityId(discaunt).isDisplayed()) {
			scrollToElement(table.findElementByAccessibilityId(discaunt));
		}
		getMaqtrixPanel().findElement(By.name(discaunt)).findElement(By.name("unselected")).click();
	}

	public MobileElement getMaqtrixPanel() {
		return (MobileElement) appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails" + viewMode);
	}

	public void clickDiscaunt(String discaunt) {
		MobileElement table = getMaqtrixPanel();
		if (!table.findElementByAccessibilityId(discaunt).isDisplayed()) {
		    scrollToElement(table.findElementByAccessibilityId(discaunt));
		}
		if (!table.findElementByAccessibilityId(discaunt).isDisplayed())
			swipeTableUp(table.findElementByAccessibilityId(discaunt), table);

		getMaqtrixPanel().findElement(MobileBy.AccessibilityId(discaunt)).click();
	}

	public void switchOffOption(String optionname) {
		appiumdriver.findElementByAccessibilityId("Other").click();
		viewMode = "OtherView";
	}

	public String getDiscauntPriceAndValue(String discaunt) {
		return getMaqtrixPanel().findElement(By.name(discaunt)).getAttribute("label");
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

		return vehiclePart.findElementsByAccessibilityId("selected").size() > 0;
	}

	public boolean isNotesExists() {
		return notescell.isDisplayed();
	}

	public boolean isTechniciansExists() {
		return technicianscell.isDisplayed();
	}

	public String getTechniciansValue() {
		return getMaqtrixPanel()
		.findElement(By.xpath("//XCUIElementTypeCell[contains(@name,\"Technicians\")]/XCUIElementTypeStaticText[2]"))
		.getAttribute("name");
	}

	public TechniciansPopup openTechniciansPopup() {
		clickOnTechnicians();
		return new TechniciansPopup();
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

	public String getPriceMatrixTotalPriceValue() {
		MobileElement toolBar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeButton[@name='Delete']/..");
		return toolBar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}
}
