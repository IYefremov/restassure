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

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class SRAppointmentInfoPopup extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpStartDate_dateInput")
	private TextField fromdatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpEndDate_dateInput")
	private TextField todatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpStartTime_dateInput")
	private TextField starttimefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpStartTime_timePopupLink")
	private WebElement starttimeclockbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpEndTime_dateInput")
	private TextField endtimefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rdpEndTime_timePopupLink")
	private WebElement endtimeclockbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbxSubject")
	private TextField subjectfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbAppLocations_Input")
	private ComboBox locationtypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbAppLocations_DropDown")
	private DropDown locationtypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbAppointmentLocations_Input")
	private ComboBox locationcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbAppointmentLocations_DropDown")
	private DropDown locationdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbTechnician_Input")
	private TextField technicianfld;
	
	@FindBy(id = "gvTechnicians")
	private WebElement technicianspopup;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbCountries_Input")
	private TextField countryfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbAppointmentClientName")
	private TextField clientinfonamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbAppointmentClientAddress")
	private TextField clientinfoaddressfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbAppointmentClientPhone")
	private TextField clientinfophonefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbAppointmentClientEmail")
	private TextField clientinfomailfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbxZip")
	private TextField clientzipfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbCountries_Input")
	private TextField clientcountryfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_rcbStates_Input")
	private TextField clientstatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbxAddress")
	private TextField clientaddressfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_tbxCity")
	private TextField clientcityfld;
	
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btnAddApp")
	private WebElement addapointmentbtn;
	
	public SRAppointmentInfoPopup(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void selectLocationType(String locationtype) {
		selectComboboxValue(locationtypecmb, locationtypedd, locationtype);
		waitABit(1000);
	}
	
	public void selectLocation(String applocation) {
		selectComboboxValue(locationcmb, locationdd, applocation);
	}
	
	public String getClientZipValue() {
		return clientzipfld.getValue();
	}
	
	public String getClientCountryValue() {
		return clientcountryfld.getValue();
	}
	
	public String getClientStateValue() {
		return clientstatefld.getValue();
	}
	
	public String getClientAddressValue() throws InterruptedException {
		Thread.sleep(3000);
		return clientaddressfld.getValue();
	}
	
	public String getClientCityValue() {
		return clientcityfld.getValue();
	}
	
	public void setClientAddressValue(String clientaddress) {
		clearAndType(clientaddressfld, clientaddress);
	}
	
	public void setClientCityValue(String clientcity) {
		clearAndType(clientcityfld, clientcity);
	}
	
	public void setClientZipValue(String clientzip) {
		clearAndType(clientzipfld, clientzip);
	}
	
	public String getFromDateValue() {
		return fromdatefld.getValue();
	}
	
	public void setFromDateValue(String datevalue) {
		clearAndType(fromdatefld, datevalue);
	}
	
	public void setToDateValue(String datevalue) {
		clearAndType(todatefld, datevalue);
	}
	
	public String getStartTimeValue() {
		return starttimefld.getValue();
	}
	
	public void setStartTimeValue(String starttime) {
		clearAndType(starttimefld, starttime);
		click(starttimeclockbtn);
	}
	
	public String getEndTimeValue() {
		return endtimefld.getValue();
	}
	
	public void setEndTimeValue(String endtime) {
		clearAndType(endtimefld, endtime);
		click(endtimeclockbtn);
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(
				  driver.findElement(By.id("ctl00_ctl00_Content_Main_rdpEndTime_timeView_wrapper")))));
	}
	
	public String getSubjectValue() {
		return subjectfld.getValue();
	}
	
	public String getTechnicianFieldValue() {
		return technicianfld.getValue();
	}
	
	public String getTechnicianValue() {
		wait.until(ExpectedConditions.visibilityOf(technicianspopup));
		waitABit(1000);
		return technicianspopup.findElement(By.xpath(".//table/tbody/tr/td")).getText();
		//return technicianfld.getValue();
	}
	
	public String getClientInfoNameValue() {
		waitABit(2000);
	    return clientinfonamefld.getValue();
	}
	
	public String getClientInfoAddressValue() {
		return clientinfoaddressfld.getValue();
	}
	
	public String getClientInfoPhoneValue() {
		return clientinfophonefld.getValue();
	}
	
	public String getClientInfoEmailValue() {
		return clientinfomailfld.getValue();
	}	
	
	public void clickAddAppointment() {
		click(addapointmentbtn);
		waitABit(4000);
	}

}
