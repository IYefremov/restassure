package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ApproveInspectionsScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator =".popovers()[0]")
    private IOSElement approvepopup;
	
	@iOSFindBy(uiAutomator =".popover().navigationBar().buttons()[\"Approve\"]")
    private IOSElement approvebtn;
	
	@iOSFindBy(uiAutomator =".buttons()['ApproveAll']")
    private IOSElement approveallbtn;
	
	@iOSFindBy(uiAutomator =".buttons()['SkipAll']")
    private IOSElement skipallbtn;
	
	@iOSFindBy(uiAutomator =".buttons()['DeclineAll']")
    private IOSElement declineallbtn;
	
	@iOSFindBy(uiAutomator =".popovers()[0].toolbars()[0].buttons()['Cancel']")
    private IOSElement cancelstatusreasonbtn;
	
	@iOSFindBy(uiAutomator =".popovers()[0].toolbars()[0].buttons()['Done']")
    private IOSElement donestatusreasonbtn;
	
	public ApproveInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickApproveButton() {
		approvebtn.click();
	}
	
	public void clickApproveAllServicesButton() {
		approveallbtn.click();
	}
	
	public void clickDeclineAllServicesButton() {
		declineallbtn.click();
	}
	
	public void clickSkipAllServicesButton() {
		skipallbtn.click();
	}

	public void drawApprovalSignature () {
		
		//Helpers.drawSignature1();
		Helpers.waitABit(2000);
		int xx = approvepopup.getLocation().getX();
		int yy = approvepopup.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 100,yy + 100).moveTo(xx + 200, yy + 200).release().perform();
	}
	
	public void selectInspectionForApprove(String inspnum) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspnum + "']")).click();
	}
	
	public void drawSignature AfterSelection() {
		Helpers.waitABit(2000);
		IOSElement approveimage = (IOSElement) appiumdriver.findElement(MobileBy.IosUIAutomation(".images()[6]"));
		int xx = approveimage.getLocation().getX();
		int yy = approveimage.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 10,yy + 10).moveTo(xx + 50, yy + 50).release().perform();
	}
	
	public void approveInspectionWithSelectionAndSignature(String inspnum) {
		selectInspectionForApprove(inspnum);
		drawSignature AfterSelection();
		clickSaveButton();
	}
	
	public void approveInspectionApproveAllAndSignature(String inspnum) {
		selectInspectionForApprove(inspnum);
		drawSignature AfterSelection();
		approveallbtn.click();
		clickSaveButton();
	}
	
	public boolean isInspectionServiceExistsForApprove(String inspservice) {
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".tableViews()[1].cells()['" + inspservice + "']")).size() > 0;
	}
	
	public String getInspectionServicePrice(String inspservice) {
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()['" + inspservice + "'].staticTexts()[1]")).getAttribute("name");
	}
	
	public void clickCancelStatusReasonButton() {
		cancelstatusreasonbtn.click();
	}
	
	public void clickDoneStatusReasonButton() {
		donestatusreasonbtn.click();
	}
	
	public void selectStatusReason(String statusreson) throws InterruptedException {
		selectUIAPickerValue(statusreson);
		clickDoneStatusReasonButton();
	}
	
	public void selectInspectionServiceToApprove(String inspservice) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()['" + inspservice + "'].buttons()['approve little off']")).click();
	}

	public void selectInspectionServiceToDecline(String inspservice) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()['" + inspservice + "'].buttons()['decline little off']")).click();
	}
	
	public void selectInspectionServiceToSkip(String inspservice) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()['" + inspservice + "'].buttons()['skip little off']")).click();
	}
	
	public int getNumberOfActiveApproveButtons() {
		return appiumdriver.findElements(MobileBy.xpath("//UIAButton[@name='approve little']")).size();
	}
	
	public int getNumberOfActiveDeclineButtons() {
		return appiumdriver.findElements(MobileBy.xpath("//UIAButton[@name='decline little']")).size();
	}

	public int getNumberOfActiveSkipButtons() {
		return appiumdriver.findElements(MobileBy.xpath("//UIAButton[@name='skip little']")).size();
	}

}
