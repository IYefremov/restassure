package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.PricesCalculations;

public class OrderSummaryScreen extends iOSHDBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	@iOSFindBy(name = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"OrderSummaryForm\"]/UIAButton[contains(@name, \"Total Sale\")]")
    private IOSElement totalsalefinal;
	
	private By approveandcreateinvoicechekbox = By.name("black unchecked");

	public OrderSummaryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableCell[@name=\""
						+ defaultServiceValue + "\"]").isDisplayed());
	}

	public void assertServiceIsSelected(String service) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableCell[@name=\""
						+ service + "\"]").isDisplayed());
	}

	public void checkApproveAndCreateInvoice() {
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
	}
	
	public boolean checkApproveAndCreateInvoiceExists() {
		if (elementExists(approveandcreateinvoicechekbox)) 
			return element(approveandcreateinvoicechekbox).isDisplayed();
		return true;
	}

	public InvoiceInfoScreen selectDefaultInvoiceType() {
		defaultinvoicetype.click();
		return new InvoiceInfoScreen(appiumdriver);
	}
	
	public InvoiceInfoScreen selectInvoiceType(String invoicetype) {
		appiumdriver.findElementByName(invoicetype).click();
		return new InvoiceInfoScreen(appiumdriver);
	}

	public void selectWorkOrderDetails(String workorderdetails) {

		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + workorderdetails + "\"]").click();
	}

	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[8]").getText(), summ);
	}

	public String getWorkOrderNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[1]/UIAStaticText[3]").getText();
	}
	
	public void setTotalSale(String totalsale) throws InterruptedException {
		//final String totalsaletxt = "Total Sale";
		IOSElement totalsalefld = (IOSElement)  appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[0].textFields()[0]"));
		totalsalefld.click();
		//appiumdriver.findElementByXPath("//UIANavigationBar[@name=\"OrderSummaryForm\"]/UIAButton[contains(@name, \"" + totalsalecaption + "\")]").click();
		//IOSElement totalsalefld = (IOSElement) appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[1]/UIATextField[1]");
		//IOSElement totalsalefld = (IOSElement) appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIATextField[1]");
		
		totalsalefld.setValue("");
		totalsalefld.setValue(totalsale);
		Helpers.keyboadrType("\n");
		//Helpers.acceptAlert();
		//totalsalecaption = (IOSElement)  appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[0].navigationBars()['OrderSummaryForm'].buttons()['" + totalsaletxt + "']"));
		Assert.assertEquals(totalsalefld.getAttribute("value"), PricesCalculations.getPriceRepresentation(totalsale));
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
}
