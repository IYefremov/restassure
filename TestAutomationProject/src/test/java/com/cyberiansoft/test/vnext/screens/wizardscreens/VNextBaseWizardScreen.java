package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.factories.InspectionTypeData;
import com.cyberiansoft.test.vnext.factories.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextTypeScreenContext;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextBaseWizardScreen extends VNextBaseScreen {

	public static VNextTypeScreenContext typeScreenContext;
	public static InspectionTypes inspectionType;
	
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
	
	public VNextBaseWizardScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
	}



	
	public VNextInspectionsScreen cancelInspection() {
		clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		//Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		return new VNextInspectionsScreen(appiumdriver);
	}

	public VNextWorkOrdersScreen cancelWorkOrder() {
		clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		//Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		return new VNextWorkOrdersScreen(appiumdriver);
	}

	public void clickCancelMenuItem() {
		clickMenuButton();
		tap(cancelinspectionmenu);
	}

	public void clcikSaveInspectionViaMenuAsFinal() {
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickFinalButton();
	}
	
	public VNextInspectionsScreen saveInspectionViaMenu() {
		clickSaveInspectionMenuButton();
		if (new InspectionTypeData(inspectionType).isCanBeFinalDraft())
			clcikSaveInspectionViaMenuAsFinal();
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public VNextWorkOrdersScreen saveWorkOrderViaMenu() {
		clickSaveWorkOrderMenuButton();
		return new VNextWorkOrdersScreen(appiumdriver);
	}
	
	public void clickMenuButton() {
		tap(menubtn);
	}
	
	public void clickSaveInspectionMenuButton() {
		clickMenuButton();
		tap(saveinspectionmenu);
	}
	
	public void clickSaveWorkOrderMenuButton() {
		clickMenuButton();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(saveworkordermenu));
		tap(saveworkordermenu);
	}
	
	public String getNewInspectionNumber() {
		return inspectionnumber.getText().trim();
	}
	
	public VNextNotesScreen clickInspectionNotesOption() {
		clickMenuButton();
		tap(inspectionnotesmenu);
		return new VNextNotesScreen(appiumdriver);
	}
	
	public String getInspectionTotalPriceValue() {
		return appiumdriver.findElement(By.xpath("//div[contains(@class, 'toolbar-inner')]/div[@class='right']")).getText().trim();
	}

}