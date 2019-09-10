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
import org.openqa.selenium.support.ui.Select;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class NewTeamsDialogWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField teamnamefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_timeZones")
	private WebElement teamtimezonecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbDesc")
	private TextField teamdescfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingID")
	private TextField teamaccountingIDfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboAreas_Input")
	private ComboBox teamareacmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboAreas_DropDown")
	private DropDown teamareadd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTimeSheetTypes_Input")
	private ComboBox teamtimesheettypecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTimeSheetTypes_DropDown")
	private DropDown teamtimesheettypedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLocations_Input")
	private ComboBox teamlocationcmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLocations_DropDown")
	private DropDown teamlocationdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTeamType_Input")
	private ComboBox teamtypecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboTeamType_DropDown")
	private DropDown teamtypedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cblLocations")
	private WebElement teamadditionallocationcmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement addteamOKbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement addteamCancelbtn;

	//Vendor settings
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCompany")
	private TextField teamcompanyfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress")
	private TextField teamaddressfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress2")
	private TextField teamaddress2fld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCity")
	private TextField teamcityfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlCountry_Input")
	private ComboBox teamcountrycmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlCountry_DropDown")
	private DropDown teamcountrydd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlState_Input")
	private ComboBox teamstatecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlState_DropDown")
	private DropDown teamstatedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbZip")
	private TextField teamzipfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbEmail")
	private TextField teamemailfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPhone")
	private TextField teamphonefld;

	public NewTeamsDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void setNewTeamName(String team) {
		clearAndType(teamnamefld, team);
	}

	public String getNewTeamName() {
		return teamnamefld.getValue();
	}

	public void setNewTeamDescription(String teamdesc) {
		clearAndType(teamdescfld, teamdesc);
	}

	public void setNewTeamAccountingID(String teamaccid) {
		clearAndType(teamaccountingIDfld, teamaccid);
	}

	public void selectTeamTimezone(String teamtimezone) {
		Select sel = new Select(teamtimezonecmb);
		sel.selectByVisibleText(teamtimezone);
	}

	public void selectTeamArea(String teamarea) {
		selectComboboxValue(teamareacmb, teamareadd, teamarea);
	}

	public void selectTeamTimesheetType(String teamtimesheettype) {

		selectComboboxValue(teamtimesheettypecmb, teamtimesheettypedd, teamtimesheettype);
	}

	public void selectTeamDefaultRepairLocation(String teamdefaultlocation) {
		selectComboboxValue(teamlocationcmb, teamlocationdd, teamdefaultlocation);
	}

	public void selectTeamAdditionalRepairLocation(String teamadditionallocation) {
		click(teamadditionallocationcmb.findElement(By.xpath(".//label[text()='" + teamadditionallocation + "']")));
	}

	public void selectTeamType(String teamtype) {
		selectComboboxValue(teamtypecmb, teamtypedd, teamtype);
	}

	///////////// VEndor

	public void setNewTeamCompany(String teamcompany) {
		clearAndType(teamcompanyfld, teamcompany);
	}

	public void setNewTeamAddress(String teamaddress) {
		clearAndType(teamaddressfld, teamaddress);
	}

	public void setNewTeamCity(String teamcity) {
		clearAndType(teamcityfld, teamcity);
	}

	public void setNewTeamZip(String teamzip) {
		clearAndType(teamzipfld, teamzip);
	}

	public void setNewTeamEmail(String teamemail) {
		clearAndType(teamemailfld, teamemail);
	}

	public void setNewTeamPhone(String teamphone) {
		clearAndType(teamphonefld, teamphone);
	}

	public void selectTeamCountry(String teamcountry) {
		selectComboboxValueAndWait(teamcountrycmb, teamcountrydd, teamcountry);
	}

	public void selectTeamState(String teamstate) {
		waitABit(2000);
		selectComboboxValue(teamstatecmb, teamstatedd, teamstate);
	}

	public void clickAddTeamOKButton() {
		clickAndWait(addteamOKbtn);
	}

	public void clickAddTeamCancelButton() {
		click(addteamCancelbtn);
	}

}
