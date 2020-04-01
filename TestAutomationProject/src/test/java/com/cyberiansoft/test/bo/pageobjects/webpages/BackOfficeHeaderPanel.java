package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class BackOfficeHeaderPanel extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_LoginStatus_LoginStatus1")
	private WebElement logoutlink;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Home']")
	private WebElement hometab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Company']")
	private WebElement companyTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Operations']")
	private WebElement operationsTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Monitor']")
	private WebElement monitorTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Super User']")
	private WebElement superUserTab;

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

	public BackOfficeHeaderPanel() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public WebElement getTab(String tab) {
	    return driver.findElement(By.xpath("//span[@class='rtsTxt' and text()='" + tab + "']"));
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

    public void clickSuperUserLink() {
        openSubmenu(superUserTab, "Super User");
    }

	public void clickOperationsLink() {
        openSubmenu(operationsTab, "Operations");
	}

    public void clickMonitorLink() {
        openSubmenu(monitorTab, "Monitor");
    }

    public void clickCompanyLink() {
        openSubmenu(companyTab, "Company");
    }

    public void clickMiscellaneousLink() {
        openSubmenu(miscellaneoustab, "Miscellanneous");
    }

    public void clickReportsLink() {
        openSubmenu(reportstab, "Reports");
    }

    public void clickTimesheetsLink() {
        openSubmenu(timesheetstab, "TimeSheets");
    }


    public void clickHomeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(hometab)).click();
	}
	public void refresh() {
	    Utils.acceptAlertIfPresent();
		driver.navigate().refresh();
	}

    private void openSubmenu(WebElement menu, String title) {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        Utils.clickElement(menu);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        try {
            WaitUtilsWebDriver.waitUntilTitleContains(title);
        } catch (Exception e) {
            Utils.clickElement(menu);
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
            WaitUtilsWebDriver.waitUntilTitleContains(title);
        }
        WaitUtilsWebDriver.waitABit(1000);
    }
}
