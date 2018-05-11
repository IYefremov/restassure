package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

//@ExtensionMethod(WebElementExt.class)
public class InvoicesWebPage extends WebPageWithFilter {

	public final static String WOTABLE_DATE_COLUMN_NAME = "Date";

	// Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlDevices_DropDown")
	private DropDown searchdevicesinputdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")
	private TextField searchclientscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_DropDown")
	private DropDown searchclientsdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tRONo")
	private TextField searchwonofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbPo")
	private TextField searchponofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbStockNo")
	private TextField searchwostockfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboOrderType_Input")
	private WebElement searchwotypecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboServiceGroups_Input")
	private WebElement searchpackagecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlInsurance_Input")
	private WebElement searchinsurancecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboEmployee_Input")
	private WebElement searchtechniciancmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")
	private ComboBox searchstatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_DropDown")
	private DropDown searchstatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlPORequired_Input")
	private WebElement searchbillingcmb;

	@FindBy(xpath = "//*[@for='ctl00_ctl00_Content_Main_ctl04_filterer_cbArchived']")
	private WebElement searcharchivedchkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbVIN")
	private TextField searchvinfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_txtInvoiceNo")
	private TextField searchinvoicenofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbClaimNum")
	private TextField searchclaimofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountFrom")
	private TextField searchamountfromfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountTo")
	private TextField searchamounttofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_popupButton")
	private WebElement searchfrompopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_popupButton")
	private WebElement searchtopopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_grdInvoices_ctl00")
	private WebTable invoicestable;

	@FindBy(className = "rmBottomArrow")
	private WebElement botArrow;

	@FindBy(className = "updateProcess")
	private WebElement updateProcess;

	@FindBy(linkText = "Select")
	private WebElement selectBTN;

	@FindBy(linkText = "Mark as Paid")
	private WebElement markAsPaidOption;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_pContent")
	private WebElement paymentNote;

	@FindBy(className = "rmVertical")
	private WebElement ivoiceOptions;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCashNotes")
	private WebElement paymentTextField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnCashPay")
	private WebElement markAsPaidBTN;

	@FindBy(xpath = "//tr[contains(@class, 'order-row border-row custom-order-row')]")
	private WebElement editableRow;

	@FindBy(xpath = "//div[contains(@title, 'Save')]")
    private WebElement saveVehicleInfoBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_grdInvoices_ctl00_ctl04_Button1")
	private WebElement voidBTN;

	@FindBy(xpath = "//tr[@id='ctl00_ctl00_Content_Main_grdInvoices_ctl00__0']/td[contains(text(), 'Paid')]")
    private WebElement firstInvoiceWithPaidStatus;

	@FindBy(xpath = "//tr[@id='ctl00_ctl00_Content_Main_grdInvoices_ctl00__0']//a[@class='entity-link']")
    private WebElement firstInvoiceName;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnPopupCancel")
    private WebElement closeButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnChangeInvoiceNumber")
    private WebElement changeButton;

	@FindBy(xpath = "//div[@class='rmSlide' and contains(@style, 'block')]")
    private WebElement slideDisplayed;

	@FindBy(xpath = "//a[@class='rmLink rmRootLink']")
    private List<WebElement> selectButtons;

	// @FindBy(className = "rfdSkinnedButton")
	// private WebElement voidBTN;

