package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private IOSElement servicesbtn;
	
	@iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;*/
	
	public RegularPriceMatrixScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public WebElement getServiceCell(String servicename) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicename + "']/.."));
	}
	
	public void selectPriceMatrix(String pricematrix) {
		if (!appiumdriver.findElementByName(pricematrix).isDisplayed()) {
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + pricematrix + "']/..")));
			appiumdriver.findElement(MobileBy.AccessibilityId(pricematrix)).click();
		}
		appiumdriver.findElement(MobileBy.AccessibilityId(pricematrix)).click();
	}

	public void setSizeAndSeverity(String size, String severity) {
		new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId("Size")).perform();
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(size)));
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + size + "']")).click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + severity + "']")).click();
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

	public void setPrice(String price) {
		WebElement par = getTableParentCell("Price");
		par.findElement(By.xpath("//XCUIElementTypeTextField")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(price);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setTime(String timevalue) throws InterruptedException {
		WebElement par = getTableParentCell("Time");
		par.findElement(By.xpath("//XCUIElementTypeTextField")).sendKeys(timevalue + "\n");
	}

	public String getPrice() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Price")));
		WebElement par = getTableParentCell("Price");
		return par.findElement(By.xpath("//XCUIElementTypeTextField")).getAttribute("value");
	}

	public void selectDiscaunt(String discaunt) {
		clickDiscaunt(discaunt);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.saveSelectedServiceDetails();
	}

	public void clickDiscaunt(String discaunt) {
		MobileElement elDiscount = (MobileElement) appiumdriver.findElementByAccessibilityId(discaunt);
		if (!elDiscount.isDisplayed()) {
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + discaunt + "']/..")));
			//appiumdriver.findElementByAccessibilityId(discaunt).click();
		}
		appiumdriver.findElementByAccessibilityId(discaunt).click();
	}
	
	public void switchOffOption(String optionname) {
		if (appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").getAttribute("value").equals("1"))
			appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").click();
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		WebElement par = getTableParentCell(discaunt);
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value").replaceAll("[^a-zA-Z0-9$.,% ]", " ").trim();
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementsByAccessibilityId(discaunt).size() > 0;
	}
	
	/*public boolean isPriceMatrixSelected(String pricematrix) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ pricematrix + "\"]/UIAButton[@name=\"selected\"]").isDisplayed();
	}*/
	
	public boolean isPriceMatrixContainsPriceValue(String pricematrix, String pricevalue) {
		WebElement par = getTableParentCell(pricematrix);
		return par.findElements(By.xpath("//XCUIElementTypeStaticText[@name=\""+ pricevalue + "\"]")).size() > 0;
	}

	public boolean isNotesExists() {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='Notes']")).isDisplayed();
	}

	public boolean isTechniciansExists() {
		return appiumdriver.findElementByAccessibilityId("Technicians").isDisplayed();
	}

	public String getTechniciansValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Technicians")));
		WebElement par = getTableParentCell("Technicians");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}

	public void clickOnTechnicians() {
        appiumdriver.findElementByAccessibilityId("Technicians").click();
	}
	
	public void clickNotesButton() {
        appiumdriver.findElementByAccessibilityId("Compose").click();
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
	
	public void clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Back"))).click();
	}
	
	public String clearVehicleData() {
        appiumdriver.findElementByAccessibilityId("Delete").click();
		return Helpers.getAlertTextAndAccept();
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		return appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
	}

	public String getInspectionSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("SubtotalAmount")).getAttribute("value");
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}
