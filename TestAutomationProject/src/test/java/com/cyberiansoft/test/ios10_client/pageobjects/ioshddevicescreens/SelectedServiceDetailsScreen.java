package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class SelectedServiceDetailsScreen extends iOSHDBaseScreen {
	
	
	@iOSFindBy(accessibility = "Price")
    private IOSElement servicepricefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Price']/XCUIElementTypeTextField[1]")
    private IOSElement servicepricevaluefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Adjustments']/XCUIElementTypeTextField[1]")
    private IOSElement serviceadjustmentsfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Vehicle Part']/XCUIElementTypeStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Service Part']/XCUIElementTypeStaticText[2]")
    private IOSElement servicepartfld;
	
	@iOSFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartscell;
	
	@iOSFindBy(accessibility = "Service Part")
    private IOSElement servicepartscell;
	
	@iOSFindBy(accessibility = "Questions")
    private IOSElement questionsfld;
	
	@iOSFindBy(accessibility = "Remove")
    private IOSElement removeservice;
	
	@iOSFindBy(accessibility = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility  = "Notes")
    private IOSElement notesfld;
	
	@iOSFindBy(accessibility = "Vehicle Parts")
    private IOSElement vehiclepartsfldname;
	
	@iOSFindBy(accessibility = "Custom")
    private IOSElement technitianscustomview;
	
	@iOSFindBy(accessibility = "Evenly")
    private IOSElement technitiansevenlyview;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility = "PercentageGroupsView")
    private IOSElement adjustmentstable;
	
	@iOSFindBy(accessibility = "BundleItemsView")
    private IOSElement bundleitemstable;
	
	
	public SelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertServicePriceValue(String expectedprice) {
		Assert.assertEquals(servicepricevaluefld.getText(), expectedprice);
	}

	public void assertServiceAdjustmentsValue(String adjustments) {
		Assert.assertEquals(serviceadjustmentsfld.getText(), adjustments);
	}

	public void setServicePriceValue(String _price)
			throws InterruptedException {
		servicepricefld.click();
		WebElement par = getTableParentCell("Price");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		//servicepricevaluefld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_price);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public void clickVehiclePartsCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(vehiclepartscell)).click();
	}
	
	public ServicePartPopup clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicepartscell)).click();
		return new ServicePartPopup(appiumdriver);
	}
	
	public void clickNotesCell() {
		notesfld.click();
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getServicePartValue() {
		return servicepartfld.getAttribute("name");
	}

	public void answerQuestion(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['" + answer + "']")).click();	
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Back']")).click();	
	}
	
	public void answerTaxPoint1Question(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Tax_Point_1")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		Helpers.waitABit(500);
	}
	
	public void answerQuestion2(String answer) {

		questionsfld.click();
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionspopup.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}

	public void answerQuestionCheckButton() {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		
		quantityfld.click();
		Helpers.waitABit(500);
		
		WebElement par = getTableParentCell("Quantity");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		
		//if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
		//	appiumdriver.findElementByAccessibilityId("Clear text").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Quantity']/XCUIElementTypeTextField[1]").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public void assertAdjustmentValue(String adjustment,
			String adjustmentvalue) {
		Helpers.waitABit(500);
		Assert.assertEquals(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + adjustment + "']/XCUIElementTypeTextField[1]")).getAttribute("value"), adjustmentvalue);
	}

	public void selectAdjustment(String adjustment) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + adjustment + "']/XCUIElementTypeButton[@name=\"unselected\"]")).click();
	}
	
	public void selectBundle(String bundle) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name=\""
								+ bundle + "\"]/XCUIElementTypeButton[@name=\"unselected\"]").click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name=\""
								+ bundle + "\"]/XCUIElementTypeButton[@name=\"custom detail button\"]").click();
		setServiceQuantityValue(_quantity);
	}
	
	public PriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + matrics + "']").click();
		return new PriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return vehiclepartsfldname.isDisplayed();
	}

	public void saveSelectedServiceDetails() throws InterruptedException {
		appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(2000);
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
			appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView/UIATableCell[contains(@name, \""
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
		return serviceadjustmentsfld.getText();
	}
	
	public void clickTechniciansIcon() {

		List<WebElement> elemts = appiumdriver.findElementsByXPath("//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIAToolbar[1]/*");
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
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"selected\"]").click();
	}

	public String getTechnicianPrice(String technician) {
		return appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name, \""
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
		((IOSDriver) appiumdriver).getKeyboard().pressKey(percentage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");

	}
	
	public void changeAmountOfBundleService(String newamount) {
		appiumdriver.findElementByXPath("//XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeButton[3]").click();
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTextField[1]");
		amountfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
		Helpers.waitABit(1000);
	}

	public void assertTechnicianIsSelected(String technician) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"selected\"]").isDisplayed());
	}

	public void assertTechnicianIsNotSelected(String technician) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"unselected\"]").isDisplayed());
	}

	public void selectVehiclePart(String vehiclepart) {
		WebElement vehiclepartstable = null;
		if (appiumdriver.findElementsByAccessibilityId("VehiclePartSelectorView").size() > 1)
			vehiclepartstable = (WebElement) appiumdriver.findElementsByAccessibilityId("VehiclePartSelectorView").get(1);
		else
			vehiclepartstable = appiumdriver.findElementByAccessibilityId("VehiclePartSelectorView");
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(vehiclepartstable.findElement(MobileBy.name(vehiclepart))).waitAction(300).release().perform();
		Assert.assertTrue(vehiclepartstable.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + vehiclepart + "']/XCUIElementTypeButton[@name='selected']")).size() > 0);
	}

	public void cancelSelectedServiceDetails() {
		cancelbtn.click();
	}

	public void clickAdjustments() {
		appiumdriver.findElementByName("Adjustments").click();
	}
	
	public String getListOfSelectedVehicleParts() {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Vehicle Part']/XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public boolean isQuestionFormCellExists() {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists =  appiumdriver.findElements(MobileBy.AccessibilityId("Questions")).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.MILLISECONDS);
		return exists;
	}
	
	public String getServiceDetailsPriceValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[1]").getAttribute("value");
	}
	
	public String getServiceDetailsTotalValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[2]").getAttribute("value");
	}
	
	public String getServiceDetailsFieldValue(String fieldname) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public void setServiceTimeValue(String _timevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Time"))).click();	
        WebElement par = getTableParentCell("Time");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_timevalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setServiceRateValue(String _ratevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Rate"))).click();
        WebElement par = getTableParentCell("Rate");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_ratevalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
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
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}