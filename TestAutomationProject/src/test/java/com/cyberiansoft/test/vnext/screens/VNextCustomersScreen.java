package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextCustomersScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='customers-list']")
	private WebElement customersscreen;

	@FindBy(xpath="//span[@class='client-mode']")
	private WebElement clientmode;
	
	@FindBy(xpath="//*[@data-autotests-id='customers-list']")
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
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(customersscreen));
		BaseUtils.waitABit(1500);
		if (checkHelpPopupPresence())		
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		if (elementExists("//*[@data-autotests-id='search-cancel']"))
			if (cancelsearchbtn.isDisplayed())
				tap(cancelsearchbtn);
	}
	
	public void selectCustomer(AppCustomer customer) {
		if (elementExists("//*[@data-automation-id='search-icon']"))
			searchCustomerByName(customer.getFullName());
		if (customerslist.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")).size() > 0) {
			WebElement elem = customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']"));
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
			
		} else {
			List<WebElement> ctmrs = customerslist.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name']"));
			WebElement elem = ctmrs.get(ctmrs.size()-1);
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);	
			//waitABit(1000);
			tap(customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
			//waitABit(1000);

		}
	}
	
	public void selectCustomerByCompanyName(String customercompany) {
		WebElement elem = customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);			
		tap(customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]")));
		BaseUtils.waitABit(1000);
	}
	
	public VNextNewCustomerScreen clickAddCustomerButton() {
		tap(customersscreen.findElement(By.xpath(".//a[@class='floating-button color-red']")));
		tap(customersscreen.findElement(By.xpath(".//a[@action='add_customer' and @class='customers-button']")));
		//tap(customersscreen.findElement(By.xpath(".//a[@action='add' and @class='customers-button']")));
		return new VNextNewCustomerScreen(appiumdriver);
	}
	
	public boolean isAddCustomerButtonDisplayed() {
		return customersscreen.findElements(By.xpath(".//a[@action='add']")).size() > 0;
	}

	public boolean isAddCustomerButtonExists() {
		return elementExists("//a[@action='add']");
	}

	public boolean isCustomerExists(AppCustomer customer) {
		searchCustomerByName(customer.getFullName());
		return customerslist.findElements(By.xpath(".//p[text()='" + customer.getFullName() + "']")).size() > 0;		
	}
	
	public void clickBackButton() {
		if (cancelsearchbtn.isDisplayed())
			tap(cancelsearchbtn);
		clickScreenBackButton();
	}
	
	public void switchToRetailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(retailcustomertab));
		tap(retailcustomertab);
	}
	
	public void switchToWholesaleMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(wholesalecustomertab));
		tap(wholesalecustomertab);
	}
	
	public void searchCustomerByName(String customername) {
 		typeSearchParameters(customername);
		
	}
	
	public void clickSearchButton() {
		tap(searchbtn);
	}
	
	public void clickCancelSearchButton() {
		tap(cancelsearchbtn);
	}
	
	public void typeSearchParameters(String searchtxt) {
		if (appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")).isDisplayed())
			tap(appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchfld));
		if (searchfld.getAttribute("value").length() > 0) {
			WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@data-automation-id='search-clear']"), appiumdriver);
			tap(appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-clear']")));
		}
		WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@data-autotests-id='search-input']"), appiumdriver);
		tap(searchfld);
		appiumdriver.getKeyboard().sendKeys(searchtxt);
		//searchfld.clear();
		//searchfld.sendKeys(searchtxt);
		appiumdriver.hideKeyboard();
	}
	
	public boolean isNothingFoundCaptionDisplayed() {
		return customersscreen.findElement(By.xpath(".//b[text()='Nothing found']")).isDisplayed();
	}

	public VNextNewCustomerScreen openCustomerForEdit(AppCustomer customer) {
		selectCustomer(customer);
		VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(appiumdriver);
		return customersMenuScreen.clickEditCustomerMenuItem();
	}

	public VNextCustomersScreen setCustomerAsDefault(AppCustomer customer) {
		selectCustomer(customer);
		VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(appiumdriver);
		return customersMenuScreen.clickSetCustomerAsDefault();
	}

	public String getDefaultCustomerValue() {
		return clientmode.getText();
	}
}
