package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

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

public class VNextWorkOrdersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@action='add']/i")
	private WebElement addwobtn;
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement workorderslist;
	
	@FindBy(xpath="//a[@action='back']/i")
	private WebElement backbtn;
	
	@FindBy(xpath="//a[@action='create-invoice']/i")
	private WebElement createinvoicemenu;
	
	public VNextWorkOrdersScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(workorderslist));
	}
	
	public VNextCustomersScreen clickAddWorkOrderButton() {
		waitABit(2000);		
		tap(addwobtn);
		log(LogStatus.INFO, "Tap Add work order button");
		return new VNextCustomersScreen(appiumdriver);
	}
	
	public String getFirstWorkOrderNumber() {
		return workorderslist.findElement(By.xpath(".//div[@class='item-title']")).getText();
	}
	
	public VNextInspectionsMenuScreen clickOnWorkOrderByInspNumber(String workordernumber) {
		tap(workorderslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + workordernumber + "']")));
		log(LogStatus.INFO, "Tap on Work order: " + workordernumber);
		return new VNextInspectionsMenuScreen(appiumdriver);
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Tap Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public String getWorkOrderPriceValue(String wonumber) {
		String woprice = null;
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null)
			woprice = workordercell.findElement(By.xpath(".//div[@class='item-after']")).getText();
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		return woprice;		
	}
	
	public WebElement getWorkOrderCell(String wonumber) {
		WebElement wocell = null;
		List<WebElement> workorders = workorderslist.findElements(By.xpath(".//a[@class='item-link item-content']"));
		for (WebElement workordercell : workorders)
			if (workordercell.findElements(By.xpath(".//div[@class='item-title' and text()='" + wonumber + "']")).size() > 0) {
				wocell = workordercell;
				break;
			}
		return wocell;
	}
	
	public VNextInvoiceInfoScreen clickCreateInvoiceFromWorkOrder(String wonumber) {
		WebElement workordercell = getWorkOrderCell(wonumber);
		if (workordercell != null) {
			tap(workordercell.findElement(By.xpath(".//div[@class='item-title' and text()='" + wonumber + "']")));
			log(LogStatus.INFO, "Click on Work Order: " + wonumber);
		}
		else
			Assert.assertTrue(false, "Can't find work order: " + wonumber);
		return clickCreateInvoiceMenuItem();
		
	}
	
	public VNextInvoiceInfoScreen clickCreateInvoiceMenuItem() {
		tap(createinvoicemenu);
		log(LogStatus.INFO, "Click Create Invoice menu item");
		return new VNextInvoiceInfoScreen(appiumdriver);
	}

}
