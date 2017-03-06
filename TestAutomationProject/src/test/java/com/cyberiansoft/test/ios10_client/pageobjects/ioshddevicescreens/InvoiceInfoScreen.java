package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class InvoiceInfoScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "Draft")
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
    private IOSElement cancelbtn;

	public InvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickSaveEmptyPO() {
		clickSaveButton();
		Helpers.acceptAlert();
	}

	public void clickSaveAsDraft()  {
		clickSaveButton();
		draftalertbtn.click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(2000);
	}

	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		Helpers.waitABit(500);
	}
	
	public String getInvoicePOValue() {
		return setpofld.getAttribute("value");
	}

	public void setPO(String _po) {
		setPOWithoutHidingkeyboard(_po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}
	
	public void setPOWithoutHidingkeyboard(String _po) {
		setpofld.setValue(_po);
	}

	public void assertWOIsSelected(String wonumber) {
		Assert.assertTrue(appiumdriver.findElementsByAccessibilityId(wonumber).size() > 0);
	}
	
	public void clickFirstWO() {
		invoicewostable.findElementByXPath("//XCUIElementTypeCell[1]").click();
		Helpers.waitABit(1000);
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}
	
	public void addWorkOrder(String wonumber) {
		Helpers.waitABit(2000);
		invoicewostable.findElementByXPath("//XCUIElementTypeCell[2]").click();
		//appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]").click();
		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name='"
						+ wonumber + "']/XCUIElementTypeButton[@name=\"unselected\"]").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		Helpers.waitABit(1000);
	}
	
	public String getInvoiceNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[1]/XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"I-00\")]").getAttribute("value");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
	}

	//public void clickSaveButton() {
	//	savebtn.click();
	//}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void cancelInvoice() {
		clickCancelButton();
		acceptAlert();
	}

	public void clickInvoicePayButton() {
		invoicepaybtn.click();
	}
	
	public void changePaynentMethodToCashNormal() {
		cashnormalbtn.click();
	}
	
	public void setCashCheckAmountValue(String amountvalue) {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='Cash / Check']/..");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).clear();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(amountvalue);
	}
	
	public void clickInvoicePayDialogButon() {
		appiumdriver.findElementByAccessibilityId("Pay").click();
	}
	
	
}
