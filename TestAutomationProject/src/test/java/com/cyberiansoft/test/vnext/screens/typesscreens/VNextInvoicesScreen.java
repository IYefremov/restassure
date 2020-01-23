package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class VNextInvoicesScreen extends VNextBaseTypeScreen {

	@FindBy(xpath = "//div[contains(@class, 'page invoices-list')]")
	private WebElement rootElement;
	
	@FindBy(xpath="//*[@data-autotests-id='invoices-list']")
	private WebElement invoiceslist;
	
	@FindBy(xpath="//*[@action='hide-multiselect-actions']")
	private WebElement cancelselectedinvoices;

    public VNextInvoicesScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}

	public void waitInvoicesScreenLoad(){
		WaitUtils.elementShouldBeVisible(invoiceslist, true);
	}
	
	public String getInvoicePriceValue(String invoicenumber) {
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		return invoicecell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
	}
	
	public String getInvoiceStatusValue(String invoicenumber) {
		WebElement inspcell = getInvoiceCell(invoicenumber);
		return inspcell.findElement(By.xpath(".//*[@action='select']/div[contains(@class, 'entity-item-status')]/span[contains(@class, 'entity-item-status')]")).getText();
	}

	public String getInvoiceDateValue(String invoicenumber) {
		WebElement inspcell = getInvoiceCell(invoicenumber);
		return inspcell.findElement(By.xpath(".//*[@action='select']/div[2]/span[@class='text-dim']")).getText();
	}
	
	public ArrayList<String> getInvoiceWorkOrders(String invoicenumber) {
		ArrayList<String> workOrders = new ArrayList<>();
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		expandInvoiceDetails(invoicenumber);
		WaitUtils.waitUntilElementIsClickable(invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
				findElement(By.xpath(".//div[@class='truncate']")));
		String wos = invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[@class='truncate']")).getText().trim();
		List<String> wosarray = Arrays.asList(wos.split(","));
		for (String wonumber : wosarray)
			workOrders.add(wonumber.trim());
		return workOrders;
	}
	
	public String getInvoicePONumberValue(String invoicenumber) {
		WaitUtils.elementShouldBeVisible(invoiceslist, true);
		String poNumber = "";
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		expandInvoiceDetails(invoicenumber);
		BaseUtils.waitABit(500);
		poNumber = invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[contains(text(), 'PO#')]")).getText();
		poNumber = poNumber.substring(3, poNumber.length()).trim();
		return poNumber;
	}

	public String getInvoiceCustomerValue(String invoicenumber) {
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		return invoicecell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText().trim();
	}
	
	public void expandInvoiceDetails(String invoicenumber) {
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (!invoicecell.getAttribute("class").contains("expanded")) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(invoicecell.findElement(By.xpath(".//*[@action='toggle_item']"))));
			try {
				tap(invoicecell.findElement(By.xpath(".//*[@action='toggle_item']")));
			} catch (WebDriverException e) {
				((JavascriptExecutor) appiumdriver).executeScript("arguments[0].scrollIntoView(true);", invoicecell.findElement(By.xpath(".//*[@action='toggle_item']")));
				tap(invoicecell.findElement(By.xpath(".//*[@action='toggle_item']")));
			}

		}
	}
	
	protected WebElement getInvoiceCell(String invoicenumber) {
		WaitUtils.elementShouldBeVisible(invoiceslist,true);
		clearSearchField();
        return getListCell(invoiceslist, invoicenumber);
	}
	
	public boolean isInvoiceExists(String invoicenumber) {
		clearSearchField();
		WaitUtils.waitUntilElementIsClickable(invoiceslist);
		return invoiceslist.findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']")).size() > 0;
	}

	public void waitUntilInvoiceDisappearsFromList(String invoiceNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 3);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath("//div[@class='checkbox-item-title' and text()='" + invoiceNumber + "']"), 1));
	}
	
	public void unselectAllSelectedInvoices() {
		if (cancelselectedinvoices.isDisplayed())
			tap(cancelselectedinvoices);
	}
	
	public VNextHomeScreen clickBackButton() {
		waitInvoicesScreenLoad();
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInvoiceMenuScreen clickOnInvoiceByInvoiceNumber(String invoicenumber) {
		WaitUtils.elementShouldBeVisible(getRootElement(),true);
        if (!WaitUtils.isElementPresent(By.xpath("//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']")))
			clearSearchField();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-autotests-id='invoices-list']")));
		WebElement invoiceCell = invoiceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']"));
		if (!invoiceCell.isDisplayed()) {
			Actions actions = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			actions.moveToElement(invoiceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']"))).perform();
		}
		BaseUtils.waitABit(500);
		tap(invoiceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']")));
		return new VNextInvoiceMenuScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickOnInvoiceToEmail(String invoicenumber) {
		VNextInvoiceMenuScreen invoicemenulist = clickOnInvoiceByInvoiceNumber(invoicenumber);
		return invoicemenulist.clickEmailInvoiceMenuItem();
	}
	
	public VNextWorkOrdersScreen clickAddInvoiceButton() {
        clickAddButton();
		VNextWorkOrdersScreen woscreeen = new VNextWorkOrdersScreen(appiumdriver);
		if (appiumdriver.findElements(By.xpath("//div[text()='Tap a work order, and then tap Create Invoice.']")).size() > 0)
			new VNextInformationDialog(appiumdriver).clickInformationDialogOKButton();
		return woscreeen;
	}
	
	public void switchToTeamInvoicesView() {
		switchToTeamView();
		BaseUtils.waitABit(1000);
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading invoices']"));
		WaitUtils.getGeneralFluentWait().until(
				ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='button active' and @action='team']")));
	}

    public void switchToMyInvoicesView() {
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading invoices']"));
		switchToMyView();
	}
	
	public void selectInvoice(String invoiceNumber) {
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		if (invoicecell.findElement(By.xpath(".//*[@action='check-item']")).getAttribute("checked") == null)
			invoicecell.findElement(By.xpath(".//*[@action='check-item']")).click();
	}
	
	public void clickOnSelectedInvoicesMailButton() {
		tap(appiumdriver.findElement(By.xpath(".//*[@action='multiselect-actions-send-email']")));
	}

	public boolean isInvoiceHasNotesIcon(String invoiceNumber) {
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		return invoicecell.findElements(By.xpath(".//*[@data-autotests-id='invoice_notes']")).size() > 0;
	}

	public boolean isInvoiceHasPaymentIcon(String invoiceNumber) {
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		return invoicecell.findElements(By.xpath(".//*[@data-autotests-id='invoice_paid']")).size() > 0;
	}
	public String getFirstInvoiceNumber() {
		WaitUtils.elementShouldBeVisible(invoiceslist,true);
		return invoiceslist.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}
}
