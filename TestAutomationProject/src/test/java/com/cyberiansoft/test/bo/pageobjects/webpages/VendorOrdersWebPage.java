package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class VendorOrdersWebPage extends WebPageWithFilter {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable vendororderstable;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbVIN")
	private TextField searchvinfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_Input")
	private ComboBox searchlocationcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_DropDown")
	private DropDown searchlocationdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboTeams_Input")
	private ComboBox searchvendorcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboTeams_DropDown")
	private DropDown searchvendordd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_Input")
	private TextField searcustomercmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_DropDown")
	private DropDown searcustomerdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboEmployees_Input")
	private TextField searchemployeecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboEmployees_DropDown")
	private DropDown searchemployeedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbStockNo")
	private TextField searchstocknofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbRONo")
	private TextField searchronofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboServicesStatus_Input")
	private ComboBox searchservicestatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboServicesStatus_DropDown")
	private DropDown searchservicestatusdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbWO")
	private TextField searchwonofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;

    @FindBy(xpath = "//input[contains(@id, 'filterer_dpFrom_dateInput') and @type='text']")
    private TextField searchdatefromfld;

    @FindBy(xpath = "//input[contains(@id, 'filterer_dpTo_dateInput') and @type='text']")
    private TextField searchdatetofld;

	public VendorOrdersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public boolean searchPanelIsVisible() {
		return searchvinfld.isDisplayed();
	}
	
	public boolean repairOrdersTableIsVisible() {
		return vendororderstable.isDisplayed();
	}
	
	public int getRepairOrdersTableRowCount() {
		waitABit(4000);
		return vendororderstable.getTableRowCount();
	}
	
	public void selectSearchLocation(String location) {
		selectComboboxValue(searchlocationcmb, searchlocationdd, location);
	}
	
	public void selectSearchVendor(String vendor) { 
		selectComboboxValue(searchvendorcmb, searchvendordd, vendor);
	}
	
	public void selectSearchServiceStatus(String servicestatus) { 
		selectComboboxValue(searchservicestatuscmb, searchservicestatusdd, servicestatus);
	}
	
	public void setSearchVIN(String vin) { 
		clearAndType(searchvinfld, vin);
	}
	
	public void selectSearchCustomer(String customer)  { 
		selectComboboxValueWithTyping(searcustomercmb, searcustomerdd, customer);
	}
	
	public void selectSearchEmployee(String employee) { 
		selectComboboxValueWithTyping(searchemployeecmb, searchemployeedd, employee);
	}
	
	public void setSearchRoNumber(String rono) { 
		clearAndType(searchronofld, rono);
	}
	
	public void setSearchWorkorderNumber(String wono) { 
		clearAndType(searchwonofld, wono);
	}
	
	public void clickFindButton() {
		clickAndWait(findbtn);
	}
	
	public void verifyVendorOrdersTableColumnsAreVisible() {
		Assert.assertTrue(vendororderstable.tableColumnExists("Services"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Order#"));
		Assert.assertTrue(vendororderstable.tableColumnExists("!"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Flag"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Team/Vendor"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Customer"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Vehicle"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Stock# / RO#"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Amount"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Retail Amount"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Start Date"));


		Assert.assertTrue(vendororderstable.tableColumnExists("Target Date"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Active Phase"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Days In Process"));
		Assert.assertTrue(vendororderstable.tableColumnExists("Completed (%)"));
	}
	
	public List<WebElement>  getVendorOrdersTableRows() {
		return vendororderstable.getTableRows();
	}
	
	public WebElement getTableRowWithVendorOrder(String orderno) {
		List<WebElement> rows = getVendorOrdersTableRows();		
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]/a")).getText().equals(orderno)) {
				return row;
			}
		}
		return null;
	}
	
	public boolean vendorOrderExists(String orderno) {
        return vendororderstable.getWrappedElement().findElements(By.xpath(".//tr/td/a[text()='" + orderno + "']")).size() > 0;
	}
	
	public String getTeamVendorForVendorOrder(String orderno) {
		String teamvendor = "";
		WebElement row = getTableRowWithVendorOrder(orderno);
		if (row != null) {
			teamvendor = row.findElement(By.xpath(".//td[5]")).getText();
		} else {
            Assert.fail("Can't find " + orderno + " vendor order");
		}
		return teamvendor;
	}
	
	public String getCustomerForVendorOrder(String orderno) {
		String _customer = "";
		WebElement row = getTableRowWithVendorOrder(orderno);
		if (row != null) {
			_customer = row.findElement(By.xpath(".//td[6]")).getText();
		} else {
            Assert.fail("Can't find " + orderno + " vendor order");
		}
		return _customer;
	}
	
	public void openOrderNoInformationWindow(String orderno) {
		WebElement row = getTableRowWithVendorOrder(orderno);
		if (row != null) {
			row.findElement(By.xpath(".//td[2]/a")).click();
		} else {
            Assert.fail("Can't find " + orderno + " vendor order");
		}
	}
	
	public void openOrderNoInformationWindowAndVerifyContent(String orderno, String vin, String customer, String employee) throws InterruptedException {
		openOrderNoInformationWindow(orderno);
		waitForNewTab();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through your windows
		while (it.hasNext()) {
			String parent = it.next();
			String newwin = it.next();
			driver.switchTo().window(newwin);
		Thread.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + vin + "']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + customer + "']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + employee + "']")).isDisplayed());
			// perform actions on new window
			driver.close();
			driver.switchTo().window(parent);
		//Thread.sleep(2000);
		}
	}
	
	public void clickServicesByOrderNo(String orderno) {
		WebElement row = getTableRowWithVendorOrder(orderno);
		if (row != null) {
			row.findElement(By.xpath(".//td/a[text()='Services']")).click();
		} else {
            Assert.fail("Can't find " + orderno + " vendor order");
		}
	}
	
	public void openServicesInformationByOrderNoWindowAndVerifyContent(String orderno, String vin, String customer) {
		clickServicesByOrderNo(orderno);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td/span[text()='" + orderno + "']")));
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/span[text()='" + vin + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/span[text()='" + customer + "']")).isDisplayed());
		driver.navigate().back();
	}

    public void setSearchFromDate(String dateformat) {
        clearAndType(searchdatefromfld, dateformat);
    }

    public void setSearchToDate(String dateformat) {
        clearAndType(searchdatetofld, dateformat);
    }
}
