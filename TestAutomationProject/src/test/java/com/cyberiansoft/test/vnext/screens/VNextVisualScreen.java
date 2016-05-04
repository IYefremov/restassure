package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVisualScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//i[@action='save']")
	private WebElement saveinspectionbtn;
	
	public VNextVisualScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(saveinspectionbtn));
	}

	public VNextInspectionsScreen clickSaveInspectionButton() {		
		tap(saveinspectionbtn);
		testReporter.log(LogStatus.INFO, "Tap Save Inspection button");
		return new VNextInspectionsScreen(appiumdriver);
	}
}
