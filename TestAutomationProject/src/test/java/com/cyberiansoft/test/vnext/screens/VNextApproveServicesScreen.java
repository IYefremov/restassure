package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextApproveServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='approve-services']")
	private WebElement approveservicesscreen;
	
	@FindBy(xpath="//*[@action='select-all' and @value='1']")
	private WebElement approveallbtn;
	
	@FindBy(xpath="//*[@action='select-all' and @value='2']")
	private WebElement declineallbtn;
	
	@FindBy(xpath="//*[@action='select-all' and @value='3']")
	private WebElement skipallbtn;
	
	@FindBy(xpath="//*[@data-autotests-id='sapprove-services-list']")
	private WebElement serviceslist;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	public VNextApproveServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveallbtn));
		BaseUtils.waitABit(1000);
	}
	
	public void clickApproveAllButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(approveallbtn));
		tap(approveallbtn);
		log(LogStatus.INFO, "Tap on Approve All Button");
	}
	
	public void clickDeclineAllButton() {
		tap(declineallbtn);
		log(LogStatus.INFO, "Tap on Decline All Button");
	}
	
	public void clickSkipAllButton() {
		tap(skipallbtn);
		log(LogStatus.INFO, "Tap on Skip All Button");
	}
	
	public boolean isApproveAllButtonDisplayed() {
		return approveallbtn.isDisplayed();
	}
	
	public boolean isDeclineAllButtonDisplayed() {
		return declineallbtn.isDisplayed();
	}

	public boolean isSkipAllButtonDisplayed() {
		return skipallbtn.isDisplayed();
	}
	
	public boolean isServicePresentInTheList(String serviceName) {
		return serviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")).size() > 0;
	}
	
	public void clickSaveButton() {
		tap(savebtn);
		log(LogStatus.INFO, "Tap on Save Button");
	}
	
	public void clickApproveButtonForService(String serviceName) {
		WebElement serviceRow = getServiceRowByServiceName(serviceName);
		if (serviceRow != null) {
			tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'approve')]")));
		} else {
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		}
	}
	
	public void clickDeclineButtonForService(String serviceName) {
		WebElement serviceRow = getServiceRowByServiceName(serviceName);
		if (serviceRow != null) {
			tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'decline')]")));
		} else {
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		}
	}
	
	public void clickSkipButtonForService(String serviceName) {
		WebElement serviceRow = getServiceRowByServiceName(serviceName);
		if (serviceRow != null) {
			tap(serviceRow.findElement(By.xpath(".//label[contains(@for, 'skip')]")));
		} else {
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		}
	}
	
	private WebElement getServiceRowByServiceName(String serviceName) {
		WebElement serviceRow = null;
		List<WebElement> rows = serviceslist.findElements(By.xpath(".//div[contains(@class, 'entity-item accordion-item')]"));
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(serviceName)) {
				serviceRow = row;
				break;
			}
		}
		return serviceRow;
	}
}
