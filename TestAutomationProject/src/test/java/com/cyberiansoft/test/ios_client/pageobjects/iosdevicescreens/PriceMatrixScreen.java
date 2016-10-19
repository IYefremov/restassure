package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class PriceMatrixScreen extends iOSHDBaseScreen {
	
	//Sizes
	public final static String DIME_SIZE = "DIME";
	public final static String NKL_SIZE = "NKL";
	public final static String QTR_SIZE = "QTR";
	public final static String HLF_SIZE = "HLF";
	public final static String QUICK_QUOTE_SIZE = "Quick Quote";
	
	//Severities
	public final static String VERYLIGHT_SEVERITY = "Very Light (1-5 Dents)";
	public final static String LIGHT_SEVERITY = "Light (6-15 Dents)";
	public final static String HEAVY_SEVERITY = "Heavy (51-75 Dents)";
	public final static String MEDIUM_SEVERITY = "Medium (31-50 Dents)";
	public final static String MODERATE_SEVERITY = "Moderate (16-30 Dents)";
	public final static String SEVERE_SEVERITY = "Severe (76-100 Dents)";
	public final static String QUICK_QUOTE_SEVERITY = "Quick Quote";
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Price\"]/UIAStaticText[@name=\"Price\"]")
    private IOSElement pricecell;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Time\"]/UIAStaticText[@name=\"Time\"]")
    private IOSElement timecell;
	
	@iOSFindBy(uiAutomator = ".tableViews()[1].cells()[\"Price\"].textFields()[0]")
    private IOSElement pricevaluefld;
	
	@iOSFindBy(uiAutomator = ".tableViews()[1].cells()[\"Notes\"]")
    private IOSElement notescell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[contains(@name,\"Technicians\")]")
    private IOSElement technicianscell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[contains(@name,\"Technicians\")]/UIAStaticText[2]")
    private IOSElement technicianscellvalue;
	
	@iOSFindBy(name = "Compose")
    private IOSElement composecell;
	
	@iOSFindBy(xpath = "//UIATableView[2]/UIATableCell[@name=\"Clear\"]")
    private IOSElement clearvehiclepartdatabtn;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Cancel\"]")
    private IOSElement cancelbtn;
	
	public PriceMatrixScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectPriceMatrix(String pricematrix) {
		Helpers.scroolTo(pricematrix);
		Helpers.text_exact(pricematrix).click();

	}

	public void setSizeAndSeverity(String size, String severity) {
		Helpers.text_exact("Size").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
				+ size + "\"]").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[2]/UIATableCell[@name=\""
				+ severity + "\"]").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIANavigationBar[1]/UIAButton[@name=\"Save\"]").click();
	}

	public void setPrice(String price) throws InterruptedException {
		pricecell.click();
		Helpers.keyboadrType(price + "\n");
	}
	
	public void setTime(String timevalue) throws InterruptedException {
		timecell.click();
		Helpers.keyboadrType(timevalue + "\n");
	}

	/*public void setSeverity(String severity) {
		Helpers.text_exact("Severity").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ severity + "\"]").click();
	}*/

	public void assertPriceCorrect(String price) {
		Assert.assertEquals(pricevaluefld.getText(), price);
	}

	public void selectDiscaunt(String discaunt) {
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].tableViews()[1].cells()['"
			//	+ discaunt + "']")).click();
		Helpers.scroolToByXpath("//UIATableCell[@name='"
				+ discaunt + "']");
		
		appiumdriver.findElementByXPath("//UIATableCell[@name='"
				+ discaunt + "']/UIAButton[@name='unselected']").click();
	}

	public void clickDiscaunt(String discaunt) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name,\""
						+ discaunt + "\")]").click();
	}
	
	public void switchOffOption(String optionname) {
		((IOSElement) appiumdriver.findElementByXPath("//UIASwitch[@name=\"" + optionname + "\"]")).setValue("0");
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		return appiumdriver.findElementByXPath("//UIATableView[2]/UIATableCell[@name=\""
						+ discaunt + "\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementByXPath("//UIATableView[2]/UIATableCell[@name=\""
						+ discaunt + "\"]").isDisplayed();
	}
	
	public boolean isPriceMatrixSelected(String pricematrix) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ pricematrix + "\"]/UIAButton[@name=\"selected\"]").isDisplayed();
	}

	public void assertNotesExists() {
		Assert.assertTrue(notescell.isDisplayed());
	}

	public void assertTechniciansExists() {
		Assert.assertTrue(technicianscell.isDisplayed());
	}

	public String getTechniciansValue() {
		return technicianscellvalue.getAttribute("name");
	}

	public void clickOnTechnicians() {
		technicianscell.click();
	}
	
	public void clickNotesButton() {
		composecell.click();
	}

	public void clickSaveButton() {
		savebtn.click();
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void clearVehicleData() {
		clearvehiclepartdatabtn.click();
		String msg = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(msg, AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
	}

}
