package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.ServiceRateData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
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
		WebElement priceFld = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Price")));
		return priceFld.findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}

	public String getServiceAdjustmentsValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement adjustmentsFld =  wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Adjustments")));
		return adjustmentsFld.findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}

	public void setServicePriceValue(String _price) {
		BaseUtils.waitABit(2000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement priceFld = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Price")));
		priceFld.findElement(MobileBy.className("XCUIElementTypeTextField")).clear();
		priceFld.findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(_price + "\n");
	}

	public void clickVehiclePartsCell() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ClarificationBox_VehiclePartSelector")));
		appiumdriver.findElementByAccessibilityId("ClarificationBox_VehiclePartSelector").
				findElement(MobileBy.AccessibilityId("custom detail button")).click();
	}

	public void clickTechniciansCell() {
		new WebDriverWait(appiumdriver, 10)
				.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Technicians"))).click();
	}
	
	public void clickNotesCell() {
		appiumdriver.findElementByAccessibilityId("Notes").click();
	}

	public String getVehiclePartValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement vehiclePartRow = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ClarificationBox_VehiclePartSelector")));
		return vehiclePartRow.getAttribute("label");
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

	public void answerServiceQuestion(QuestionsData questionData) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		if (questionData.isLogicalQuestion())
			questionsScreen.answerLogicalQuestion(questionData);
		else if (questionData.isTextQuestion())
			questionsScreen.answerTextQuestion(questionData);
		else
			questionsScreen.answerQuestion(questionData);
		appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
	}

	public void answerQuestions(List<QuestionsData> questionsData) {

		appiumdriver.findElementByAccessibilityId("Questions").click();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		for (QuestionsData questionData : questionsData)
			if (questionData.isLogicalQuestion())
				questionsScreen.answerLogicalQuestion(questionData);
			else if (questionData.isTextQuestion())
				questionsScreen.answerTextQuestion(questionData);
			else
				questionsScreen.answerQuestion(questionData);
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
	
	public boolean isQuestionFormCellExists() {
		boolean exists =  appiumdriver.findElements(MobileBy.AccessibilityId("Questions")).size() > 0;
		return exists;
	}

	public void setServiceQuantityValue(String _quantity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement priceFld = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Quantity")));
		priceFld.findElement(MobileBy.className("XCUIElementTypeTextField")).clear();
		priceFld.findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(_quantity + "\n");
	}
	
	public void setServiceTimeValue(String _timevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        WebElement time = wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name='Time'"))));

		time.findElement(By.className("XCUIElementTypeTextField")).click();
		time.findElement(By.className("XCUIElementTypeTextField")).clear();
		time.findElement(By.className("XCUIElementTypeTextField")).sendKeys(_timevalue + "\n");
	}

	public String getTimeValue() {
		return appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name='Time'")).
				findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}
	
	public void setServiceRateValue(String _ratevalue) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement time = wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name='Rate'"))));

		time.findElement(By.className("XCUIElementTypeTextField")).click();
		time.findElement(By.className("XCUIElementTypeTextField")).clear();
		time.findElement(By.className("XCUIElementTypeTextField")).sendKeys(_ratevalue + "\n");
	}

	public String getLaborRateValue() {
		return appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name='Rate'")).
				findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
	}

	public String getRateValue(String rateName) {
		return appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name='" + rateName + "'")).
				findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
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

	public void saveSelectedServiceDetails() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
	}

	public void clickSelectedServiceDetailsDoneButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Done")).click();
	}

	public void selectTechniciansCustomView() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement priceFld = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSegmentedControl")));
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

	public void clickRemoveServiceButton() {
		appiumdriver.findElementByAccessibilityId("Remove").click();
	}
	
	public void removeService() {
		clickRemoveServiceButton();
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
	}

	public String getAdjustmentsValue() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"Adjustments\"]/XCUIElementTypeTextField[1]").getAttribute("value");
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
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(technician+"\n");
	}
	
	public void cancelSearchTechnician() {
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Cancel']").click();
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
	
	public void checkPreexistingDamage(String damageName) {
		appiumdriver.findElementByAccessibilityId(damageName).findElement(MobileBy.AccessibilityId("black unchecked")).click();
	}
	
	public String getCustomTechnicianPercentage(String technician) {
		String techitianlabel = appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[contains(@name, \""
						+ technician + "\")]/XCUIElementTypeStaticText[1]").getAttribute("label");
		
		return techitianlabel.substring(techitianlabel.lastIndexOf("%"), techitianlabel.indexOf(")"));
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

	public void selectVehiclePart(String vehiclepart) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		MobileElement table  = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("VehiclePartSelectorView")));
		 //(MobileElement) appiumdriver.findElementByAccessibilityId("VehiclePartSelectorView");
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
		return appiumdriver.findElementByAccessibilityId("ClarificationBox_PartSelector").findElement(By.xpath("//XCUIElementTypeStaticText[1]")) .getAttribute("value");
	}
	
	public void clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByName("ClarificationBox_PartSelector"))).click();
	}
	
	public void selectServicePartCategory(String categoryname) {
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
		appiumdriver.findElementByAccessibilityId("ClarificationBox_PanelPartSelector").click();
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
