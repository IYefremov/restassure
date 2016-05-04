package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class WorkOrderTypesWebPage extends BaseWebPage {
	
	final String monitorrepairingchkbox = "Monitor Repairing";
	final String invoicecompletedroonlychkbox = "Invoice completed RO only";
	final String delayedrostartchkbox = "Delayed RO Start";
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00")
	private WebTable wotypestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_qv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addwotypebtn;
	
	//New WO type
	@FindBy(xpath = "//input[contains(@id, 'Card_name')]")
	private TextField newwotypenamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_description')]")
	private TextField newwotypedescfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbAliasName')]")
	private TextField newwotypealiasnamefld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboServiceGroup_Input')]")
	private ComboBox newwotypeservicepkgcmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboServiceGroup_DropDown')]")
	private DropDown newwotypeservicepkgdd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlServiceGroupingType_Input')]")
	private ComboBox newwotypegroupservicesbycmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlServiceGroupingType_DropDown')]")
	private DropDown newwotypegroupservicesbydd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboPriceAccess_Input')]")
	private ComboBox newwotypepriceaccesscmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboPriceAccess_DropDown')]")
	private DropDown newwotypepriceaccessdd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboSharingType_Input')]")
	private ComboBox newwotypesharingcmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboSharingType_DropDown')]")
	private DropDown newwotypesharingdd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbApproxRepairTime')]")
	private TextField newwotypeapproxrepairtimefld;
	
	////////////////////////
	@FindBy(xpath = "//span[text()='Question Forms']")
	private WebElement questionformstab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_lAvailable")
	private WebElement avalablequestionformslist;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_lAssigned")
	private WebElement assignedquestionformslist;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_btnAddToAssigned")
	private WebElement addtoassignedbtn;
	
	/////////////////////////////////
	@FindBy(xpath = "//span[text()='Invoice Types']")
	private WebElement invoicetypestab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlInvoiceTypes_lAvailable")
	private WebElement avalableinvoicetypelist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlInvoiceTypes_lAssigned")
	private WebElement assignedinvoicetypeslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlInvoiceTypes_btnAddToAssigned")
	private WebElement addtoassignedivoicetypesbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newwotypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newwotypecancelbtn;
	
	
	public WorkOrderTypesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickAddWorkOrderTypeButton() {
		clickAndWait(addwotypebtn);
	}
	
	public void setNewWorkOrderTypeName(String wotype) {
		clearAndType(newwotypenamefld, wotype);	
	}
	
	public void setNewWorkOrderTypeDescription(String wotypedesc) {
		clearAndType(newwotypedescfld, wotypedesc);	
	}
	
	public void setNewWorkOrderTypeAliasName(String wotypealiasname) {
		clearAndType(newwotypealiasnamefld, wotypealiasname);	
	}
	
	public void selectNewWorkOrderyTypeServicePackage(String wotypeservicepkg) {
		selectComboboxValue(newwotypeservicepkgcmb, newwotypeservicepkgdd, wotypeservicepkg);
	}
	
	public void selectNewWorkOrderyTypeGroupServicesBy(String wotypegroupby) {
		selectComboboxValue(newwotypegroupservicesbycmb, newwotypegroupservicesbydd, wotypegroupby);
	}
	
	public void selectNewWorkOrderyTypePriceAccess(String wotypepriceaccess) {
		selectComboboxValue(newwotypepriceaccesscmb, newwotypepriceaccessdd, wotypepriceaccess);
	}
	
	public String getNewWorkOrderyTypePriceAccess() {
		return newwotypepriceaccesscmb.getSelectedValue();
	}
	
	public void selectNewWorkOrderyTypeSharing(String wotypesharingtype) {
		selectComboboxValue(newwotypesharingcmb, newwotypesharingdd, wotypesharingtype);
	}
	
	public void chechWOTypeOption(String optionname) {
		checkboxSelect(optionname);
	}
	
	public void clickNewWorkOrderTypeOKButton() {
		clickAndWait(newwotypeOKbtn);
	}
	
	public void clickNewWorkOrderTypeCancelButton() {
		click(newwotypecancelbtn);
	}
	
	public void assignQuestionForm(String questionformtoassign) {
		questionformstab.click();
		selectAvailableQuestionForm(questionformtoassign);
		addtoassignedbtn.click();
	}
	
	public void selectAvailableQuestionForm(String questionformtoassign) {
		Select availablesrvs = new Select(avalablequestionformslist);
		availablesrvs.selectByVisibleText(questionformtoassign);
	}
	
	public void assignInvoiceType(String invoicetypetoassign) {
		invoicetypestab.click();
		selectAvailableInvoiceType(invoicetypetoassign);
		addtoassignedivoicetypesbtn.click();
	}
	
	public void selectAvailableInvoiceType(String invoicetypetoassign) {
		Select availablesrvs = new Select(avalableinvoicetypelist);
		availablesrvs.selectByVisibleText(invoicetypetoassign);
	}
	
	public String createNewWorkOrderType(String wotype, String servicepkg) {
		setNewWorkOrderTypeName(wotype);
		selectNewWorkOrderyTypeServicePackage(servicepkg);
		String defaultwotypepriceaccess = getNewWorkOrderyTypePriceAccess();
		clickNewWorkOrderTypeOKButton();
		return defaultwotypepriceaccess;
	}
	
	public void setNewWorkOrderTypeMonitorRepairingInformation(boolean invoicecompletedroonly, boolean delayedrostart, String approxrepairtime) {
		chechWOTypeOption(monitorrepairingchkbox);
		chechWOTypeOption(invoicecompletedroonlychkbox);
		chechWOTypeOption(delayedrostartchkbox);
		clearAndType(newwotypeapproxrepairtimefld, approxrepairtime);
	}
	
	public List<WebElement> getWorkOrderTypesTableRows() {
		return wotypestable.getTableRows();
	}
	
	public int getWorkOrderTypeIndexColumn() {
		return wotypestable.getTableColumnIndex("Type");
	}
	
	public WebElement getTableRowWithWorkOrderType(String wotype) {
		List<WebElement> rows = getWorkOrderTypesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + getWorkOrderTypeIndexColumn() + "]")).getText().equals(wotype)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableWorkOrderTypeDescription(String wotype) {
		String wodescription = "";
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			wodescription = row.findElement(By.xpath(".//td[8]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");
		return wodescription;
	}
	
	public String getTableWorkOrderTypeServicePackage(String wotype) {
		String wodescription = "";
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			wodescription = row.findElement(By.xpath(".//td[7]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");
		return wodescription;
	}
	
	public String getTableWorkOrderTypePriceAccess(String wotype) {
		String wodescription = "";
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			wodescription = row.findElement(By.xpath(".//td[9]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");
		return wodescription;
	}
	
	public boolean isWorkOrderTypeExists(String wotype) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  wotypestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + wotype + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickEditWorkOrderType(String wotype) {
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");
	}
	
	public void deleteWorkOrderType(String wotype) {
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");	
		}
	}
	
	public void deleteWorkOrderTypeAndCancelDeleting(String wotype) {
		WebElement row = getTableRowWithWorkOrderType(wotype);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + wotype + " work order type");	
		}
	}

}
