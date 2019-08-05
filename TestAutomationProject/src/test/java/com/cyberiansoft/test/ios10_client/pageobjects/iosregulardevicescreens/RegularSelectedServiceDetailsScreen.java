package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.ServiceRateData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularSelectedServiceDetailsScreen extends iOSRegularBaseScreen {
	
	/*@iOSXCUITFindBy(accessibility = "Vehicle Part")
    private IOSElement vehiclepartscell;
	
	@iOSXCUITFindBy(accessibility = "Questions")
    private IOSElement questionsfld;

	
	@iOSXCUITFindBy(accessibility = "Notes")
    private IOSElement notesfld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Vehicle Parts\"]")
    private IOSElement vehiclepartsfldname;
	
	@iOSXCUITFindBy(accessibility  = "Service Part")
    private IOSElement servicepartcell;
	
	@iOSXCUITFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Custom\"]")
    private IOSElement technitianscustomview;
	
	@iOSXCUITFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Evenly\"]")
    private IOSElement technitiansevenlyview;*/
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	
	public RegularSelectedServiceDetailsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
		pricecell.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_price + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(_price);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void clickVehiclePartsCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Select Vehicle Part"))).click();
	}

	public void clickTechniciansCell() {
		new WebDriverWait(appiumdriver, 10)
				.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Technicians"))).click();
	}
	
	public RegularNotesScreen clickNotesCell() {
		appiumdriver.findElementByAccessibilityId("Notes").click();
		return new RegularNotesScreen();
	}

	public String getVehiclePartValue() {
		WebElement par = getTableParentCell("Vehicle Part");	
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
		//return vehiclepartsfld.getAttribute("name");
	}

	public String getTechniciansValue() {
		WebElement par = getTableParentCell("Technicians");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}

	public void answerQuestion(QuestionsData questionsData) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")));
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[2]")).click();
		if (questionsData.getQuestionAnswer() != null)
			appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionAnswer())).click();
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
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
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();	
	}

	public void answerQuestion2(QuestionsData questionsData) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.answerQuestion(questionsData);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}

	public void answerQuestions(List<QuestionsData> questionsData) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		for (QuestionsData questionData : questionsData)
			if (questionData.isLogicalQuestion())
				questionsscreen.answerLogicalQuestion(questionData);
			else if (questionData.isTextQuestion())
				questionsscreen.answerTextQuestion(questionData);
			else
				questionsscreen.answerQuestion(questionData);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}
	
	public String getQuestion2Value() {
		String questionvalue = "";
		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
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
		boolean exists =  appiumdriver.findElements(MobileBy.AccessibilityId("Questions")).size() > 0;
		return exists;
	}

	public void setServiceQuantityValue(String _quantity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Quantity")));
		WebElement par = getTableParentCell("Quantity");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_quantity + "\n");


		/*par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par = getTableParentCell("Quantity");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Quantity");
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");*/
		Helpers.waitABit(300);
	}
	
	public void setServiceTimeValue(String _timevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Time")));	
        WebElement par = getTableParentCell("Time");
		
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_timevalue + "\n");

		/*par = getTableParentCell("Time");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]/XCUIElementTypeButton")).click();
		par = getTableParentCell("Time");
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_timevalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");*/
	}
	
	public void setServiceRateValue(String _ratevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Rate")));	
        WebElement par = getTableParentCell("Rate");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_ratevalue + "\n");
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
	
	public void changeBundleQuantity(String bundle, String _quantity) {
		WebElement par = getTableParentCell(bundle);
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='custom detail button']")).click();
		setServiceQuantityValue(_quantity);
	}
	
	public RegularPriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + matrics + "']")).click();;
		return new RegularPriceMatrixScreen();
	}

	public boolean vehiclePartsIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Vehicle Parts").isDisplayed();
	}

	public void saveSelectedServiceDetails() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
	}

	public void clickSelectedServiceDetailsDoneButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Done")).click();
	}


	public String saveSelectedServiceDetailsWithAlert() {
		saveSelectedServiceDetails();
		return Helpers.getAlertTextAndAccept();
	}

	public void selectTechniciansCustomView() {
		appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Custom")).click();
	}

	public boolean isCustomTabSelected() {
		return appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Custom")).getAttribute("value") != null;
	}

	public void selectTechniciansEvenlyView() {
		appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Evenly")).click();
	}

	public boolean isEvenlyTabSelected() {
		return appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Evenly")).getAttribute("value") != null;
	}
	
	public void removeService() {
		appiumdriver.findElementByAccessibilityId("Remove").click();
		Helpers.acceptAlertIfExists();
	}

	public void setTechnicianCustomPriceValue(String technician,
			String _quantity) {
		
		WebElement tech = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeStaticText[1]"));
		tech.click();
		if (appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).size() > 0) {
			appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
					+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).click();

		}
		tech.sendKeys(_quantity + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public String getAdjustmentsValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"Adjustments\"]/XCUIElementTypeTextField[1]").getAttribute("value");
	}
	
	public String getServiceDetailsFieldValue(String fieldname) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + fieldname + "']/XCUIElementTypeTextField[1]").getAttribute("value");
	}

	public String getServiceRateValue(ServiceRateData serviceRateData) {
		return appiumdriver.findElementByAccessibilityId(serviceRateData.getServiceRateName()).findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
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
	
	public String getServiceDetailsTotalValue() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		List<MobileElement> tests = toolbar.findElementsByClassName("XCUIElementTypeStaticText");
		if (tests.size() > 1)
			return toolbar.findElementsByClassName("XCUIElementTypeStaticText").get(1).getAttribute("value");
		else
			return toolbar.findElementsByClassName("XCUIElementTypeStaticText").get(0).getAttribute("value");
	}
	
	
	public void clickTechniciansIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("technician")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("technician"))).click();
	}

	public void selecTechnician(String technician) {
		appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='unselected']").click();
	}
	
	public void searchTechnician(String technician) {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(technician);
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
		
		return techitianlabel.substring(techitianlabel.lastIndexOf("%"), techitianlabel.indexOf(")"));
	}

	public void setTechnicianCustomPercentageValue(String technician,
			String percentage) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeStaticText[1]")).click();
		if (appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).size() > 0) {
			appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
			+ technician + "\")]/XCUIElementTypeTextField[1]/XCUIElementTypeButton[@name=\"Clear text\"]")).click();
		}
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
				+ technician + "\")]/XCUIElementTypeTextField[1]")).sendKeys(percentage + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(percentage);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
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
		amountfld.sendKeys(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();

	}

	public boolean isTechnicianSelected(String technician) {
		return appiumdriver.
				findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeButton[@name=\"selected\"]").size() > 0;
	}

	public boolean isTechnicianNotSelected(String technician) {
		return appiumdriver.
				findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeButton[@name=\"unselected\"]").size() > 0;
	}

	public void selectVehiclePart(String vehiclepart) {		
		MobileElement table  = (MobileElement) appiumdriver.findElementByAccessibilityId("VehiclePartSelectorView");
		if (!table.findElementByAccessibilityId(vehiclepart).isDisplayed()) {
			swipeToElement(table.findElement(MobileBy.AccessibilityId(vehiclepart)));
		}
		table.findElementByAccessibilityId(vehiclepart).click();
	}

	public void selectVehicleParts(List<VehiclePartData> vehicleParts) {
		for (VehiclePartData vehiclePartData : vehicleParts)
			selectVehiclePart(vehiclePartData.getVehiclePartName());
	}

	public void cancelSelectedServiceDetails() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.visibilityOf(cancelbtn));
		cancelbtn.click();
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
		WebElement par = getTableParentCell("Part");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByName("Part"))).click();
	}
	
	public void selectServicePartCategory(String categoryname) {
		//appiumdriver.findElementByAccessibilityId("Category").click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		WebElement par = getTableParentCell("Category");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Category"))).click();
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
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(subcategoryname)));
		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		WebElement par = getTableParentCell("Subcategory");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")) .getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(subcategorypartname)));
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(subcategorypositionname)));
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

	public void clickSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

	public RegularPriceMatrixScreen selectPriceMatrices(String pricematrice) {
		appiumdriver.findElementByAccessibilityId(pricematrice).click();
		return new RegularPriceMatrixScreen();
	}

	public void clickOperationCell() {
		appiumdriver.findElementByAccessibilityId("Operation").click();
	}

	public void selectLaborServicePanel(String panelName) {
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + panelName + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + panelName + "']/..")));
		}
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(panelName)).click();
	}

	public void selectLaborServicePart(String partName) {
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(partName+"\n");
		appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(partName)).click();
	}
}
