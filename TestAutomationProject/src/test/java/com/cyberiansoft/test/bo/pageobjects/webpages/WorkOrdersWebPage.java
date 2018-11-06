package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

//import com.cyberiansoft.test.bo.utils.WebElementExt;
//import lombok.experimental.ExtensionMethod;
//
//@ExtensionMethod(WebElementExt.class)
public class WorkOrdersWebPage extends WebPageWithFilter {

	public final static String WOTABLE_DATE_COLUMN_NAME = "Date";

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable wotable;

	// Search Panel
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_Input")
	private ComboBox searchcustomercmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_DropDown")
	private DropDown searchcustomerdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboServiceGroups_Input")
	private ComboBox searchpackagecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboServiceGroups_DropDown")
	private DropDown searchpackagedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboService_Input")
	private WebElement searchservicecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboEmployee_Input")
	private WebElement searchtechniciancmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_Input")
	private ComboBox searchstatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_DropDown")
	private DropDown searchstatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboOrderType_Input")
	private WebElement searchwotypecmb;

	@FindBy(xpath = "//*[@for='ctl00_ctl00_Content_Main_ctl02_filterer_chkInvoiceFromDevice']")
	private WebElement searchinvoicefromdevicechkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbVIN")
	private TextField searchvinfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbOrderNum")
	private TextField searchordernofld;

	@FindBy(xpath = "//label[contains(@for, 'filterer_chkInvoiceFromDevice')]")
	private WebElement invoicefromdevicechkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbPO")
	private TextField searchponofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbRO")
	private TextField searchronofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_popupButton")
	private WebElement searchfrompopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_popupButton")
	private WebElement searchtopopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;

	////////////// Create Invoice Form
	@FindBy(xpath = "//input[contains(@id, 'Content_Main_tbPoNum')]")
	private TextField ponumfld;

	@FindBy(xpath = "//textarea[contains(@id, 'Content_Main_txtInvoiceDescription')]")
	private WebElement invoicedescfld;

	@FindBy(xpath = "//input[contains(@id, 'Content_Main_btCreateInvoice')]")
	private WebElement createinvoicebtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebElement workOrdersTable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_btCreateInvoice")
	private WebElement createInvoiceToWorkORderButton;

