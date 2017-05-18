package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularPriceMatrixScreen extends iOSRegularBaseScreen {
	
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
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Price\"]/UIAStaticText[@name=\"Price\"]")
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
    private IOSElement backbtn;
	
	public RegularPriceMatrixScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public WebElement getServiceCell(String servicename) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + servicename + "']/.."));
	}
	
	public void selectPriceMatrix(String pricematrix) {
		if (!appiumdriver.findElementByName(pricematrix).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + pricematrix + "']/..")));
		appiumdriver.findElement(MobileBy.AccessibilityId(pricematrix)).click();
	}

	public void setSizeAndSeverity(String size, String severity) {
		new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId("Size")).perform();
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(size)));
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + size + "']")).click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + severity + "']")).click();
		appiumdriver.findElementByAccessibilityId("Save").click();
		Helpers.waitABit(500);
	}

	public void setPrice(String price) {
		Helpers.waitABit(500);
		WebElement par = getTableParentCell("Price");
		par.findElement(By.xpath("//XCUIElementTypeTextField")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(price);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setTime(String timevalue) throws InterruptedException {
		WebElement par = getTableParentCell("Time");
		par.findElement(By.xpath("//XCUIElementTypeTextField")).sendKeys(timevalue + "\n");
	}

	public void assertPriceCorrect(String expectedprice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Price")));
		WebElement par = getTableParentCell("Price");
		Assert.assertEquals(par.findElement(By.xpath("//XCUIElementTypeTextField")).getAttribute("value"), expectedprice);
	}

	public void selectDiscaunt(String discaunt) {
		Helpers.waitABit(300);
		clickDiscaunt(discaunt);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.saveSelectedServiceDetails();
		Helpers.waitABit(500);
	}

	public void clickDiscaunt(String discaunt) {
		if (!appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + discaunt + "']/..")).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + discaunt + "']/..")));
		appiumdriver.findElementByAccessibilityId(discaunt).click();
	}
	
	public void switchOffOption(String optionname) {
		Helpers.waitABit(1000);
		if (appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").getAttribute("value").equals("true"))
			appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").click();
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		WebElement par = getTableParentCell(discaunt);
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
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

	public void assertNotesExists() {
		Assert.assertTrue(notescell.isDisplayed());
	}

	public void assertTechniciansExists() {
		Assert.assertTrue(technicianscell.isDisplayed());
	}

	public String getTechniciansValue() {
		Helpers.waitABit(1000);
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Technicians")));
		WebElement par = getTableParentCell("Technicians");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}

	public void clickOnTechnicians() {
		technicianscell.click();
		Helpers.waitABit(1000);
	}
	
	public void clickNotesButton() {
		composecell.click();
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void clickServicesButton() {
		servicesbtn.click();
	}	
	
	public void clickBackButton() {
		backbtn.click();
		Helpers.waitABit(1000);
	}
	
	public void clearVehicleData() {
		toolbardeletebtn.click();
		String msg = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(msg, AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
	}
	
	public String getPriceMatrixVehiclePartSubTotalPrice() {
		
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText")).getAttribute("value");
	}

	public String getInspectionSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("SubtotalAmount")).getAttribute("value");
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}
