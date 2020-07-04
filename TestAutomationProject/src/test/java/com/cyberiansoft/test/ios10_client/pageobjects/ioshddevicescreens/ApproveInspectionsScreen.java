package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.ios10_client.utils.AppiumWait;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
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

public class ApproveInspectionsScreen extends iOSHDBaseScreen {
	
	//@iOSXCUITFindBy(accessibility = "TouchInputViewInPopover")
	@iOSXCUITFindBy(accessibility = "TouchInputView")
    private IOSElement approvepopup;
	
	@iOSXCUITFindBy(accessibility = "Approve")
    private IOSElement approvebtn;
	
	@iOSXCUITFindBy(accessibility = "ApproveAll")
    private IOSElement approveallbtn;
	
	@iOSXCUITFindBy(accessibility = "SkipAll")
    private IOSElement skipallbtn;
	
	@iOSXCUITFindBy(accessibility = "DeclineAll")
    private IOSElement declineallbtn;
	
	@iOSXCUITFindBy(accessibility ="Cancel")
    private IOSElement cancelstatusreasonbtn;
	
	@iOSXCUITFindBy(accessibility ="Done")
    private IOSElement donebtn;
	
	public ApproveInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void clickApproveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Approve")));
		approvebtn.click();
	}
	
	public void clickApproveAfterSelection() {
		Helpers.waitForAlert();
		AppiumWait.getGeneralFluentWait(5, 300).until(driver -> {
			appiumdriver.findElementByClassName("XCUIElementTypeAlert").findElement(MobileBy.AccessibilityId("Approve")).click();
			return true;
		});
	}
	

	public void clickDoneButton() {
		if (appiumdriver.findElements(MobileBy.AccessibilityId("Done")).size() > 1)
			((IOSElement) appiumdriver.findElements(MobileBy.AccessibilityId("Done")).get(1)).click();
		else
			appiumdriver.findElement(MobileBy.AccessibilityId("Done")).click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
	public void clickApproveAllServicesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveallbtn));
		approveallbtn.click();
	}
	
	public void clickDeclineAllServicesButton() {
		declineallbtn.click();
	}
	
	public void clickSkipAllServicesButton() {
		skipallbtn.click();
	}

	public void drawApprovalSignature() {
		//clickSignButton();
		//Helpers.drawSignature1();
		int xx = approvepopup.getLocation().getX();
		int yy = approvepopup.getLocation().getY();
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(xx + 100,yy + 130)).
		waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(xx + 200, yy + 230)).release().perform();
	}
	
	public void selectInspectionForApprove(String inspectionNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		WebElement approveInspectionsView = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("type=='XCUIElementTypeTable' and name =='ApproveInspectionsView'")));
		approveInspectionsView.findElement(MobileBy.AccessibilityId(inspectionNumber)).click();
	}
	
	public void clickSignButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Sign")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Sign"))).click();
	}
	
	
	public void drawSignatureAfterSelection() {
		clickSignButton();

		IOSElement approveimage = null;
		if (appiumdriver.findElements(MobileBy.AccessibilityId("TouchInputView")).size() == 2)
			approveimage = (IOSElement) appiumdriver.findElements(MobileBy.AccessibilityId("TouchInputView")).get(1);
		else if (appiumdriver.findElements(MobileBy.AccessibilityId("TouchInputView")).size() > 2)
			approveimage = (IOSElement) appiumdriver.findElements(MobileBy.AccessibilityId("TouchInputView")).get(2);
		else
			approveimage  = (IOSElement) appiumdriver.findElement(MobileBy.AccessibilityId("TouchInputView"));
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(approveimage.getLocation().getX()+100, approveimage.getLocation().getY()+100)).
				waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).
		moveTo(PointOption.point(approveimage.getLocation().getX()+200, approveimage.getLocation().getY()+150)).release().perform();

	}
	
	
	public void approveInspectionWithSelectionAndSignature(String inspnum) {
		selectInspectionForApprove(inspnum);
		clickSaveButton();
		drawSignatureAfterSelection();
		clickDoneButton();
	}
	
	public void approveInspectionApproveAllAndSignature(String inspnum) {
		selectInspectionForApprove(inspnum);
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ApproveAll")));
		approveallbtn.click();
		clickSaveButton();
		drawSignatureAfterSelection();
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
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspservice + "']/XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void clickCancelStatusReasonButton() {
		appiumdriver.findElementByAccessibilityId("StringPickerVC_Reason").findElement(MobileBy.AccessibilityId("Cancel")).click();
		//cancelstatusreasonbtn.click();
	}
	
	public void clickDoneStatusReasonButton() {
		donebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
	public void selectStatusReason(String statusreson) {
		selectUIAPickerValue(statusreson);
		clickDoneStatusReasonButton();
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

	private WebElement getServiceCell(String serviceName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeCell' AND name = '" +serviceName + "'")));
	}
	
	public void selectInspectionServiceToApprove(String serviceName) {
		getServiceCell(serviceName).findElement(MobileBy.AccessibilityId("approve little off")).click();
	}
	
	public void selectInspectionServiceToApproveByIndex(String serviceName, int inspnumber) {
		((IOSElement) appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + serviceName + "']/XCUIElementTypeButton[@name='approve little off']")).get(inspnumber)).click();
	}

	public void selectInspectionServiceToDecline(String serviceName) {
		getServiceCell(serviceName).findElement(MobileBy.AccessibilityId("decline little off")).click();
	}
	
	public void selectInspectionServiceToSkip(String serviceName) {
		getServiceCell(serviceName).findElement(MobileBy.AccessibilityId("skip little off")).click();
	}
	
	public void selectInspectionServiceToSkipByIndex(String serviceName, int inspnumber) {
		((IOSElement) appiumdriver.findElements(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + serviceName + "']/XCUIElementTypeButton[@name='skip little off']")).get(inspnumber)).click();
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

	public void clickSaveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

	public boolean isServiceApproved(String serviceName) {
		return !getServiceCell(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'approve little'"))
				.getAttribute("name").contains("off");
	}

	public boolean isServiceSkipped(String serviceName) {
		return !getServiceCell(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'skip little'"))
				.getAttribute("name").contains("off");
	}

	public boolean isServiceDeclibed(String serviceName) {
		return !getServiceCell(serviceName).findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' and name contains 'decline little'"))
				.getAttribute("name").contains("off");
	}

}
