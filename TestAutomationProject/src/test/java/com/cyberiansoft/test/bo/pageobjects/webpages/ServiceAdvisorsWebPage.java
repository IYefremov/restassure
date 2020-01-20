package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

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
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Clients"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Full Name"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Email"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Address"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Phone"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Roles"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Accounting ID"));
		Assert.assertTrue(serviceadvisorstable.tableColumnExists("Commissions"));
	}
	
	public String getTableServiceAdvisorFullName(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[4]");
	}
	
	public String getTableServiceAdvisorEmail(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[5]");
	}

    private String getTableServiceAdvisor(String firstname, String lastname, String s) {
        String serviceadvisoremail = "";
        WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
        if (row != null) {
            serviceadvisoremail = row.findElement(By.xpath(s))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
        } else
            Assert.fail("Can't find " + firstname + " " + lastname + " service advisor");
        return serviceadvisoremail;
    }

    public String getTableServiceAdvisorAddress(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[6]");
	}
	
	public String getTableServiceAdvisorPhone(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[7]");
	}
	
	public String getTableServiceAdvisorRoles(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[8]");
	}
	
	public String getTableServiceAdvisorAccountingID(String firstname, String lastname) {
        return getTableServiceAdvisor(firstname, lastname, ".//td[9]");
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
	
	private WebElement getTableRowWithServiceAdvisor(String firstName, String lastName) {
		List<WebElement> rows = getServiceAdvisorsTableRows();
		return rows.stream().filter(row -> {
		    WaitUtilsWebDriver.waitABit(500);
		    return Utils.getText(row).contains(firstName + " " + lastName);
        }).findFirst().orElse(null);
	}
	
	public void setUserSearchCriteria(String _user) {
		clearAndType(searchuserfld, _user);
	}

	public void selectSearchClient(String _client) {
		wait.until(ExpectedConditions.elementToBeClickable(searchclientcbx)).click();
		searchclientcbx.sendKeys(_client);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + _client + "']")));
		driver.findElement(By.xpath("//li[text()='" + _client + "']")).click();
	}
	
	public boolean serviceAdvisorExists(String firstname, String lastname) {
		boolean exists =  serviceadvisorstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + firstname + " " + lastname + "']")).size() > 0;
		return exists;
	}
	
	public void clickEditServiceAdvisor(String firstname, String lastname) {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");
	}
	
	public void deleteServiceAdvisor(String firstname, String lastname) {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");		
	}
	
	public void deleteServiceAdvisorAndCancelDeleting(String firstname, String lastname) {
		WebElement row = getTableRowWithServiceAdvisor(firstname, lastname);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " service advisor");		
	}
	
	public void createNewServiceAdvisor(String email, String firstname, String lastname, String clientname, String role) {
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
