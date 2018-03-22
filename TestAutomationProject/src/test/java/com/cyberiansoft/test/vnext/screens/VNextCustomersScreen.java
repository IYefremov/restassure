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

import com.cyberiansoft.test.vnext.utils.VNextCustomer;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class VNextCustomersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='customers-list']")
	private WebElement customersscreen;
	
	@FindBy(xpath="//div[contains(@class, 'list-block-search')]")
	private WebElement customerslist;
	
	@FindBy(xpath="//a[@action='select-customer']")
	private WebElement firstcustomer;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addcustomerbtn;
	
	@FindBy(xpath="//*[@action='search']")
	private WebElement searchbtn;
	
	@FindBy(xpath="//*[@data-autotests-id='search-input']")
	private WebElement searchfld;
	
	@FindBy(xpath="//*[@data-autotests-id='search-cancel']")
	private WebElement cancelsearchbtn;
	
	@FindBy(xpath="//*[@action='select-retail']")
	private WebElement retailcustomertab;
	
	@FindBy(xpath="//*[@action='select-wholesale']")
	private WebElement wholesalecustomertab;
	
	public VNextCustomersScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, 15, TimeUnit.SECONDS), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(customersscreen));
		waitABit(1500);
		if (checkHelpPopupPresence())		
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
	}
	
	public void selectCustomer(VNextCustomer customer) {
		if (customerslist.findElements(By.xpath(".//*[@action='select-customer']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")).size() > 0) {
			WebElement elem = customerslist.findElement(By.xpath(".//*[@action='select-customer']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']"));	
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//*[@action='select-customer']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
			
		} else {
			List<WebElement> ctmrs = customerslist.findElements(By.xpath(".//*[@action='select-customer']/p[@class='list-item-text list-item-name']"));
			WebElement elem = ctmrs.get(ctmrs.size()-1);
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//*[@action='select-customer']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
			//waitABit(1000);

		}
		log(LogStatus.INFO, "Select customer " + customer.getFullName());
	}
	
	public void selectCustomerByCompanyName(String customercompany) {
		WebElement elem = customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);			
		tap(customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]")));
		waitABit(1000);
		log(LogStatus.INFO, "Select customer by Address " + customercompany);
	}
	
	public VNextNewCustomerScreen clickAddCustomerButton() {
		tap(customersscreen.findElement(By.xpath(".//a[@class='floating-button color-red']")));
		/*List<WebElement> addbtns = customersscreen.findElements(By.xpath(".//a[@action='add' and @data-text='Create new customer']"));
		for (WebElement addbtn : addbtns) {
			if (addbtn.isDisplayed())
				tap(addbtn);
		}*/
		tap(customersscreen.findElement(By.xpath(".//a[@action='add' and @class='customers-button']")));
		log(LogStatus.INFO, "Click Add customer button");
		return new VNextNewCustomerScreen(appiumdriver);	
	}
	
	public boolean isAddCustomerButtonDisplayed() {
		return customersscreen.findElement(By.xpath(".//a[@action='add']")).isDisplayed();
		
	}
	
	public boolean isCustomerExists(VNextCustomer customer) {
		return customerslist.findElements(By.xpath(".//p[text()='" + customer.getFullName() + "']")).size() > 0;		
	}
	
	public void clickBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Click Customers screen Back button");
	}
	
	public void switchToRetailMode() {
		tap(retailcustomertab);
		log(LogStatus.INFO, "Switch To Retail Mode");
	}
	
	public void switchToWholesaleMode() {
		tap(wholesalecustomertab);
		log(LogStatus.INFO, "Switch To Wholesale Mode");
	}
	
	public void searchCustomerByName(String customername) {
 		typeSearchParameters(customername);
		
	}
	
	public void clickSearchButton() {
		tap(searchbtn);
	}
	
	public void clickCancelSearchButton() {
		tap(cancelsearchbtn);
		log(LogStatus.INFO, "Tap Cancel Search Button");
	}
	
	public void typeSearchParameters(String searchtxt) {
		tap(appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchfld));
		appiumdriver.getKeyboard().sendKeys(searchtxt);
		//searchfld.clear();
		//searchfld.sendKeys(searchtxt);
		appiumdriver.hideKeyboard();
	}
	
	public boolean isNothingFoundCaptionDisplayed() {
		return customersscreen.findElement(By.xpath(".//b[text()='Nothing found']")).isDisplayed();
	}
}