	public InvoicesWebPage(WebDriver driver) {
		super(driver);
		// driver.findElement(By.className("header")).shouldHave("MainPage");
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void verifyInvoicesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(invoicestable.getWrappedElement()));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chkAllInvoices")));
		Assert.assertTrue(invoicestable.tableColumnExists("Invoice #"));
		Assert.assertTrue(invoicestable.tableColumnExists("Date"));
		Assert.assertTrue(invoicestable.tableColumnExists("Status"));
		Assert.assertTrue(invoicestable.tableColumnExists("Reason"));
		Assert.assertTrue(invoicestable.tableColumnExists("Amount"));
		Assert.assertTrue(invoicestable.tableColumnExists("Paid"));
		Assert.assertTrue(invoicestable.tableColumnExists("Customer"));
		Assert.assertTrue(invoicestable.tableColumnExists("PO#"));
		Assert.assertTrue(invoicestable.tableColumnExists("Notes"));
		Assert.assertTrue(invoicestable.tableColumnExists("Media"));
		Assert.assertTrue(invoicestable.tableColumnExists("Duplicates"));
		Assert.assertTrue(invoicestable.tableColumnExists("Action"));
	}

	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchclientscmb.isDisplayed());
		Assert.assertTrue(searchwonofld.isDisplayed());
		Assert.assertTrue(searchponofld.isDisplayed());
		Assert.assertTrue(searchwostockfld.isDisplayed());
		Assert.assertTrue(searchwotypecmb.isDisplayed());
		Assert.assertTrue(searchpackagecmb.isDisplayed());
		Assert.assertTrue(searchinsurancecmb.isDisplayed());
		Assert.assertTrue(searchtechniciancmb.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
		Assert.assertTrue(searchbillingcmb.isDisplayed());
		Assert.assertTrue(searcharchivedchkbox.isDisplayed());
		Assert.assertTrue(searchvinfld.isDisplayed());
		Assert.assertTrue(searchinvoicenofld.isDisplayed());
		Assert.assertTrue(searchclaimofld.isDisplayed());
		Assert.assertTrue(searchamountfromfld.isDisplayed());
		Assert.assertTrue(searchamounttofld.isDisplayed());
	}

	public WebTable getInvoicesTable() {
		return invoicestable;
	}

	public int getInvoicesTableRowCount() {
	    waitForLoading();
		return getInvoicesTableRows().size();
	}

	public List<WebElement> getInvoicesTableRows() {
		return invoicestable.getTableRows();
	}

	public InvoicesWebPage clickFindButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(findbtn)).click();
        waitForLoading();
        waitABit(2000);
        return this;
	}

	public boolean invoicesTableIsVisible() {
		wait.until(ExpectedConditions.visibilityOf(invoicestable.getWrappedElement()));
		return invoicestable.isDisplayed();
	}

	public void selectSearchCustomer(String customer) {
		searchclientscmb.click();
		searchclientscmb.clearAndType(customer);
		wait.until(ExpectedConditions.visibilityOf(searchclientsdd.getWrappedElement()));
		waitABit(1000);
		searchclientsdd.selectByVisibleText(customer);
	}

	public void setSearchPONumber(String ponumber) {
		clearAndType(searchponofld, ponumber);
	}

	public void setSearchFromDate(String date, String month, String year) {
		wait.until(ExpectedConditions.elementToBeClickable(searchfrompopupbtn)).click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//tr/td/a[text()='" + date + "']")).click();
	}

	public void setSearchFromDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date) {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date, String month, String year) {
		wait.until(ExpectedConditions.elementToBeClickable(searchtopopupbtn)).click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		// Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}

	public void selectSearchStatus(WebConstants.InvoiceStatuses status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status.getName());
	}

	public void setSearchAmountFrom(String amount) {
		clearAndType(searchamountfromfld, amount);
	}

	public void setSearchAmountTo(String amount) {
		clearAndType(searchamounttofld, amount);
	}

	public void setSearchInvoiceNumber(String invoicenum) {
		waitUntilElementIsClickable(searchinvoicenofld.getWrappedElement());
		clearAndType(searchinvoicenofld, invoicenum);
	}

	public boolean isInvoiceNumberExists(String invoicenum) {
		// By.xpath(".//tr/td/a[text()='" + invoicenum + "']")
		boolean exists = invoicestable.getWrappedElement().findElements(By.partialLinkText(invoicenum)).size() > 0;
		return exists;
	}

	public String getInvoiceStatus(String invoicenumber) {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row
					.findElement(
							By.xpath("./td[" + invoicestable.getTableColumnIndex("Status") + "]/table/tbody/tr/td"))
					.getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public String getInvoicePONumber(String invoicenumber) {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + invoicestable.getTableColumnIndex("PO#") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public String getInvoicePOPaidValue(String invoicenumber) {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + invoicestable.getTableColumnIndex("Paid") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public void changeInvoiceStatus(String invoicenumber, String invoicestatus) {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			row.findElement(By.xpath(".//a[contains(@id, 'comboStatus_Arrow')]")).click();
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//div[contains(@id, 'comboStatus_DropDown')]"))));
			driver.findElement(By.xpath("//div[contains(@id, 'comboStatus_DropDown')]"))
					.findElement(By.xpath(".//ul/li/a[text()='" + invoicestatus + "']")).click();
			wait.until(ExpectedConditions.alertIsPresent()).accept();
			waitUntilPageReloaded();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
	}

	public WebElement getTableRowWithInvoiceNumber(String invoicenumber) {
		waitABit(4000);
		List<WebElement> rows = getInvoicesTableRows();
		waitABit(4000);
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + invoicestable.getTableColumnIndex("Invoice #") + "]/a")).getText()
					.equals(invoicenumber)) {
				return row;
			}
		}
		return null;
	}

	public WebElement clickSelectButtonForInvoice(String invoicenumber) {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath(".//span[text()='Select']"))).click().build().perform();
			// waitABit(300);
			// act.click(row.findElement(By.xpath(".//span[text()='Select']"))).build().perform();
		}
		return row;
	}

	public void clickInvoiceSelectExpandableMenu(String invoicenumber, String menuitem) {

		WebElement row = clickSelectButtonForInvoice(invoicenumber);
		waitABit(1000);
		if (row != null) {
			wait.until(ExpectedConditions.visibilityOf(row.findElement(By.xpath(".//div[@class='rmSlide']"))));
			Actions act = new Actions(driver);
			if (menuitem.equals("Edit") || menuitem.equals("Print preview (server)")) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + menuitem + "')]"))).click();
			} else {
				while (true) {
					try {
						act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmBottomArrow")))
								.perform();
					} catch (Exception e) {
					}
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + menuitem + "')]")))
								.click();
						break;
					} catch (Exception e) {
					}
				}
			}

			// if
			// (!getTableRowWithInvoiceNumber(invoicenumber).findElement(By.xpath(".//span[text()='"
			// + menuitem + "']"))
			// .isDisplayed()) {
			// act.moveToElement(getTableRowWithInvoiceNumber(invoicenumber)
			// .findElement(By.xpath(".//a[@class='rmBottomArrow']"))).perform();
			// }
			// wait.until(ExpectedConditions.elementToBeClickable((WebElement)
			// getTableRowWithInvoiceNumber(invoicenumber)
			// .findElement(By.xpath(".//span[text()='" + menuitem + "']"))));
			// act.click(getTableRowWithInvoiceNumber(invoicenumber)
			// .findElement(By.xpath(".//span[text()='" + menuitem +
			// "']"))).perform();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
	}

	public boolean sendInvoiceEmail(String invoicenumber, String email) throws InterruptedException {
		boolean emailsended = false;
		clickInvoiceSelectExpandableMenu(invoicenumber, "Send Email");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).sendKeys(email);
		clickAndWait(driver.findElement(By.id("ctl00_ctl00_Content_Main_btnSendEmail")));
		emailsended = true;
		return emailsended;
	}

	public SendInvoiceCustomEmailTabWebPage clickSendCustomEmail(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();

		clickInvoiceSelectExpandableMenu(invoicenumber, "Send Custom Email");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, SendInvoiceCustomEmailTabWebPage.class);
	}

	public InvoiceEmailActivityTabWebPage clickEmailActivity(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Email Activity");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEmailActivityTabWebPage.class);
	}

	public InvoiceEditTabWebPage clickEditInvoice(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		// System.out.println(mainWindowHandle);
		clickInvoiceSelectExpandableMenu(invoicenumber, "Edit");
		// waitForNewTab();
		// Set<String> windows = driver.getWindowHandles();
		// for (String window : windows) {
		// if (!window.equals(mainWindowHandle))
		// driver.switchTo().window(window);
		// }

		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEditTabWebPage.class);
	}

	public InvoicePaymentsTabWebPage clickInvoicePayments(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Payments");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoicePaymentsTabWebPage.class);
	}

	public void clickInvoicePrintPreview(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Print preview (server)");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}

	public String getPrintPreviewServiceListValue(String servicename) {
		WebElement parentrow = driver
				.findElement(By.xpath("//table/tbody/tr/td/div[text()='" + servicename + "']/../.."));
		return parentrow.findElement(By.xpath("./td[2]/div/div/div/span")).getText();
	}

	public String getPrintPreviewServiceNetValue(String servicename) {
		WebElement parentrow = driver
				.findElement(By.xpath("//table/tbody/tr/td/div[text()='" + servicename + "']/../.."));
		return parentrow.findElement(By.xpath("./td[3]/div")).getText();
	}

	public String getPrintPreviewTestMartrixLaborServiceListValue(String servicename) {
		WebElement parentrow = driver.findElement(
				By.xpath("//table/tbody/tr/td/div/table/tbody/tr/td[text()='" + servicename + "']/../../../../../.."));
		return parentrow.findElement(By.xpath("./td[2]/div/table/tbody/tr/td")).getText();
	}

	public String getPrintPreviewTestMartrixLaborServiceNetValue(String servicename) {
		WebElement parentrow = driver.findElement(
				By.xpath("//table/tbody/tr/td/div/table/tbody/tr/td[text()='" + servicename + "']/../../../../../.."));
		return parentrow.findElement(By.xpath("./td[3]/div/table/tbody/tr/td")).getText();
	}

	public void clickInvoiceInternalTechInfo(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Internal Tech. Info");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}

	public void archiveInvoiceByNumber(String invoicenumber) {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			checkboxSelect(row.findElement(By.xpath(".//td/input[@type='checkbox']")));
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		driver.findElement(By.xpath("//span[@class='rtbText' and text()='Archive']")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
		waitUntilPageReloaded();
	}

	public WebElement getTechInfoServicesTable() {
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td/div[text()='SERVICES']/../../../.."));
	}

	public String getTechInfoServicesTableServiceValue(String columnname, String servicename) {
		int icolumn = getTechInfoServicesTableColumnIndex(columnname);
		int irow = getTechInfoServicesTableServiceRowIndex(servicename);
		return getTechInfoServicesTable().findElement(By.xpath("./tbody/tr[" + irow + "]/td[" + icolumn + "]"))
				.getText();
	}

	public int getTechInfoServicesTableColumnIndex(String columnname) {
		int iterator = 0;
		int icolumn = -1;
		List<WebElement> columns = getTechInfoServicesTable().findElements(By.xpath("./tbody/tr[4]/td"));
		for (WebElement column : columns) {
			++iterator;
			if (column.getText().contains(columnname)) {
				icolumn = iterator;
				break;
			}
		}
		return icolumn;
	}

	public int getTechInfoServicesTableServiceRowIndex(String servicename) {
		int iterator = 0;
		int irow = -1;
		List<WebElement> rows = getTechInfoServicesTable().findElements(By.xpath("./tbody/tr"));
		for (WebElement row : rows) {
			++iterator;
			if (row.getText().contains(servicename)) {
				irow = iterator;
				break;
			}
		}
		return irow;
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public String getFirstInvoiceName() {
	    try {
            return wait.until(ExpectedConditions.visibilityOf(firstInvoiceName)).getText();
        } catch (TimeoutException e) {
	        Assert.fail("The first invoice name has not been displayed!", e);
        }
        return "";
    }

	public String selectActionForFirstInvoice(String string, boolean switchArrow) {
		String mainWindow = driver.getWindowHandle();
		scrollWindowDown(300);
		Actions act = new Actions(driver);
		
		if (string.equals("Edit") || string.equals("Print preview (server)")) {
			act.moveToElement(selectBTN).click().build().perform();
			ivoiceOptions.findElement(By.linkText(string)).click();
			Set<String> frames = driver.getWindowHandles();
			waitABit(10000);
			frames.remove(mainWindow);
			driver.switchTo().window(frames.iterator().next());
			return frames.iterator().next();
		} else if (string.equals("Pay")) {
			 act.moveToElement(selectBTN).click().build().perform();
			 try {
				ivoiceOptions.findElement(By.linkText(string)).click();
				waitABit(1000);
			 } catch(Exception e) {
			     act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmBottomArrow"))).perform();
			     ivoiceOptions.findElement(By.linkText(string)).click();
			 }
			 waitForLoading();
                return mainWindow;
			} else if (string.equals("Mark as Paid") || string.equals("Mark as Unpaid")) {
				act.moveToElement(selectBTN).click().build().perform();
				try{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rmVertical"))).findElement(By.linkText(string)).click();
				}catch(Exception e){
					try {
					act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmBottomArrow"))).perform();
					} catch(Exception ignored){}
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rmVertical"))).findElement(By.linkText(string)).click();
				}
            waitForLoading();

				if(string.equals("Mark as Paid")){
				wait.until(ExpectedConditions.visibilityOf(paymentNote));
				paymentTextField.sendKeys("test");
				markAsPaidBTN.click();
				driver.switchTo().alert().accept();
				waitForLoading();
				driver.navigate().refresh();
				}
				return mainWindow;
			}
			else if((string.equals("Email Activity") && !switchArrow)){
				act.moveToElement(selectBTN).click().build().perform();
				try{
				act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmBottomArrow"))).perform();
				}catch(Exception e){}
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//span[contains(text(), '" + string + "')]")));
				waitABit(1000);
				wait.until(ExpectedConditions
						.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(), '" + string + "')]")))).click();
		
					Set frames = driver.getWindowHandles();
					waitABit(10000);
					frames.remove(mainWindow);
					driver.switchTo().window((String) frames.iterator().next());
					return (String) frames.iterator().next();
				
			}
		 else if (string.equals("Download JSON")) {
		    selectButtons.get(0).click();
//		    act.moveToElement(selectButtons.get(0)).click().build().perform();
            try {
                wait.until(ExpectedConditions.visibilityOf(slideDisplayed));
                act.moveToElement(wait.until(ExpectedConditions
                        .visibilityOf(slideDisplayed
                                .findElement((By.xpath("//span[contains(text(), '" + string + "')]"))))))
                        .click().build().perform();
            } catch (Exception e) {
                Assert.fail("The \"Download JSON\" button has not been displayed.", e);
            }
			return mainWindow;
		} else if ((string.equals("Send Email") && switchArrow) || (string.equals("Send Custom Email") && switchArrow)) {
			act.moveToElement(selectBTN).click().build().perform();
			waitABit(1000);
			try {
				act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmTopArrow"))).perform();
			} catch (Exception e) {
			}
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + string + "')]")));
				waitABit(500);
				driver.findElement(By.xpath("//span[contains(text(), '" + string + "')]")).click();
			} catch (TimeoutException ignored) {}
			if (string.equals("Send Email"))
				return mainWindow;
			else {
				Set frames = driver.getWindowHandles();
				waitABit(10000);
				frames.remove(mainWindow);
				driver.switchTo().window((String) frames.iterator().next());
				return (String) frames.iterator().next();
			}
		} else {
			act.moveToElement(selectBTN).click().build().perform();
			waitABit(2000);
			try {
				act.moveToElement(selectBTN).moveToElement(driver.findElement(By.className("rmBottomArrow"))).perform();
			} catch (Exception ignored) {}
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + string + "')]"))).click();
//				driver.findElement(By.xpath("//span[contains(text(), '" + string + "')]")).click();
			} catch (TimeoutException e) {
			}
			waitABit(3000);
			Set<String> windows = driver.getWindowHandles();
			if (windows.size() > 1) {
				for (String window : windows) {
					if (!window.equals(mainWindow))
						return window;
				}
			}
			return mainWindow;
		}
	}

	public void scrollWindowDown(int pix) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + pix + ")", "");
	}

	public boolean isFirstInvoiceMarkedAsPaid() {
		try {
			Actions actioons = new Actions(driver);
			actioons.moveToElement(selectBTN).click().build().perform();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Mark as Unpaid"))).click();
			actioons.moveToElement(selectBTN).click().build().perform();
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void editVehicleInfo(String editText) {

		List<WebElement> editableFields = driver.findElements(By.tagName("td"));
		Actions act = new Actions(driver);
		for (WebElement element : editableFields) {
			act.moveToElement(element).click().sendKeys(editText).build().perform();
		}
		saveVehicleInfoBTN.click();
	}

	public InvoiceEditTabWebPage clickEditFirstInvoice() {

		String mainWindowHandle = driver.getWindowHandle();
		Actions act = new Actions(driver);
		act.moveToElement(selectBTN).click().build().perform();
		ivoiceOptions.findElement(By.linkText("Edit")).click();
		waitForNewTab();

		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEditTabWebPage.class);
	}

	public boolean checkInvoiceFrameOpened() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_panelPopup")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	private boolean isInvoiceChangeable(WebElement element) {
		try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_popupInvoiceNoSuffix")))
					.sendKeys("test");
            element.click();
			return true;
		} catch (Exception e) {
            System.err.println("EXCEPTION: " + e);
			return false;
		}
	}

	public boolean isChangeButtonClicked() {
		return isInvoiceChangeable(changeButton);
	}

	public boolean isCloseButtonClicked() {
        return isInvoiceChangeable(closeButton);
	}

	public boolean isWindowOpened() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return driver.getWindowHandles().size() > 1;
	}

	public void closeTab(String newTab) {
		driver.switchTo().window(newTab).close();
		Set<String> windows = driver.getWindowHandles();
		driver.switchTo().window(windows.iterator().next());
	}

   	public boolean recalcTechSplitProceed() throws InterruptedException {
		try {
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public int countEmailActivities(String emailWindow) {
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(emailWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		try {
			int size = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr"))).size();
			driver.close();
			driver.switchTo().window(mainWindow);
			waitABit(1000);
			return size;
		} catch (TimeoutException e) {
			driver.close();
			driver.switchTo().window(mainWindow);
            waitABit(1000);
			return 0;
		}
	}

	public boolean isSendEmailBoxOpened() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void setEmailAndSend(String string) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).sendKeys(string);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_btnSendEmail")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void setCustomEmailAndSend(String email, String emailWindow) {
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(emailWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_txtEmailTo")).clear();
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_txtEmailTo")).sendKeys(email);
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_btnSend")).click();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Your message has been sent.')]")));
		driver.close();
		driver.switchTo().window(mainWindow);
	}

	public void setSearchByYear(String s) {
		wait.until(ExpectedConditions
				.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboYear_Input")))).click();
		wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//li[contains(text(), '" + s + "')]")))).click();
	}

	public void setSearchByMake(String string) {
		wait.until(ExpectedConditions.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtMake"))))
				.clear();
		wait.until(ExpectedConditions.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtMake"))))
				.sendKeys(string);
	}

	public void setSearchByModel(String string) {
		wait.until(ExpectedConditions.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtModel"))))
				.clear();
		wait.until(ExpectedConditions.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtModel"))))
				.sendKeys(string);
	}

	public boolean checkSearchResult() {
		return driver.findElements(By.className("rgRow")).size() > 0;
	}

	public void selectSearchStatus(String s) {
		wait.until(ExpectedConditions
				.elementToBeClickable((By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")))).click();
		wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//li[contains(text(), '" + s + "')]")))).click();
	}

	public boolean checkWindowContent(String tab, String... content) {
		driver.switchTo().window(tab);
		for (String str : content) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(str)));
			} catch (TimeoutException e) {
				return false;
			}
		}
		return true;
	}

	public void checkPayBoxContent() throws InterruptedException {
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		WebElement paymentInfo = driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_pContent"));
//		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Invoice#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'PO#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'TOTAL:')]")));
			
			paymentInfo.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtPoRoNumber"))
					.sendKeys("123");
			paymentInfo.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_chkPoRoApprove"))
					.click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Cash/Check')]")))
					.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Check #:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Amount:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Notes:')]")));
			
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCheckNumber")));
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_tbCashAmount")));
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCashNotes")));
			
			paymentInfo.findElement(By.xpath("//input[@id='ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCheckNumber']"));
			paymentInfo.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCashAmount"));
			paymentInfo.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCashNotes"));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Credit Card')]")))
					.click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Amount:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'First Name:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Last Name:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Card#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Expiration:')]")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbCardAmount")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCardFirstName")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCardLastName")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCardNumber")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCardCVV")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnCardPay")));
			driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnCardPayCancel")).click();
			Thread.sleep(1000);
