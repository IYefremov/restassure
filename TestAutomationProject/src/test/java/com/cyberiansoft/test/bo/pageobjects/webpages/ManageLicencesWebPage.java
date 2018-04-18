package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
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

public class ManageLicencesWebPage extends WebPageWithPagination {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_licences_ctl00")
	private WebTable managelicensestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_licences_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addmanagelicensebtn;
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'filterer_comboApplications_Input')]")
	private ComboBox dearchlicenceappcmb;
	
	@FindBy(xpath = "//*[contains(@id, 'filterer_comboApplications_DropDown')]")
	private DropDown dearchlicenceappdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;
	
	//New License
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlLicenseApp_Input')]")
	private ComboBox newlicenceappcmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlLicenseApp_DropDown')]")
	private DropDown newlicenceappdd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlLicenceTypes_Input')]")
	private ComboBox newlicencetypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlLicenceTypes_DropDown')]")
	private DropDown newlicencetypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newlicenceOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newlicencecancelbtn;
	
	public ManageLicencesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean searchPanelIsExpanded() {
		wait.until(ExpectedConditions.visibilityOf(searchbtn)).click();
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void selectLicenceSearchApplicationParameter(String licenceapp) {
		selectComboboxValue(dearchlicenceappcmb, dearchlicenceappdd, licenceapp);
	}
	
	public ManageLicencesWebPage clickFindButton() {
		clickAndWait(findbtn);
		waitABit(3000);
		return this;
	}
	
	public void clickAddManageLicenceButton() {
		clickAndWait(addmanagelicensebtn);
		waitABit(2000);
	}
	
	public void selectNewLicenceApplication(String licenceapp) {
		selectComboboxValue(newlicenceappcmb, newlicenceappdd, licenceapp);
		
	}
	
	public void selectNewLicenceType(String licencetype) {
		selectComboboxValue(newlicencetypecmb, newlicencetypedd, licencetype);
	}
	
	public String getNewLicenceType() {
		return newlicencetypecmb.getSelectedValue();
	}
	
	public void clickNewLicenceOKButton() {
		clickAndWait(newlicenceOKbtn);
		waitABit(4000);
	}
	
	public void clickNewLicenceCancelButton() {
		click(newlicencecancelbtn);
	}
	
	public List<WebElement>  getManageLicencesTableRows() {
		return managelicensestable.getTableRows();
	}
	
	public WebElement getTableRowWithLicenceApplication(String licenceapp) {
		List<WebElement> rows = getManageLicencesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().equals(licenceapp)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableLicenceNumber(String licenceapp) {
		String licencenum = "";
		WebElement row = getTableRowWithLicenceApplication(licenceapp);
		if (row != null) {
			licencenum = row.findElement(By.xpath(".//td[4]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + licenceapp + " licence application");
		return licencenum;
	}
	
	public String getTableLicenceType(String licenceapp) {
		String licencetype = "";
		WebElement row = getTableRowWithLicenceApplication(licenceapp);
		if (row != null) {
			licencetype = row.findElement(By.xpath(".//td[5]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + licenceapp + " licence application");
		return licencetype;
	}
	
	
	public boolean licenceApplicationExists(String licenceapp) {
        return managelicensestable
                .getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + licenceapp + "']")).size() > 0;
	}
	
	public void clickEditLicenceApplication(String licenceapp) {
		WebElement row = getTableRowWithLicenceApplication(licenceapp);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + licenceapp + " licence application");
	}
	
	public void deleteLicenceApplication(String licenceapp) {
		WebElement row = getTableRowWithLicenceApplication(licenceapp);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + licenceapp + " licence application");	
		}
	}
	
	public void deleteLicenceApplicationAndCancelDeleting(String licenceapp) {
		WebElement row = getTableRowWithLicenceApplication(licenceapp);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + licenceapp + " licence application");	
		}
	}

}
