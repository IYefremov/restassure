package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
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

import java.util.List;

public class RegularPriceMatrixScreen extends RegularBaseWizardScreen {
	
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
	
	/*@iOSXCUITFindBy(xpath = "//UIATableCell[@name=\"Price\"]/UIAStaticText[@name=\"Price\"]")
    private IOSElement pricecell;
	
	@iOSXCUITFindBy(xpath = "//UIATableCell[@name=\"Time\"]/UIAStaticText[@name=\"Time\"]")
    private IOSElement timecell;
	
	@iOSXCUITFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Price\"]/UIATextField[1]")
    private IOSElement pricevaluefld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='Notes']")
    private IOSElement notescell;
	
	@iOSXCUITFindBy(accessibility = "Technicians")
    private IOSElement technicianscell;
	
	@iOSXCUITFindBy(accessibility = "Compose")
    private IOSElement composecell;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Delete")
    private IOSElement toolbardeletebtn;
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSXCUITFindBy(accessibility = "Services")
    private IOSElement servicesbtn;*/

	@iOSXCUITFindBy(accessibility = "PriceMatrixVehicleParts")
	private IOSElement priceMatrixVehiclePartsTable;
	
	@iOSXCUITFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	public RegularPriceMatrixScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitPriceMatrixScreenLoad() {
		WaitUtils.elementShouldBeVisible(priceMatrixVehiclePartsTable, true);
	}
	
	public void selectPriceMatrix(String pricematrix) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		MobileElement priceMatrixVeihclePartList = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PriceMatrixVehiclePartList")));
		MobileElement priceMatrixCell = priceMatrixVeihclePartList.findElementByAccessibilityId(pricematrix);
		if (!priceMatrixCell.isDisplayed()) {
			SwipeUtils.swipeToElement(pricematrix);
		}
		priceMatrixCell.findElement(MobileBy.AccessibilityId(pricematrix)).click();
	}
	
	public String getVehiclePartPriceValue(String pricematrix) {
		waitPriceMatrixScreenLoad();
		return appiumdriver.findElementByAccessibilityId("PriceMatrixVehiclePartList").
				findElement(MobileBy.AccessibilityId(pricematrix)).findElements(MobileBy.className("XCUIElementTypeStaticText")).
				get(1).getText();
	}

	public void clickCancelButton() {
        appiumdriver.findElementByAccessibilityId("Cancel").click();
	}

	public void clickNotesButton() {
		appiumdriver.findElementByAccessibilityId("Compose").click();
	}
	
	public void clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back"))).click();
		backbtn.click();
	}
	
	public String clearVehicleData() {
        appiumdriver.findElementByAccessibilityId("Clear").click();
		return Helpers.getAlertTextAndAccept();
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		String priceValue = appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
		return priceValue.substring(priceValue.indexOf("$"), priceValue.length());
	}

	public String getInspectionSubTotalPrice() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("SubtotalAmount")));
		return appiumdriver.findElement(MobileBy.AccessibilityId("SubtotalAmount")).getAttribute("value");
	}

}
