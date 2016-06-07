package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.relevantcodes.extentreports.LogStatus;

public class VNextBaseInspectionsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='estimation-number']/span")
	private WebElement inspectionnumber;

	@FindBy(xpath="//div[@class='left']/i[@action='menu']")
	private WebElement menubtn;
	
	@FindBy(xpath="//div[text()='Cancel Inspection']")
	private WebElement cancelinspectionmenu;
	
	@FindBy(xpath="//div[text()='Save Inspection']")
	private WebElement saveinspectionmenu;
	
	@FindBy(xpath="//div[text()='Notes']")
	private WebElement inspectionnotesmenu;
	
	public VNextBaseInspectionsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
	}
	
	public VNextInspectionsScreen cancelInspection() {
		clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public void clickCancelInspectionMenuItem() {
		clickMenuButton();
		tap(cancelinspectionmenu);
		testReporter.log(LogStatus.INFO, "Tap Cancel inspection button");
	}
	
	public VNextInspectionsScreen saveInspectionViaMenu() {
		clickSaveInspectionMenuButton();
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public void clickMenuButton() {
		tap(menubtn); 
		testReporter.log(LogStatus.INFO, "Tap Menu button");
	}
	
	public void clickSaveInspectionMenuButton() {
		clickMenuButton();
		tap(saveinspectionmenu);
		testReporter.log(LogStatus.INFO, "Tap Save Inspection button");
	}
	
	public String getNewInspectionNumber() {
		return inspectionnumber.getText();
	}
	
	public VNextNotesScreen clickInspectionNotesOption() {
		clickMenuButton();
		tap(inspectionnotesmenu);
		testReporter.log(LogStatus.INFO, "Tap Inspection Notes button");
		return new VNextNotesScreen(appiumdriver);
	}
}
