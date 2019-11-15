package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.bo.webelements.impl.ComboBoxImpl;
import com.cyberiansoft.test.bo.webelements.impl.DropDownImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class VendorOrderServicesWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_radToolBarOrder")
	private WebElement tollbarbuttons;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvPhases_ctl00_ctl04_phaseStatus_DropDown")
	private DropDown phasestatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvPhases_ctl00_ctl06_phaseStatus_Input")
	private ComboBox statuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvPhases_ctl00_ctl06_phaseStatus_DropDown")
	private DropDown statusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl07_comboStatus_Input")
	private ComboBox combostatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl07_comboStatus_DropDown")
	private DropDown combostatusdd;

	@FindBy(xpath = "//input[@value='Change Status']")
	private WebElement changestatusbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_hlBackRO")
	private WebElement backtoROLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable servicestable;

	@FindBy(xpath = "//div[contains(@id, 'comboVendor_DropDown')]")
	private DropDown combovendordd;

	public VendorOrderServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void setServicesStatus(String status) {
		selectComboboxValue(statuscmb, statusdd, status);
		clickChangeStatusButton();
	}

	public void setStartPhaseStatus(String status) {
		selectComboboxValue(combostatuscmb, combostatusdd, status);
		clickChangeStatusButton();
	}

	public void clickChangeStatusButton() {
		clickAndWait(changestatusbtn);
	}

	public RepairOrdersWebPage clickBackToROLink() {
		click(backtoROLink);
		return PageFactory.initElements(
				driver, RepairOrdersWebPage.class);
	}

	public ComboBox getRepairOrderStatusCombobox() {
		wait.until(ExpectedConditions.visibilityOf(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderStatus_Input')]"))));
		return new ComboBoxImpl(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderStatus_Input')]")));
	}

	public ComboBox getRepairOrderReasonCombobox() {
		wait.until(ExpectedConditions.visibilityOf(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderReason_Input')]"))));
		return new ComboBoxImpl(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderReason_Input')]")));
	}

	public void selectRepairOrderStatus(String _status) {
		selectComboboxValueAndWait(getRepairOrderStatusCombobox(), new DropDownImpl(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderStatus_DropDown')]"))), _status);
	}

	public void selectRepairOrderReason(String _reason) {
		if (getBrowserType().equals("firefox")) {
			WebElement combobox = getRepairOrderReasonCombobox().getWrappedElement();
			wait.until(ExpectedConditions.elementToBeClickable(combobox));
			combobox.click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]"))));
			waitABit(300);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")).findElement(By.xpath(".//li[text()='" + _reason + "']")));
			driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")).findElement(By.xpath(".//li[text()='" + _reason + "']")).click();
			wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")))));
		} else
			selectComboboxValue(getRepairOrderReasonCombobox(), new DropDownImpl(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]"))), _reason);
	}

	public List<WebElement> getRepairOrderServiceTableRows() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_gv_ctl00")));
		return servicestable.getTableRows();
	}

	private WebElement getTableRowWithRepairOrderService(String serviceName) {
		List<WebElement> rows = getRepairOrderServiceTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[@class='col_2']")).getText().contains(serviceName)) {
				return row;
			}
		}
		return null;
	}

	public void changeRepairOrderServiceVendor(String serviceName, String vendorName) {
		WebElement row = getTableRowWithRepairOrderService(serviceName);
		if (row != null) {
			row.findElement(By.xpath(".//input[contains(@id, 'comboVendor_Input')]")).click();
			combovendordd.selectByVisibleText(vendorName);
		} else
			Assert.assertTrue(false, "Can't find " + serviceName + " repair order service");
	}

	public String getRepairOrderServiceTechnician(String serviceName) {
		String tech = "";
		WebElement row = getTableRowWithRepairOrderService(serviceName);
		if (row != null) {
			tech = row.findElement(By.xpath(".//input[contains(@id, 'comboEmployee_Input')]")).getAttribute("value");
		} else
			Assert.assertTrue(false, "Can't find " + serviceName + " repair order service");
		return tech;
	}

	public String getRepairOrderServiceVendor(String serviceName) {
		String vendorName = "";
		WebElement row = getTableRowWithRepairOrderService(serviceName);
		if (row != null) {
			vendorName = row.findElement(By.xpath(".//input[contains(@id, 'comboVendor_Input')]")).getAttribute("value");
		} else
			Assert.assertTrue(false, "Can't find " + serviceName + " repair order service");
		return vendorName;
	}

}
