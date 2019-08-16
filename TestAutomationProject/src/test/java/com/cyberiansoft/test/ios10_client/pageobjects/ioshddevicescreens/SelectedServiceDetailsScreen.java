package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.ServiceRateData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
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
	
	
	//@iOSXCUITFindBy(accessibility = "Price")
    // IOSElement servicepricefld;
	
	/*@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Price']/XCUIElementTypeTextField[1]")
    private IOSElement servicepricevaluefld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Adjustments']/XCUIElementTypeTextField[1]")
    private IOSElement serviceadjustmentsfld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Vehicle Part']/XCUIElementTypeStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[@name='Service Part']/XCUIElementTypeStaticText[2]")
    private IOSElement servicepartfld;*/

	/*@iOSXCUITFindBy(accessibility = "Service Part")
    private IOSElement servicepartscell;
	
	@iOSXCUITFindBy(accessibility = "Questions")
    private IOSElement questionsfld;
	
	@iOSXCUITFindBy(accessibility = "Remove")
    private IOSElement removeservice;
	
	@iOSXCUITFindBy(accessibility = "Quantity")
    private IOSElement quantityfld;
	
	@iOSXCUITFindBy(accessibility  = "Notes")
    private IOSElement notesfld;
	
	@iOSXCUITFindBy(accessibility = "Custom")
    private IOSElement technitianscustomview;
	
	@iOSXCUITFindBy(accessibility = "Evenly")
    private IOSElement technitiansevenlyview;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelbtn;
	
	@iOSXCUITFindBy(accessibility = "PercentageGroupsView")
    private IOSElement adjustmentstable;
	
	@iOSXCUITFindBy(accessibility = "BundleItemsView")
    private IOSElement bundleitemstable;*/

	@iOSXCUITFindBy(accessibility = "ClarificationBox_VehiclePartSelector")
	private IOSElement vehiclepartscell;

	@iOSXCUITFindBy(accessibility = "Part")
	private IOSElement servicepartscell;

	@iOSXCUITFindBy(accessibility = "Vehicle Parts")
	private IOSElement vehiclepartsfldname;
	
	public SelectedServiceDetailsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
		return vehiclepartscell.getAttribute("label").replace("\n", " ");
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

	public void answerQuestion(QuestionsData questionsData) {
		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).click();
		if (questionsData.getQuestionAnswer() != null) {
			appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionAnswer())).click();
		}
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
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		MobileElement saveButton = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND visible == 1 AND name == 'Save'")));
		saveButton.click();
	}

	public void clickCancelSelectedServiceDetails() {
		MobileElement cancelButton = (MobileElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND visible == 1 AND name == 'Cancel'"));
		cancelButton.click();
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

	public void selectVehicleParts(List<VehiclePartData> vehiclePartsData) {
		IOSElement vehiclepartstable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'VehiclePartSelectorView' and type = 'XCUIElementTypeTable'"));
		for (VehiclePartData vehiclePartData : vehiclePartsData) {
			if (!vehiclepartstable.findElementByAccessibilityId(vehiclePartData.getVehiclePartName()).isDisplayed()) {
				scrollToElement(vehiclepartstable.findElementByAccessibilityId(vehiclePartData.getVehiclePartName()));
			}
			vehiclepartstable.findElementByAccessibilityId(vehiclePartData.getVehiclePartName()).click();
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
		return vehiclepartscell.getAttribute("label");
	}
	
	public boolean isQuestionFormCellExists() {
		boolean exists =  appiumdriver.findElements(MobileBy.AccessibilityId("Questions")).size() > 0;
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
	
	public String getServiceRateValue(ServiceRateData serviceRateData) {
		return appiumdriver.findElementByAccessibilityId(serviceRateData.getServiceRateName()).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
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
	
	public boolean isServiceRateFieldEditable(ServiceRateData serviceRateData) {
		appiumdriver.findElementByAccessibilityId(serviceRateData.getServiceRateName()).findElement(MobileBy.className("XCUIElementTypeTextField")).click();
		return appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0;
	}
	
	public void setServiceRateFieldValue(ServiceRateData serviceRateData) {
		appiumdriver.findElementByAccessibilityId(serviceRateData.getServiceRateName()).findElement(MobileBy.className("XCUIElementTypeTextField")).click();
		appiumdriver.findElementByAccessibilityId("Clear text").click();
		appiumdriver.findElementByAccessibilityId(serviceRateData.getServiceRateName()).findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(serviceRateData.getServiceRateValue() + "\n");
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
