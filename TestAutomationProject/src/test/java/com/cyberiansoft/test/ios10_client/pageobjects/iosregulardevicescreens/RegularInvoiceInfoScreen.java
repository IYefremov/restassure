package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.HideKeyboardStrategy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularInvoiceInfoScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;

	@iOSFindBy(accessibility  = "action pay")
    private IOSElement invoicepaybtn;
	
	@iOSFindBy(accessibility  = "cash normal")
    private IOSElement cashnormalbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAButton[@name=\"Return\"]")
    private IOSElement hidekeyboardbtn;

	public RegularInvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickSaveEmptyPO() {
		savebtn.click();
		Helpers.acceptAlert();
	}

	public void clickSaveAsDraft() {
		clickSaveButton();
		draftalertbtn.click();
		savebtn.click();
		Helpers.waitABit(500);
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading team order")));
		}
	}

	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
		savebtn.click();
		Helpers.waitABit(500);
	}

	public void setPO(String _po) throws InterruptedException {
		setPOWithoutHidingkeyboard(_po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		//((IOSDriver) appiumdriver).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Return");
		//hidekeyboardbtn.click();
	}
	
	public void setPOWithoutHidingkeyboard(String _po)  {
		Helpers.waitABit(500);
		WebElement par = getTableParentCell("PO#");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_po);
	}
	
	public String  getInvoicePOValue()  {
		Helpers.waitABit(500);
		WebElement par = getTableParentCell("PO#");
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public boolean isWOSelected(String wo) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
						+ wo + "']").size() > 0;
	}
	
	public void clickWO(String wonumber) {
		WebElement par = getTableParentCell(wonumber);
		par.findElement(By.xpath("//XCUIElementTypeStaticText[1]")).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[1]/XCUIElementTypeCell[1]").click();
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}
	
	public void clickOnWO(String wonum) {
		appiumdriver.findElementByAccessibilityId(wonum).click();
	}
	
	public void addWorkOrder(String wonumber)  {
		Helpers.waitABit(1000);
		appiumdriver.findElementByAccessibilityId("Insert").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[3]").click();
		Helpers.waitABit(1000);
		WebElement par = getTableParentCell(wonumber);
		//par.findElement(By.xpath(".//XCUIElementTypeTextField[1]"));
		par.findElement(By.xpath("//XCUIElementTypeButton[@name=\"unselected\"]")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getInvoiceNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"I-\")]").getAttribute("value");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void cancelInvoice() {
		clickCancelButton();
		acceptAlert();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
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
