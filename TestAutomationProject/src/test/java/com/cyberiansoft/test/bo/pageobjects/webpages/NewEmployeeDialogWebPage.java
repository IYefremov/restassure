package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;


public class NewEmployeeDialogWebPage extends BaseWebPage {

	@FindBy(xpath = "//input[contains(@id, 'Card_comboTeams_Input')]")
	private ComboBox employeeteamcmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_comboTeams_DropDown')]")
	private DropDown employeeteamdd;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbFirstName')]")
	private TextField employeefirstnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbLastName')]")
	private TextField employeelastnamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbPassword')]")
	private TextField employeepswfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_comboHomeTeams_Input')]")
	private ComboBox employeehometeamcmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_comboHomeTeams_DropDown')]")
	private DropDown employeehometeamdd;

	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress')]")
	private TextField employeeaddressfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbCity')]")
	private TextField employeecityfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_countryState_ddlCountry_Input')]")
	private ComboBox employeecountrycmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlCountry_DropDown')]")
	private DropDown employeecountrydd;

	@FindBy(xpath = "//input[contains(@id, 'Card_countryState_ddlState_Input')]")
	private ComboBox employeestatecmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_countryState_ddlState_DropDown')]")
	private DropDown employeestatedd;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbZip')]")
	private TextField employeezipfld;

	@FindBy(id = "inPhone0")
	private TextField employeephonefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbEmail')]")
	private TextField employeemailfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId')]")
	private TextField employeeaccidfld;

	@FindBy(xpath = "//input[contains(@id, 'Card_dpStartingDate_dateInput')]")
	private TextField employeestartdatefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_comboTypes_Input')]")
	private ComboBox employeetypecmb;

	@FindBy(xpath = "//*[contains(@id, 'Card_comboTypes_DropDown')]")
	private DropDown employeetypedd;

	@FindBy(xpath = "//table[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cblRoles')]")
	private WebElement rolescheckboxes;

	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk')]")
	private WebElement OKbtn;

	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel')]")
	private WebElement cancelbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_imgTeamInfo")
	private WebElement infoBubble;

	@FindBy(id = "ctl00_ctl00_Content_Main_ModalDialog1")
	private WebElement newEmployeeDialog;

	@FindBy(xpath = "//div[@id='RadToolTipWrapper_ctl00_ctl00_Content_Main_ctl01_ctl01_Card_toolTipTeamInfo' " +
			"and contains(@style, 'visibility: visible;')]")
	private WebElement infoContentDialog;

	public NewEmployeeDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void selectNewEmployeeTeam(String employeeteam) {
		selectComboboxValue(employeeteamcmb, employeeteamdd, employeeteam);
		Assert.assertEquals(employeeteamcmb.getSelectedValue(), employeeteam);
	}

	public String getNewEmployeeTeam() {
		return employeeteamcmb.getSelectedValue();
	}

	public void setNewEmployeeFirstName(String firstname) {
		clearAndType(employeefirstnamefld, firstname);
	}

	public String getNewEmployeeFirstName() {
		return employeefirstnamefld.getValue();
	}

	public void setNewEmployeeLastName(String lastname) {
		clearAndType(employeelastnamefld, lastname);
	}

	public String getNewEmployeeLastName() {
		return employeelastnamefld.getValue();
	}

	public void setNewEmployeePassword(String employeepsw) {
		clearAndType(employeepswfld, employeepsw);
	}

	public String getNewEmployeePassword() {
		return employeepswfld.getValue();
	}

	public void selectNewEmployeeHomeTeam(String employeeteam) {
		selectComboboxValue(employeehometeamcmb, employeehometeamdd, employeeteam);
	}

	public String getNewEmployeeHomeTeam() {
		return employeehometeamcmb.getSelectedValue();
	}

	public void setNewEmployeeAddress(String employeeaddress) {
		clearAndType(employeeaddressfld, employeeaddress);
	}

	public String getNewEmployeeAddress() {
		return employeeaddressfld.getValue();
	}

	public void setNewEmployeeCity(String employeecity) {
		clearAndType(employeecityfld, employeecity);
	}

	public String getNewEmployeeCity() {
		return employeecityfld.getValue();
	}

	public void selectNewEmployeeCountry(String employeecountry) {
		selectComboboxValue(employeecountrycmb, employeecountrydd, employeecountry);
		waitForLoading();
		Assert.assertEquals(employeecountrycmb.getSelectedValue()
				.replaceAll("\\u00A0", "")
				.trim(), employeecountry);
	}

	public String getNewEmployeeCountry() {
		return employeecountrycmb.getSelectedValue()
				.replaceAll("\\u00A0", "")
				.trim();
	}

	public void selectNewEmployeeState(String employeestate) {
		selectComboboxValue(employeestatecmb, employeestatedd, employeestate);
	}

	public String getNewEmployeeState() {
		return employeestatecmb.getSelectedValue();
	}

	public void setNewEmployeeZip(String employeezip) {
		clearAndType(employeezipfld, employeezip);
	}

	public String getNewEmployeeZip() {
		return employeezipfld.getValue();
	}

	public void setNewEmployeePhone(String employeephone) {
		clearAndType(employeephonefld, employeephone);
	}

	public String getNewEmployeePhone() {
		return employeephonefld.getValue();
	}

	public void setNewEmployeeMail(String employeemail) {
		clearAndType(employeemailfld, employeemail);
	}

	public String getNewEmployeeMail() {
		return employeemailfld.getValue();
	}

	public void setNewEmployeeAccountingID(String employeeaccid) {
		clearAndType(employeeaccidfld, employeeaccid);
	}

	public String getNewEmployeeAccountingID() {
		return employeeaccidfld.getValue();
	}

	public void setNewEmployeeStartingDate(String employeestartdate) {
		clearAndType(employeestartdatefld, employeestartdate);
	}

	public String getNewEmployeeStartingDate() {
		return employeestartdatefld.getValue();
	}

	public void selectNewEmployeeType(String employeetype) {
		selectComboboxValue(employeetypecmb, employeetypedd, employeetype);
	}

	public String getNewEmployeeType() {
		return employeetypecmb.getSelectedValue();
	}

	public void selectEmployeeRole(String employeerole) {
		wait.until(ExpectedConditions.elementToBeClickable(rolescheckboxes));
		waitABit(300);
		checkboxSelect((WebElement) rolescheckboxes.findElement(By.xpath(".//label[text()='" + employeerole + "']")));
	}


	public boolean isEmployeeRoleChecked(String employeerole) {
		return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='" + employeerole + "']")));
	}

	public void clickOKButton() {
		clickAndWait(OKbtn);
	}

	public void clickCancelButton() {
		wait.until(ExpectedConditions.elementToBeClickable(cancelbtn)).click();
		wait.until(ExpectedConditions.attributeContains(newEmployeeDialog, "style", "display: none;"));
	}

	public void clickInfoBubble() {
		click(infoBubble);
	}
}
