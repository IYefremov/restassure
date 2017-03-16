package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularApproveInspectionsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(xpath ="//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelbtn;
	
	@iOSFindBy(accessibility ="Approve")
    private IOSElement approvebtn;
	
	@iOSFindBy(accessibility ="ApproveAll")
    private IOSElement approveallbtn;
	
	@iOSFindBy(accessibility ="DeclineAll")
    private IOSElement declineallbtn;
	
	@iOSFindBy(accessibility ="SkipAll")
    private IOSElement skipallbtn;
	
	@iOSFindBy(xpath ="//XCUIElementTypeToolbar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelstatusreasonbtn;
	
	@iOSFindBy(accessibility  ="Done")
    private IOSElement donestatusreasonbtn;
	
	public RegularApproveInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectInspectionToApprove() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
	}
	
	public void clickDoneButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(donestatusreasonbtn).waitAction(300).release().perform();
		Helpers.waitABit(2000);
	}

	public void clickApproveButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Approve")).click();
		Helpers.waitABit(500);
	}
	
	public void clickApproveAllServicesButton() {
		approveallbtn.click();
	}
	
	public void clickDeclineAllServicesButton() {
		declineallbtn.click();
	}
	
	public void clickSkipAllServicesButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(skipallbtn).waitAction(1000).release().perform();
		//skipallbtn.click();
	}

	public void clickCancelButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(cancelbtn).waitAction(1000).release().perform();
	}
	
	public void clickSaveButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(savebtn).waitAction(1000).release().perform();
		Helpers.waitABit(1000);
	}
	
	public void clickSignButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Sign")).click();
	}
	
	public void clickSingnAndDrawApprovalSignature () throws InterruptedException {
		clickSignButton();
		Thread.sleep(1000);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 100,yy + 100).waitAction(300).moveTo(xx + 200, yy + 200).release().perform();
		Helpers.waitABit(1000);

	}
	
	public void drawApprovalSignature () throws InterruptedException {
		Thread.sleep(1000);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(xx + 100,yy + 100).waitAction(3000).moveTo(xx + 200, yy + 200).release().perform();
		//Helpers.waitABit(3000);

	}

	public void clickInspectionInfoAlertCloseButton() {
		Helpers.waitForAlert();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Close")).waitAction(1000).release().perform();
	}
	
	public void approveInspectionApproveAllAndSignature() throws InterruptedException {
		Helpers.waitABit(4000);
		approveallbtn.click();
		clickSaveButton();
		clickSingnAndDrawApprovalSignature ();
		clickDoneButton();
	}
	
	public boolean isInspectionServiceExistsForApprove(String inspservice) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(inspservice)).size() > 0;
	}
	
	public String getInspectionServicePrice(String inspservice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(inspservice)));
		WebElement par = getTableCell(inspservice);
		return par.findElement(MobileBy.xpath(".//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void clickCancelStatusReasonButton() {
		cancelstatusreasonbtn.click();
	}
	
	public void clickDoneStatusReasonButton() {
		System.out.println("+++++" + appiumdriver.findElementsByAccessibilityId("Done").size());
		donestatusreasonbtn.click();
		Helpers.waitABit(2000);
	}
	
	public void selectStatusReason(String statusreson) throws InterruptedException {
		Helpers.waitABit(2000);
		selectUIAPickerValue(statusreson);
		clickDoneStatusReasonButton();
	}
	
	public void selectInspectionServiceToApprove(String inspservice) {
		appiumdriver.findElement(MobileBy.AccessibilityId(inspservice)).click();
		
		//par.findElement(By.xpath(".//XCUIElementTypeButton[@name='approve little off']")).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='approve little off']"))).waitAction(1000).release().perform();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['approve little off']")).click();
	}
	
	public void selectInspectionServiceToApproveByIndex(String inspservice, int inspnumber) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press((WebElement) appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='approve little off']")).get(inspnumber)).waitAction(1000).release().perform();
	}

	public void selectInspectionServiceToDecline(String inspservice) {
		WebElement par = getTableCell(inspservice);
		par.findElement(By.xpath(".//XCUIElementTypeButton[@name='decline little off']")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['decline little off']")).click();
	}
	
	public void selectInspectionServiceToSkip(String inspservice) {
		appiumdriver.findElement(MobileBy.AccessibilityId(inspservice)).click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='skip little off']"))).waitAction(1000).release().perform();
		
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['skip little off']")).click();
	}
	
	public void selectInspectionServiceToSkipByIndex(String inspservice, int inspnumber) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press((WebElement) appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='skip little off']")).get(inspnumber)).waitAction(1000).release().perform();
	}
	
	public void selectInspection(String inspnumber) {
		appiumdriver.findElement(MobileBy.AccessibilityId(inspnumber)).click();
	}
	
	public int getNumberOfActiveApproveButtons() {
		return appiumdriver.findElements(MobileBy.AccessibilityId("approve little")).size();
	}
	
	public int getNumberOfActiveDeclineButtons() {
		return appiumdriver.findElements(MobileBy.AccessibilityId("decline little")).size();
	}

	public int getNumberOfActiveSkipButtons() {
		return appiumdriver.findElements(MobileBy.AccessibilityId("skip little")).size();
	}
	
	public String getInspectionTotalAmount() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("labelInspectionAmount")).getAttribute("value");
	}
	
	public WebElement getTableCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}
