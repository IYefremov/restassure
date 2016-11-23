package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class SelectedServiceDetailsScreen extends iOSHDBaseScreen {
	
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Price']")
    private IOSElement servicepricefld;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Price'].textFields()[0]")
    private IOSElement servicepricevaluefld;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Adjustments'].textFields()[0]")
    private IOSElement serviceadjustmentsfld;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name,\"Vehicle Part\")]/UIAStaticText[2]")
    private IOSElement vehiclepartsfld;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name,\"Service Part\")]/UIAStaticText[2]")
    private IOSElement servicepartfld;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Vehicle Part']")
    private IOSElement vehiclepartscell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Service Part']")
    private IOSElement servicepartscell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Questions']")
    private IOSElement questionsfld;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIAToolbar[1]/UIAButton[@name=\"Remove\"]")
    private IOSElement removeservice;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Quantity']")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility  = "Notes")
    private IOSElement notesfld;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"Vehicle Parts\"]")
    private IOSElement vehiclepartsfldname;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Custom\"]")
    private IOSElement technitianscustomview;
	
	@iOSFindBy(xpath = "//UIASegmentedControl[1]/UIAButton[@name=\"Evenly\"]")
    private IOSElement technitiansevenlyview;
	
	@iOSFindBy(uiAutomator = ".popover().navigationBar().buttons()[\"Cancel\"]")
    private IOSElement cancelbtn;
	
	
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

	public void setServicePriceValue(String _quantity)
			throws InterruptedException {
		servicepricefld.click();
		servicepricevaluefld.clear();
		servicepricevaluefld.setValue(_quantity);
		Helpers.keyboadrType("\n");
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
		return vehiclepartsfld.getAttribute("name");
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
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[5]")).click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['" + answer + "']")).click();	
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Back']")).click();	
	}
	
	public void answerQuestion2(String answer) {

		questionsfld.click();
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionspopup.answerQuestion2(answer);
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Back']")).click();	
	}

	public void answerQuestionCheckButton() {

		questionsfld.click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[1]")).click();	
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Back']")).click();	
	}
	
	public void setServiceQuantityValue(String _quantity)
			throws InterruptedException {	
		
		quantityfld.click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['Quantity'].textFields()[0]")).clear();
		Helpers.keyboadrType(_quantity + "\n");
	}

	public void assertAdjustmentValue(String adjustment,
			String adjustmentvalue) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
								+ adjustment + "\"]/UIATextField[1]")
						.getAttribute("value"), adjustmentvalue);
	}

	public void selectAdjustment(String adjustment) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
								+ adjustment + "\"]/UIAButton[@name=\"unselected\"]").click();
	}
	
	public void selectBundle(String bundle) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
								+ bundle + "\"]/UIAButton[@name=\"unselected\"]").click();
	}
	
	public void changeBundleQuantity(String bundle, String _quantity) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
								+ bundle + "\"]/UIAButton[@name=\"custom detail button\"]").click();
		setServiceQuantityValue(_quantity);
	}
	
	public PriceMatrixScreen selectMatrics(String matrics) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
								+ matrics + "\"]/UIAStaticText[@name=\"" + matrics + "\"]").click();
		return new PriceMatrixScreen(appiumdriver);
	}

	public boolean vehiclePartsIsDisplayed() {
		return vehiclepartsfldname.isDisplayed();
	}

	public void saveSelectedServiceDetails() throws InterruptedException {
		Thread.sleep(2000);
		if (elementExists("//UIAPopover[1]")) {
			appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Save']")).click();	
		} else {
			appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[0].navigationBars()['Technicians'].buttons()['Save']")).click();	
		}
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
		return appiumdriver.findElementByXPath("//UIATableCell[@name=\"Adjustments\"]/UIATextField[1]").getText();
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
		Helpers.keyboadrType(percentage);
		Helpers.keyboadrType("\n");

	}
	
	public void changeAmountOfBundleService(String newamount) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIAToolbar[1]/UIAButton[3]").click();
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
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[contains(@name, \""
						+ technician + "\")]/UIAButton[@name=\"unselected\"]").isDisplayed());
	}

	public void selectVehiclePart(String vehiclepart) {
		if (appiumdriver.findElements(MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name='unselected']")).size() > 0) {
			Helpers.scroolToByXpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']");
			//appiumdriver.findElement(MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name='unselected']")).click();
			appiumdriver.findElement(MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name='unselected']")).click();
		}
		else
			Assert.assertTrue(appiumdriver.findElements(MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + vehiclepart + "']/UIAButton[@name='unselected']")).size() > 0);
	}

	public void cancelSelectedServiceDetails() {
		cancelbtn.click();
	}

	public void clickAdjustments() {
		appiumdriver.findElementByName("Adjustments").click();
	}
	
	public String getListOfSelectedVehicleParts() {
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['Vehicle Part'].staticTexts()[1]")).getAttribute("value");
	}
	
	public boolean isQuestionFormCellExists() {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists =  appiumdriver.findElements(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['Questions']")).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.MILLISECONDS);
		return exists;
	}
	
	public String getServiceDetailsPriceValue() {
		return appiumdriver.findElementByXPath("//UIAPopover[1]/UIAToolbar[1]/UIAStaticText[1]").getAttribute("value");
	}

}
