package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Miscellaneous']")
	private WebElement miscellaneoustab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Reports']")
	private WebElement reportstab;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Timesheets']")
	private WebElement timesheetstab;
	
	public BackOfficeHeaderPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickLogout() {
        try {
            while (driver.getWindowHandles().size() > 1) {
                driver.close();
                for (String activeHandle : driver.getWindowHandles())
				driver.switchTo().window(activeHandle);
            }
        } catch (Exception e) {
            System.err.println("Closing driver exception: " + e);
        }
		driver.switchTo().defaultContent();
		waitABit(1000);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-500)", "");
		try {
		    wait.until(ExpectedConditions.elementToBeClickable(logoutlink)).click();
		} catch (Exception ignored) {}
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(driver, BackOfficeLoginWebPage.class);
        try {
            wait.until(ExpectedConditions.visibilityOf(loginpage.getLoginButton()));
        } catch (TimeoutException e) {
            driver.close();
            System.err.println("Login button has not been displayed!\n" + e);
        }
		waitABit(4000);
	}
	
	public OperationsWebPage clickOperationsLink() {
		waitABit(5000);
		wait.until(ExpectedConditions.elementToBeClickable(operationstab)).click();
		return PageFactory.initElements(
				driver, OperationsWebPage.class);
	}
	
	public HomeWebPage clickHomeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(hometab)).click();
		return PageFactory.initElements(
				driver, HomeWebPage.class);
	}
	
	public CompanyWebPage clickCompanyLink() {
		wait.until(ExpectedConditions.elementToBeClickable(companytab)).click();
		return PageFactory.initElements(
				driver, CompanyWebPage.class);
	}
	
	public MonitorWebPage clickMonitorLink() {
		wait.until(ExpectedConditions.elementToBeClickable(monitortab)).click();
		return PageFactory.initElements(
				driver, MonitorWebPage.class);
	}
	
	public SuperUserWebPage clickSuperUserLink() {
		wait.until(ExpectedConditions.elementToBeClickable(superusertab)).click();
		return PageFactory.initElements(
				driver, SuperUserWebPage.class);
	}

	public MiscellaneousWebPage clickMiscellaneousLink() {
		wait.until(ExpectedConditions.elementToBeClickable(miscellaneoustab)).click();
		return PageFactory.initElements(
				driver, MiscellaneousWebPage.class);
	}

	public ReportsWebPage clickReportsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(reportstab)).click();
		return PageFactory.initElements(
				driver, ReportsWebPage.class);
		
	}

	public void refresh() {
		try{
			driver.switchTo().alert().accept();
		} catch (Exception e) {}
		driver.navigate().refresh();
	}

	public TimesheetsWebPage clickTimesheetsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(timesheetstab)).click();
		return PageFactory.initElements(
				driver, TimesheetsWebPage.class);
		
	}

}
