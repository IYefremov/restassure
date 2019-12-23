package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.impl.TextFieldImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class NewRepairLocationDialogWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField repairlocationnamefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboStatus_Input")
	private ComboBox repairlocationstatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboStatus_DropDown")
	private DropDown repairlocationstatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbApproxRepairTime")
	private TextField approxrepairtimfld;

	@FindBy(xpath = "//span[text()='Phase Enforcement']")
	private WebElement phaseenforcementchkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_timeZones")
	private WebElement locationworkinghourstimezonecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress")
	private TextField locationaddressfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress2")
	private TextField locationaddress2fld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCity")
	private TextField locationcityfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlCountry_Input")
	private WebElement locationcountrycmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlState_Input")
	private WebElement locationstatecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbZip")
	private TextField locationzipfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbEmail")
	private TextField locationmailfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPhone")
	private TextField locationphonefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement repairLocationOkButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement repairLocationCancelButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbCompleteInvoicedROs")
	private WebElement completeInvoicesROsCheckbox;

	public NewRepairLocationDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void createNewRepairLocation(String repairlocationname) {
		setNewRepairLocationName(repairlocationname);
		clickOKButton();
	}

	public void clickCompleteInvoicesROsCheckbox() {
		wait.until(ExpectedConditions.elementToBeClickable(completeInvoicesROsCheckbox)).click();
	}

	public boolean isCompleteInvoicesROsOptionChecked() {
		try {
			wait.until(ExpectedConditions.attributeToBeNotEmpty(completeInvoicesROsCheckbox, "checked"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void createNewRepairLocation(String repairlocationname, String repairlocationstatus, String repairlocationtomezone) {
		setNewRepairLocationName(repairlocationname);
		selectNewRepairLocationStatus(repairlocationstatus);
		selectNewRepairLocationWorkingHoursTimeZone(repairlocationtomezone);
		clickOKButton();
	}

	public void setNewRepairLocationName(String repairlocationname) {
		clearAndType(repairlocationnamefld, repairlocationname);
	}

	public void selectNewRepairLocationStatus(String repairlocationstatus) {
		selectComboboxValue(repairlocationstatuscmb, repairlocationstatusdd, repairlocationstatus);
	}

	public void setNewRepairLocationApproxRepairTime(String approxrepairtime) {
		clearAndType(approxrepairtimfld, approxrepairtime);
	}

	public void selectNewRepairLocationWorkingHoursTimeZone(String timezonevalue) {
		Select selectBox = new Select(locationworkinghourstimezonecmb);
		selectBox.selectByValue(timezonevalue);
	}

	public void selectPhaseEnforcementOption() {
		click(phaseenforcementchkbox);
	}

	public void setNewRepairLocationWorkingHours(String workingday, String starttime, String finishtime) {
		final String datecheckboxxpath = "Card_cb" + workingday;
		final String starttimexpath = "Card_rtp" + workingday + "Start_dateInput";
		final String finishtimexpath = "Card_rtp" + workingday + "Finish_dateInput";
		if (driver.findElements(By.xpath("//label[contains(@id, '" + datecheckboxxpath + "')]")).size() > 0)
			driver.findElement(By.xpath("//label[contains(@id, '" + datecheckboxxpath + "')]")).click();
		else
			driver.findElement(By.xpath("//input[contains(@id, '" + datecheckboxxpath + "')]")).click();

		new TextFieldImpl(driver.findElement(By.xpath("//input[contains(@id, '" + starttimexpath + "')]"))).clearAndType(starttime);
		new TextFieldImpl(driver.findElement(By.xpath("//input[contains(@id, '" + finishtimexpath + "')]"))).clearAndType(finishtime);
	}

	public void selectAddressInfoTab() {
		driver.findElement(By.xpath("//span[text()='Address Info']")).click();
		wait.until(ExpectedConditions.visibilityOf(locationaddressfld.getWrappedElement()));
		wait.until(ExpectedConditions.visibilityOf(locationaddress2fld.getWrappedElement()));
		wait.until(ExpectedConditions.visibilityOf(locationcityfld.getWrappedElement()));
		wait.until(ExpectedConditions.visibilityOf(locationcountrycmb));
		wait.until(ExpectedConditions.visibilityOf(locationstatecmb));
		wait.until(ExpectedConditions.visibilityOf(locationzipfld.getWrappedElement()));
		wait.until(ExpectedConditions.visibilityOf(locationmailfld.getWrappedElement()));
		wait.until(ExpectedConditions.visibilityOf(locationphonefld.getWrappedElement()));
	}

	public void selectWorkingHoursTab() {
		driver.findElement(By.xpath("//span[text()='Working Hours']")).click();
		wait.until(ExpectedConditions.
				visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(text(), 'Once Time Zone is set, it cannot be changed')]")));
	}

	public void clickOKButton() {
		try {
			waitShort.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
		} catch (Exception ignored) {
		}
		click(repairLocationOkButton);
		waitForLoading();
	}

	public void clickCancelButton() {
		clickAndWait(repairLocationCancelButton);
	}
}
