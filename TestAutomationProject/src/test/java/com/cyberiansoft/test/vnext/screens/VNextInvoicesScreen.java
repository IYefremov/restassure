package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInvoicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='page invoices-list hide-searchbar hide-toolbar page-on-center']")
	private WebElement invoicesscreen;
	
	@FindBy(xpath="//a[@action='back']/i")
	private WebElement backbtn;
	
	public VNextInvoicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicesscreen));
	}
	
	public String getInvoicePriceValue(String invoicenumber) {
		String invoiceprice = null;
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (invoicecell != null)
			invoiceprice = invoicecell.findElement(By.xpath(".//div[@class='item-after']")).getText();
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
		return invoiceprice;	
	}
	
	public WebElement getInvoiceCell(String invoicenumber) {
		WebElement invoicecell = null;
		List<WebElement> invoices = getInvoicesList().findElements(By.xpath(".//a[@class='item-link item-content']"));
		for (WebElement invcell : invoices)
			if (invcell.findElements(By.xpath(".//div[@class='item-title' and text()='" + invoicenumber + "']")).size() > 0) {
				invoicecell = invcell;
				break;
			}
				
		return invoicecell;
	}
	
	public boolean isInvoiceExists(String invoicenumber) {
		return invoicesscreen.findElements(By.xpath(".//div[@class='item-title' and text()='" + invoicenumber + "']")).size() > 0;
	}
	
	public WebElement getInvoicesList() {
		return invoicesscreen.findElement(By.xpath(".//div[@class='list-block list-block-search searchbar-found virtual-list']"));
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Click Invoices screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInvoiceMenuScreen clickOnInvoiceByInvoiceNumber(String invoicenumber) {
		tap(getInvoicesList().findElement(By.xpath(".//div[@class='item-title' and text()='" + invoicenumber + "']")));
		log(LogStatus.INFO, "Tap VNextInvoiceMenuScreen Invoice: " + invoicenumber);
		return new VNextInvoiceMenuScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickOnInvoiceToEmail(String invoicenumber) {
		VNextInvoiceMenuScreen invoicemenulist = clickOnInvoiceByInvoiceNumber(invoicenumber);
		return invoicemenulist.clickEmailInvoiceMenuItem();
	}

}
