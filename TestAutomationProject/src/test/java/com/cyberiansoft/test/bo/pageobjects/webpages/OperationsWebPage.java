package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public InvoicesWebPage clickInvoicesLink() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(invoiceslink)).click();
		return PageFactory.initElements(
				driver, InvoicesWebPage.class);
	}
	
	public WorkOrdersWebPage clickWorkOrdersLink() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(workorderslink)).click();
		return PageFactory.initElements(
				driver, WorkOrdersWebPage.class);
	}
	
	public TechnicianCommissionsWebPage clickTechnicianCommissionsLink() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(techniciancommissionslink)).click();
		return PageFactory.initElements(
				driver, TechnicianCommissionsWebPage.class);
	}
	
	public ServiceRequestsWebPage clickServiceRequestsLink() throws InterruptedException {
		Thread.sleep(2000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicerequestslink)).click();
		return PageFactory.initElements(
				driver, ServiceRequestsWebPage.class);
	}
	
	public ServiceContractsWebPage clickServiceContactsLink() throws InterruptedException {
		Thread.sleep(2000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicecontactslink)).click();
		return PageFactory.initElements(
				driver, ServiceContractsWebPage.class);
	}
	
	public InspectionsWebPage clickInspectionsLink() {
		waitABit(2000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(inspectionslink)).click();
		return PageFactory.initElements(
				driver, InspectionsWebPage.class);
	}
	
	public VendorBillsWebPage clickVendorBillsLink() {
		waitABit(2000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(vendorbillslink)).click();
		return PageFactory.initElements(
				driver, VendorBillsWebPage.class);
	}
	
	public ServiceRequestsListWebPage clickNewServiceRequestLink() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(newservicerequestlink)).click();
		return PageFactory.initElements(
				driver, ServiceRequestsListWebPage.class);
	}
	
	public NewInspectionWebPage clickNewInspectionLink() throws InterruptedException {
		Thread.sleep(2000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(newinspectionlink)).click();
		return PageFactory.initElements(
				driver, NewInspectionWebPage.class);
	}

}