//		} catch (TimeoutException e) {
//			return false;
//		}
//
//		return true;
	}

	public boolean checkAuditLogWindowContent(String auditLogWindow) throws InterruptedException {
Thread.sleep(3000);
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(auditLogWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Invoice #')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Date')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Customer')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'PO#')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Amount')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Paid')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkInvoiceTableInfo() throws InterruptedException {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")));
			wait.until(
					ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")))
					.click();
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Invoice #')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Date')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Status')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Reason')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Amount')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Paid')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Customer')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'PO#')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Notes')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Media')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Duplicates')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Action')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkInvoiceTablePagination() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgArrPart1")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgNumPart")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rgArrPart2")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkInvoicesSearchFields() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboArea_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbStockNo")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tRONo")));
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbPo")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboOrderType_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboInvoiceType_Input")));

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboTeam_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboServiceGroups_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlTimeframe_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlInsurance_Input")));

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboEmployee_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlPORequired_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbVIN")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtInvoiceNo")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountFrom")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountTo")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboService_Input")));

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboOwner_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboYear_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtMake")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_btnRecalculateTotal")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtModel")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_tbClaimNum")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean checkInvoicesSearchResults() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboTeam_Input")))
					.click();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboTeam_Input")))
					.sendKeys("Default Team");
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboTeam_Arrow")))
					.click();
			waitABit(500);

			wait.until(ExpectedConditions
					.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlTimeframe_Input"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
					.findElements(By.className("rcbItem")).stream().filter(e -> e.getText().equals("Custom"))
					.findFirst().get().click();

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")))
					.clear();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")))
					.sendKeys("3/6/2017");
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")))
					.clear();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")))
					.sendKeys("4/6/2017");

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")))
					.click();

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")))
					.click();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")))
					.sendKeys("000 1111");

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")));
			wait.until(
					ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")))
					.click();
			waitABit(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'I-000-00283')]")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void selectIvoicesFromTop(int count) {
		int innerCounter = 4;
		for (int i = 0; i < count; i++) {
			String id = String.format("%2s", innerCounter).replace(' ', '0');
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.id("ctl00_ctl00_Content_Main_grdInvoices_ctl00_ctl" + id + "_chkInvoice"))).click();
			innerCounter += 3;
		}
	}

	public void clickExportButton() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Export')]"))).click();
		waitForLoading();
