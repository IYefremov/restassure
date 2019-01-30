package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextChangeInvoicePONumberDialog extends VNextBaseScreen {
	
	@FindBy(id="InvoicesChangePO")
	private WebElement changeponumberfld;
	
	@FindBy(xpath="//span[contains(@class, 'modal-button') and text()='Save']")
	private WebElement savebtn;
	
	public VNextChangeInvoicePONumberDialog(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("InvoicesChangePO")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(changeponumberfld));
	}
	
	public VNextInvoicesScreen changeInvoicePONumber(String poNumber) {
		setInvoicePONumber(poNumber);
		clickSaveButton();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		try {
			wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.
					xpath("//div[@class='notifier' and @data-type='success']"))));
		} catch (NoSuchElementException e) {
			//
		}
		return new VNextInvoicesScreen(appiumdriver);
	}

	
	public void setInvoicePONumber(String poNumber) {
		changeponumberfld.clear();
		changeponumberfld.sendKeys(poNumber);
		BaseUtils.waitABit(1000);
		//appiumdriver.hideKeyboard();
	}

	public String getInvoicePreviousPONumber() {
		return changeponumberfld.getAttribute("value");
	}
	
	public void clickSaveButton() {
		tap(savebtn);
	}
	
	public void clickDontSaveButton() {
		tap(appiumdriver.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
	}
	
	public boolean isPONUmberShouldntBeEmptyErrorAppears() {
		return appiumdriver.findElement(By.xpath("//div[@class='modal-validation']")).isDisplayed();
	}
}
