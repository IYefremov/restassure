package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.InvoiceListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VNextInvoicesScreen extends VNextBaseTypeScreen {

	@FindBy(xpath = "//div[contains(@class, 'page invoices-list')]")
	private WebElement rootElement;
	
	@FindBy(xpath="//*[@action='hide-multiselect-actions']")
	private WebElement cancelselectedinvoices;

	@FindBy(xpath = "//*[@data-autotests-id='invoices-list']")
	private WebElement screenList;

	@FindBy(xpath = "//*[@data-autotests-id='invoices-list']/div")
	private List<InvoiceListElement> invoicesList;

	public VNextInvoicesScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	public void waitInvoicesScreenLoad(){
		WaitUtils.elementShouldBeVisible(screenList, true);
	}
	
	public String getInvoicePriceValue(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
	}
	
	public String getInvoiceStatusValue(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='select']/div[contains(@class, 'entity-item-status')]/span[contains(@class, 'entity-item-status')]")).getText();
	}

	public String getInvoiceDateValue(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='select']/div[2]/span[@class='text-dim']")).getText();
	}

	//todo: rewrite
	public ArrayList<String> getInvoiceWorkOrders(String invoiceID) {
		ArrayList<String> workOrders = new ArrayList<>();
		expandInvoiceDetails(invoiceID);
		WaitUtils.waitUntilElementIsClickable(getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//div[@class='accordion-item-content']")).
				findElement(By.xpath(".//div[@class='truncate']")));
		String wos = getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[@class='truncate']")).getText().trim();
		String[] wosarray = wos.split(",");
		for (String wonumber : wosarray)
			workOrders.add(wonumber.trim());
		return workOrders;
	}

	//todo: rewrite
	public String getInvoicePONumberValue(String invoiceID) {
		WaitUtils.elementShouldBeVisible(screenList, true);
		String poNumber = "";
		expandInvoiceDetails(invoiceID);
		BaseUtils.waitABit(500);
		poNumber = getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[contains(text(), 'PO#')]")).getText();
		poNumber = poNumber.substring(3).trim();
		return poNumber;
	}

	public String getInvoiceCustomerValue(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//div[@class='entity-item-title']")).getText().trim();
	}

	//todo: rewrite
	public void expandInvoiceDetails(String invoiceID) {
		if (!getInvoiceElement(invoiceID).getRootElement().getAttribute("class").contains("expanded")) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='toggle_item']"))));
			try {
				tap(getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='toggle_item']")));
			} catch (WebDriverException e) {
				((JavascriptExecutor) appiumdriver).executeScript("arguments[0].scrollIntoView(true);", getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='toggle_item']")));
				tap(getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='toggle_item']")));
			}

		}
	}

	public InvoiceListElement getInvoiceElement(String invoiceId) {
		WaitUtils.waitUntilElementIsClickable(screenList);
		WaitUtils.getGeneralFluentWait().until((webdriver) -> invoicesList.size() > 0);
		return invoicesList.stream().filter(listElement -> listElement.getId().equals(invoiceId)).findFirst().orElseThrow(() -> new RuntimeException("Invoice not found " + invoiceId));
	}
	
	public boolean isInvoiceExists(String invoiceID) {
		clearSearchField();
		WaitUtils.waitUntilElementIsClickable(screenList);
		return getInvoiceElement(invoiceID).getRootElement().findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoiceID + "']")).size() > 0;
	}

	public void waitUntilInvoiceDisappearsFromList(String invoiceID) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 3);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath("//div[@class='checkbox-item-title' and text()='" + invoiceID + "']"), 1));
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
	
	public void selectInvoice(String invoiceID) {
		if (getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='check-item']")).getAttribute("checked") == null)
			getInvoiceElement(invoiceID).getRootElement().findElement(By.xpath(".//*[@action='check-item']")).click();
	}
	
	public void clickOnSelectedInvoicesMailButton() {
		tap(appiumdriver.findElement(By.xpath(".//*[@action='multiselect-actions-send-email']")));
	}

	public boolean isInvoiceHasNotesIcon(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElements(By.xpath(".//*[@data-autotests-id='invoice_notes']")).size() > 0;
	}

	public boolean isInvoiceHasPaymentIcon(String invoiceID) {
		return getInvoiceElement(invoiceID).getRootElement().findElements(By.xpath(".//*[@data-autotests-id='invoice_paid']")).size() > 0;
	}
	public String getFirstInvoiceNumber() {
		WaitUtils.elementShouldBeVisible(screenList,true);
		return screenList.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}
}
