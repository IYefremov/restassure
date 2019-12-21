package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

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

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Inter Application Exchange']")
	private WebElement interApplicationExchangeLink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Client Users']")
	private WebElement clientUsersLink;

	public CompanyWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickClientsLink() {
        WaitUtilsWebDriver.elementShouldBeVisible(clientslink, true);
        Utils.clickElement(clientslink);
	}

	public void clickClientUsersLink() {
        Utils.clickElement(clientUsersLink);
	}

	public void clickServicePackagesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(servicepackageslink)));
	}

	public void clickUsersLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(userslink)));
	}

	public void clickEmployeesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(employeeslink)));
	}

	public void clickServicesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(serviceslink)));
		wait.until(ExpectedConditions.titleContains("Services"));
	}

	public void clickTeamsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(teamslink)));
	}

	public void clickJobsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(jobslink)));
	}

	public void clickAreasLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(areaslink)));
	}

	public void clickEmailTemplatesLink() {
	    Utils.clickElement(emailtemplateslink);
	}

	public void clickPrintServersLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(printserverslink)));
	}

	public void clickPrintTemplatesConfigurationsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(printtemplatesconfiglink)));
	}

	public void clickInsuranceCompaniesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(insurancecompanieslink)));
	}

	public void clickServiceAdvisorsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(serviceadvisorslink)));
	}

	public void clickQuestionsFormsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(questionsformslink)));
	}

	public void clickInspectionTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(inspectiontypeslink)));
	}

	public void clickSuppliesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(supplieslink)));
	}

	public void clickExpensesTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(expensestypeslink)));
	}

	public void clickInvoiceTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(invoicetypeslink)));
	}

	public void clickWorkOrderTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(workordertypeslink)));
	}

	public void clickPriceMatricesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(pricematriceslink)));
	}

	public void clickServiceContractTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(servicecontracttypeslink)));
	}

	public void clickServiceRequestTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(servicerequesttypeslink)));
	}

	public void clickVehiclePartsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(vehiclepartslink)));
	}

	public void clickTimesheetTypesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(timesheetypesslink)));
	}

	public void clickManageLicencesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(managelicenceslink)));
	}

	public void clickManageDevicesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(managedeviceslink)));
	}

	public void clickInterApplicationExchangeLink() {
		wait.until(ExpectedConditions.elementToBeClickable(interApplicationExchangeLink)).click();
	}
}
