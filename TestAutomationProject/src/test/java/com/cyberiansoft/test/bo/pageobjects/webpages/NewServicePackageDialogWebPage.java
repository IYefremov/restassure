package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewServicePackageDialogWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//input[contains(@id, 'Card_name')]")
	private TextField newservicepackagenamefld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlPackageType_Input')]")
	private ComboBox newservicepackagetypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlPackageType_DropDown')]")
	private DropDown newservicepackagetypedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_formTypes_Input')]")
	private ComboBox newservicepackageformtypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_formTypes_DropDown')]")
	private DropDown newservicepackageformtypedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tTechnicianComCost')]")
	private TextField newservicepackagetechniciancommissionsfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tAdvisorComCost')]")
	private TextField newservicepackageadvisorcommissionsfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newwotypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newwotypecancelbtn;
	
	public NewServicePackageDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void createNewServicePackage(String servicepackagename) {
		setNewServicePackageName(servicepackagename);
		clickOKButton();
	}
	
	public void setNewServicePackageName(String servicepackagename) {
		clearAndType(newservicepackagenamefld, servicepackagename);
	}
	
	public String getNewServicePackageName() {
		return newservicepackagenamefld.getValue();
	}
	
	public void selectNewServicePackageType(String servicepackagetype) {
		selectComboboxValue(newservicepackagetypecmb, newservicepackagetypedd, servicepackagetype);
	}
	
	public String getNewServicePackageType() {
		return newservicepackagetypecmb.getSelectedValue();
	}
	
	public void selectNewServicePackageFormType(String servicepackageformtype) {
		selectComboboxValue(newservicepackageformtypecmb, newservicepackageformtypedd, servicepackageformtype);
	}
	
	public String getNewServicePackageFormType() {
		return newservicepackageformtypecmb.getSelectedValue();
	}
	
	public void setNewServicePackageTechnicianCommissions(String servicepackagetechcomm) {
		clearAndType(newservicepackagetechniciancommissionsfld, servicepackagetechcomm);
	}
	
	public String getNewServicePackageTechnicianCommissions() {
		return newservicepackagetechniciancommissionsfld.getValue();
	}
	
	public void setNewServicePackageAdvisorCommissions(String servicepackageadvcomm) {
		clearAndType(newservicepackageadvisorcommissionsfld, servicepackageadvcomm);
	}
	
	public String getNewServicePackageAdvisorCommissions() {
		return newservicepackageadvisorcommissionsfld.getValue();
	}
	
	public void clickOKButton() {
		clickAndWait(newwotypeOKbtn);
	}
	
	public void clickCancelButton() {
		click(newwotypecancelbtn);
	}
}
