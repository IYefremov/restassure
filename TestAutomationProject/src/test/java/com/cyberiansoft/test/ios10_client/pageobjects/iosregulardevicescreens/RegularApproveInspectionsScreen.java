package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegularApproveInspectionsScreen extends iOSRegularBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(xpath ="//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelbtn;
	
	@iOSXCUITFindBy(accessibility ="Approve")
    private IOSElement approvebtn;
	
	@iOSXCUITFindBy(accessibility ="ApproveAll")
    private IOSElement approveallbtn;
	
	@iOSXCUITFindBy(accessibility ="DeclineAll")
    private IOSElement declineallbtn;
	
	@iOSXCUITFindBy(accessibility ="SkipAll")
    private IOSElement skipallbtn;
	
	@iOSXCUITFindBy(xpath ="//XCUIElementTypeToolbar/XCUIElementTypeButton[@name='Cancel']")
    private IOSElement cancelstatusreasonbtn;
	
	@iOSXCUITFindBy(accessibility  ="Done")
    private IOSElement donestatusreasonbtn;
	
	public RegularApproveInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void clickDoneButton() {
		donestatusreasonbtn.click();
	}

	public void clickApproveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Approve")));
		wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(approvebtn)).click();
	}
	
	public void clickApproveAllServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ApproveAll")));
		approveallbtn.click();
	}
	
	public void clickDeclineAllServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("DeclineAll")));
		declineallbtn.click();
	}
	
	public void clickSkipAllServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("SkipAll")));
		skipallbtn.click();
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void clickSaveButton() {
		savebtn.click();
	}
	
	public void clickSignButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Sign")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Sign"))).click();
	}
	
	public void clickSingnAndDrawApprovalSignature() {
		clickSignButton();
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther[@name='TouchInputView']/XCUIElementTypeImage[1]/XCUIElementTypeOther");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();

	}
	
	public void drawApprovalSignature() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("TouchInputView")));
		MobileElement element = (MobileElement) appiumdriver.findElementByAccessibilityId("TouchInputView");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).
				moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();
		clickDoneButton();

	}
	
	public void approveInspectionApproveAllAndSignature() {
		approveallbtn.click();
		clickSaveButton();
		clickSingnAndDrawApprovalSignature();
		clickDoneButton();
	}
	
	public boolean isInspectionServiceExistsForApprove(String inspservice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("approve little off")));
		return appiumdriver.findElements(MobileBy.AccessibilityId(inspservice)).size() > 0;
	}

	public boolean isInspectionServiceExistsForApprove(ServiceData serviceData) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("approve little off")));
		String inspservice = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null)
			inspservice = inspservice + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		return appiumdriver.findElements(MobileBy.AccessibilityId(inspservice)).size() > 0;
	}
	
	public String getInspectionServicePrice(String inspservice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,30);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspservice)));
		WebElement par = getTableCell(inspservice);
		return par.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void clickCancelStatusReasonButton() {
		IOSElement pickertoolbar = (IOSElement) appiumdriver.findElementByAccessibilityId("StringPickerVC_Reason");
		pickertoolbar.findElementByAccessibilityId("Cancel").click();
	}
	
	public void clickDoneStatusReasonButton() {
		donestatusreasonbtn.click();
	}
	
	public void selectStatusReason(String statusreson) {
		selectUIAPickerValue(statusreson);
		clickDoneStatusReasonButton();
	}
	
	public void selectInspectionServiceToApprove(String inspservice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspservice)));
		swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspservice + "']/..")));

		appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='approve little off']")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['approve little off']")).click();
	}
	
	public void selectInspectionServiceToApproveByIndex(String inspservice, int inspnumber) {
		((IOSElement) appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='approve little off']")).get(inspnumber)).click();
	}

	public void selectInspectionServiceToDecline(String inspservice) {
		WebElement par = getTableCell(inspservice);
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='decline little off']")).click();
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['decline little off']")).click();
	}
	
	public void selectInspectionServiceToSkip(String inspservice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(inspservice)));

		if (!appiumdriver.findElementByAccessibilityId(inspservice).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + inspservice + "']/..")));
			appiumdriver.findElement(MobileBy.AccessibilityId(inspservice)).click();
		}
		appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeButton[@name='skip little off']")).click();
		
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()['" + inspservice + "'].buttons()['skip little off']")).click();
	}
	
	public void selectInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ApproveInspectionsView")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber))).click();
	}

	public void clickDeclinePopupButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Decline")).click();
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
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ApproveAll")));
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

	public void selectApproveInspectionServiceStatus(ServiceData serviceData) {
		String serviceName = serviceData.getServiceName();
		if (serviceData.getVehiclePart() != null) {
			serviceName = serviceName + " (" + serviceData.getVehiclePart().getVehiclePartName() + ")";
		}
		switch (serviceData.getServiceStatus()){
			case APPROVED:
				selectInspectionServiceToApprove(serviceName);
				break;
			case DECLINED:
				selectInspectionServiceToDecline(serviceName);
				break;
			case SKIPPED:
				selectInspectionServiceToSkip(serviceName);
				break;
		}
	}

	public boolean isServiceApproved(String serviceName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ApproveAll")));
		return !appiumdriver.findElementByAccessibilityId(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'approve little'"))
				.getAttribute("name").contains("off");
	}

	public boolean isServiceSkipped(String serviceName) {
		return !appiumdriver.findElementByAccessibilityId(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'skip little'"))
				.getAttribute("name").contains("off");
	}

	public boolean isServiceDeclibed(String serviceName) {
		return !appiumdriver.findElementByAccessibilityId(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'decline little'"))
				.getAttribute("name").contains("off");
	}

}
