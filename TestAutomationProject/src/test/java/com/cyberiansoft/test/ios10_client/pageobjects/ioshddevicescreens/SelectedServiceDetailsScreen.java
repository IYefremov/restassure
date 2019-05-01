package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

	@iOSFindBy(accessibility = "Vehicle Part")
	private IOSElement vehiclepartscell;

	@iOSFindBy(accessibility = "Part")
	private IOSElement servicepartscell;

	@iOSFindBy(accessibility = "Vehicle Parts")
	private IOSElement vehiclepartsfldname;
	
	public SelectedServiceDetailsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		//appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public String getServicePriceValue() {
		WebElement pricecell = appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Price' and type = 'XCUIElementTypeCell' AND visible == 1"));

		IOSElement pricefld = (IOSElement) pricecell.findElement(MobileBy.className("XCUIElementTypeTextField"));
		return pricefld.getText();
	}

	public String getServiceAdjustmentsValue() {
		return getAdjustmentsValue();
	}

	/*public void setServicePriceValue(String _price)	 {

		WebElement pricefld = appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Price' and type = 'XCUIElementTypeCell' AND visible == 1"));
		pricefld.click();
		if (pricefld.findElements(MobileBy.AccessibilityId("Clear text")).size() > 0)
			pricefld.findElement(MobileBy.AccessibilityId("Clear text")).click();
		pricefld.sendKeys(_price + "\n");
		BaseUtils.waitABit(1000);
	}*/


	public void setServicePriceValue(String _price) {
		appiumdriver.findElementByAccessibilityId("Price").click();
		if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
			appiumdriver.findElementByAccessibilityId("Clear text").click();

		appiumdriver.findElementByAccessibilityId("Price").sendKeys(_price + "\n");
	}

	public void clickVehiclePartsCell() {
		vehiclepartscell.click();
	}
	
	public ServicePartPopup clickServicePartCell() {
		servicepartscell.click();
		return new ServicePartPopup();
	}
	
	public NotesScreen clickNotesCell() {
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new NotesScreen();
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}


	public String getTechniciansValue() {
		WebElement par = getTableParentCell("Technicians");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}

	public String getServicePartValue() {
		WebElement servicepartfld = ((IOSElement) appiumdriver.findElementByAccessibilityId("Part")).findElementsByClassName("XCUIElementTypeStaticText").get(1);
		return servicepartfld.getAttribute("value");
	}

	public void answerQuestion(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Choose One Hail Project Code")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}
	
	public void answerTaxPoint1Question(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Tax_Point_1")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}
	
	public void answerQuestion2(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		QuestionsPopup questionspopup = new QuestionsPopup();
		questionspopup.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public String getQuestion2Value() {
		String questionvalue = "";
		appiumdriver.findElementByAccessibilityId("Questions").click();
		QuestionsPopup questionspopup = new QuestionsPopup();
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
	
	public void setServiceQuantityValue(String _quantity) {
		appiumdriver.findElementByAccessibilityId("Quantity").click();
		if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
			appiumdriver.findElementByAccessibilityId("Clear text").click();

		appiumdriver.findElementByAccessibilityId("Quantity").sendKeys(_quantity + "\n");
	}

	public String getAdjustmentValue(String adjustment) {
		return appiumdriver.findElement(MobileBy.AccessibilityId(adjustment)).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
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
	
	public void changeBundleQuantity(String bundle, String _quantity) {
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")).findElement(MobileBy.AccessibilityId(bundle))
		.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		setServiceQuantityValue(_quantity);
	}
	
	public PriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + matrics + "']").click();
		return new PriceMatrixScreen();
	}

	public boolean vehiclePartsIsDisplayed() {
		return vehiclepartsfldname.isDisplayed();
	}

	public void saveSelectedServiceDetails() {

		MobileElement navBar = (MobileElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeNavigationBar' AND visible == 1"));
		navBar.findElementByAccessibilityId("Save").click();
	}

	public void clickCancelSelectedServiceDetails() {
		MobileElement navBar = (MobileElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeNavigationBar' AND visible == 1"));
		navBar.findElementByAccessibilityId("Cancel").click();
	}

	public String saveSelectedServiceDetailsWithAlert() {
		saveSelectedServiceDetails();
		return Helpers.getAlertTextAndAccept();
	}
	

	
	public void removeService() {
		appiumdriver.findElementByAccessibilityId("Remove").click();
		Helpers.acceptAlertIfExists();
	}

	public String getAdjustmentsValue() {
		WebElement adjustmentsfld = ((IOSElement) appiumdriver.findElementByAccessibilityId("Adjustments")).findElementByClassName("XCUIElementTypeTextField");
		return adjustmentsfld.getText();
	}
	
	public TechniciansPopup clickTechniciansIcon() {
		List<IOSElement> techtoolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (IOSElement techtoolbar : techtoolbars)
			if (techtoolbar.findElementsByAccessibilityId("technician").size() > 0)
				techtoolbar.findElementByAccessibilityId("technician").click();
		return new TechniciansPopup();
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
        amountfld.sendKeys(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
	}



	public void selectVehiclePart(String vehiclepart) {
		IOSElement vehiclepartstable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'VehiclePartSelectorView' and type = 'XCUIElementTypeTable'"));
		if (!vehiclepartstable.findElementByAccessibilityId(vehiclepart).isDisplayed()) {
			scrollToElement(vehiclepartstable.findElementByAccessibilityId(vehiclepart));
		}
		vehiclepartstable.findElementByAccessibilityId(vehiclepart).click();
	}

	public void selectVehicleParts(String[] vehicleParts) {
		IOSElement vehiclepartstable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'VehiclePartSelectorView' and type = 'XCUIElementTypeTable'"));
		for (String vehiclepart : vehicleParts) {
			if (!vehiclepartstable.findElementByAccessibilityId(vehiclepart).isDisplayed()) {
				scrollToElement(vehiclepartstable.findElementByAccessibilityId(vehiclepart));
			}
			vehiclepartstable.findElementByAccessibilityId(vehiclepart).click();
		}
	}

	public void cancelSelectedServiceDetails() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Cancel")));
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
	
	public void setServiceTimeValue(String _timevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Time"))).click();	
        WebElement par = getTableParentCell("Time");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
        par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_timevalue + "\n");
	}
	
	public void setServiceRateValue(String _ratevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Rate"))).click();
        WebElement par = getTableParentCell("Rate");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
        par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_ratevalue + "\n");
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

	public TechniciansPopup clickTechniciansCell() {
		new WebDriverWait(appiumdriver, 10)
				.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Technicians"))).click();
		return new TechniciansPopup();
	}

}
