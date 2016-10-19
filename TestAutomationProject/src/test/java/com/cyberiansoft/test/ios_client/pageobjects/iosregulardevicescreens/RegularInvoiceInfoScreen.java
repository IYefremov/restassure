package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularInvoiceInfoScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Draft\"]")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Final\"]")
    private IOSElement finalalertbtn;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIATableView[1]/UIATableCell[@name=\"PO#\"]/UIATextField[1]")
    private IOSElement setpofld;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(name = "Cancel")
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
		hidekeyboardbtn.click();
	}
	
	public void setPOWithoutHidingkeyboard(String _po) throws InterruptedException {
		setpofld.click();
		Helpers.keyboadrType(_po);
		//setpofld.setValue(_po);
	}

	public void assertWOIsSelected(String wo) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[contains(@name, \""
						+ wo + "\")]").isDisplayed());
	}
	
	public void clickFirstWO() {
		appiumdriver.findElementByXPath("//UIAScrollView[2]/UIATableView[1]/UIATableCell[2]").click();
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIAStaticText[@name='TotalAmount']").getAttribute("value"), summ);
	}
	
	public void clickOnWO(String wonum) {
		appiumdriver.findElementByAccessibilityId(wonum).click();
	}
	
	public void addWorkOrder(String wonumber) throws InterruptedException {
		Thread.sleep(3000);
		appiumdriver.findElementByXPath("//UIAScrollView[2]/UIATableView[1]/UIATableCell[3]").click();
		//appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]").click();
		appiumdriver.findElementByXPath("//UIATableCell[contains(@name, \""
						+ wonumber + "\")]/UIAButton[@name=\"unselected\"]").click();
		appiumdriver.findElementByXPath("//UIANavigationBar[1]/UIAButton[@name=\"Done\"]").click();
	}
	
	public String getInvoiceNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[contains(@name, \"I-\")]").getAttribute("name");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByXPath("//UIANavigationBar[1]/UIAStaticText[1]").getAttribute("name");
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public void cancelInvoice() {
		clickCancelButton();
		acceptAlert();
	}

}
