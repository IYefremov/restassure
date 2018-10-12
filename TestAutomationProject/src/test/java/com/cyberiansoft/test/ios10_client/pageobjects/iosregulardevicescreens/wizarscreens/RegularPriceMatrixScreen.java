package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
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
	
	/*@iOSFindBy(xpath = "//UIATableCell[@name=\"Price\"]/UIAStaticText[@name=\"Price\"]")
    private IOSElement pricecell;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Time\"]/UIAStaticText[@name=\"Time\"]")
    private IOSElement timecell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Price\"]/UIATextField[1]")
    private IOSElement pricevaluefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='Notes']")
    private IOSElement notescell;
	
	@iOSFindBy(accessibility = "Technicians")
    private IOSElement technicianscell;
	
	@iOSFindBy(accessibility = "Compose")
    private IOSElement composecell;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Delete")
    private IOSElement toolbardeletebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility = "Services")
    private IOSElement servicesbtn;*/
	
	@iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	public RegularPriceMatrixScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitPriceMatrixScreenLoad() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'PriceMatrixVehicleParts'")));
	}
	
	public RegularVehiclePartScreen selectPriceMatrix(String pricematrix) {
		if (!appiumdriver.findElementByName(pricematrix).isDisplayed()) {
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + pricematrix + "']/..")));
		}
		appiumdriver.findElement(MobileBy.AccessibilityId(pricematrix)).click();
		return new RegularVehiclePartScreen();
	}
	
	public boolean isPriceMatrixContainsPriceValue(String pricematrix, String pricevalue) {
		WebElement par = getTableParentCell(pricematrix);
		return par.findElements(By.xpath("//XCUIElementTypeStaticText[@name=\""+ pricevalue + "\"]")).size() > 0;
	}

	public void clickCancelButton() {
        appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public void clickServicesButton() {
		List<WebElement> serviesbtns = appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElements(By.name("Services"));
		
		for(WebElement srvcbtn : serviesbtns){
			if (srvcbtn.isDisplayed())
				srvcbtn.click();
		}
		
		//appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(By.name("Services")).click();
		//servicesbtn.click();
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
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}
