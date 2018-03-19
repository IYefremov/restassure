package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class InvoiceInfoScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;
	
	@iOSFindBy(accessibility = "txtPO")
    private IOSElement setpofld;
	
	@iOSFindBy(accessibility = "InvoiceOrdersTable")
    private IOSElement invoicewostable;
	
	@iOSFindBy(accessibility  = "action pay")
    private IOSElement invoicepaybtn;
	
	@iOSFindBy(accessibility  = "cash normal")
    private IOSElement cashnormalbtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;*/

	public InvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickSaveEmptyPO() {
		clickSaveButton();
		Alert alert = appiumdriver.switchTo().alert();
		alert.accept();
	}

	public void clickSaveAsDraft()  {
		appiumdriver.findElementByAccessibilityId("Save").click();
		appiumdriver.findElementByAccessibilityId("Draft").click();
		clickSaveButton();
	}

	public void clickSaveAsFinal() {
		clickSaveButton();
		appiumdriver.findElementByAccessibilityId("Final").click();
		clickSaveButton();
		Helpers.waitABit(500);
	}
	
	public String getInvoicePOValue() {
		return appiumdriver.findElementByAccessibilityId("txtPO").getAttribute("value");
	}

	public void setPO(String _po) {
		setPOWithoutHidingkeyboard(_po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setPOWithoutHidingkeyboard(String _po) {
		((IOSElement) appiumdriver.findElementByAccessibilityId("txtPO")).setValue(_po);
	}

	public void assertWOIsSelected(String wonumber) {
		Assert.assertTrue(appiumdriver.findElementsByAccessibilityId(wonumber).size() > 0);
	}
	
	public void clickFirstWO() {
		((IOSElement) appiumdriver.findElementByAccessibilityId("InvoiceOrdersTable")).findElementByXPath("//XCUIElementTypeCell[1]").click();
		Helpers.waitABit(1000);
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}
	
	public void addWorkOrder(String wonumber) {
		Helpers.waitABit(2000);
		((IOSElement) appiumdriver.findElementByAccessibilityId("InvoiceOrdersTable")).findElementByXPath("//XCUIElementTypeCell[2]").click();
		//appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name='"
						+ wonumber + "']/XCUIElementTypeButton[@name=\"unselected\"]").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		Helpers.waitABit(1000);
	}
	
	public String getInvoiceNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name contains 'I-00'").getAttribute("value");
		//return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"I-00\")]").getAttribute("value");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
	}

	//public void clickSaveButton() {
	//	savebtn.click();
	//}

	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public void cancelInvoice() {
		clickCancelButton();
		acceptAlert();
	}

	public void clickInvoicePayButton() {
		appiumdriver.findElementByAccessibilityId("action pay").click();
	}
	
	public void changePaynentMethodToCashNormal() {
		appiumdriver.findElementByAccessibilityId("cash normal").click();
	}
	
	public void setCashCheckAmountValue(String amountvalue) {
		appiumdriver.findElementByAccessibilityId("Payment_Tab_Cash").click();
		//IOSElement par = (IOSElement) appiumdriver.findElementsByAccessibilityId("InvoicePaymentView").get(1);
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").clear();
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").sendKeys(amountvalue);
	}
	
	public void clickInvoicePayDialogButon() {
		appiumdriver.findElementByAccessibilityId("Pay").click();
	}
	
	
}
