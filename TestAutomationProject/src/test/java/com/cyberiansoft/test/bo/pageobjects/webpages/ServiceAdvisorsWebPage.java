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

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ServiceAdvisorsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable serviceadvisorstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement serviceadvisoraddbtn;
	
	//New Service Advisor
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbEmail")
	private TextField serviceadvisormailfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPassword")
	private TextField serviceadvisorpswfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbConfirmPassword")
	private TextField serviceadvisorconfirmpswfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbFirstName")
	private TextField serviceadvisorfirstnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbLastName")
	private TextField serviceadvisorlastnamefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlClients_Input")
	private TextField serviceadvisorcustomercmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlClients_DropDown")
	private DropDown serviceadvisorcustomerdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCompany")
	private TextField serviceadvisorcompanyfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAddress")
	private TextField serviceadvisoraddressfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCity")
	private TextField serviceadvisorcityfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlCountry_Input")
	private ComboBox serviceadvisorcountrycmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlCountry_DropDown")
	private DropDown serviceadvisorcountrydd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlState_Input")
	private ComboBox serviceadvisorstatecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_countryState_ddlState_DropDown")
	private DropDown serviceadvisorstatedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbZip")
	private TextField serviceadvisorzipfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbPhone")
	private TextField serviceadvisorphonefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAccountingID")
	private TextField serviceadvisoraccidfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement serviceadvisorOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement serviceadvisorcancelbtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")
	private WebElement searchclientcbx;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
	private TextField searchuserfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@title='Delete']")
	private WebElement deletemarker;

	public ServiceAdvisorsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(serviceadvisorstable.getWrappedElement()));
	}
	
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyServiceAdvisorsTableColumnsAreVisible() {	
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Clients"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Full Name"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Email"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Address"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Phone"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Roles"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Accounting ID"));
		Assert.assertTrue(serviceadvisorstable.isTableColumnExists("Commissions"));
	}
	
	public String getTableServiceAdvisorFullName(String firstname, String lastname) {
		String serviceadvisorfullname = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			serviceadvisorfullname = row.findElement(By.xpath(".//td[4]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return serviceadvisorfullname;
	}
	
	public String getTableServiceAdvisorEmail(String firstname, String lastname) {
		String serviceadvisoremail = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			serviceadvisoremail = row.findElement(By.xpath(".//td[5]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return serviceadvisoremail;
	}
	
	public String getTableServiceAdvisorAddress(String firstname, String lastname) {
		String serviceadvisoraddress = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			serviceadvisoraddress = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return serviceadvisoraddress;
	}
	
	public String getTableServiceAdvisorPhone(String firstname, String lastname) {
		String serviceadvisorphone = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			serviceadvisorphone = row.findElement(By.xpath(".//td[7]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return serviceadvisorphone;
	}
	
	public String getTableServiceAdvisorRoles(String firstname, String lastname) {
		String serviceadvisorroles = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			serviceadvisorroles = row.findElement(By.xpath(".//td[8]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return serviceadvisorroles;
	}
	
	public String getTableServiceAdvisorAccountingID(String firstname, String lastname) {
		String teamtimesheettype = "";
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			teamtimesheettype = row.findElement(By.xpath(".//td[9]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
		return teamtimesheettype;
	}
	
	public void clickServiceAdvisorAddButton() {
		click(serviceadvisoraddbtn);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public int getServiceAdvisorsTableRowsCount() {
		return getServiceAdvisorsTableRows().size();
	}
	
	public List<WebElement>  getServiceAdvisorsTableRows() {
		return serviceadvisorstable.getTableRows();
	}
	
	public WebElement getTableRowWithServiceAdvisor(String firstname, String lastname) {
		List<WebElement> rows = getServiceAdvisorsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[4]")).getText().contains(firstname + " " + lastname)) {
				return row;
			}
		} 
		return null;
	}
	
	public void setUserSearchCriteria(String _user) {
		clearAndType(searchuserfld, _user);
	}

	public void selectSearchClient(String _client) throws InterruptedException  { 
		//Thread.sleep(1000);
		searchclientcbx.click();
		searchclientcbx.sendKeys(_client);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + _client + "']")));
		driver.findElement(By.xpath("//li[text()='" + _client + "']")).click();
	}
	
	public boolean isServiceAdvisorExists(String firstname, String lastname) {
		boolean exists =  serviceadvisorstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + firstname + " " + lastname + "']")).size() > 0;
		return exists;
	}
	
	public void clickEditServiceAdvisor(String firstname, String lastname) throws InterruptedException {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
	}
	
	public void deleteServiceAdvisor(String firstname, String lastname) throws InterruptedException {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");		
	}
	
	public void deleteServiceAdvisorAndCancelDeleting(String firstname, String lastname) throws InterruptedException {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");		
	}
	
	public void createNewServiceAdvisor(String email, String firstname, String lastname, String clientname, String role) throws InterruptedException {
		setNewServiceAdvisorEmail(email);
		setNewServiceAdvisorFirstName(firstname);
		setNewServiceAdvisorLastName(lastname);
		selectServiceAdvisorClient(clientname);
		selectNewServiceAdvisorRole(role);
		clickNewServiceAdvisorOKButton();
	}
	public void setNewServiceAdvisorEmail(String email) {
		clearAndType(serviceadvisormailfld, email);
	}
	
	public void setNewServiceAdvisorLastName(String lastname) {
		clearAndType(serviceadvisorlastnamefld, lastname);
	}
	
	public void setNewServiceAdvisorFirstName(String firstname) {
		clearAndType(serviceadvisorfirstnamefld, firstname);
	}
	
	public void selectNewServiceAdvisorRole(String role) {
		driver.findElement(By.xpath("//label[text()='" + role  + "']")).click();
	}
	
	public void selectServiceAdvisorClient(String clientname) { 
		selectComboboxValueWithTyping(serviceadvisorcustomercmb, serviceadvisorcustomerdd, clientname);
	}
	
	public void setNewServiceAdvisorCompany(String company) {
		clearAndType(serviceadvisorcompanyfld, company);
	}
	
	public void setNewServiceAdvisorAddress(String address) {
		clearAndType(serviceadvisoraddressfld, address);
	}
	
	public void setNewServiceAdvisorCity(String city) {
		clearAndType(serviceadvisorcityfld, city);
	}
	
	public void setNewServiceAdvisorZip(String zip) {
		clearAndType(serviceadvisorzipfld, zip);
	}
	
	public void setNewServiceAdvisorPhone(String phone) {
		clearAndType(serviceadvisorphonefld, phone);
	}
	
	public void setNewServiceAdvisorAccountingID(String accid) {
		clearAndType(serviceadvisoraccidfld, accid);
	}
	
	public void selectNewServiceAdvisorCountry(String country) {
		selectComboboxValueAndWait(serviceadvisorcountrycmb, serviceadvisorcountrydd, country);
	}
	
	public void selectNewServiceAdvisorState(String state) {
		selectComboboxValue(serviceadvisorstatecmb, serviceadvisorstatedd, state);
	}

	public void clickNewServiceAdvisorOKButton() {
		clickAndWait(serviceadvisorOKbtn);
	}
	
	public void clickNewServiceAdvisorCancelButton() {
		click(serviceadvisorcancelbtn);
	}
}
