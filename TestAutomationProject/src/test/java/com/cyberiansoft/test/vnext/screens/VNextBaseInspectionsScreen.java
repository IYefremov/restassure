package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.relevantcodes.extentreports.LogStatus;

public class VNextBaseInspectionsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='estimation-number']")
	private WebElement inspectionnumber;

	@FindBy(xpath="//*[@action='more_actions']")
	private WebElement menubtn;
	
	@FindBy(xpath="//a[@handler='_cancel']")
	private WebElement cancelinspectionmenu;
	
	@FindBy(xpath="//a[@handler='_save']")
	private WebElement saveinspectionmenu;
	
	@FindBy(xpath="//a[@handler='_save']")
	private WebElement saveworkordermenu;
	
	@FindBy(xpath="//a[@handler='_notes']")
	private WebElement inspectionnotesmenu;
	
	@FindBy(xpath="//span[@action='save']")
	private WebElement savebtn;
	
	public VNextBaseInspectionsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
	}
	
	public VNextInspectionsScreen cancelInspection() {
		clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		//Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public void clickCancelInspectionMenuItem() {
		clickMenuButton();
		tap(cancelinspectionmenu);
		log(LogStatus.INFO, "Tap Cancel inspection button");
	}
	
	public VNextInspectionsScreen saveInspectionViaMenu() {
		clickSaveInspectionMenuButton();
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextWorkOrdersScreen saveWorkOrderViaMenu() {
		clickSaveWorkOrderMenuButton();
		return new VNextWorkOrdersScreen(appiumdriver);
	}
	
	public void clickMenuButton() {
		tap(menubtn); 
		log(LogStatus.INFO, "Tap Menu button");
	}
	
	public void clickSaveInspectionMenuButton() {
		clickMenuButton();
		tap(saveinspectionmenu);
		log(LogStatus.INFO, "Tap Save Inspection button");
	}
	
	public void clickSaveWorkOrderMenuButton() {
		clickMenuButton();
		tap(saveworkordermenu);
		log(LogStatus.INFO, "Tap Save Work Order button");
	}
	
	public String getNewInspectionNumber() {
		return inspectionnumber.getText().trim();
	}
	
	public VNextNotesScreen clickInspectionNotesOption() {
		clickMenuButton();
		tap(inspectionnotesmenu);
		log(LogStatus.INFO, "Tap Inspection Notes button");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public String getInspectionTotalPriceValue() {
		return appiumdriver.findElement(By.xpath("//div[contains(@class, 'toolbar-inner')]/div[@class='right']")).getText().trim();
	}

}
