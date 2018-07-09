package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import io.appium.java_client.AppiumDriver;
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
	private By invoicescustomlayout = By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[16]/XCUIElementTypeSwitch");

	public SettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setInspectionToSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElement(inspectionsinglepagetoggle);
		if (singlepagetoggle.getAttribute("value").equals("0"))
			singlepagetoggle.click();
	}

	public void setInspectionToNonSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElement(inspectionsinglepagetoggle);
		if (singlepagetoggle.getAttribute("value").equals("1"))
			singlepagetoggle.click();
	}

	public void setInsvoicesCustomLayoutOff() {
		swipeScreenUp();
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElement(invoicescustomlayout);
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
