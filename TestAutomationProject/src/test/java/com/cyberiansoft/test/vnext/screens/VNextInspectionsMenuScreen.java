package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextInspectionsMenuScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@data-name='view']")
	private WebElement viewinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='edit']")
	private WebElement editinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_createOrder']")
	private WebElement createwoinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='notes']")
	private WebElement notesinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='refresh']")
	private WebElement refreshpicturesinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='email']")
	private WebElement emailinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='archive']")
	private WebElement archiveinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='approve']")
	private WebElement approveinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_deleteWorkOrder']")
	private WebElement deleteorderbtn;
	
	@FindBy(xpath="//a[@data-name='addSupplement']")
	private WebElement addsupplementbtn;
	
	@FindBy(xpath="//body/div[@data-menu='popup']")
	private WebElement inspectionsmenuscreen;
	
	@FindBy(xpath="//div[@class='close-popup close-actions']")
	private WebElement closebtn;
	
	public VNextInspectionsMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(inspectionsmenuscreen));
	}
	
	public VNextVehicleInfoScreen clickEditInspectionMenuItem() {			
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
		BaseUtils.waitABit(1000);
		tap(editinspectionbtn);
		BaseUtils.waitABit(4000);		
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
		BaseUtils.waitABit(8000);
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
		BaseUtils.waitABit(3000);
		log(LogStatus.INFO, "Tap on View Inspection Menu");
		return new VNextViewScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen clickAddSupplementInspectionMenuItem() {			
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(addsupplementbtn));
		tap(addsupplementbtn);	
		log(LogStatus.INFO, "Tap on Inspection Edit Menu");
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public boolean isAddSupplementInspectionMenuItemPresent() {			
		return addsupplementbtn.isDisplayed();
	}

	public void clickCloseInspectionMenuButton() {
		List<MobileElement> closebtns = appiumdriver.findElements(By.xpath("//div[@class='close-popup close-actions']"));
		for (WebElement closebtn : closebtns)
			if (closebtn.isDisplayed()) {
				tap(closebtn);
				break;
			}
		log(LogStatus.INFO, "Tap on Close Inspection Menu button");
		//return new VNextInspectionsScreen(appiumdriver);
	}
	
	public boolean isDeleteWorkOrderMenuButtonExists() {
		return appiumdriver.findElement(By.xpath("//a[@handler='_deleteWorkOrder']")).isDisplayed();
	}
	
	public void clickDeleteWorkOrderMenuButton() {
		tap(deleteorderbtn);
		log(LogStatus.INFO, "Tap on Delete Work Order Menu button");
	}
	
	public VNextWorkOrdersScreen deleteWorkOrder() {
		clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDeleteButton();	
		return new VNextWorkOrdersScreen(appiumdriver);
	}
}
