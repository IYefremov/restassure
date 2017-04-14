package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewServiceDialogWebPage extends BaseWebPage {
	
	final String multiplecheckbox = "Multiple";
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbName')]")
	private TextField servicenamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_comboType_Input')]")
	private ComboBox servicetypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboType_DropDown')]")
	private DropDown servicetypedd;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbDesc')]")
	private TextField servicedescfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId')]")
	private TextField serviceaccidfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId2')]")
	private TextField serviceaccid2fld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboAssignmentTypes_Input')]")
	private ComboBox serviceassignedtocmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboAssignmentTypes_DropDown')]")
	private DropDown serviceassignedtodd;
	
	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk')]")
	private WebElement OKbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel')]")
	private WebElement cancelbtn;
	
	public NewServiceDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}

	public void setNewServiceName(String servicename) {
		clearAndType(servicenamefld, servicename);
	}
	
	public String getNewServiceName() {
		return servicenamefld.getValue();
	}
	
	public void selectNewServiceType(String servicetype) {
		selectComboboxValue(servicetypecmb, servicetypedd, servicetype);
	}
	
	public String getNewServiceType() {
		return servicetypecmb.getSelectedValue();
	}
	
	public void setNewServiceDescription(String servicedesc) {
		clearAndType(servicedescfld, servicedesc);
	}
	
	public String getNewServiceDescription() {
		return servicedescfld.getValue();
	}
	
	public void setNewServiceAccountingID(String serviceaccid) {
		clearAndType(serviceaccidfld, serviceaccid);
	}
	
	public String getNewServiceAccountingID() {
		return serviceaccidfld.getValue();
	}
	
	public void selectNewServiceAssignedTo(String serviceassignedto) {
		selectComboboxValue(serviceassignedtocmb, serviceassignedtodd, serviceassignedto);
	}
	
	public String getNewServiceAssignedTo() {
		return serviceassignedtocmb.getSelectedValue();
	}
	
	public void selectNewServiceMultiple() {
		checkboxSelect(multiplecheckbox);
	}
	
	public boolean isNewServiceMultipleSelected() {
		return isCheckboxChecked(driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbMultiple")));
	}
	
	public void clickOKButton() {
		clickAndWait(OKbtn);
	}
	
	public void clickCancelButton() {
		click(cancelbtn);
	}
}
