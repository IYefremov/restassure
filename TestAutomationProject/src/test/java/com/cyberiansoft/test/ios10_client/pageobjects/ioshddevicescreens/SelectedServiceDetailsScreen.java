package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.time.Duration;
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
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility = "PercentageGroupsView")
    private IOSElement adjustmentstable;
	
	@iOSFindBy(accessibility = "BundleItemsView")
    private IOSElement bundleitemstable;
	
	
	public SelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertServicePriceValue(String expectedprice) {
		Assert.assertEquals(servicepricevaluefld.getText(), expectedprice);
	}

	public void assertServiceAdjustmentsValue(String adjustments) {
		Assert.assertEquals(serviceadjustmentsfld.getText(), adjustments);
	}

	public void setServicePriceValue(String _price)	 {
		
		WebElement pricefld = null;
		List<WebElement> priceflds = appiumdriver.findElementsByAccessibilityId("Price");
		for (WebElement prc : priceflds)
			if (prc.isDisplayed())
				prc.click();
		if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
			appiumdriver.findElementByAccessibilityId("Clear text").click();
		appiumdriver.getKeyboard().sendKeys(_price + "\n");
		/*WebElement par = getTableParentCell("Price");
		
		
		par.findElement(
				By.xpath("//XCUIElementTypeTextField[1]")).click();
		par.findElement(
				By.xpath("//XCUIElementTypeTextField[1]")).clear();
		par.findElement(
				By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_price + "\n");
		*/
		//IOSElement prf = (IOSElement) pricefld.findElement(
		//		By.xpath("//XCUIElementTypeTextField[1]"));
		//prf.setValue(value);
		//servicepricevaluefld.clear();
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(_price);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(1000);
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
	
	public NotesScreen clickNotesCell() {
		notesfld.click();
		return new NotesScreen(appiumdriver);
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getServicePartValue() {
		return servicepartfld.getAttribute("value");
	}

	public void answerQuestion(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Choose One Hail Project Code")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		Helpers.waitABit(500);
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
	
	public String getQuestion2Value() {
		String questionvalue = "";
		questionsfld.click();
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionvalue = questionspopup.getQuestion2Value();
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		return questionvalue;
	}

	public void answerQuestionCheckButton() {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeLogical_Q1")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		
		quantityfld.click();
		
		Helpers.waitABit(500);
		
		WebElement par = getTableParentCell("Quantity");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		
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
		Helpers.waitABit(500);
		/*List<WebElement> savebtns = appiumdriver.findElementsByName("Save");
		System.out.println("+++" + savebtns.size());
		for (WebElement sv : savebtns)
			if (sv.isDisplayed())
				sv.click();*/
		List<WebElement> navbars = appiumdriver.findElementsByClassName("XCUIElementTypeNavigationBar");
		for (WebElement nv : navbars) {
			if(nv.isDisplayed()) {
			//if (nv.findElements(By.name("Save")).size() > 0) {
				nv.findElement(By.name("Save")).click();
				break;
			}
		}
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		/*if (appiumdriver.findElementsByAccessibilityId("Save").size() > 1)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("Save").get(1)).click();
		else
			appiumdriver.findElementByAccessibilityId("Save").click();*/
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
		
		IOSElement techsplittable =  getTechnicianSplitTable();	
	
		techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
			+ technician + "')]/XCUIElementTypeStaticText[1]").click();
		Helpers.waitABit(500);
		if (techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
				+ technician + "')]/XCUIElementTypeTextField[1]").size() > 0)
			techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
					+ technician + "')]/XCUIElementTypeTextField[1]").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(1000);
		/*if (elementExists("//UIAPopover[1]")) {
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
		Helpers.keyboadrType("\n");*/
	}

	public String getAdjustmentsValue() {
		return serviceadjustmentsfld.getText();
	}
	
	public void clickTechniciansIcon() {
		List<IOSElement> techtoolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (IOSElement techtoolbar : techtoolbars)
			if (techtoolbar.findElementsByAccessibilityId("technician").size() > 0)
				techtoolbar.findElementByAccessibilityId("technician").click();
		/*if (appiumdriver.findElementsByClassName("XCUIElementTypeToolbar").size() > 2) {
			IOSElement popuptoolbar = (IOSElement) appiumdriver.findElementsByClassName("XCUIElementTypeToolbar").get(2);
			popuptoolbar.findElementByAccessibilityId("technician").click();
		} else {
			IOSElement popuptoolbar = (IOSElement) appiumdriver.findElementsByClassName("XCUIElementTypeToolbar").get(1);
			popuptoolbar.findElementByAccessibilityId("technician").click();
		}	*/
	}

	public void selecTechnician(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();
		techsplittable.findElement(By.xpath("//XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public void searchTechnician(String technician) {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(technician);
		Helpers.waitABit(1000);
	}
	
	public void cancelSearchTechnician() {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
	}

	public void unselecTechnician(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();
		techsplittable.findElement(By.xpath("//XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='selected']")).click();
	}

	public String getTechnicianPrice(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();	
		
		return techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
				+ technician + "')]/XCUIElementTypeTextField[1]").getAttribute("value");
	}

	public String getTechnicianPercentage(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();	
	
	return techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
			+ technician + "')]/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public void checkPreexistingDamage() {
		WebElement preexistingdamagebtn =  appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Pre-existing damage']/XCUIElementTypeButton[@name='black unchecked']");
		if (preexistingdamagebtn.getAttribute("label").equals("black unchecked"))
			preexistingdamagebtn.click();
	}
	
	public void uncheckPreexistingDamage() {
		WebElement preexistingdamagebtn =  appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Pre-existing damage']/XCUIElementTypeButton[@name='black unchecked']");
		if (preexistingdamagebtn.getAttribute("label").equals("black checked"))
			preexistingdamagebtn.click();
	}
	
	public String getCustomTechnicianPercentage(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();		
		String techitianlabel = techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
				+ technician + "')]").getAttribute("label");
		
		return techitianlabel.substring(techitianlabel.indexOf("%"), techitianlabel.indexOf(")"));
	}

	public void setTechnicianCustomPercentageValue(String technician,
			String percentage) {
		IOSElement techsplittable =  getTechnicianSplitTable();

		if (techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
				+ technician + "')]/XCUIElementTypeTextField[1]").size() > 0)
			techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
					+ technician + "')]/XCUIElementTypeTextField[1]").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(percentage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(1000);

	}
	
	public void changeAmountOfBundleService(String newamount) {
		appiumdriver.findElementByXPath("//XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeToolbar/XCUIElementTypeButton[3]").click();
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTextField[1]");
		amountfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
		Helpers.waitABit(1000);
	}

	public boolean isTechnicianIsSelected(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();
		return techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
						+ technician + "')]/XCUIElementTypeButton[@name='selected']").size() > 0;
	}
	
	public IOSElement getTechnicianSplitTable() {
		IOSElement techsplittable =  null;
		if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView").size() > 1) {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		} else if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsSingleSelectionView").size() > 1) {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		}
		else
			techsplittable = (IOSElement) appiumdriver.findElementByAccessibilityId("DefaultEmployeeSelectorView");
		return techsplittable;
	}

	public boolean isTechnicianIsNotSelected(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();	
		return techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
						+ technician + "')]/XCUIElementTypeButton[@name='unselected']").size() > 0;
	}

	public void selectVehiclePart(String vehiclepart) {
		WebElement vehiclepartstable = null;
		List<WebElement> vpselectors = appiumdriver.findElementsByAccessibilityId("VehiclePartSelectorView");
		for (WebElement vp : vpselectors) 
			if (vp.getAttribute("type").equals("XCUIElementTypeTable")) {
				vehiclepartstable = vp;
				break;
			}
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(vehiclepartstable.findElement(MobileBy.name(vehiclepart))).waitAction(Duration.ofSeconds(1)).release().perform();
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
