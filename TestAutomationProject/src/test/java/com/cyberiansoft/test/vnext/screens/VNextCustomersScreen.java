package com.cyberiansoft.test.vnext.screens;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

public class VNextCustomersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='page customers-list hide-searchbar page-on-center']")
	private WebElement customersscreen;
	
	@FindBy(xpath="//a[@class='link icon-only back']/i")
	private WebElement backbtn;
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement customerslist;
	
	@FindBy(xpath="//a[@action='select-customer']")
	private WebElement firstcustomer;
	
	@FindBy(xpath="//a[@action='add']/i")
	private WebElement addcustomerbtn;
	
	public VNextCustomersScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(firstcustomer));
	}
	
	public void selectCustomer(String customer) {
		if (customerslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + customer + "']")).size() > 0) {
			WebElement elem = customerslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + customer + "']"));	
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);			
			tap(customerslist.findElement(By.xpath(".//div[text()='" + customer + "']")));
			waitABit(1000);
		} else {
			
			while (customerslist.findElements(By.xpath(".//div[text()='" + customer + "']")).size() < 1) {
				switchApplicationContext(AppContexts.NATIVE_CONTEXT);
				int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
				appiumdriver.swipe(20, yscreenresolution-180, 20, 140, 1000);
				Assert.assertTrue(switchToWebViewContext());
				//switchApplicationContext(AppContexts.WEB_CONTEXT);			
				}	
			WebElement elem = customerslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + customer + "']"));	
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);
			
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
			appiumdriver.swipe(20, 140, 20, yscreenresolution-180, 1000);
			Assert.assertTrue(switchToWebViewContext());
			//switchApplicationContext(AppContexts.WEB_CONTEXT);
			
			
			tap(customerslist.findElement(By.xpath(".//div[text()='" + customer + "']")));
			waitABit(1000);
		}
		log(LogStatus.INFO, "Select customer " + customer);
	}
	
	public void selectCustomerByCustomerAddress(String customeraddress) {
		System.out.println("++++++++" + customerslist.findElements(By.xpath(".//div[@class='item-address' and contains(text(), '" + customeraddress + "')]")).size());
		while (customerslist.findElements(By.xpath(".//div[@class='item-address' and contains(text(), '" + customeraddress + "')]")).size() < 1) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
			appiumdriver.swipe(20, yscreenresolution-180, 20, 140, 1000);
			Assert.assertTrue(switchToWebViewContext());
			//switchApplicationContext(AppContexts.WEB_CONTEXT);
			
		}	
		
		WebElement elem = customerslist.findElement(By.xpath(".//div[@class='item-address' and contains(text(), '" + customeraddress + "')]"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.swipe(20, 140, 20, yscreenresolution-180, 1000);
		Assert.assertTrue(switchToWebViewContext());
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
		
		
		tap(customerslist.findElement(By.xpath(".//div[@class='item-address' and contains(text(), '" + customeraddress + "')]")));
		waitABit(1000);
		log(LogStatus.INFO, "Select customer by Address " + customeraddress);
	}
	
	public VNextNewCustomerScreen clickAddCustomerButton() {
		tap(customersscreen.findElement(By.xpath(".//a[@action='add']/i")));
		log(LogStatus.INFO, "Click Add customer button");
		return new VNextNewCustomerScreen(appiumdriver);	
	}
	
	public boolean isCustomerExists(String customer) {
		return customerslist.findElements(By.xpath(".//div[text()='" + customer + "']")).size() > 0;
		
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Click customer Back button");
		return new VNextHomeScreen(appiumdriver);
	}
}
