package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewPrintTemplatesDialogWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl01_Card_comboCategory_Input")
	private ComboBox printtemplatecategorycmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl01_Card_comboCategory_DropDown")
	private DropDown printtemplatecategorydd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl01_Card_comboTemplates_Input")
	private ComboBox printtemplatecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl01_Card_comboTemplates_DropDown")
	private DropDown printtemplatedd;
	
	@FindBy(id = "tbAlias")
	private TextField printtemplatenamefld;
	
	@FindBy(id = "_rfdSkinnedctl00_ctl00_Content_Main_ctl09_ctl01_Card_chbActive")
	private WebElement printtemplatedefaultchkbox;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl02_BtnOk")
	private WebElement newprinttemplateOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl09_ctl02_BtnCancel")
	private WebElement newprinttemplatecancelbtn;
	
	public NewPrintTemplatesDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(printtemplatecategorycmb.getWrappedElement()));
	}
	
	public void createNewPrintTemplate(String ptcategory, String pttype, String ptname, boolean ptdefault) {
		selectPrintTemplateCategory(ptcategory);
		selectPrintTemplateType(pttype);
		setPrintTemplateName(ptname);
		clickNewPrintTemplateOKButton();
	}
	
	public void selectPrintTemplateCategory(String ptcategory) {
		selectComboboxValueAndWait(printtemplatecategorycmb, printtemplatecategorydd, ptcategory);
	}
	
	public void selectPrintTemplateType(String pttype) {
		selectComboboxValue(printtemplatecmb, printtemplatedd, pttype);
	}
	
	public void setPrintTemplateName(String ptname) {
		clearAndType(printtemplatenamefld, ptname);
	}
	
	public void selectPrintTeplateDefaultCheckBox() {
		checkboxSelect(printtemplatedefaultchkbox);
	}
	
	public void clickNewPrintTemplateOKButton() {
		clickAndWait(newprinttemplateOKbtn);
	}
	
	public void clickNewPrintTemplateCancelButton() {
		newprinttemplatecancelbtn.click();
	}

}
