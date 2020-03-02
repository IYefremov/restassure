package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class RepairLocationDepartmentsTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable departmentstable;
	
	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement adddepartmentbtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbDepartmentName")
	private TextField newdepartmentnamefld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_tbDescription")
	private TextField newdepartmentdescfld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement newdepartmentOKBtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
	private WebElement newdepartmentcancelBtn;
	
	public RepairLocationDepartmentsTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddDepartmentButton() {
		clickAndWait(adddepartmentbtn);
	}
	
	public void setRepairLocationDepartmentAsdefault(String repairlocationdepartment) {
		WebElement row = getTableRowWithRepairLocationDepartment(repairlocationdepartment);
		if (row != null) {
			click(row.findElement(By.xpath(".//input[@value='Set as Default']")));
			acceptAlertAndWait();
			row = getTableRowWithRepairLocationDepartment(repairlocationdepartment);
			Assert.assertFalse(row.findElements(By.xpath(".//input[@value='Set as Default']")).size() > 0);
			Assert.assertFalse(row.findElements(By.xpath(".//input[@title='Delete']")).size() > 0);
		} else {
            Assert.fail("Can't find " + repairlocationdepartment + " repair location department");
		}
	}

	public List<WebElement>  getRepairLocationDepartmentsTableRows() {
		return departmentstable.getTableRows();
	}
	
	public WebElement getTableRowWithRepairLocationDepartment(String repairlocationdepartment) {
		List<WebElement> rows = getRepairLocationDepartmentsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().equals(repairlocationdepartment)) {
				return row;
			}
		} 
		return null;
	}
	
	public boolean isRepairLocationDepartmentExists(String repairlocationdepartment) {
        return departmentstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + repairlocationdepartment + "']")).size() > 0;
	}
	
	public void deleteRepairLocationDepartment(String repairlocationdepartment) {
		WebElement row = getTableRowWithRepairLocationDepartment(repairlocationdepartment);
		if (row != null) {
			deleteTableRow(row);
		} else {
            Assert.fail("Can't find " + repairlocationdepartment + " repair location department");
		}
	}
	
	public void clickEditRepairLocationDepartment(String repairlocationdepartment) {
		WebElement row = getTableRowWithRepairLocationDepartment(repairlocationdepartment);
		if (row != null) {
			clickEditTableRow(row);
		} else
            Assert.fail("Can't find " + repairlocationdepartment + " repair location department");
	}

	public void setNewRepairLocationDepartmentName(String repairlocationdepartment) {
		clearAndType(newdepartmentnamefld, repairlocationdepartment);
	}
	
	public String getNewRepairLocationDepartmentName() {
		return newdepartmentnamefld.getValue();
	}
	
	public void setNewRepairLocationDepartmentDescription(String repairlocationdepartmentdesc) {
		clearAndType(newdepartmentdescfld, repairlocationdepartmentdesc);
	}
	
	public String getNewRepairLocationDepartmentDescription() {
		return newdepartmentdescfld.getValue();
	}
	
	public void selectNewRepairLocationDepartmentAcceptanceRequired() {
		checkboxSelect("Acceptance Required");
	}
	
	public void clickNewRepairLocationDepartmentOKButton() {
		clickAndWait(newdepartmentOKBtn);
	}
	
	public void clickNewRepairLocationDepartmentCancelButton() {
		click(newdepartmentcancelBtn);
	}
}
