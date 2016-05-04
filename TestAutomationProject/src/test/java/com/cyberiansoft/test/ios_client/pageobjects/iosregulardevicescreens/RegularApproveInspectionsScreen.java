package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularApproveInspectionsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(uiAutomator =".navigationBar().buttons()[\"Approve\"]")
    private IOSElement approvebtn;
	
	@iOSFindBy(uiAutomator =".toolbars()[0].buttons()[0]")
    private IOSElement approveallbtn;
	
	public RegularApproveInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void selectInspectionToApprove() {
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]").click();
	}

	public void clickApproveButton() {
		approvebtn.click();
	}

	public void drawApprovalSignature () throws InterruptedException {
		
		Thread.sleep(1000);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[2]/UIANavigationBar[1]");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 100,yy + 100).moveTo(xx + 200, yy + 200).release().perform();

	}

	public void clickInspectionInfoAlertCloseButton() {
		Helpers.waitForAlert();
		appiumdriver.findElementByXPath("//UIAAlert[1]/UIATableView[1]/UIATableCell[@name=\"Close\"]").click();
	}
	
	public void approveInspectionApproveAllAndSignature(String inspnum) throws InterruptedException {
		approveallbtn.click();
		clickSaveButton();
		drawApprovalSignature ();
		clickApproveButton();
	}

}
