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

import com.cyberiansoft.test.bo.utils.WebElementExt;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(WebElementExt.class)
public class WorkOrdersWebPage extends WebPageWithTimeframeFilter {

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
		Assert.assertTrue(wotable.isTableColumnExists("Order#"));
		Assert.assertTrue(wotable.isTableColumnExists("Invoice#"));
		Assert.assertTrue(wotable.isTableColumnExists("Stock#"));
		Assert.assertTrue(wotable.isTableColumnExists("Customer"));
		Assert.assertTrue(wotable.isTableColumnExists("Vehicle"));
		Assert.assertTrue(wotable.isTableColumnExists("Type / Package"));
		Assert.assertTrue(wotable.isTableColumnExists("Date"));
		Assert.assertTrue(wotable.isTableColumnExists("Technician"));
		Assert.assertTrue(wotable.isTableColumnExists("Advisor"));
		Assert.assertTrue(wotable.isTableColumnExists("Amount"));
		Assert.assertTrue(wotable.isTableColumnExists("Notes"));
		Assert.assertTrue(wotable.isTableColumnExists("Media"));
		Assert.assertTrue(wotable.isTableColumnExists("Action"));
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

	public int getWorkOrdersTableRowCount() throws InterruptedException {
		Thread.sleep(3000);
		return wotable.getTableRowCount();
	}

	public List<WebElement> getWorkOrdersTableRows() {
		return wotable.getTableRows();
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
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

	public String getTableRowWorkOrderNumber(WebElement row) throws InterruptedException {
		Thread.sleep(2000);
		// System.out.println(row.findElement(By.xpath(".//td[" +
		// wotable.getTableColumnIndex("Order#") +
		// "]")).findElements(By.tagName("a")).size());
		return row.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Order#") + "]"))
				.findElements(By.tagName("a")).get(1).getText();
	}

	public WorkOrderInfoTabWebPage clickWorkOrderInTable(String wonumber) throws InterruptedException {
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

	public WebElement getTableRowWithWorkOrder(String wonumber) throws InterruptedException {
		List<WebElement> rows = getWorkOrdersTableRows();
		for (WebElement row : rows) {
			Thread.sleep(3000);
			if (getTableRowWorkOrderNumber(row).equals(wonumber)) {
				return row;
			}
		}
		return null;
	}

	public String getWorkOrderDate(String wonumber) throws InterruptedException {
		String status = null;
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + wotable.getTableColumnIndex("Date") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
		return status;
	}

	public void selectWorkOrderInTheTable(String wonumber) throws InterruptedException {
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			labeledCheckBoxSelect(row.findElement(By.xpath(".//td/input[contains(@id, 'cbAction')]")));
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
	}

	public String getFirstWorkOrderNumberInTheTable() {
		return wotable.getWrappedElement()
				.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Order#") + "]/a")).getText();
	}

	public void createInvoiceFromWorkOrder(String wonumber, String ponum) throws InterruptedException {
		selectWorkOrderInTheTable(wonumber);
		setInvoicePONumber(ponum);
		clickAndWait(createinvoicebtn);
	}

	public void setInvoicePONumber(String ponum) {
		ponumfld.clearAndType(ponum);
	}

	public String getWorkOrderInvoiceNumber(String wonumber) throws InterruptedException {
		String invoicenum = "";
		WebElement row = getTableRowWithWorkOrder(wonumber);
		if (row != null) {
			Thread.sleep(2000);
			invoicenum = row.findElement(By.xpath(".//td[" + wotable.getTableColumnIndex("Invoice#") + "]/a"))
					.getText();
		} else {
			Assert.assertTrue(false, "Can't find " + wonumber + " work order");
		}
		return invoicenum;
	}

}
