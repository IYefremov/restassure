package com.cyberiansoft.test.vnext.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class VNextCustomersScreen extends VNextBaseScreen {
	@FindBy(xpath="//div[contains(@class, 'page customers customers-list')]")
	private WebElement customersscreen;
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement customerslist;
	
	@FindBy(xpath="//a[@action='select-customer']")
	private WebElement firstcustomer;
	
	@FindBy(xpath="//a[@action='add']/i")
	private WebElement addcustomerbtn;
	
	public VNextCustomersScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		//PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, 15, TimeUnit.SECONDS), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(customersscreen));

		//if (appiumdriver.findElements(By.xpath("//div[@class='help-button' and text()='OK, got it']")).size() > 0)
			//if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
		if (checkHelpPopupPresence())		
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
	}
	
	public void selectCustomer(String customer) {		
		if (customerslist.findElements(By.xpath(".//a[@class='list-item']/p[@class='list-item-text list-item-name' and text()='" + customer + "']")).size() > 0) {
			WebElement elem = customerslist.findElement(By.xpath(".//a[@class='list-item']/p[@class='list-item-text list-item-name' and text()='" + customer + "']"));	
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//a[@class='list-item']/p[@class='list-item-text list-item-name' and text()='" + customer + "']")));
			
		} else {
			List<WebElement> ctmrs = customerslist.findElements(By.xpath(".//a[@class='list-item']/p[@class='list-item-text list-item-name']"));
			WebElement elem = ctmrs.get(ctmrs.size()-1);
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//a[@class='list-item']/p[@class='list-item-text list-item-name' and text()='" + customer + "']")));
			//waitABit(1000);

		}
		log(LogStatus.INFO, "Select customer " + customer);
	}
	
	/*public void selectCustomerByCustomerAddress(String customeraddress) {
		WebElement elem = customerslist.findElement(By.xpath(".//div[@class='list-item-text list-item-address' and contains(text(), '" + customeraddress + "')]"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);			
		tap(customerslist.findElement(By.xpath(".//div[@class='list-item-text list-item-address' and contains(text(), '" + customeraddress + "')]")));
		waitABit(1000);
		log(LogStatus.INFO, "Select customer by Address " + customeraddress);
	}*/
	
	public void selectCustomerByCompanyName(String customercompany) {
		WebElement elem = customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);			
		tap(customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]")));
		waitABit(1000);
		log(LogStatus.INFO, "Select customer by Address " + customercompany);
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
		clickScreenBackButton();
		log(LogStatus.INFO, "Click Customers screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
}