//		Thread.sleep(1000);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void setStatusForSelectedInvoices(String status) throws InterruptedException {
		List<WebElement> invoicesSelectCheckoxesChecked = driver
				.findElement(By.id("ctl00_ctl00_Content_Main_grdInvoices"))
				.findElements(By.className(" rfdCheckboxChecked"));
		WebElement invoiceRow = null;
		for (int i = 0; i < invoicesSelectCheckoxesChecked.size(); i++) {
			invoiceRow = invoicesSelectCheckoxesChecked.get(i).findElement(By.xpath("..")).findElement(By.xpath(".."));
			invoiceRow.findElement(By.xpath("//td[contains(@class, 'rcbArrowCell rcbArrowCellLeft')]")).click();
			driver.findElement(
					By.xpath("//div[contains(@class, 'RadComboBoxDropDown RadComboBoxDropDown_Office2007 ')]"))
					.findElement(By.xpath("//a[contains(@text(), 'Exported')]")).click();
			driver.switchTo().alert().accept();
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		}
	}

	public String getMainWindow() {
		return driver.getWindowHandle();
	}

	public ExportInvoicesWebPage switchToExportInvoicesWindow(String mainWindow) {
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(mainWindow)) {
				driver.switchTo().window(window);
			}
		}
		return PageFactory.initElements(driver, ExportInvoicesWebPage.class);
	}
}
