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
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularInvoiceInfoScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;

	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAButton[@name=\"Return\"]")
    private IOSElement hidekeyboardbtn;

	public RegularInvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickSaveEmptyPO() {
		savebtn.click();
		Helpers.acceptAlert();
	}

	public void clickSaveAsDraft() throws InterruptedException {
		savebtn.click();
		draftalertbtn.click();
		Thread.sleep(1000);
	}

	public void clickSaveAsFinal() {
		savebtn.click();
		finalalertbtn.click();
	}

	public void setPO(String _po) throws InterruptedException {
		setPOWithoutHidingkeyboard(_po);
		//IOSDriver drv 
		((IOSDriver) appiumdriver).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
		//hidekeyboardbtn.click();
	}
	
	public void setPOWithoutHidingkeyboard(String _po)  {
		Helpers.waitABit(500);
		WebElement par = getTableParentCell("PO#");
		par.findElement(By.xpath(".//XCUIElementTypeTextField[1]")).sendKeys(_po);
		//setpofld.click();
		//Helpers.keyboadrType(_po);
		//setpofld.setValue(_po);
	}

	public void assertWOIsSelected(String wo) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[contains(@name, \""
						+ wo + "\")]").isDisplayed());
	}
	
	public void clickWO(String wonumber) {
		WebElement par = getTableParentCell(wonumber);
		par.findElement(By.xpath(".//XCUIElementTypeStaticText[1]")).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[1]/XCUIElementTypeCell[1]").click();
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}
	
	public void clickOnWO(String wonum) {
		appiumdriver.findElementByAccessibilityId(wonum).click();
	}
	
	public void addWorkOrder(String wonumber)  {
		Helpers.waitABit(2000);
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[3]").click();
		Helpers.waitABit(1000);
		WebElement par = getTableParentCell(wonumber);
		//par.findElement(By.xpath(".//XCUIElementTypeTextField[1]"));
		par.findElement(By.xpath(".//XCUIElementTypeButton[@name=\"unselected\"]")).click();
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


}