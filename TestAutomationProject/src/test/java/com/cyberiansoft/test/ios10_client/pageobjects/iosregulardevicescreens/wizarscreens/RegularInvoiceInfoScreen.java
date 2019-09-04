package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularInvoiceInfoScreen extends RegularBaseWizardScreen implements ITypeScreen {
	
	@iOSXCUITFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	@iOSXCUITFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;

	@iOSXCUITFindBy(accessibility  = "action pay")
    private IOSElement invoicepaybtn;
	
	@iOSXCUITFindBy(accessibility  = "Payment_Tab_Cash")
    private IOSElement cashnormalbtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;

	public RegularInvoiceInfoScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

	}

	public void waitInvoiceInfoScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Info")));
		wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Info")));
	}
	
	public void clickSaveEmptyPO() {
		savebtn.click();
		Helpers.acceptAlert();
	}

	public void  clickSaveAsDraft() {
		clickSave();
		draftalertbtn.click();
		savebtn.click();
	}

	public void clickSaveAsFinal() {
		clickSave();
		finalalertbtn.click();
		savebtn.click();
	}

	public void setPO(String poNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement poFld = wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("txtPO")));
		poFld.sendKeys(poNumber + "\n");
	}
	
	public void setPOWithoutHidingkeyboard(String poNumber)  {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement poFld = wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("txtPO")));
		poFld.sendKeys(poNumber);
	}
	
	public String  getInvoicePOValue()  {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement poFld = wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("txtPO")));
		return poFld.getAttribute("value");
	}

	public boolean isWOSelected(String workOrderId) {
		return appiumdriver.findElements(MobileBy.iOSNsPredicateString("type='XCUIElementTypeCell' and name='" + workOrderId + "'")).size() > 0;
	}
	
	public void clickWO(String workOrderId) {
		appiumdriver.findElement(MobileBy.iOSNsPredicateString("type='XCUIElementTypeCell' and name='" + workOrderId + "'"))
				.findElement(MobileBy.className("XCUIElementTypeStaticText")).click();
	}

	public void cancelInvoice() {
		clickCancelButton();
		acceptAlert();
	}

	public String getOrderSumm() {
		waitInvoiceInfoScreenLoaded();
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public void clickOnWO(String workOrderId) {
		appiumdriver.findElementByAccessibilityId(workOrderId).click();
	}
	
	public void addWorkOrder(String workOrderId)  {
		appiumdriver.findElementByAccessibilityId("Insert").click();
		appiumdriver.findElementByAccessibilityId("InvoiceOrdersView").findElement(MobileBy.AccessibilityId(workOrderId))
				.findElement(MobileBy.AccessibilityId("unselected")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public void addTeamWorkOrder(String workOrderId)  {
		appiumdriver.findElementByAccessibilityId("Insert").click();
		appiumdriver.findElementByAccessibilityId("TeamInvoiceOrdersView").findElement(MobileBy.AccessibilityId(workOrderId))
				.findElement(MobileBy.AccessibilityId("unselected")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getInvoiceNumber() {
		waitInvoiceInfoScreenLoaded();
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name CONTAINS 'I-'").getAttribute("value");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}

	public void clickInvoicePayButton() {
		invoicepaybtn.click();
	}
	
	public void changePaynentMethodToCashNormal() {
		cashnormalbtn.click();
	}
	
	public void setCashCheckAmountValue(String amountvalue) {
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").clear();
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").sendKeys(amountvalue);
	}
	
	public void clickInvoicePayDialogButon() {
		appiumdriver.findElementByAccessibilityId("Pay").click();
	}
}
