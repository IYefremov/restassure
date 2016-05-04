package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.utils.WebDriverInstansiator;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.impl.ComboBoxImpl;
import com.cyberiansoft.test.bo.webelements.impl.DropDownImpl;

public class VendorOrderServicesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_radToolBarOrder")
	private WebElement tollbarbuttons;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvPhases_ctl00_ctl04_phaseStatus_Input")
	private ComboBox phasestatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvPhases_ctl00_ctl04_phaseStatus_DropDown")
	private DropDown phasestatusdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl07_comboStatus_Input")
	private ComboBox combostatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl07_comboStatus_DropDown")
	private DropDown combostatusdd;
	
	@FindBy(xpath = "//input[@value='Change Status']")
	private WebElement changestatusbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_hlBackRO")
	private WebElement backtoROLink;
	
	public VendorOrderServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setServicesStatus(String _status) {
		selectComboboxValue(phasestatuscmb, phasestatusdd, _status);
		clickChangeStatusButton();
	}
	
	public void setStartPhaseStatus(String _status) {
		selectComboboxValue(combostatuscmb, combostatusdd, _status);
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
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderStatus_Input')]"))));
		return new ComboBoxImpl(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderStatus_Input')]")));
	}
	
	public ComboBox getRepairOrderReasonCombobox() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderReason_Input')]"))));
		return new ComboBoxImpl(tollbarbuttons.findElement(By.xpath(".//input[contains(@id, 'comboOrderReason_Input')]")));
	}
	
	public void selectRepairOrderStatus(String _status) {
		selectComboboxValueAndWait(getRepairOrderStatusCombobox(), new DropDownImpl(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderStatus_DropDown')]"))), _status);
	}
	
	public void selectRepairOrderReason(String _reason) {
		if (getBrowserType().equals("firefox")) {		
			WebElement combobox = getRepairOrderReasonCombobox().getWrappedElement();
			new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
				.until(ExpectedConditions.elementToBeClickable(combobox));
			combobox.click();
			new WebDriverWait(WebDriverInstansiator.getDriver(), 10)
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]"))));
			waitABit(300);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")).findElement(By.xpath(".//li[text()='" + _reason + "']")));
			driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")).findElement(By.xpath(".//li[text()='" + _reason + "']")).click();
			new WebDriverWait(WebDriverInstansiator.getDriver(), 5)
				.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]")))));		
		} else
			selectComboboxValue(getRepairOrderReasonCombobox(), new DropDownImpl(driver.findElement(By.xpath("//*[contains(@id, 'comboOrderReason_DropDown')]"))), _reason);
	}

}
