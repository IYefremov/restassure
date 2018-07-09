package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class ActiveDevicesWebPage extends BaseWebPage {

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
			waitABit(3000);
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

	public String selectLicenseNumber() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(licenseField)).click();
        } catch (Exception e) {
            Assert.fail("The license combobox is not clickable.", e);
        }
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By
                .xpath("//div[@id='ctl00_ctl00_Content_Main_ctl00_ctl01_Card_cbFreeLicenses_DropDown']//li"), 1));
        String license = licenseDropDownOptions.get(RandomUtils.nextInt(1, 10)).getText();
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).sendKeys(license);
        licenseField.sendKeys(Keys.ENTER);
        return license;
    }

	public ActiveDevicesWebPage selectLicenseNumber(String license) {
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(licenseField)).sendKeys(license);
        licenseField.submit();
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
                driver.switchTo().alert().accept();
                driver.switchTo().defaultContent();
            }
        } catch (Exception ignored) {}
    }
}