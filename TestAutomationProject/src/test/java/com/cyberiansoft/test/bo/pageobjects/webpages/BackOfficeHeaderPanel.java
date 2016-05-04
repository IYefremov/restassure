package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class BackOfficeHeaderPanel extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_LoginStatus_LoginStatus1")
	private WebElement logoutlink;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Home']")
	private WebElement hometab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Company']")
	private WebElement companytab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Operations']")
	private WebElement operationstab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Monitor']")
	private WebElement monitortab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Super User']")
	private WebElement superusertab;
	
	public BackOfficeHeaderPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickLogout() {
		if (driver.getWindowHandles().size() > 1) {
			driver.close();
			for (String activeHandle : driver.getWindowHandles())
				driver.switchTo().window(activeHandle);	
		}
		driver.switchTo().defaultContent();
		waitABit(1000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(logoutlink)).click();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(driver,
				BackOfficeLoginWebPage.class);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(loginpage.getLoginButton()));
	}
	
	public OperationsWebPage clickOperationsLink() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(operationstab)).click();
		return PageFactory.initElements(
				driver, OperationsWebPage.class);
	}
	
	public HomeWebPage clickHomeLink() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(hometab)).click();
		return PageFactory.initElements(
				driver, HomeWebPage.class);
	}
	
	public CompanyWebPage clickCompanyLink() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(companytab)).click();
		return PageFactory.initElements(
				driver, CompanyWebPage.class);
	}
	
	public MonitorWebPage clickMonitorLink() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(monitortab)).click();
		return PageFactory.initElements(
				driver, MonitorWebPage.class);
	}
	
	public SuperUserWebPage clickSuperUserLink() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(superusertab)).click();
		return PageFactory.initElements(
				driver, SuperUserWebPage.class);
	}

}
