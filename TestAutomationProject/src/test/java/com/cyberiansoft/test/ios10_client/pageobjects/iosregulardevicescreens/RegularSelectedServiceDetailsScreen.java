package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularSelectedServiceDetailsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[contains(@name,\"Vehicle Part\")]/UIAStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartscell;
	
	@iOSFindBy(accessibility = "Questions")
    private IOSElement questionsfld;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Remove\"]")
    private IOSElement removeservice;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility = "Notes")
    private IOSElement notesfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Vehicle Parts\"]")
    private IOSElement vehiclepartsfldname;
	
	@iOSFindBy(accessibility  = "Service Part")
    private IOSElement servicepartcell;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Custom\"]")
    private IOSElement technitianscustomview;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Evenly\"]")
    private IOSElement technitiansevenlyview;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Cancel\"]")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAKey[@name=\"Delete\"]")
    private IOSElement keyboarddeletebtn;
	
	
	public RegularSelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Helpers.waitABit(500);
	}

	public void assertServicePriceValue(String expectedprice) {
		WebElement par = getTableParentCell("Price");
		Assert.assertEquals(par.findElement(By.xpath("./XCUIElementTypeTextField")).getAttribute("value"), expectedprice);
	}

	public void assertServiceAdjustmentsValue(String adjustments) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Adjustments")));
		WebElement par = getTableParentCell("Adjustments");
		
		Assert.assertEquals(par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).getText(), adjustments);
	}

	public void setServicePriceValue(String _quantity)
			throws InterruptedException {
		Helpers.waitABit(500);
		WebElement par = getTableParentCell("Price");
		//par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).click();
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).clear();
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_quantity);
	}

	public void clickVehiclePartsCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(vehiclepartscell)).click();
	}
	
	public void clickNotesCell() {
		notesfld.click();
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("value");
		//return vehiclepartsfld.getAttribute("name");
	}

	public void answerQuestion(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void answerQuestion2(String answer) {

		questionsfld.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void answerQuestionCheckButton() {

		questionsfld.click();
		Helpers.waitABit(500);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		Helpers.waitABit(1000);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		Helpers.waitABit(1000);
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void answerTaxPoint1Question(String answer) {
		Helpers.waitABit(500);
		questionsfld.click();
		Helpers.waitABit(500);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[6]")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}
	
	public boolean isQuestionFormCellExists() {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists =  appiumdriver.findElements(MobileBy.AccessibilityId("Questions")).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.MILLISECONDS);
		return exists;
	}

	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Quantity")));
		WebElement par = getTableParentCell("Quantity");
		
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Quantity");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Quantity");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_quantity);
	}
	
	public void setServiceTimeValue(String _timevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Time")));	
        WebElement par = getTableParentCell("Time");
		
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Time");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Time");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_timevalue);
	}
	
	public void setServiceRateValue(String _ratevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Rate")));	
        WebElement par = getTableParentCell("Rate");
		
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Rate");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Rate");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_ratevalue);
	}

	public void assertAdjustmentValue(String adjustment,
			String adjustmentvalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(adjustment)));
		WebElement par = getTableParentCell(adjustment);
		
		Assert.assertEquals(par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).getText(), adjustmentvalue);
	}

	public void selectAdjustment(String adjustment) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(adjustment)));
		WebElement par = getTableParentCell(adjustment);
		
		par.findElement(By.xpath(".//XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public void selectBundle(String bundle) {
		WebElement par = getTableParentCell(bundle);
		par.findElement(By.xpath(".//XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		WebElement par = getTableParentCell(bundle);
		par.findElement(By.xpath(".//XCUIElementTypeButton[@name='custom detail button']")).click();
		setServiceQuantityValue(_quantity);
	}
	
	public RegularPriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + matrics + "']")).click();;
		return new RegularPriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return vehiclepartsfldname.isDisplayed();
	}

	public void saveSelectedServiceDetails() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();	
		Helpers.waitABit(500);
	}

	public String saveSelectedServiceDetailsWithAlert()
			throws InterruptedException {
		saveSelectedServiceDetails();
		return Helpers.getAlertTextAndAccept();
	}

	public void selectTechniciansCustomView() {
		technitianscustomview.click();
	}

	public void selectTechniciansEvenlyView() {
		technitiansevenlyview.click();
	}
	
	public void removeService() throws InterruptedException {
		removeservice.click();
		Helpers.acceptAlertIfExists();
	}

	public void setTechnicianCustomPriceValue(String technician,
			String _quantity) throws InterruptedException {

		if (elementExists("//UIAPopover[1]")) {
			appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAStaticText[1]").click();
		} else {
			appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
							+ technician + "\")]/UIAStaticText[1]").click();
		}

		if (elementExists("//UIATableView/UIATableCell[contains(@name, \""
						+ technician
						+ "\")]/UIATextField[1]/UIAButton[@name=\"Clear text\"]")) {

			((IOSElement) appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
							+ technician
							+ "\")]/UIATextField[1]")).setValue("");
		}
		Helpers.keyboadrType(_quantity);
		Helpers.keyboadrType("\n");
	}

	public String getAdjustmentsValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"Adjustments\"]/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public String getServiceDetailsFieldValue(String fieldname) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public boolean isServiceDetailsFieldEditable(String fieldname) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").click();
		return appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0;
	}
	
	public void setServiceDetailsFieldValue(String fieldname, String _value) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").click();
		appiumdriver.findElementByAccessibilityId("Clear text").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").sendKeys(_value + "\n");
	}
	
	public String getServiceDetailsTotalValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[2]").getAttribute("value");
	}
	
	
	public void clickTechniciansIcon() {

		List<WebElement> elemts = appiumdriver.findElementsByXPath("//UIAToolbar[1]/*");
		for (WebElement element : elemts) {
			if (element.getAttribute("name").equals("technician")) {
				element.click();
			}
		}

	}

	public void selecTechnician(String technician) {
		Helpers.scroolTo(technician);
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name,\""
						+ technician + "\")]/UIAButton[@name=\"unselected\"]").click();
	}

	public void unselecTechnician(String technician) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"selected\"]").click();
	}

	public String getTechnicianPrice(String technician) {
		return appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIATextField[1]").getAttribute("value");
	}

	public String getTechnicianPercentage(String technician) {
		return appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIATextField[1]").getAttribute("value");
	}
	
	public void checkPreexistingDamage() {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name= \"Pre-existing damage\"]/UIAButton[@name= \"black unchecked\"]").click();
	}
	
	public void uncheckPreexistingDamage() {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name= \"Pre-existing damage\"]/UIAButton[@name= \"black checked\"]").click();
	}
	
	public String getCustomTechnicianPercentage(String technician) {
		String techitianlabel = appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAStaticText[1]").getAttribute("label");
		
		return techitianlabel.substring(techitianlabel.indexOf("%"), techitianlabel.indexOf(")"));
	}

	public void setTechnicianCustomPercentageValue(String technician,
			String percentage) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ technician + "\"]").click();

		if (elementExists("//UIATableView/UIATableCell[@name=\""
						+ technician
						+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]")) {
			appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
							+ technician
							+ "\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]").click();
		}
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name=\""
						+ technician + "\"]").clear();
		Helpers.keyboadrType(percentage);
		Helpers.keyboadrType("\n");

	}
	
	public void changeAmountOfBundleService(String newamount) {
		appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[1]/XCUIElementTypeButton[3]").click();
		Helpers.waitABit(300);
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTextField");
		amountfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
	}

	public void assertTechnicianIsSelected(String technician) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"selected\"]").isDisplayed());
	}

	public void assertTechnicianIsNotSelected(String technician) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"unselected\"]").isDisplayed());
	}

	public void selectVehiclePart(String vehiclepart) {
		Helpers.waitABit(300);
		appiumdriver.findElementByAccessibilityId(vehiclepart).click();
		//Helpers.waitABit(300);
		// par = getTableParentCell(vehiclepart);
		//new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("unselected"))).perform() ;
		//par.findElement(By.xpath(".//XCUIElementTypeButton[@label='unselected']")).click();
		//appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name=\"unselected\"]").click();
		WebElement par = getTableParentCell(vehiclepart);
		Assert.assertTrue(par.findElements(By.xpath(".//XCUIElementTypeButton[@label='selected']")).size() > 0);
	}

	public void cancelSelectedServiceDetails() {
		cancelbtn.click();
	}

	public void clickAdjustments() {
		appiumdriver.findElementByName("Adjustments").click();
		Helpers.waitABit(300);
	}
	
	public String getServiceDetailsPriceValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeToolbar[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]"))));
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[1]").getAttribute("value");
	}
	
	//Service Parts /////////
	
	public String getServicePartValue() {
		WebElement par = getTableParentCell("Service Part");
		return par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicepartcell)).click();
		Helpers.waitABit(500);
	}
	
	public void selectServicePartCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId("Category").click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		WebElement par = getTableParentCell("Category");
		return par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {
		appiumdriver.findElementByAccessibilityId("Subcategory").click();
		Helpers.waitABit(300);
		//appiumdriver.findElementByAccessibilityId("Search").sendKeys(subcategoryname);
		Helpers.waitABit(300);
		appiumdriver.tap(1, appiumdriver.findElementByAccessibilityId(subcategoryname), 1000);
		//appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + subcategoryname + "']")).click();
		//appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		WebElement par = getTableParentCell("Subcategory");
		return par.findElement(By.xpath(".//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		appiumdriver.findElementByAccessibilityId("Part").click();
		Helpers.waitABit(300);
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		appiumdriver.findElementByAccessibilityId("Position").click();
		Helpers.waitABit(300);
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}