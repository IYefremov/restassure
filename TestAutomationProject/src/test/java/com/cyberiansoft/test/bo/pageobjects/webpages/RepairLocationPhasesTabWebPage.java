package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class RepairLocationPhasesTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable phasestable;
	
	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addphasebtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbPhaseName")
	private TextField newphasenamefld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbDescription")
	private TextField newphasedescfld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboTypes_Input")
	private ComboBox newphasetypecmb;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboTypes_DropDown")
	private DropDown newphasetypedd;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboCheckOutTypes_Input")
	private ComboBox newphasecheckouttypecmb;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboCheckOutTypes_DropDown")
	private DropDown newphasecheckouttypedd;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboDepartments_Input")
	private ComboBox newphasedepartmentcmb;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboDepartments_DropDown")
	private DropDown newphasedepartmentdd;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbApproxTransTime")
	private TextField newphaseppproxtranstimefld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbApproxRepairTime")
	private TextField newphaseppproxrepairtimefld;
	
	@FindBy(xpath = "//label[@for='ctl00_Content_ctl01_ctl01_Card_cbDoNotTrackServiceStatuses']")
	private WebElement donottrackindstatuseschkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_Content_ctl01_ctl01_Card_cbStartServiceRequired']")
	private WebElement startservicerequiredchkbox;
	
	@FindBy(xpath = "//label[@for='ctl00_Content_ctl01_ctl01_Card_cbQCRequired']")
	private WebElement qcrequiredcheckbox;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement newphaseOKBtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
	private WebElement newphasecancelBtn;
	
	public RepairLocationPhasesTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddPhasetButton() {
		clickAndWait(addphasebtn);
	}
	
	public List<WebElement>  getRepairLocationPhasesTableRows() {
		return phasestable.getTableRows();
	}
	
	public WebElement getTableRowWithRepairLocationPhase(String repairlocationphase) {
		List<WebElement> rows = getRepairLocationPhasesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[4]")).getText().equals(repairlocationphase)) {
				return row;
			}
		} 
		return null;
	}
	
	public boolean isRepairLocationPhaseExists(String repairlocationphase) {
		boolean exists =  phasestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + repairlocationphase + "']")).size() > 0;
		return exists;
	}
	
	public void deleteRepairLocationPhase(String repairlocationphase) {
		WebElement row = getTableRowWithRepairLocationPhase(repairlocationphase);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocationphase + " repair location phase");	
		}
	}
	
	public void clickEditRepairLocationPhase(String repairlocationphase) {
		WebElement row = getTableRowWithRepairLocationPhase(repairlocationphase);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + repairlocationphase + " repair location phase");
	}
	
	//New Phase
	public void setNewRepairLocationPhaseName(String repairlocationphase) {
		clearAndType(newphasenamefld, repairlocationphase);
	}
	
	public String getNewRepairLocationPhaseName() {
		return newphasenamefld.getValue();
	}
	
	public void setNewRepairLocationPhaseDescription(String repairlocationphasedesc) {
		clearAndType(newphasedescfld, repairlocationphasedesc);
	}
	
	public String getNewRepairLocationPhaseDescription() {
		return newphasedescfld.getValue();
	}
	
	public void selectNewRepairLocationPhaseType(String repairlocationphasetype) {
		selectComboboxValue(newphasetypecmb, newphasetypedd, repairlocationphasetype);
	}

	public String getNewRepairLocationPhaseType() {
		return newphasetypecmb.getSelectedValue();
	}
	
	public void selectNewRepairLocationPhaseCheckOutType(String repairlocationphasecheckouttype) {
		selectComboboxValue(newphasecheckouttypecmb, newphasecheckouttypedd, repairlocationphasecheckouttype);
	}

	public String getNewRepairLocationPhaseCheckOutType() {
		return newphasecheckouttypecmb.getSelectedValue();
	}
	
	public void selectNewRepairLocationPhaseDepartment(String repairlocationphasedepartment) {
		selectComboboxValue(newphasedepartmentcmb, newphasedepartmentdd,  repairlocationphasedepartment);
	}

	public String getNewRepairLocationPhaseDepartment() {
		return newphasedepartmentcmb.getSelectedValue();
	}
	
	public void setNewRepairLocationPhaseApproxTransitionTime(String repairlocationphaseaaproxtranstime) {
		clearAndType(newphaseppproxtranstimefld, repairlocationphaseaaproxtranstime);
	}
	
	public String getNewRepairLocationPhaseApproxTransitionTime() {
		return newphaseppproxtranstimefld.getValue();
	}
	
	public void setNewRepairLocationPhaseApproxRepairTime(String repairlocationphaseaaproxrepairtime) {
		clearAndType(newphaseppproxrepairtimefld, repairlocationphaseaaproxrepairtime);
	}
	
	public String getNewRepairLocationPhaseApproxRepairTime() {
		return newphaseppproxrepairtimefld.getValue();
	}
	
	public void clickNewRepairLocationPhaseOKButton() {
		clickAndWait(newphaseOKBtn);
	}
	
	public void clickNewRepairLocationPhaseCancelButton() {
		click(newphasecancelBtn);
	}
	
	public void selectDoNotTrackIndividualServiceStatuses() {
		checkboxSelect(donottrackindstatuseschkbox);
	}
	
	public void unselectDoNotTrackIndividualServiceStatuses() {
		checkboxUnselect(donottrackindstatuseschkbox);
	}
	
	public boolean isDoNotTrackIndividualServiceStatusesSelected() {
		return isCheckboxChecked(donottrackindstatuseschkbox);
	}
	
	public void selectStartServiceRequired() {
		checkboxSelect(startservicerequiredchkbox);
	}
	
	public void unselectStartServiceRequired() {
		checkboxUnselect(startservicerequiredchkbox);
	}
	
	public boolean isStartServiceRequiredSelected() {
		return isCheckboxChecked(startservicerequiredchkbox);
	}
	
	public void selectQCRequired() {
		checkboxSelect(qcrequiredcheckbox);
	}
	
	public void unselectQCRequired() {
		checkboxUnselect(qcrequiredcheckbox);
	}
	
	public boolean isQCRequiredSelected() {
		return isCheckboxChecked(qcrequiredcheckbox);
	}
}
