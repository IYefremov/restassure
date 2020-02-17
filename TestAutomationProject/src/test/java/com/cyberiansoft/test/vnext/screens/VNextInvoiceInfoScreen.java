package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;

@Getter
public class VNextInvoiceInfoScreen extends VNextBaseScreen {

	@FindBy(xpath = "//div[@data-page='info']")
	private WebElement rootElement;
	
	@FindBy(xpath="//input[@name='Invoices.PONo']")
	private WebElement invoicepo;

	@FindBy(xpath="//input[@name='Invoices.InvoiceDate']")
	private WebElement invoicedate;

	@FindBy(xpath="//*[@action='add-order']")
	private WebElement addorderbtn;

	@FindBy(xpath="//*[@action='create-invoice']/i")
	private WebElement createinvoicemenu;
	
	@FindBy(id="total")
	private WebElement invoicetotalamont;
	
	@FindBy(xpath="//div[@class='invoce-info-container']")
	private WebElement invoiceinfopanel;

    public VNextInvoiceInfoScreen(WebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(appiumdriver, this);
		WaitUtils.elementShouldBeVisible(getRootElement(),true);
	}
	
	public void setInvoicePONumber(String ponumber) {
		WaitUtils.click(invoicepo);
		invoicepo.clear();
		invoicepo.sendKeys(ponumber);
		BaseUtils.waitABit(500);
	}

	public String getInvoicePONumberValue() {
		return invoicepo.getAttribute("value");
	}

	public String getInvoiceDateValue() {
		return invoicedate.getAttribute("value");
	}
	
	public VNextNotesScreen clickNotesMenuItem() {
        VNextInvoiceMenuScreen invoiceMenuScreen = new VNextInvoiceMenuScreen(appiumdriver);
        return invoiceMenuScreen.clickInvoiceNotesMenuItem();
	}
	
	public String getInvoiceTotalAmount() {
		WaitUtils.waitUntilElementIsClickable(invoicetotalamont);
		return invoicetotalamont.getText().trim();
	}
	
	public boolean isWorkOrderSelectedForInvoice(String wonumber) {
		WaitUtils.elementShouldBeVisible(getRootElement(),true);
		return invoiceinfopanel.findElements(By.xpath(".//div[text()='" + wonumber + "']")).size() > 0;
	}
	
	public void clickInvoiceInfoBackButton() {
		clickScreenBackButton();
	}

	public void changeInvoiceDayValue(LocalDate date) {
		tap(invoicedate);
		setInvoiceSelectedDateValue(date);
		closeInvoiceSelectDatePicker();
 	}

 	private WebElement getDatePickerWheel() {
		return appiumdriver.findElement(By.xpath("//div[contains(@class, 'picker-modal picker-columns')]"));
	}

	private WebElement getDatePickerWheelDateColumn() {
		return getDatePickerWheel().findElement(By.xpath(".//div[@class='picker-modal-inner picker-items']/div[2]"));
	}

	public int getInvoiceSelectedDateValue() {
		WebElement pickerwheeldatecolumn = getDatePickerWheelDateColumn();
		return Integer.valueOf(pickerwheeldatecolumn.findElement(By.xpath(".//div[@class='picker-item picker-selected']")).getAttribute("data-picker-value"));
	}

	public void setInvoiceSelectedDateValue(LocalDate date) {
		WebElement pickerwheeldatecolumn = getDatePickerWheelDateColumn();
		tap(pickerwheeldatecolumn.findElement(By.xpath(".//div[@data-picker-value='" + date.getDayOfMonth() + "']")));
	}

	private void closeInvoiceSelectDatePicker() {
		WebElement pickerwheel = getDatePickerWheel();
		tap(pickerwheel.findElement(By.xpath(".//a[@class='link close-picker']")));
	}

	public void addWorkOrdersToInvoice(List<String> workOrders) {
		VNextSelectWorkOrdersScreen selectWorkOrdersScreen = clickAddWorkOrdersButton();
		for (String woNumber : workOrders)
			selectWorkOrdersScreen.selectWorkOrder(woNumber);
		selectWorkOrdersScreen.clickAddWorkOrders();
	}

	public VNextSelectWorkOrdersScreen clickAddWorkOrdersButton() {
		tap(addorderbtn);
		return new VNextSelectWorkOrdersScreen(appiumdriver);
	}

	public void deattechWorkOrdersFromInvoice(List<String> workOrders) {
		for (String woNumber : workOrders) {
			WebElement woCell = getInvoiceWorkOrderPanel(woNumber);
			if (woCell != null)
				tap(woCell.findElement(By.xpath(".//*[@action='delete-order']")));
			else
				Assert.fail("Can't find work order: " + woNumber);
		}
	}

	private WebElement getInvoiceWorkOrderPanel(String workOrderNumber) {
		WebElement woCell = null;
		List<WebElement> workOrdersPanels = getListOfInvoiceWorkOrders();
		for (WebElement workOrdersPanel : workOrdersPanels)
			if (workOrdersPanel.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim().equals(workOrderNumber)) {
				woCell = workOrdersPanel;
				break;
			}
		return woCell;
	}

	private List<WebElement> getListOfInvoiceWorkOrders() {
		return invoiceinfopanel.findElements(By.xpath(".//*[@action='edit-order']"));
	}

	public VNextVehicleInfoScreen clickOnWorkOrder(String workOrderNumber) {
		tap(appiumdriver.
				findElement(By.xpath("//div[@class='checkbox-item-title' and text()='" + workOrderNumber + "']")));
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
	}
}
