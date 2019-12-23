package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OperationsWebPage extends BaseWebPage {

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Invoices']")
	private WebElement invoiceslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Work Orders']")
	private WebElement workorderslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Technician Commissions']")
	private WebElement techniciancommissionslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Requests']")
	private WebElement servicerequestslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Contracts']")
	private WebElement servicecontactslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Inspections']")
	private WebElement inspectionslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Vendor Bills']")
	private WebElement vendorbillslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='New Inspection']")
	private WebElement newinspectionlink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Requests List']")
	private WebElement newservicerequestlink;

	public OperationsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickInvoicesLink() {
		wait.until(ExpectedConditions.elementToBeClickable(invoiceslink)).click();
	}

	public void clickWorkOrdersLink() {
		wait.until(ExpectedConditions.elementToBeClickable(workorderslink)).click();
	}

	public void clickTechnicianCommissionsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(techniciancommissionslink)).click();
	}

	public void clickServiceContactsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(servicecontactslink)).click();
	}

	public void clickInspectionsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(inspectionslink)).click();
	}

	public void clickVendorBillsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(vendorbillslink)).click();
	}

	public void clickNewServiceRequestList() {
	    WaitUtilsWebDriver.elementShouldBeVisible(newservicerequestlink, true);
        Utils.clickElement(newservicerequestlink);
	}
}
