package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SettingsScreen extends BaseAppScreen {
	
	private By inspectionsinglepagetoggle = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[3]");
	//@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[3]")
    //private IOSElement inspectionsinglepagetoggle;
	
	private By duplicatestoggle = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[1]");	
	
	//@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[1]")	
    //private IOSElement duplicatestoggle;
	
	private By showtopcustomerstoggle = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[12]/XCUIElementTypeSwitch[2]");
	private By invoicescustomlayout = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]/XCUIElementTypeSwitch");

	public SettingsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setInspectionToSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElement(inspectionsinglepagetoggle);
		if (singlepagetoggle.getAttribute("value").equals("0"))
			singlepagetoggle.click();
		BaseTestCase.inspSinglePageMode = true;
	}

	public void setInspectionToNonSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElement(inspectionsinglepagetoggle);
		if (singlepagetoggle.getAttribute("value").equals("1"))
			singlepagetoggle.click();
		BaseTestCase.inspSinglePageMode = false;
	}

	public void setInsvoicesCustomLayoutOff() {
		swipeScreenUp();
		IOSElement par = (IOSElement)  appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[@name='Use in invoices']/.."));
		IOSElement singlepagetoggle = (IOSElement) par.findElement(MobileBy.className("XCUIElementTypeSwitch"));
		if (singlepagetoggle.getAttribute("value").equals("1"))
			singlepagetoggle.click();
	}

	public void setCheckDuplicatesOn() {
		IOSElement dublicates = (IOSElement) appiumdriver.findElement(duplicatestoggle);
		if (dublicates.getAttribute("value").equals("0"))
			dublicates.click();
	}

	public void setCheckDuplicatesOff() {
		IOSElement dublicates = (IOSElement) appiumdriver.findElement(duplicatestoggle);
		if (dublicates.getAttribute("value").equals("1"))
			dublicates.click();
	}
	
	public void setShowTopCustomersOn() {
		IOSElement showtopcustomerstogle = (IOSElement) appiumdriver.findElement(showtopcustomerstoggle);
		if (showtopcustomerstogle.getAttribute("value").equals("0"))
			showtopcustomerstogle.click();
	}
	
	public void setShowTopCustomersOff() {
		IOSElement showtopcustomerstogle = (IOSElement) appiumdriver.findElement(showtopcustomerstoggle);
		if (showtopcustomerstogle.getAttribute("value").equals("1"))
			showtopcustomerstogle.click();
	}

}
