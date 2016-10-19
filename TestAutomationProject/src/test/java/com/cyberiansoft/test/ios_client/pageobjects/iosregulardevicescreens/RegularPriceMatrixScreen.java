package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularPriceMatrixScreen extends iOSRegularBaseScreen {
	
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
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Price\"]/UIATextField[1]")
    private IOSElement pricevaluefld;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[@name=\"Notes\"]")
    private IOSElement notescell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[contains(@name,\"Technicians\")]")
    private IOSElement technicianscell;
	
	@iOSFindBy(xpath = "//UIATableView/UIATableCell[contains(@name,\"Technicians\")]/UIAStaticText[2]")
    private IOSElement technicianscellvalue;
	
	@iOSFindBy(accessibility  = "Compose")
    private IOSElement composecell;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Delete\"]")
    private IOSElement toolbardeletebtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[1][@name=\"Cancel\"]")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[1][@name=\"Services\"]")
    private IOSElement servicesbtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@name=\"Back\"]")
    private IOSElement backbtn;
	
	public RegularPriceMatrixScreen(AppiumDriver driver) {
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
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
				+ size + "\"]").click();
		//appiumdriver.findElementByXPath("//UIANavigationBar[1]/UIAButton[@name=\"Save\"]").click();
		//Helpers.text_exact("Severity").click();
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
				+ severity + "\"]").click();
		appiumdriver.findElementByXPath("//UIANavigationBar[1]/UIAButton[@name=\"Save\"]").click();
	}

	public void setPrice(String price) throws InterruptedException {
		pricecell.click();
		Helpers.keyboadrType(price + "\n");
	}
	
	public void setTime(String timevalue) throws InterruptedException {
		timecell.click();
		Helpers.keyboadrType(timevalue + "\n");
	}

	public void assertPriceCorrect(String price) {
		Assert.assertEquals(pricevaluefld.getText(), price);
	}

	public void selectDiscaunt(String discaunt) {
		Helpers.scroolTo(discaunt);
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
				+ discaunt + "\"]/UIAButton[@name=\"unselected\"]").click();
	}

	public void clickDiscaunt(String discaunt) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name,\""
						+ discaunt + "\")]").click();
	}
	
	public void switchOffOption(String optionname) {
		((IOSElement) appiumdriver.findElementByXPath("//UIASwitch[@name=\"" + optionname + "\"]")).setValue("0");
	}
	
	public String getDiscauntPriceAndValue(String discaunt) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ discaunt + "\"]/UIAStaticText[2]").getAttribute("name");
	}
	
	public boolean isDiscauntPresent(String discaunt) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ discaunt + "\"]").isDisplayed();
	}
	
	/*public boolean isPriceMatrixSelected(String pricematrix) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ pricematrix + "\"]/UIAButton[@name=\"selected\"]").isDisplayed();
	}*/
	
	public boolean isPriceMatrixContainsPriceValue(String pricematrix, String pricevalue) {
		//Helpers.scroolTo(pricematrix);
		return appiumdriver.findElementsByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ pricematrix + "\"]/UIAStaticText[@name=\""+ pricevalue + "\"]").size() > 0;
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

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void clickServicesButton() {
		servicesbtn.click();
	}	
	
	public void clickBackButton() {
		backbtn.click();
	}
	
	public void clearVehicleData() {
		toolbardeletebtn.click();
		String msg = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(msg, AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
	}

	public String getInspectionSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.xpath("//UIAStaticText[@name='SubtotalAmount']")).getAttribute("value");
	}
}
