package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class SelectedServiceDetailsScreen extends iOSHDBaseScreen {
	
	
	//@iOSFindBy(accessibility = "Price")
    // IOSElement servicepricefld;
	
	/*@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Price']/XCUIElementTypeTextField[1]")
    private IOSElement servicepricevaluefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Adjustments']/XCUIElementTypeTextField[1]")
    private IOSElement serviceadjustmentsfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Vehicle Part']/XCUIElementTypeStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Service Part']/XCUIElementTypeStaticText[2]")
    private IOSElement servicepartfld;*/
	
	/*@iOSFindBy(accessibility = "Vehicle Part")
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
    private IOSElement bundleitemstable;*/
	
	
	public SelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertServicePriceValue(String expectedprice) {
		IOSElement pricecell = null;
		List<WebElement> priceflds = appiumdriver.findElementsByAccessibilityId("Price");
		for (WebElement prc : priceflds)
			if (prc.isDisplayed()) {
				pricecell = (IOSElement) prc;
				break;
			}
		IOSElement pricefld = (IOSElement) pricecell.findElementByClassName("XCUIElementTypeTextField");
		Assert.assertEquals(pricefld.getText(), expectedprice);
	}

	public void assertServiceAdjustmentsValue(String adjustments) {
		Assert.assertEquals(getAdjustmentsValue(), adjustments);
	}

	public void setServicePriceValue(String _price)	 {
		
		WebElement pricefld = null;
		List<WebElement> priceflds = appiumdriver.findElementsByAccessibilityId("Price");
		for (WebElement prc : priceflds)
			if (prc.isDisplayed()) {
				prc.click();
				break;
			}
		if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
			appiumdriver.findElementByAccessibilityId("Clear text").click();
		appiumdriver.getKeyboard().sendKeys(_price + "\n");
	}

	public void clickVehiclePartsCell() {
		appiumdriver.findElementByAccessibilityId("Vehicle Part").click();
	}
	
	public ServicePartPopup clickServicePartCell() {
		appiumdriver.findElementByAccessibilityId("Service Part").click();
		return new ServicePartPopup(appiumdriver);
	}
	
	public NotesScreen clickNotesCell() {
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new NotesScreen(appiumdriver);
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public String getServicePartValue() {
		WebElement servicepartfld = ((IOSElement) appiumdriver.findElementByAccessibilityId("Service Part")).findElementsByClassName("XCUIElementTypeStaticText").get(1);	
		return servicepartfld.getAttribute("value");
	}

	public void answerQuestion(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Choose One Hail Project Code")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		Helpers.waitABit(500);
	}
	
	public void answerTaxPoint1Question(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Tax_Point_1")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		Helpers.waitABit(500);
	}
	
	public void answerQuestion2(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionspopup.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public String getQuestion2Value() {
		String questionvalue = "";
		appiumdriver.findElementByAccessibilityId("Questions").click();
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionvalue = questionspopup.getQuestion2Value();
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		return questionvalue;
	}

	public void answerQuestionCheckButton() {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeLogical_Q1")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		
		appiumdriver.findElementByAccessibilityId("Quantity").click();
		if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
			appiumdriver.findElementByAccessibilityId("Clear text").click();
		
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void assertAdjustmentValue(String adjustment,
			String adjustmentvalue) {
		Assert.assertEquals(appiumdriver.findElement(MobileBy.AccessibilityId(adjustment)).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value"), adjustmentvalue);
	}

	public void selectAdjustment(String adjustment) {
		appiumdriver.findElement(MobileBy.AccessibilityId(adjustment)).findElement(MobileBy.AccessibilityId("unselected")).click();
	}
	
	public void selectBundle(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		IOSElement bundleview = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"))); 
		//Helpers.waitABit(1000);
		IOSElement bundleitem = (IOSElement) appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']"));
		bundleitem.findElement(MobileBy.AccessibilityId("unselected")).click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")).findElement(MobileBy.AccessibilityId(bundle))
		.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		setServiceQuantityValue(_quantity);
	}
	
	public PriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + matrics + "']").click();
		return new PriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Vehicle Parts").isDisplayed();
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
	
	public String saveTechnociansViewWithAlert()
			throws InterruptedException {
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Technicians' and type = 'XCUIElementTypeNavigationBar'"))
		.findElement(MobileBy.AccessibilityId("Save")).click();
		return Helpers.getAlertTextAndAccept();
	}

	public void selectTechniciansCustomView() {
		appiumdriver.findElementByAccessibilityId("Custom").click();
	}

	public void selectTechniciansEvenlyView() {
		appiumdriver.findElementByAccessibilityId("Evenly").click();
	}
	
	public void removeService() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Remove").click();
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
		WebElement adjustmentsfld = ((IOSElement) appiumdriver.findElementByAccessibilityId("Adjustments")).findElementByClassName("XCUIElementTypeTextField");
		return adjustmentsfld.getText();
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
		//FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		//wait.until(ExpectedConditions.presenceOfElementLocated(By.name("TechnicianSplitsView"))); 

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
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeCell[contains(@name, '"
					+ technician + "')]"))).click();
		if (appiumdriver.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
				+ technician + "')]").findElements(MobileBy.AccessibilityId("Clear text")).size() > 0)
			appiumdriver.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
					+ technician + "')]").findElement(MobileBy.AccessibilityId("Clear text")).click();
		//}
		typeTechnicianValue(percentage);

	}
	
	public void typeTechnicianValue(String percentage) {
		((IOSDriver) appiumdriver).getKeyboard().pressKey(percentage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(1000);
	}
	
	public void changeAmountOfBundleService(String newamount) {
		MobileElement toolbar = null;
		List<MobileElement> toolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (MobileElement tbr : toolbars)
			if (tbr.isDisplayed()) {
				toolbar = tbr;
				break;
			}
		List<MobileElement> buttons = toolbar.findElementsByClassName("XCUIElementTypeButton");
		for (MobileElement btn : buttons)
			if (btn.getText().contains("$")) {
				btn.click();
				break;
			}
		
		IOSElement bundlealert = (IOSElement) appiumdriver.findElementByAccessibilityId("Bundle service amount");
		IOSElement amountfld = (IOSElement) bundlealert.findElementByClassName("XCUIElementTypeTextField");
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
		if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView").size() > 0) {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		} else if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsSingleSelectionView").size() > 0) {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsSingleSelectionView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		} else {
			List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView");
			for (IOSElement techview : techviews)
				if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
					techsplittable = techview;
					break;
				}
		}
		return techsplittable;
	}

	public boolean isTechnicianIsNotSelected(String technician) {
		IOSElement techsplittable =  getTechnicianSplitTable();	
		return techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
						+ technician + "')]/XCUIElementTypeButton[@name='unselected']").size() > 0;
	}

	public void selectVehiclePart(String vehiclepart) {
		WebElement vehiclepartstable = appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'VehiclePartSelectorView' and type = 'XCUIElementTypeTable'"));
		TouchAction action = new TouchAction(appiumdriver);
		action.press(vehiclepartstable.findElement(MobileBy.name(vehiclepart))).waitAction(Duration.ofSeconds(1)).release().perform();
		Assert.assertTrue(vehiclepartstable.findElement(MobileBy.name(vehiclepart)).findElements(MobileBy.name("selected")).size() > 0);
	}

	public void cancelSelectedServiceDetails() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
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
		MobileElement toolbar = null;
		List<MobileElement> toolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (MobileElement tbr : toolbars)
			if (tbr.isDisplayed()) {
				toolbar = tbr;
				break;
			}
		return toolbar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}
	
	public String getServiceDetailsTotalValue() {
		MobileElement toolbar = null;
		List<MobileElement> toolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (MobileElement tbr : toolbars)
			if (tbr.isDisplayed()) {
				toolbar = tbr;
				break;
			}
		return toolbar.findElementsByClassName("XCUIElementTypeStaticText").get(1).getAttribute("value");
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
