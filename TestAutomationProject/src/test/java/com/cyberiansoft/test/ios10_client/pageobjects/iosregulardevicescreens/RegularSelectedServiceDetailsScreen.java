package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularSelectedServiceDetailsScreen extends iOSRegularBaseScreen {
	
	/*@iOSFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartscell;
	
	@iOSFindBy(accessibility = "Questions")
    private IOSElement questionsfld;

	
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
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	*/
	
	
	public RegularSelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public String getServicePriceValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Price")));
		WebElement pricefld = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Price']/XCUIElementTypeTextField[1]"));
		return pricefld.getAttribute("value");
	}

	public String getServiceAdjustmentsValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Adjustments")));
		WebElement par = getTableParentCell("Adjustments");
		
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getText();
	}

	public void setServicePriceValue(String _price) {
		WebElement pricecell = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/	XCUIElementTypeStaticText[@name='Price']/.."));
		pricecell.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).clear();
		
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_price);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void clickVehiclePartsCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Vehicle Part"))).click();
	}
	
	public RegularNotesScreen clickNotesCell() {
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new RegularNotesScreen(appiumdriver);
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
		//return vehiclepartsfld.getAttribute("name");
	}

	public void answerQuestion(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")));
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void answerQuestion2(String answer) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public String getQuestion2Value() {
		String questionvalue = "";
		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionvalue = questionsscreen.getQuestion2Value();
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
		return questionvalue;
	}
	
	public void answerQuestionCheckButton() {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}
	
	public void answerTaxPoint1Question(String answer) {
		appiumdriver.findElementByAccessibilityId("Questions").click();
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
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Quantity");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Quantity");
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setServiceTimeValue(String _timevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Time")));	
        WebElement par = getTableParentCell("Time");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Time");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Time");
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_timevalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void setServiceRateValue(String _ratevalue)
			throws InterruptedException {	
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Rate")));	
        WebElement par = getTableParentCell("Rate");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Rate");
		if (par.findElements(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).size() > 0)
			par.findElement(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_ratevalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public String getAdjustmentValue(String adjustment) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(adjustment)));
		WebElement par = getTableParentCell(adjustment);
		
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getText();
	}

	public void selectAdjustment(String adjustment) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(adjustment)));
		WebElement par = getTableParentCell(adjustment);
		
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public void selectBundle(String bundle) {
		WebElement par = getTableParentCell(bundle);
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		WebElement par = getTableParentCell(bundle);
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='custom detail button']")).click();
		setServiceQuantityValue(_quantity);
	}
	
	public RegularPriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + matrics + "']")).click();;
		return new RegularPriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Vehicle Parts").isDisplayed();
	}

	public void saveSelectedServiceDetails() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
	}

	public String saveSelectedServiceDetailsWithAlert()
			throws InterruptedException {
		saveSelectedServiceDetails();
		return Helpers.getAlertTextAndAccept();
	}

	public void selectTechniciansCustomView() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Custom")));
		appiumdriver.findElement(MobileBy.xpath("//UIASegmentedControl[1]/UIAButton[@name=\"Custom\"]")).click();
	}

	public void selectTechniciansEvenlyView() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Evenly")));
		appiumdriver.findElement(MobileBy.xpath("//UIASegmentedControl[1]/UIAButton[@name=\"Evenly\"]")).click();
	}
	
	public void removeService() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Remove").click();
		Helpers.acceptAlertIfExists();
	}

	public void setTechnicianCustomPriceValue(String technician,
			String _quantity) {
		
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeStaticText[1]")).click();
		if (appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).size() > 0) {
			appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
					+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).click();
			//appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
			//		+ technician + "\")]/XCUIElementTypeTextField[1]")).clear();
		}
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementsByClassName("XCUIElementTypeStaticText").get(1).getAttribute("value");
	}
	
	
	public void clickTechniciansIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("technician")));
		appiumdriver.findElementByAccessibilityId("technician").click();
	}

	public void selecTechnician(String technician) {
		appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='unselected']").click();
	}
	
	public void searchTechnician(String technician) {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(technician);
	}
	
	public void cancelSearchTechnician() {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
	}

	public void unselecTechnician(String technician) {
		appiumdriver.
		findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeButton[@name=\"selected\"]").click();
	}

	public String getTechnicianPrice(String technician) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeTextField[1]").getAttribute("value");
	}

	public String getTechnicianPercentage(String technician) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public void checkPreexistingDamage() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= \"Pre-existing damage\"]/XCUIElementTypeButton[@name= \"black unchecked\"]").click();
	}
	
	public void uncheckPreexistingDamage() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= \"Pre-existing damage\"]/XCUIElementTypeButton[@name= \"black checked\"]").click();
	}
	
	public String getCustomTechnicianPercentage(String technician) {
		String techitianlabel = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeStaticText[1]").getAttribute("label");
		
		return techitianlabel.substring(techitianlabel.indexOf("%"), techitianlabel.indexOf(")"));
	}

	public void setTechnicianCustomPercentageValue(String technician,
			String percentage) throws InterruptedException {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeStaticText[1]")).click();
		if (appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).size() > 0) {
			appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
			+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).click();
		}
		((IOSDriver) appiumdriver).getKeyboard().pressKey(percentage);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public void changeAmountOfBundleService(String newamount) {
		List<WebElement> toolbarbtns = appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElements(MobileBy.className("XCUIElementTypeButton"));
		for (WebElement btn : toolbarbtns)
			if (btn.getAttribute("name").contains("$")) {
				btn.click();
				break;
			}
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert").findElement(MobileBy.className("XCUIElementTypeTextField"));
		amountfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
	}

	public boolean isTechnicianIsSelected(String technician) {
		return appiumdriver.
				findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeButton[@name=\"selected\"]").size() > 0;
	}

	public boolean isTechnicianIsNotSelected(String technician) {
		return appiumdriver.
				findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeButton[@name=\"unselected\"]").size() > 0;
	}

	public void selectVehiclePart(String vehiclepart) {		
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("VehiclePartSelectorView");
		if (!table.findElementByAccessibilityId(vehiclepart).isDisplayed()) {
			swipeToElement(table.findElement(MobileBy.AccessibilityId(vehiclepart)));
			//table.findElementByAccessibilityId(vehiclepart).click();
		}
		table.findElementByAccessibilityId(vehiclepart).click();
		/*if (appiumdriver.findElementByAccessibilityId("VehiclePartSelectorView").findElement(
				MobileBy.AccessibilityId(vehiclepart)).findElements(MobileBy.AccessibilityId("unselected")).size() > 0)
			table.findElementByAccessibilityId(vehiclepart).click();*/
	}

	public void cancelSelectedServiceDetails() {
		appiumdriver.findElementByName("Cancel").click();
	}

	public void clickAdjustments() {
		appiumdriver.findElementByName("Adjustments").click();
	}
	
	public String getServiceDetailsPriceValue() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}
	
	//Service Parts /////////
	
	public String getServicePartValue() {
		WebElement par = getTableParentCell("Service Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByName("Service Part"))).click();
	}
	
	public void selectServicePartCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId("Category").click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		WebElement par = getTableParentCell("Category");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + categoryname + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + categoryname + "']/..")));
			//appiumdriver.findElementByAccessibilityId(categoryname).click();
		}
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Subcategory")));
		appiumdriver.findElementByAccessibilityId("Subcategory").click();
		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
		//appiumdriver.tap(1, appiumdriver.findElementByAccessibilityId(subcategoryname), 1000);
		//appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + subcategoryname + "']")).click();
		//appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		WebElement par = getTableParentCell("Subcategory");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Part")));
		appiumdriver.findElementByAccessibilityId("Part").click();
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		appiumdriver.findElementByAccessibilityId("Position").click();
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

	public void clickSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

}
