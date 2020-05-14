package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.util.List;

@Getter
public class VNextInvoiceInfoScreen extends VNextBaseScreen {

	@FindBy(xpath = "//div[@data-page='info']")
	private WebElement rootElement;
	
	@FindBy(xpath="//input[@name='Invoices.PONo']")
	private WebElement invoicePO;

	@FindBy(xpath="//input[@name='Invoices.InvoiceDate']")
	private WebElement invoiceDate;

	@FindBy(xpath="//*[@action='add-order']")
	private WebElement addOrderBtn;

	@FindBy(xpath="//*[@action='create-invoice']/i")
	private WebElement createinvoicemenu;
	
	@FindBy(id="total")
	private WebElement invoiceTotalAmont;
	
	@FindBy(xpath="//div[@class='invoce-info-container']")
	private WebElement invoiceInfoPanel;

	public VNextInvoiceInfoScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	public void changeInvoiceDayValue(LocalDate date) {
		tap(invoiceDate);
		setInvoiceSelectedDateValue(date);
		closeInvoiceSelectDatePicker();
 	}

 	private WebElement getDatePickerWheel() {
		return appiumdriver.findElement(By.xpath("//div[contains(@class, 'picker-modal picker-columns')]"));
	}

	private WebElement getDatePickerWheelDateColumn() {
		return getDatePickerWheel().findElement(By.xpath(".//div[@class='picker-modal-inner picker-items']/div[2]"));
	}

	public void setInvoiceSelectedDateValue(LocalDate date) {
		WebElement pickerwheeldatecolumn = getDatePickerWheelDateColumn();
		tap(pickerwheeldatecolumn.findElement(By.xpath(".//div[@data-picker-value='" + date.getDayOfMonth() + "']")));
	}

	private void closeInvoiceSelectDatePicker() {
		WebElement pickerwheel = getDatePickerWheel();
		tap(pickerwheel.findElement(By.xpath(".//a[@class='link close-picker']")));
	}

	public WebElement getInvoiceWorkOrderPanel(String workOrderNumber) {
		return getWorkOrdersList().stream().
				filter(workOrdersPanel -> workOrdersPanel.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim().equals(workOrderNumber)).
				findFirst()
				.orElseThrow(() -> new RuntimeException("Work Order not found " + workOrderNumber));
	}

	private List<WebElement> getWorkOrdersList() {
		return invoiceInfoPanel.findElements(By.xpath(".//*[@action='edit-order']"));
	}

	public void clickOnWorkOrder(String workOrderNumber) {
		WaitUtils.elementShouldBeVisible(getRootElement(),true);
		tap(appiumdriver.
				findElement(By.xpath("//div[@class='checkbox-item-title' and text()='" + workOrderNumber + "']")));
	}
}
