package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class NewInspectionTypeDialogWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_name")
	private TextField newinsptypenamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboSharingType_Input")
	private ComboBox insptypesharingcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboSharingType_DropDown")
	private DropDown insptypesharingdd;
	
	@FindBy(xpath = "//td[@class='data']/label[contains(@for, 'Card_cbEmailPhotos')]")
	private WebElement insptypeemailpicturescmbox;
	
	@FindBy(xpath = "//label[@for='ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbUseNewPrinting']")
	private WebElement insptypeusenewprintingcmbox;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlReportTemplateApplication_Input")
	private ComboBox wholesaleprinttemplatecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlReportTemplateApplication_DropDown")
	private DropDown wholesaleprinttemplatedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlRetailReportTemplateApplication_Input")
	private ComboBox retailprinttemplatecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlRetailReportTemplateApplication_DropDown")
	private DropDown retailprinttemplatedd;
	
	////////////////////////
	@FindBy(xpath = "//span[text()='Question Forms']")
	private WebElement questionformstab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_lAvailable")
	private WebElement avalablequestionformslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_lAssigned")
	private WebElement assignedquestionformslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlForms_btnAddToAssigned")
	private WebElement addtoassignedbtn;
	
	//////////////////////////////////
	@FindBy(xpath = "//span[text()='Price Matrixes']")
	private WebElement pricematrixestab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlMatrixes_lAvailable")
	private WebElement avalablepricematrixeslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlMatrixes_lAssigned")
	private WebElement assignedpricematrixeslist;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlMatrixes_btnAddToAssigned")
	private WebElement addtoassignedmatrixbtn;
	
	//////////////////////////////////
	@FindBy(xpath = "//span[text()='Packages']")
	private WebElement packagestab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlGroups_lAvailable")
	private WebElement avalablepackageslist;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlGroups_lAssigned")
	private WebElement assignedpackageslist;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlGroups_btnAddToAssigned")
	private WebElement addtoassignedpkgbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_dlGroups_btnAllToAssigned")
	private WebElement addalltoassignedpkgbtn;
	
	/////////////////////////////////////////
	@FindBy(xpath = "//span[text()='Visual Inspections']")
	private WebElement visualinspectionstab;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl00_AddNewRecordButton")
	private WebElement addnewrecordvisualinspbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl02_EditFormControl_comboAvailableVisualForms_Input")
	private ComboBox avalablevisualformscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl02_EditFormControl_comboAvailableVisualForms_DropDown")
	private DropDown avalablevisualformsdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl02_EditFormControl_comboPackage_Input")
	private ComboBox packagevisualinspcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl02_EditFormControl_comboPackage_DropDown")
	private DropDown packagevisualinspdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00_ctl02_ctl02_EditFormControl_btnInsert")
	private WebElement insertvisulinspbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_gvVisualForms_ctl00")
	private WebTable visualformstable;
	
	/////////////////////////////////////
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newinsptypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newinsptypecancelbtn;
	
	public NewInspectionTypeDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setNewInspectionTypeName(String insptypename) {
		newinsptypenamefld.clearAndType(insptypename);
	}
	
	public void selectNewInspectionTypeSharingOption(String sharingtype) {
		selectComboboxValue(insptypesharingcmb, insptypesharingdd, sharingtype);
	}

	public void selectEmailPicturesCheckBox() {
		checkboxSelect(insptypeemailpicturescmbox);
	}
	
	public void selectUseNewPrintingCheckBox() {
		waitABit(300);
		checkboxSelect(insptypeusenewprintingcmbox);
	}
	
	public void selectNewInspectionTypeWholesalePrintTemplate(String wholesaleprinttemp) {
		selectComboboxValue(wholesaleprinttemplatecmb, wholesaleprinttemplatedd, wholesaleprinttemp);
	}
	
	public void selectNewInspectionTypeRetailPrintTemplate(String retailprinttemp) {
		selectComboboxValue(retailprinttemplatecmb, retailprinttemplatedd, retailprinttemp);
	}
	
	public void clickNewInspectionTypeOKButton() {
		clickAndWait(newinsptypeOKbtn);
	}
	
	public void clickNewInspectionTypeCancelButton() {
		newinsptypecancelbtn.click();
	}
	
	public void assignQuestionForm(String questionformtoassign) {
		questionformstab.click();
		selectAvailableQuestionForm(questionformtoassign);
		addtoassignedbtn.click();
	}
	
	public void assignPriceMatrix(String pricematrixtoassign) {
		pricematrixestab.click();
		selectAvailablePriceMatrix(pricematrixtoassign);
		addtoassignedmatrixbtn.click();
	}
	
	public void assignPackage(String packagetoassign) {
		packagestab.click();
		selectAvailablePackage(packagetoassign);
		addtoassignedpkgbtn.click();
	}
	
	public void assignAllPackages() {
		packagestab.click();		
		addalltoassignedpkgbtn.click();
	}
	
	public void selectAvailableQuestionForm(String questionformtoassign) {
		Select availablesrvs = new Select(avalablequestionformslist);
		availablesrvs.selectByVisibleText(questionformtoassign);
	}
	
	public void selectAvailablePackage(String packagetoassign) {
		Select availablesrvs = new Select(avalablepackageslist);
		availablesrvs.selectByVisibleText(packagetoassign);
	}
	
	public void selectAvailablePriceMatrix(String pricematrixtoassign) {
		Select availablesrvs = new Select(avalablepricematrixeslist);
		availablesrvs.selectByVisibleText(pricematrixtoassign);
	}
	
	public void insertVisualForm(String visualformtype, String packagetype) {
		visualinspectionstab.click();
		int rowcountbefore = visualformstable.getTableRowCount();
		clickAndWait(addnewrecordvisualinspbtn);
		selectComboboxValueAndWait(avalablevisualformscmb, avalablevisualformsdd, visualformtype);
		packagevisualinspcmb.getWrappedElement().click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", packagevisualinspdd.getWrappedElement().findElement(By.xpath("./div/ul/li[text()='" + packagetype + "']")));
		packagevisualinspdd.getWrappedElement().findElement(By.xpath("./div/ul/li[text()='" + packagetype + "']")).click();
		waitABit(300);
		clickAndWait(insertvisulinspbtn);
		Assert.assertEquals(visualformstable.getTableRowCount(), rowcountbefore+1);
	}
}
