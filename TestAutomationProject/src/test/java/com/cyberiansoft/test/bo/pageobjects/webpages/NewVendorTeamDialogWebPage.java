package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewVendorTeamDialogWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField vendorteamnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_timeZones")
	private WebElement vendortimezonecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbDesc")
	private TextField vendordescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingID")
	private TextField vendoraccidfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboAreas_Input")
	private ComboBox vendorareacmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboAreas_DropDown")
	private DropDown vendorareadd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTimeSheetTypes_Input")
	private ComboBox vendortimesheettimescmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTimeSheetTypes_DropDown")
	private DropDown vendortimesheettimesdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlEmployee_Input")
	private TextField vendorhouseaccountcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlEmployee_DropDown")
	private DropDown vendorhouseaccountdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlDefaultEmployee_Input")
	private TextField vendordefaulttechniciancmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlDefaultEmployee_DropDown")
	private DropDown vendordefaulttechniciandd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLocations_Input")
	private ComboBox vendordefaultlocationscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLocations_DropDown")
	private DropDown vendordefaultlocationsdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTeamType_Input")
	private ComboBox vendorteamtypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTeamType_DropDown")
	private DropDown vendorteamtypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement addvendorOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement addvendorcancelbtn;
	
	//Vendor settings
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCompany")
	private TextField vendorcompanyfld;
	
	
	public NewVendorTeamDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setNewVendorTeamName(String vendorname) {
		clearAndType(vendorteamnamefld, vendorname);
	}
	
	public String getNewVendorTeamName() {
		return vendorteamnamefld.getValue();
	}

	public void selectNewVendorTeamTimezone(String vendortimezone) {
		Select realSelect = new Select(vendortimezonecmb);
		realSelect.selectByValue(vendortimezone);
	}
	
	public String getNewVendorTeamTimezone() {
		Select realSelect = new Select(vendortimezonecmb);
		return realSelect.getFirstSelectedOption().getAttribute("value");
	}
	
	public void setNewVendorTeamDescription(String vendordesc) {
		clearAndType(vendordescfld, vendordesc);
	}
	
	public String getNewVendorTeamDescription() {
		return vendordescfld.getValue();
	}
	
	public void setNewVendorTeamAccountingID(String vendoraccid) {
		clearAndType(vendoraccidfld, vendoraccid);
	}
	
	public String getNewVendorTeamAccountingID() {
		return vendoraccidfld.getValue();
	}
	
	public void selectNewVendorTeamArea(String vendorarea) {
		selectComboboxValue(vendorareacmb, vendorareadd, vendorarea);
	}
	
	public String getNewVendorTeamArea() {
		return vendorareacmb.getSelectedValue();
	}
	
	public void selectNewVendorTeamTimesheetType(String vendortimesheettype) {
		selectComboboxValue(vendortimesheettimescmb, vendortimesheettimesdd, vendortimesheettype);
	}
	
	public String getNewVendorTeamTimesheetType() {
		return vendortimesheettimescmb.getSelectedValue();
	}
	
	public void selectNewVendorTeamHOUSEAccount(String vendorhouseaccount) {
		vendorhouseaccountcmb.click();
		vendorhouseaccountcmb.clearAndType(vendorhouseaccount);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(vendorhouseaccountdd.getWrappedElement()));
		waitABit(1000);
		vendorhouseaccountdd.selectByVisibleText(vendorhouseaccount);
	}
	
	public String getNewVendorTeamHOUSEAccount() {
		return vendorhouseaccountcmb.getValue();
	}
	
	public void selectNewVendorTeamDefaultTechnician(String vendordeftech) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(vendordefaulttechniciancmb.getWrappedElement()));
		vendordefaulttechniciancmb.click();
		vendordefaulttechniciancmb.clearAndType(vendordeftech);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(vendordefaulttechniciandd.getWrappedElement()));
		waitABit(1000);
		vendordefaulttechniciandd.selectByVisibleText(vendordeftech);
	}
	
	public String getNewVendorTeamDefaultTechnician() {
		return vendordefaulttechniciancmb.getValue();
	}
	
	public void selectNewVendorTeamDefaultRepairLocation(String defaultlocation) {
		selectComboboxValue(vendordefaultlocationscmb, vendordefaultlocationsdd, defaultlocation);
	}
	
	public String getNewVendorTeamDefaultRepairLocation() {
		return vendordefaultlocationscmb.getSelectedValue();
	}
	
	public void selectNewVendorTeamAdditionalRepairLocations(String additionalrepairlocation) {
		checkboxSelect(additionalrepairlocation);
	}
	
	public void selectNewVendorTeamType(String teamtype) {
		selectComboboxValue(vendorteamtypecmb, vendorteamtypedd, teamtype);
	}
	
	public String getNewVendorTeamType() {
		return vendorteamtypecmb.getSelectedValue();
	}
	
	public void setNewVendorCompany(String vendorcompany) {
		clearAndType(vendorcompanyfld, vendorcompany);
	}
	
	public String getNewVendorCompany() {
		return vendorcompanyfld.getValue();
	}
	
	public void clickOKButton() {
		clickAndWait(addvendorOKbtn);
	}
	
	public void clickCancelButton() {
		click(addvendorcancelbtn);
	}
}
