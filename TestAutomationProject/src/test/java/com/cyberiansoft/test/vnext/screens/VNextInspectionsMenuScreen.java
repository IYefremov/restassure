package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionsMenuScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@handler='_view']")
	private WebElement viewinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_edit']/span")
	private WebElement editinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_createOrder']")
	private WebElement createwoinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_notes']")
	private WebElement notesinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_refreshPictures']")
	private WebElement refreshpicturesinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_email']")
	private WebElement emailinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_archive']")
	private WebElement archiveinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_approve']")
	private WebElement approveinspectionbtn;
	
	@FindBy(xpath="//div[@class='actions-layer popup tablet-fullscreen modal-in']")
	private WebElement inspectionsmenuscreen;
	
	@FindBy(xpath="//div[@class='close-popup close-actions']")
	private WebElement closebtn;
	
	public VNextInspectionsMenuScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(inspectionsmenuscreen));
	}
	
	public VNextVehicleInfoScreen clickEditInspectionMenuItem() {			
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
		waitABit(1000);
		tap(editinspectionbtn);
		waitABit(4000);		
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
	
	public VNextNotesScreen clickNotesInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(notesinspectionbtn));
		tap(notesinspectionbtn);
		log(LogStatus.INFO, "Tap on Email Inspection Menu");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickCreateWorkOrderInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(createwoinspectionbtn));
		tap(createwoinspectionbtn);
		waitABit(8000);
		log(LogStatus.INFO, "Tap on Inspection Create Work Order Menu");
	}
	
	public boolean isCreateWorkOrderMenuPresent() {
		return createwoinspectionbtn.isDisplayed();
	}
	
	public VNextInspectionsScreen archiveInspection() {
		clickArchiveInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogArchiveButton();	
		log(LogStatus.INFO, "Tap on Inspection Archive Menu");
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public void clickArchiveInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(archiveinspectionbtn));
		tap(archiveinspectionbtn);
	}
	
	public VNextApproveScreen clickApproveInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveinspectionbtn));
		tap(approveinspectionbtn);
		log(LogStatus.INFO, "Tap on Approve Inspection Menu");
		return new VNextApproveScreen(appiumdriver);
	}
	
	public boolean isApproveMenuPresent() {
		return approveinspectionbtn.isDisplayed();
	}
	
	public VNextViewScreen clickViewInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(viewinspectionbtn));
		tap(viewinspectionbtn);
		waitABit(3000);
		log(LogStatus.INFO, "Tap on View Inspection Menu");
		return new VNextViewScreen(appiumdriver);
	}

	public VNextInspectionsScreen clickCloseInspectionMenuButton() {
		tap(closebtn);
		log(LogStatus.INFO, "Tap on Close Inspection Menu button");
		return new VNextInspectionsScreen(appiumdriver);
	}
}
