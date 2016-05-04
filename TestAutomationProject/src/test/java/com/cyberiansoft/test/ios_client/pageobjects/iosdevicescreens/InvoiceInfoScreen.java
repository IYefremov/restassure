package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class InvoiceInfoScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Draft\"]")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(xpath = "//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Final\"]")
    private IOSElement finalalertbtn;
	
	@iOSFindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATextField[1]")
    private IOSElement setpofld;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(name = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]")
    private IOSElement hidekeyboardbtn;

	public InvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickSaveEmptyPO() {
		clickSaveButton();
		Helpers.acceptAlert();
	}

	public void clickSaveAsDraft() throws InterruptedException {
		clickSaveButton();
		draftalertbtn.click();
		Thread.sleep(1000);
	}

	public void clickSaveAsFinal() {
		clickSaveButton();
		finalalertbtn.click();
	}

	public void setPO(String _po) throws InterruptedException {
		setPOWithoutHidingkeyboard(_po);
		hidekeyboardbtn.click();
	}
	
	public void setPOWithoutHidingkeyboard(String _po) throws InterruptedException {
		setpofld.setValue(_po);
	}

	public void assertWOIsSelected(String wo) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[contains(@name, \""
						+ wo + "\")]").isDisplayed());
	}
	
	public void clickFirstWO() {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView/UIATableCell[1]").click();
	}
	
	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[6]").getText(), summ);
	}
	
	public void addWorkOrder(String wonumber) throws InterruptedException {
		Thread.sleep(3000);
		appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]").click();
		//appiumdriver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]").click();
		appiumdriver.findElementByXPath("//UIATableCell[contains(@name, \""
						+ wonumber + "\")]/UIAButton[@name=\"unselected\"]").click();
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIANavigationBar[1]/UIAButton[@name=\"Done\"]").click();
	}
	
	public String getInvoiceNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[contains(@name, \"I-00\")]").getAttribute("name");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByXPath("//UIANavigationBar[@name=\"InvoiceFormView\"]/UIAStaticText[1]").getAttribute("name");
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

}
