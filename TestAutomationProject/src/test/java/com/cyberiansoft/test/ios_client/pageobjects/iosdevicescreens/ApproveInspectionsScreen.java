package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ApproveInspectionsScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator =".popovers()[0]")
    private IOSElement approvepopup;
	
	@iOSFindBy(uiAutomator =".popover().navigationBar().buttons()[\"Approve\"]")
    private IOSElement approvebtn;
	
	@iOSFindBy(uiAutomator =".buttons()['ApproveAll']")
    private IOSElement approveallbtn;
	
	public ApproveInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clickApproveButton() {
		approvebtn.click();
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

}
