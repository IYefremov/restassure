package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.ibs.pageobjects.webpages.BasePage;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class ActiveDevicesWebPage extends BasePage {

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl03_filterer_tbName")
	private WebElement filtercriterianame;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_devices_ctl00")
	private WebTable devicestable;

	@FindBy(xpath = "//input[@value='Find']")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@value='Replace']")
	private WebElement replacemarker;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_devices_ctl00_ctl04_lRegCode")
	private WebElement regcodefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_lbAddNewDevice")
	private WebElement addNewDevicesLink;

	@FindBy(xpath = "//input[@id='ctl00_ctl00_Content_Main_ctl00_ctl02_BtnOk']")
	private WebElement newDeviceDialogOkButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_ModalDialog1")
	private WebElement devicesModalDialog;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl00_ctl01_Card_cbFreeLicenses_Input")
	private WebElement licenseField;

	@FindBy(xpath = "//div[@id='ctl00_ctl00_Content_Main_ctl00_ctl01_Card_cbFreeLicenses_DropDown']//li")
	private List<WebElement> licenseDropDownOptions;

	@FindBy(xpath = "//tr[contains(@id, 'ctl00_ctl00_Content_Main_gvFreeLicences')]")
	private WebElement device;

	@FindBy(xpath = "//tr[contains(@id, 'ctl00_ctl00_Content_Main_gvFreeLicences')]//input[@title='Delete']")
	private WebElement deviceDeleteButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl00_ctl01_Card_tbMinutes")
	private WebElement expiresInField;

	public ActiveDevicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}

	public void setSearchCriteriaByName(String name) {
		wait.until(ExpectedConditions.visibilityOf(searchbtn)).click();
		filtercriterianame.sendKeys(name);
		clickAndWait(findbtn);
	}

	public String getFirstRegCodeInTable() {
		wait.until(ExpectedConditions.visibilityOf(devicestable.getWrappedElement()));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));
		if (regcodefld.getText().isEmpty())
			replacemarker.click();
		else {
			devicestable.getWrappedElement().findElement(By.xpath(".//a[text()='x']")).click();
			//waitABit(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));
			replacemarker.click();
		}
		waitABit(3000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));	
		return regcodefld.getText();
	}

	public ActiveDevicesWebPage clickAddNewDevicesLink() {
	    wait.until(ExpectedConditions.elementToBeClickable(addNewDevicesLink)).click();
	    wait.until(ExpectedConditions.attributeToBe(devicesModalDialog, "", ""));
	    return this;
    }

    /**
     * selects Random license number
     * @return
     */
	public String selectLicenseNumber() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(licenseField)).click();
        } catch (Exception e) {
            Assert.fail("The license combobox is not clickable.", e);
        }
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By
                .xpath("//div[@id='ctl00_ctl00_Content_Main_ctl00_ctl01_Card_cbFreeLicenses_DropDown']//li"), 1));
        String license = licenseDropDownOptions.get(RandomUtils.nextInt(1, 10)).getText();
        selectLicenseNumber(license);
        return license;
    }

	public ActiveDevicesWebPage selectLicenseNumber(String license) {
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).sendKeys(license);
        licenseField.sendKeys(Keys.ENTER);
        return this;
    }

	public ActiveDevicesWebPage clickDevicesModalDialogOkButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(newDeviceDialogOkButton)).click();
	    waitForLoading();
	    return this;
    }

    public boolean isLicenseDisplayed(String license) {
	    try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//tr[contains(@id, 'ctl00_ctl00_Content_Main_gvFreeLicences')]/td[text()='" + license + "']")));
            return true;
        } catch (Exception e) {
	        return false;
        }
    }

    public void deleteDevices() {
	    try {
            while (wait.until(ExpectedConditions.visibilityOf(device)).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(deviceDeleteButton)).click();
                handleAlert();
                driver.switchTo().defaultContent();
            }
        } catch (Exception ignored) {}
    }

    public boolean isAlertNotificationDisplayed() {
	    try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
	        e.printStackTrace();
	        return false;
        }
    }

    public String getAlertTextDisplayed() {
	    try {
            return driver.switchTo().alert().getText();
        } catch (Exception e) {
	        e.printStackTrace();
        }
        return null;
    }

	public String getLicenseEntityName() {
		final String entitysrart = "&entityName=0002-0";;
		String href = driver.findElement(By.xpath("//a[@title='Audit Log']")).getAttribute("href");
		return  href.substring(href.indexOf(entitysrart) + entitysrart.length() , href.indexOf(entitysrart) + entitysrart.length() + 3);
	}

	public String getApplicationID() {
		final String appLogoStart = "/AppLogo/";
		final String appIDEnd = ".png";
		String appid = driver.findElement(By.id("ctl00_ctl00_AppLogo2_LogoImage")).getAttribute("src");
		return  appid.substring(appid.indexOf(appLogoStart) + appLogoStart.length() , appid.indexOf(appIDEnd));
	}

	public String getDeviceID() {
		final String appcontext = driver.findElement(By.xpath("//a[contains(@id, 'ctl00_ctl00_Content_Main_devices_ctl00')]")).getAttribute("href");
		final String deviceIDStart = "deviceId=";
		final String deviceIDEnd = "&licenceId=";
		return appcontext.substring(appcontext.indexOf(deviceIDStart) + deviceIDStart.length(), appcontext.indexOf(deviceIDEnd));
	}

	public String getLicenseID() {
		final String appcontext = driver.findElement(By.xpath("//a[contains(@id, 'ctl00_ctl00_Content_Main_devices_ctl00')]")).getAttribute("href");
		final String licenseIDStart = "&licenceId=";
		return appcontext.substring(appcontext.indexOf(licenseIDStart) + licenseIDStart.length(), appcontext.length());
	}
}