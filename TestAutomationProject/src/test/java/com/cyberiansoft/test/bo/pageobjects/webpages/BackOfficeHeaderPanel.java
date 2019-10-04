package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
			System.err.println("Closing window driver exception: " + e);
		}
		driver.switchTo().defaultContent();
		waitABit(1000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-500)", "");
		Utils.clickElement(logoutlink);
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(driver);
		try {
            WaitUtilsWebDriver.waitForVisibility(loginpage.getLoginButton(), 10);
		} catch (TimeoutException e) {
			DriverBuilder.getInstance().quitDriver();
		}
		waitABit(4000);
	}

	public void clickOperationsLink() {
		waitABit(5000);
        Utils.clickElement(operationstab);
	}

	public void clickHomeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(hometab)).click();
	}

	public void clickCompanyLink() {
		wait.until(ExpectedConditions.elementToBeClickable(companytab)).click();
	}

	public void clickMonitorLink() {
		wait.until(ExpectedConditions.elementToBeClickable(monitortab)).click();
	}

	public void clickSuperUserLink() {
		wait.until(ExpectedConditions.elementToBeClickable(superusertab)).click();
	}

	public void clickMiscellaneousLink() {
		new Actions(driver).moveToElement(miscellaneoustab).click().build().perform();
	}

	public void clickReportsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(reportstab)).click();
	}

	public void refresh() {
	    Utils.acceptAlertIfPresent();
		driver.navigate().refresh();
	}

	public void clickTimesheetsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(timesheetstab)).click();
	}

}
