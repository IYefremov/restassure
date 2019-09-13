package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class InsuranceCompaniesWebPage extends BaseWebPage {

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

	public InsuranceCompaniesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickAddInsuranceCompanyButton() {
		click(addinsurancecompanylink);
	}

	public boolean addInsuranceCompanyButtonExists() {
		return addinsurancecompanylink.isDisplayed();
	}

	public void verifyInsuranceCompaniesDoNotExist(String insurancecompany, String insurancecompanyedited) {
		while (insuranceCompanyExists(insurancecompany)) {
			deleteInsuranceCompany(insurancecompany);
		}
		while (insuranceCompanyExists(insurancecompanyedited)) {
			deleteInsuranceCompany(insurancecompanyedited);
		}
	}

	public void verifyInsuranceCompaniesTableColumnsAreVisible() {
		Assert.assertTrue(insurancecompaniestable.tableColumnExists("Name"));
		Assert.assertTrue(insurancecompaniestable.tableColumnExists("Address"));
		Assert.assertTrue(insurancecompaniestable.tableColumnExists("Email"));
		Assert.assertTrue(insurancecompaniestable.tableColumnExists("Phone"));
		Assert.assertTrue(insurancecompaniestable.tableColumnExists("Order"));
	}

	public String getTableInsuranceCompanyAddress(String insuranceCompany) {
		return getInsurance(insuranceCompany, ".//td[4]");
	}

	public String getTableInsuranceCompanyEmail(String insuranceCompany) {
		return getInsurance(insuranceCompany, ".//td[5]");
	}

	public String getTableInsuranceCompanyPhone(String insuranceCompany) {
		return getInsurance(insuranceCompany, ".//td[6]");
	}

	private String getInsurance(String insuranceCompany, String cellLocator) {
		String insurancecompanyphone = "";
		WebElement row = getTableRowWithInsuranceCompany(insuranceCompany);
		if (row != null) {
			insurancecompanyphone = row.findElement(By.xpath(cellLocator))
					.getText()
					.replaceAll("\\u00A0", "")
					.trim();
		} else
			Assert.fail("Can't find " + insuranceCompany + " insurance company");
		return insurancecompanyphone;
	}

	private WebElement getTableRowWithInsuranceCompany(String insurancecompany) {
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
		waitABit(3000);
		WebElement row = getTableRowWithInsuranceCompany(insurancecompany);
		if (row != null) {
			clickEditTableRow(row);
		} else
			Assert.assertTrue(false, "Can't find " + insurancecompany + " insurance company");
		waitABit(1000);
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

	public boolean insuranceCompanyExists(String insurancecompany) {
		boolean exists = insurancecompaniestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + insurancecompany + "']")).size() > 0;
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
		waitABit(2000);
	}

	public void clickAddInsuranceCompanyCancelButton() {
		click(addinsurancecompanyCancelbtn);
	}

}
