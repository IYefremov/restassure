package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class CompanyWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Clients']")
	private WebElement clientslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Packages']")
	private WebElement servicepackageslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Users']")
	private WebElement userslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Employees']")
	private WebElement employeeslink;	
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Services']")
	private WebElement serviceslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Teams']")
	private WebElement teamslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Jobs']")
	private WebElement jobslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Areas']")
	private WebElement areaslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Email Templates']")
	private WebElement emailtemplateslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Print Servers']")
	private WebElement printserverslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Print Templates Configurations']")
	private WebElement printtemplatesconfiglink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Insurance Companies']")
	private WebElement insurancecompanieslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Advisors']")
	private WebElement serviceadvisorslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Question Forms']")
	private WebElement questionsformslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Inspection Types']")
	private WebElement inspectiontypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Supplies']")
	private WebElement supplieslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Invoice Types']")
	private WebElement invoicetypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Work Order Types']")
	private WebElement workordertypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Expense Types']")
	private WebElement expensestypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Request Types']")
	private WebElement servicerequesttypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Price Matrices']")
	private WebElement pricematriceslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Service Contract Types']")
	private WebElement servicecontracttypeslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Vehicle Parts']")
	private WebElement vehiclepartslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Manage Devices']")
	private WebElement managedeviceslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Manage Licences']")
	private WebElement managelicenceslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Timesheet Types']")
	private WebElement timesheetypesslink;
	
	public CompanyWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public ClientsWebPage clickClientsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(clientslink)));
		return PageFactory.initElements(
				driver, ClientsWebPage.class);
	}
	
	public ServicePackagesWebPage clickServicePackagesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicepackageslink)));
		return PageFactory.initElements(
				driver, ServicePackagesWebPage.class);
	}
	
	public UsersWebPage clickUsersLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(userslink)));
		return PageFactory.initElements(
				driver, UsersWebPage.class);
	}
	
	public EmployeesWebPage clickEmployeesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(employeeslink)));
		return PageFactory.initElements(
				driver, EmployeesWebPage.class);
	}
	
	public ServicesWebPage clickServicesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(serviceslink)));
		return PageFactory.initElements(
				driver, ServicesWebPage.class);
	}
	
	public TeamsWebPage clickTeamsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(teamslink)));
		return PageFactory.initElements(
				driver, TeamsWebPage.class);
	}
	
	public JobsWebPage clickJobsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(jobslink)));
		return PageFactory.initElements(
				driver, JobsWebPage.class);
	}
	
	public AreasWebPage clickAreasLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(areaslink)));
		return PageFactory.initElements(
				driver, AreasWebPage.class);
	}
	
	public EmailTemplatesWebPage clickEmailTemplatesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(emailtemplateslink)));
		return PageFactory.initElements(
				driver, EmailTemplatesWebPage.class);
	}
	
	public PrintServersWebPage clickPrintServersLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(printserverslink)));
		return PageFactory.initElements(
				driver, PrintServersWebPage.class);
	}
	
	public PrintTemplatesWebPage clickPrintTemplatesConfigurationsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(printtemplatesconfiglink)));
		return PageFactory.initElements(
				driver, PrintTemplatesWebPage.class);
	}
	
	public InsuranceCompaniesWePpage clickInsuranceCompaniesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(insurancecompanieslink)));
		return PageFactory.initElements(
				driver, InsuranceCompaniesWePpage.class);
	}
	
	public ServiceAdvisorsWebPage clickServiceAdvisorsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(serviceadvisorslink)));
		return PageFactory.initElements(
				driver, ServiceAdvisorsWebPage.class);
	}
	
	public QuestionsFormsWebPage clickQuestionsFormsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(questionsformslink)));
		return PageFactory.initElements(
				driver, QuestionsFormsWebPage.class);
	}
	
	public InspectionTypesWebPage clickInspectionTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(inspectiontypeslink)));
		return PageFactory.initElements(
				driver, InspectionTypesWebPage.class);
	}
	
	public SuppliesWebPage clickSuppliesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(supplieslink)));
		return PageFactory.initElements(
				driver, SuppliesWebPage.class);
	}

	public ExpensesTypesWebPage clickExpensesTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(expensestypeslink)));
		return PageFactory.initElements(
				driver, ExpensesTypesWebPage.class);
	}
	
	public InvoiceTypesWebPage clickInvoiceTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(invoicetypeslink)));
		return PageFactory.initElements(
				driver, InvoiceTypesWebPage.class);
	}
	
	public WorkOrderTypesWebPage clickWorkOrderTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(workordertypeslink)));
		return PageFactory.initElements(
				driver, WorkOrderTypesWebPage.class);
	}
	
	public PriceMatricesWebPage clickPriceMatricesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(pricematriceslink)));
		return PageFactory.initElements(
				driver, PriceMatricesWebPage.class);
	}
	
	public ServiceContractTypesWebPage clickServiceContractTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicecontracttypeslink)));
		return PageFactory.initElements(
				driver, ServiceContractTypesWebPage.class);
	}
	
	public ServiceRequestTypesWebPage clickServiceRequestTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(servicerequesttypeslink)));
		return PageFactory.initElements(
				driver, ServiceRequestTypesWebPage.class);
	}
	
	public VehiclePartsWebPage clickVehiclePartsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(vehiclepartslink)));
		return PageFactory.initElements(
				driver, VehiclePartsWebPage.class);
	}
	
	public TimesheetTypesWebPage clickTimesheetTypesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(timesheetypesslink)));
		return PageFactory.initElements(
				driver, TimesheetTypesWebPage.class);
	}
	
	public ManageLicencesWebPage clickManageLicencesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(managelicenceslink)));
		return PageFactory.initElements(
				driver, ManageLicencesWebPage.class);
	}
	
	public ActiveDevicesWebPage clickManageDevicesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(managedeviceslink)));
		return PageFactory.initElements(
				driver, ActiveDevicesWebPage.class);
	}
}
