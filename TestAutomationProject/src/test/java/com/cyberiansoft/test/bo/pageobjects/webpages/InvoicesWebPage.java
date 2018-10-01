package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
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
	private WebElement selectButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_pContent")
	private WebElement paymentNote;

	@FindBy(className = "rmVertical")
	private WebElement ivoiceOptions;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_txtCashNotes")
	private WebElement paymentTextField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_btnCashPay")
	private WebElement markAsPaidButton;

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

	@FindBy(xpath = "//div[@class='rmSlide' and contains(@style, 'none')]")
    private WebElement slideHidden;

	@FindBy(id = "ctl00_ctl00_Content_Main_popupEmailRecipients")
    private WebElement emailRecipientsPopupField;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnSendEmail")
    private WebElement sendEmailButton;

	@FindBy(className = "rmBottomArrow")
    private WebElement bottomArrow;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Email Activity']")
    private WebElement emailActivityOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Send Email']")
    private WebElement sendEmailOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Pay']")
    private WebElement payOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Mark as Paid']")
    private WebElement markAsPaidOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Mark as Unpaid']")
    private WebElement markAsUnpaidOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Edit']")
    private WebElement editOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Download JSON']")
    private WebElement downloadJsonOption;

	@FindBy(xpath = "//a[@class='rmLink']//span[text()='Send Custom Email']")
    private WebElement sendCustomEmailOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Change Invoice#']")
    private WebElement changeInvoiceOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Tech. Info']")
    private WebElement techInfoOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Recalc Tech Split']")
    private WebElement recalcTechSplitOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Internal Tech. Info']")
    private WebElement internalTechInfoOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Print preview (server)']")
    private WebElement printPreviewServerOption;

    @FindBy(xpath = "//a[@class='rmLink']//span[text()='Payments']")
    private WebElement paymentsOption;

    @FindBy(xpath = "//div[@id='ctl00_Content_gv']//td")
    private List<WebElement> emailActivityTable;

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
        try {
            wait.until(ExpectedConditions.elementToBeClickable(findbtn)).click();
        } catch (Exception ignored) {}
        waitForLoading();
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
		wait.until(ExpectedConditions.visibilityOf(updateProcess));
		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
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
			waitForLoading();
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
						act.moveToElement(selectButton).moveToElement(driver.findElement(By.className("rmBottomArrow")))
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
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
	}

	public boolean isInvoiceEmailSent(String invoicenumber, String email) {
		try {
		    waitABit(2000);
            clickInvoiceSelectExpandableMenu(invoicenumber, "Send Email");
            wait.until(ExpectedConditions.visibilityOf(emailRecipientsPopupField)).clear();
            emailRecipientsPopupField.sendKeys(email);
            clickAndWait(sendEmailButton);
            return true;
        } catch (Exception ignored) {
		    return false;
        }
	}

	public SendInvoiceCustomEmailTabWebPage clickSendCustomEmail(String invoicenumber) {
        waitABit(2000);
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

	public InvoiceEmailActivityTabWebPage clickEmailActivity(String invoicenumber) {
        waitABit(30000);
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

	public InvoiceEditTabWebPage clickEditInvoice(String invoicenumber) {
        waitABit(5000);
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

	public InvoicePaymentsTabWebPage clickInvoicePayments(String invoicenumber) {
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

	public void clickInvoicePrintPreview(String invoicenumber) {
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

	public void clickInvoiceInternalTechInfo(String invoicenumber) {
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
		waitForLoading();
	}

	public WebElement getTechInfoServicesTable() {
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td/div[text()='SERVICES']/../../../.."));
	}

	public String getTechInfoServicesTableServiceValue(String columnname, String servicename) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("ctl00_Main_report_ctl05"))));
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
                Assert.fail("The invoice has not been displayed!", e);
	    }
        return "";
    }

    public String selectEmailActivityOption() {
        return selectOptionForFirstInvoice(emailActivityOption);
    }

    public String selectSendEmailOption() {
        return selectOptionForFirstInvoice(sendEmailOption);
    }

    public String selectPayOption() {
        return selectOptionForFirstInvoice(payOption);
    }

    public String selectMarkAsPaidOption() {
        return selectOptionForFirstInvoice(markAsPaidOption);
    }

    public void handlePaymentNote() {
        wait.until(ExpectedConditions.visibilityOf(paymentNote));
        paymentTextField.sendKeys("test");
        markAsPaidButton.click();
        driver.switchTo().alert().accept();
        waitForLoading();
        driver.navigate().refresh();
    }

    public String selectMarkAsUnpaidOption() {
        return selectOptionForFirstInvoice(markAsUnpaidOption);
    }

    public String selectEditOption() {
        waitABit(3000);
        return selectOptionForFirstInvoice(editOption);
    }

    public String selectDownloadJsonOption() {
        return selectOptionForFirstInvoice(downloadJsonOption);
    }

    public String selectSendCustomEmailOption() {
        return selectOptionForFirstInvoice(sendCustomEmailOption);
    }

    public String selectChangeInvoiceOption() {
        return selectOptionForFirstInvoice(changeInvoiceOption);
    }
    
    public String selectTechInfoOption() {
	    return selectOptionForFirstInvoice(techInfoOption);
    }

    public String selectRecalcTechSplitOption() {
	    return selectOptionForFirstInvoice(recalcTechSplitOption);
    }

    public String selectInternalTechInfoOption() {
	    return selectOptionForFirstInvoice(internalTechInfoOption);
    }

    public String selectPrintPreviewServerOption() {
	    return selectOptionForFirstInvoice(printPreviewServerOption);
    }

    public String selectPaymentsOption() {
	    return selectOptionForFirstInvoice(paymentsOption);
    }

    private String selectOptionForFirstInvoice(WebElement button) {
        String mainWindow = driver.getWindowHandle();
        Actions actions = new Actions(driver);
        actions.moveToElement(selectButton).click().build().perform();

        try {
            wait.until(ExpectedConditions.visibilityOf(slideDisplayed));
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (Exception e) {
            try {
                actions.moveToElement(selectButton).moveToElement(bottomArrow).pause(Duration.ofMillis(1000)).build().perform();
                wait.until(ExpectedConditions.visibilityOf(button)).click();
            } catch (Exception ignored) {}
        }
        try {
            Set frames = driver.getWindowHandles();
            waitABit(10000);
            frames.remove(mainWindow);
            driver.switchTo().window((String) frames.iterator().next());
            return (String) frames.iterator().next();
        } catch (Exception ignored) {
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

    public void handleAlertForEdgeBrowser() {
        if (getBrowserType().contains("edge")) {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        }
    }

	public void scrollWindowDown(int pix) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + pix + ")", "");
	}

	public boolean isFirstInvoiceMarkedAsPaid() {
		try {
			Actions actions = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(selectButton)).click();

			actions.moveToElement(selectButton).click().build().perform();
//            setAttribute(slideDisplayed, "")
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Mark as Unpaid"))).click();
			actions.moveToElement(selectButton).click().build().perform();
		} catch (WebDriverException e) {
		    e.printStackTrace();
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
		act.moveToElement(selectButton).click().build().perform();
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
		waitABit(3000);
		return wait.until(e -> driver.getWindowHandles().size() > 1);
	}

	public void closeTab(String newTab) {
		driver.switchTo().window(newTab).close();
		Set<String> windows = driver.getWindowHandles();
		driver.switchTo().window(windows.iterator().next());
	}

   	public boolean recalcTechSplitProceed() {
		try {
			waitABit(1000);
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
            switchToMainWindow(mainWindow);
			return size;
		} catch (TimeoutException e) {
            switchToMainWindow(mainWindow);
			return 0;
		}
	}

	public boolean isEmailDisplayed(String emailWindow, String email) {
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
            boolean present = wait.until(ExpectedConditions
                    .visibilityOfAllElements(emailActivityTable))
                    .stream()
                    .anyMatch(e -> e.getText().equals(email));
            switchToMainWindow(mainWindow);
            return present;
        } catch (TimeoutException e) {
            switchToMainWindow(mainWindow);
            return false;
        }
    }

    private void switchToMainWindow(String mainWindow) {
        driver.close();
        driver.switchTo().window(mainWindow);
        waitABit(1000);
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
		wait.until(ExpectedConditions.visibilityOf(emailRecipientsPopupField)).clear();
        emailRecipientsPopupField.sendKeys(string);
		sendEmailButton.click();
		waitForLoading();
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

	public void checkPayBoxContent() {
		waitForLoading();
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
			waitABit(1000);
//		} catch (TimeoutException e) {
//			return false;
//		}
//
//		return true;
	}

	public boolean checkAuditLogWindowContent(String auditLogWindow) {
        waitABit(3000);
        Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(auditLogWindow))
				driver.switchTo().window(window);
		}
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Invoice #')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Date')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Customer')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'PO#')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Amount')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Paid')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkInvoiceTableInfo() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")));
			wait.until(
					ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")))
					.click();
            waitForLoading();

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

	public boolean isInvoiceFound(String invoice, String customer, String dateFrom, String dateTo) {
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
					.findFirst().ifPresent(WebElement::click);

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")))
					.clear();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")))
					.sendKeys(dateFrom);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")))
					.clear();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")))
					.sendKeys(dateTo);

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")))
					.click();

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")))
					.click();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")))
					.sendKeys(customer);

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")));
			wait.until(
					ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")))
					.click();
            waitForLoading();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '" + invoice + "')]")));
		} catch (TimeoutException e) {
		    e.printStackTrace();
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
			waitForLoading();
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

	public void chooseMarkAsUnpaidOptionIfPresent() {
        try {
            selectMarkAsUnpaidOption();
        } catch (Exception ignored) {}
    }
}
