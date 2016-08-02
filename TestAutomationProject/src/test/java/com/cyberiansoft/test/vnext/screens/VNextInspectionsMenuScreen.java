package com.cyberiansoft.test.vnext.screens;

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
	
	@FindBy(xpath="//div[@class='page inspections-menu hide-toolbar hide-searchbar page-on-center']")
	private WebElement inspectionsmenuscreen;
	
	
	public VNextInspectionsMenuScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(inspectionsmenuscreen));
	}
	
	public VNextVehicleInfoScreen clickEditInspectionMenuItem() {
		waitABit(1000);
		tap(editinspectionbtn);
		testReporter.log(LogStatus.INFO, "Tap on Inspection Edit Menu");
		return new VNextVehicleInfoScreen(appiumdriver);
	}

}
