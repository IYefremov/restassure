package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionsMenuScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@action='edit']/i")
	private WebElement editinspectionbtn;
	
	@FindBy(xpath="//a[@action='email']/i")
	private WebElement emailinspectionbtn;
	
	@FindBy(xpath="//a[@action='create-order']/i")
	private WebElement createwoinspectionbtn;
	
	@FindBy(xpath="//div[@class='page inspections-menu hide-toolbar hide-searchbar page-on-center']")
	private WebElement inspectionsmenuscreen;
	
	
	public VNextInspectionsMenuScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(inspectionsmenuscreen));
	}
	
	public VNextVehicleInfoScreen clickEditInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
		tap(editinspectionbtn);
		waitABit(1000);
		log(LogStatus.INFO, "Tap on Inspection Edit Menu");
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickEmailInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(emailinspectionbtn));
		tap(emailinspectionbtn);
		log(LogStatus.INFO, "Tap on Email Inspection Menu");
		return new VNextEmailScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen clickCreateWorkOrderInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(createwoinspectionbtn));
		tap(createwoinspectionbtn);
		waitABit(8000);
		log(LogStatus.INFO, "Tap on Inspection Create Work Order Menu");
		return new VNextVehicleInfoScreen(appiumdriver);
	}

}
