package com.cyberiansoft.test.bo.pageobjects.webpages;

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
	
	public InvoicesWebPage clickInvoicesLink() {
		wait.until(ExpectedConditions.elementToBeClickable(invoiceslink)).click();
		return PageFactory.initElements(driver, InvoicesWebPage.class);
	}
	
	public WorkOrdersWebPage clickWorkOrdersLink() {
		wait.until(ExpectedConditions.elementToBeClickable(workorderslink)).click();
		return PageFactory.initElements(
				driver, WorkOrdersWebPage.class);
	}
	
	public TechnicianCommissionsWebPage clickTechnicianCommissionsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(techniciancommissionslink)).click();
		return PageFactory.initElements(
				driver, TechnicianCommissionsWebPage.class);
	}
	
	public ServiceRequestsWebPage clickServiceRequestsLink() {
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(servicerequestslink)).click();
		return PageFactory.initElements(
				driver, ServiceRequestsWebPage.class);
	}
	
	public ServiceContractsWebPage clickServiceContactsLink() {
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(servicecontactslink)).click();
		return PageFactory.initElements(
				driver, ServiceContractsWebPage.class);
	}
	
	public InspectionsWebPage clickInspectionsLink() {
		//waitABit(2000);
		wait.until(ExpectedConditions.elementToBeClickable(inspectionslink)).click();
		return PageFactory.initElements(
				driver, InspectionsWebPage.class);
	}
	
	public VendorBillsWebPage clickVendorBillsLink() {
		//waitABit(2000);
		wait.until(ExpectedConditions.elementToBeClickable(vendorbillslink)).click();
		return PageFactory.initElements(
				driver, VendorBillsWebPage.class);
	}
	
	public ServiceRequestsListWebPage clickNewServiceRequestLink() {
		wait.until(ExpectedConditions.elementToBeClickable(newservicerequestlink)).click();
		return PageFactory.initElements(
				driver, ServiceRequestsListWebPage.class);
	}
	
	public NewInspectionWebPage clickNewInspectionLink() {
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(newinspectionlink)).click();
		return PageFactory.initElements(
				driver, NewInspectionWebPage.class);
	}

}