	public WorkOrdersWebPage(WebDriver driver) {
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

	public void verifyWorkOrdersTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(wotable.getWrappedElement()));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chkAllInvoices")));
		// Assert.assertTrue(wotable.findElement(By.id("chkAllInvoices")).isDisplayed());
		Assert.assertTrue(wotable.tableColumnExists("Order#"));
		Assert.assertTrue(wotable.tableColumnExists("Invoice#"));
		Assert.assertTrue(wotable.tableColumnExists("Stock#"));
		Assert.assertTrue(wotable.tableColumnExists("Customer"));
		Assert.assertTrue(wotable.tableColumnExists("Vehicle"));
		Assert.assertTrue(wotable.tableColumnExists("Type / Package"));
		Assert.assertTrue(wotable.tableColumnExists("Date"));
		Assert.assertTrue(wotable.tableColumnExists("Technician"));
		Assert.assertTrue(wotable.tableColumnExists("Advisor"));
		Assert.assertTrue(wotable.tableColumnExists("Amount"));
		Assert.assertTrue(wotable.tableColumnExists("Notes"));
		Assert.assertTrue(wotable.tableColumnExists("Media"));
		Assert.assertTrue(wotable.tableColumnExists("Action"));
	}

	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchcustomercmb.isDisplayed());
		Assert.assertTrue(searchservicecmb.isDisplayed());
		Assert.assertTrue(searchinvoicefromdevicechkbox.isDisplayed());
		Assert.assertTrue(searchponofld.isDisplayed());
		Assert.assertTrue(searchordernofld.isDisplayed());
		Assert.assertTrue(searchwotypecmb.isDisplayed());
		Assert.assertTrue(searchpackagecmb.isDisplayed());
		Assert.assertTrue(searchponofld.isDisplayed());
		Assert.assertTrue(searchtechniciancmb.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
		Assert.assertTrue(searchronofld.isDisplayed());
		Assert.assertTrue(searchvinfld.isDisplayed());
	}

	public WebTable getWorkOrdersTable() {
		return wotable;
	}

	public int getWorkOrdersTableRowCount() {
		waitABit(3000);
		return wotable.getTableRowCount();
	}

	public List<WebElement> getWorkOrdersTableRows() {
		return wotable.getTableRows();
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
		waitABit(4000);
	}

	public boolean workOrdersTableIsVisible() {
		return wotable.isDisplayed();
	}

	public void selectSearchCustomer(String customer) {
		selectComboboxValue(searchcustomercmb, searchcustomerdd, customer);
	}

	public void setSearchVIN(String vin) {
		clearAndType(searchvinfld, vin);
	}

	public void selectSearchStatus(String status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
	}

	public void selectSearchPackage(String _package) {
		selectComboboxValue(searchpackagecmb, searchpackagedd, _package);
	}

	public void setSearchOrderNumber(String orderno) {
		clearAndType(searchordernofld, orderno);
	}

	public void selectInvoiceFromDeviceCheckbox() {
		checkboxSelect(invoicefromdevicechkbox);
	}

	public void unselectInvoiceFromDeviceCheckbox() {
		checkboxUnselect(invoicefromdevicechkbox);
	}

	public boolean isWorkOrderExists(String wordernumber) {
		boolean exists = wotable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + wordernumber + "']"))
				.size() > 0;
		return exists;
	}

	public boolean verifySearchResultsByVIN(String vin) {
		return wotable.getWrappedElement().findElements(By.xpath(".//tr/td[contains(text(), '" + vin + "')]"))
				.size() > 0;
	}

	public String getTableRowWorkOrderNumber(WebElement row) {
		waitABit(2000);
		
		return row.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Order#") + "]"))
				.findElements(By.tagName("a")).get(0).getText();
	}

	public WorkOrderInfoTabWebPage clickWorkOrderInTable(String wonumber) {
		String mainWindowHandle = driver.getWindowHandle();
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			row.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Order#") + "]/div/a")).click();
			waitForNewTab();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
		return PageFactory.initElements(driver, WorkOrderInfoTabWebPage.class);
	}

	public WebElement getTableRowWithWorkOrder(String wonumber) {
		List<WebElement> rows = getWorkOrdersTableRows();
		for (WebElement row : rows) {
			waitABit(3000);
			System.out.println((getTableRowWorkOrderNumber(row)));
			if (getTableRowWorkOrderNumber(row).equals(wonumber)) {
				return row;
			}
		}
		return null;
	}

	public String getWorkOrderDate(String wonumber) {
		String status = null;
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + wotable.getTableColumnIndex("Date") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
		return status;
	}

	public void selectWorkOrderInTheTable(String wonumber) {
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			labeledCheckBoxSelect(row.findElement(By.xpath(".//td/input[contains(@id, 'cbAction')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
	}

	public String getFirstWorkOrderNumberInTheTable() {
//		return wotable.getWrappedElement()
//				.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Order#") + "]/a")).getText();
		return wotable.getWrappedElement().findElement(By.className("entity-link")).getText();
	}

	public void createInvoiceFromWorkOrder(String wonumber, String ponum) {
		selectWorkOrderInTheTable(wonumber);
		setInvoicePONumber(ponum);
		clickAndWait(createinvoicebtn);
	}

	public void setInvoicePONumber(String ponum) {
		ponumfld.clearAndType(ponum);
	}

	public String getWorkOrderInvoiceNumber(String wonumber) {
		String invoicenum = "";
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			waitABit(2000);
			// + wotable.getTableColumnIndex("Invoice#") +
			invoicenum = wait
                    .until(ExpectedConditions.visibilityOfAllElements(row.findElements(By.className("entity-link"))))
                    .get(1)
                    .getText();

		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
		return invoicenum;
	}

	public boolean checkWorkOrdersInfo() {
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")))
				.click();
		waitForLoading();

		try{
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Order#')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Invoice#')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'Stock#')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'RO#')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'Customer')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Vehicle')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Type / Package')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'Date')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Technician')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'Advisor')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'Amount')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th/a[contains(text(), 'PO#')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Notes')]"))));
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//th[contains(text(), 'Media')]"))));
		}
		catch(TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean checkWorkOrdersPagination() {
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgArrPart1")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgNumPart")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgArrPart2")));
		}catch(TimeoutException e){
			return false;
		}
		return true;
	}

	public boolean checkWorkOrdersSearchFields() {
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboArea_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboTeam_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboServiceGroups_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboOrderType_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboService_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboEmployee_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbVIN")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbPO")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbRO")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbStockNo")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbOrderNum")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbAmountFrom")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_tbAmountTo")));
		}catch(TimeoutException e){
			return false;
		}
		return true;
	}

	public boolean checkWorkOrdersSearchResults(String wo) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_Input"))).sendKeys("002 - Test Company");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_comboServiceGroups_Input"))).click();
        selectComboboxValue(searchpackagecmb, searchpackagedd, "Dent Repear Package");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_Input"))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
		.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals("Custom")).findFirst()
		.get().click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")).sendKeys("9/18/2015");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")).sendKeys("9/18/2015");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")))
		.click();
		waitForLoading();
		try{
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'O-000-02008'")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), '" + wo + "')]")));
		}catch(TimeoutException e){
			return false;
		}
		return true;
	}

	public void checkFirstWorkOrderCheckBox() {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_gv_ctl00_ctl04_cbAction")).click();
	}
	
	public void addInvoiceDescription(String text){
		driver.findElement(By.id("ctl00_ctl00_Content_Main_txtInvoiceDescription")).sendKeys(text);
	}
	
	public void clickCreateInvoiceButton() {
		createInvoiceToWorkORderButton.click();
		waitForLoading();
	}

	public void setSearchFromDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")).sendKeys(date);
	}
}
