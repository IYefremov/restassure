package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
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

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularSelectedServiceDetailsScreen extends iOSRegularBaseScreen {
	
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name='Price']/UIATextField[1]")
    private IOSElement servicepricefld;
		
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name='Adjustments']/UIATextField[1]")
    private IOSElement serviceadjustmentsfld;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[contains(@name,\"Vehicle Part\")]/UIAStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSFindBy(accessibility  = "Vehicle Part")
    private IOSElement vehiclepartscell;
	
	@iOSFindBy(accessibility  = "Service Part")
    private IOSElement servicepartcell;
	
	@iOSFindBy(accessibility  = "Questions")
    private IOSElement questionsfld;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Remove\"]")
    private IOSElement removeservice;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility  = "Notes")
    private IOSElement notesfld;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"Vehicle Parts\"]")
    private IOSElement vehiclepartsfldname;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Custom\"]")
    private IOSElement technitianscustomview;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Evenly\"]")
    private IOSElement technitiansevenlyview;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Cancel\"]")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAKey[@name=\"Delete\"]")
    private IOSElement keyboarddeletebtn;
	
	//Service Part
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[contains(@name,\"Service Part\")]/UIAStaticText[2]")
    private IOSElement servicepartfld;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name='Category']/UIAStaticText[2]")
    private IOSElement categoryvaluecell;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name='Subcategory']/UIAStaticText[2]")
    private IOSElement subcategoryvaluecell;
	
	
	public RegularSelectedServiceDetailsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertServicePriceValue(String expectedprice) {
		Assert.assertEquals(servicepricefld.getText(), expectedprice);
	}

	public void assertServiceAdjustmentsValue(String adjustments) {
		Assert.assertEquals(serviceadjustmentsfld.getText(), adjustments);
	}

	public void setServicePriceValue(String _quantity)
			throws InterruptedException {
		servicepricefld.click();
		servicepricefld.clear();
		Helpers.keyboadrType(_quantity + "\n");
	}

	public void clickVehiclePartsCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(vehiclepartscell)).click();
	}
	
	public void clickServicePartCell() {
		new WebDriverWait(appiumdriver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicepartcell)).click();
	}
	
	public void clickNotesCell() {
		notesfld.click();
	}

	public String getVehiclePartValue() {
		return vehiclepartsfld.getAttribute("name");
	}
	
	public String getServicePartValue() {
		return servicepartfld.getAttribute("name");
	}

	public void answerQuestion(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[2]")).click();
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[@name=\"" + answer + "\"]")).click();	
		appiumdriver.findElement(MobileBy.name("Back")).click();	
	}
	
	public void answerQuestion2(String answer) {

		questionsfld.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.name("Back")).click();	
	}
	
	public void answerQuestionCheckButton() {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[2]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.name("Back")).click();	
	}
	
	public void answerTaxPoint1Question(String answer) {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[6]")).click();
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[@name=\"" + answer + "\"]")).click();	
		appiumdriver.findElement(MobileBy.name("Back")).click();
	}
	
	public boolean isQuestionFormCellExists() {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists =  appiumdriver.findElements(MobileBy.IosUIAutomation(".tableViews()[0].cells()['Questions']")).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.MILLISECONDS);
		return exists;
	}

	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		
		quantityfld.click();
		appiumdriver.findElement(By.xpath("//UIATableView[1]/UIATableCell[@name=\"Quantity\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]")).click();
		Helpers.keyboadrType(_quantity + "\n");
	}

	public void assertAdjustmentValue(String adjustment,
			String adjustmentvalue) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ adjustment + "\"]/UIATextField[1]")
						.getAttribute("value"), adjustmentvalue);
	}

	public void selectAdjustment(String adjustment) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ adjustment + "\"]/UIAButton[@name=\"unselected\"]").click();
	}
	
	public void selectBundle(String bundle) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ bundle + "\"]/UIAButton[@name=\"unselected\"]").click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ bundle + "\"]/UIAButton[@name=\"custom detail button\"]").click();
		setServiceQuantityValue(_quantity);
	}
	
	public RegularPriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
								+ matrics + "\"]/UIAStaticText[@name=\"" + matrics + "\"]").click();
		return new RegularPriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return vehiclepartsfldname.isDisplayed();
	}

	public void saveSelectedServiceDetails() throws InterruptedException {
		appiumdriver.findElement(MobileBy.name("Save")).click();	
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
		return appiumdriver.findElementByXPath("//UIATableCell[@name=\"Adjustments\"]/UIATextField[1]").getText();
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
		appiumdriver.findElementByXPath("//UIAScrollView[2]/UIAToolbar[1]/UIAButton[3]").click();
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIATextField[1]");
		amountfld.clear();
		amountfld.setValue(newamount);
		appiumdriver.findElementByXPath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Override\"]").click();
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
		Helpers.scroolTo(vehiclepart);
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name=\"unselected\"]").click();
		/*String path = "";
		if (appiumdriver.findElements(MobileBy.IosUIAutomation(".scrollViews()[1]")).size() > 0)
			path = ".scrollViews()[1]";
		if (appiumdriver.findElements(MobileBy.IosUIAutomation(path + ".tableViews()[0].cells()['" + vehiclepart + "'].buttons()['unselected']")).size() > 0)
			appiumdriver.findElement(MobileBy.IosUIAutomation(path + ".tableViews()[0].cells()['" + vehiclepart + "'].buttons()['unselected']")).click();
		else
			Assert.assertTrue(appiumdriver.findElements(MobileBy.IosUIAutomation(path + ".tableViews()[0].cells()['" + vehiclepart + "'].buttons()['selected']")).size() > 0);*/
		Assert.assertTrue(appiumdriver.findElementsByXPath("//UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name=\"selected\"]").size() > 0);
	}

	public void cancelSelectedServiceDetails() {
		cancelbtn.click();
	}

	public void clickAdjustments() {
		appiumdriver.findElementByName("Adjustments").click();
	}
	
	public String getServiceDetailsPriceValue() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[1]").getAttribute("value");
	}
	
	//Service Parts /////////
	
	public void selectServicePartCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId("Category").click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		return categoryvaluecell.getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {
		appiumdriver.findElementByAccessibilityId("Subcategory").click();
		Helpers.scroolTo(subcategoryname);
		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		return subcategoryvaluecell.getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		appiumdriver.findElementByAccessibilityId("Part").click();
		Helpers.scroolTo(subcategorypartname);
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		appiumdriver.findElementByAccessibilityId("Position").click();
		Helpers.scroolTo(subcategorypositionname);
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}

}
