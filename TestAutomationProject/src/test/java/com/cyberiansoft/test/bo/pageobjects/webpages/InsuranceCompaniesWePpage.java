package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class InsuranceCompaniesWePpage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable insurancecompaniestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addinsurancecompanylink;

	
	//New Insurance Company
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbName")
	private TextField insurancecompanynamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress")
	private TextField insurancecompanyaddressfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbEmail")
	private TextField insurancecompanyemailfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPhone")
	private TextField insurancecompanyphonefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId")
	private TextField insurancecompanyaccauntigidfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingId2")
	private TextField insurancecompanyaccauntigid2fld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement addinsurancecompanyOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement addinsurancecompanyCancelbtn;
	
	public InsuranceCompaniesWePpage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(insurancecompaniestable.getWrappedElement()));
	}
	
	public void clickAddInsuranceCompanyButton() {
		click(addinsurancecompanylink);
	}
	
	public boolean isAddInsuranceCompanyButtonExists() {
		return addinsurancecompanylink.isDisplayed();
	}
	
	public void verifyInsuranceCompaniesTableColumnsAreVisible() {		
		Assert.assertTrue(insurancecompaniestable.isTableColumnExists("Name"));
		Assert.assertTrue(insurancecompaniestable.isTableColumnExists("Address"));
		Assert.assertTrue(insurancecompaniestable.isTableColumnExists("Email"));
		Assert.assertTrue(insurancecompaniestable.isTableColumnExists("Phone"));
		Assert.assertTrue(insurancecompaniestable.isTableColumnExists("Order"));
	}
	
	public String getTableInsuranceCompanyAddress(String insurancecompany) {
		String insurancecompanyaddress = "";
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			insurancecompanyaddress = row.findElement(By.xpath(".//td[4]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");
		return insurancecompanyaddress;
	}
	
	public String getTableInsuranceCompanyEmail(String insurancecompany) {
		String insurancecompanyemail = "";
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			insurancecompanyemail = row.findElement(By.xpath(".//td[5]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");
		return insurancecompanyemail;
	}
	
	public String getTableInsuranceCompanyPhone(String insurancecompany) {
		String insurancecompanyphone = "";
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			insurancecompanyphone = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");
		return insurancecompanyphone;
	}
		
	public WebElement getTableRowWithInsuranceCompany(String insurancecompany) {
		List<WebElement> rows = getInsuranceCompaniesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().contains(insurancecompany)) {
				return row;
			}
		} 
		return null;
	}	
	
	public List<WebElement> getInsuranceCompaniesTableRows() {
		return insurancecompaniestable.getTableRows();
	}
	
	public void clickEditInsuranceCompany(String insurancecompany) {
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");
	}
	
	public void deleteInsuranceCompany(String insurancecompany) {
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");		
	}
	
	public void deleteInsuranceCompanyAndCancelDeleting(String insurancecompany) {
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");		
	}
	
	public boolean isInsuranceCompanyExists(String insurancecompany) {
		boolean exists =  insurancecompaniestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + insurancecompany + "']")).size() > 0;
		return exists;
	}
	
	public void setNewInsuranceCompanyName(String insurancecompany) {
		clearAndType(insurancecompanynamefld, insurancecompany);
	}
	
	public String getNewInsuranceCompanyName() {
		return insurancecompanynamefld.getValue();
	}
	
	public void setNewInsuranceCompanyAddress(String insurancecompanyaddres) {
		clearAndType(insurancecompanyaddressfld, insurancecompanyaddres);
	}
	
	public void setNewInsuranceCompanyEmail(String insurancecompanyemail) {
		clearAndType(insurancecompanyemailfld, insurancecompanyemail);
	}
	
	public void setNewInsuranceCompanyPhone(String insurancecompanyphone) {
		clearAndType(insurancecompanyphonefld, insurancecompanyphone);
	}
	
	public void setNewInsuranceCompanyAccountingID(String insurancecompanyaccountingid) {
		clearAndType(insurancecompanyaccauntigidfld, insurancecompanyaccountingid);
	}
	
	public void setNewInsuranceCompanyAccountingID2(String insurancecompanyaccountingid2) {
		clearAndType(insurancecompanyaccauntigid2fld, insurancecompanyaccountingid2);
	}
	
	public void createNewInsuranceCompany(String insurancecompany) {
		setNewInsuranceCompanyName(insurancecompany);
		clickAddInsuranceCompanyOKButton();
	}
	
	public void clickAddInsuranceCompanyOKButton() {
		clickAndWait(addinsurancecompanyOKbtn);
	}
	
	public void clickAddInsuranceCompanyCancelButton() {
		click(addinsurancecompanyCancelbtn);
	}

}
