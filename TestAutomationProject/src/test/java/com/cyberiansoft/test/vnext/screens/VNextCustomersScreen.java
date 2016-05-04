package com.cyberiansoft.test.vnext.screens;

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
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement customerslist;
	
	@FindBy(xpath="//a[@action='select-customer']")
	private WebElement firstcustomer;
	
	public VNextCustomersScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(firstcustomer));
	}
	
	public void selectCustomer(String customer) {
		while (customerslist.findElements(By.xpath(".//div[text()='" + customer + "']")).size() < 1) {
			switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
			appiumdriver.swipe(20, yscreenresolution-180, 20, 140, 1000);
			switchApplicationContext(AppContexts.WEB_CONTEXT);
			
		}	
		
		WebElement elem = customerslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + customer + "']"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);
		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.swipe(20, 140, 20, yscreenresolution-180, 1000);
		switchApplicationContext(AppContexts.WEB_CONTEXT);
		
		
		tap(customerslist.findElement(By.xpath(".//div[text()='" + customer + "']")));
		waitABit(1000);
		testReporter.log(LogStatus.INFO, "Select customer " + customer);
	}

}
