package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

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

public class RegularOrderSummaryScreen extends iOSRegularBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	@iOSFindBy(name = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name=\"Total Sale\"]/UIATextField[1]")
    private IOSElement totalsalefld;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	private By approveandcreateinvoicechekbox = By.name("checkbox unchecked");

	public RegularOrderSummaryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void checkApproveAndCreateInvoice() {
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
	}
	
	public boolean checkApproveAndCreateInvoiceExists() {
		return elementExists(approveandcreateinvoicechekbox);
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByXPath("//UIASecureTextField[@value=\"Enter password here\"]")).setValue(password);
		Helpers.acceptAlert();
	}

	public RegularInvoiceInfoScreen selectDefaultInvoiceType() {
		defaultinvoicetype.click();
		return new RegularInvoiceInfoScreen(appiumdriver);
	}
	
	public RegularInvoiceInfoScreen selectInvoiceType(String invoicetype) {
		appiumdriver.findElementByName(invoicetype).click();
		return new RegularInvoiceInfoScreen(appiumdriver);
	}

	public void selectWorkOrderDetails(String workorderdetails) {

		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + workorderdetails + "\"]").click();
	}

	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElement(MobileBy.xpath("//UIAStaticText[@name='TotalAmount']")).getAttribute("value"), summ);
	}
	
	public void setTotalSale(String totalsale) throws InterruptedException {
		setTotalSaleWithoutHidingkeyboard(totalsale);
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
		Assert.assertEquals(totalsalefld.getAttribute("value"), PricesCalculations.getPriceRepresentation(totalsale));
	}
	
	public void setTotalSaleWithoutHidingkeyboard(String totalsale) throws InterruptedException {
		totalsalefld.click();
		totalsalefld.setValue(totalsale);
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
	
	public void clickSaveButton() {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		savebtn.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
