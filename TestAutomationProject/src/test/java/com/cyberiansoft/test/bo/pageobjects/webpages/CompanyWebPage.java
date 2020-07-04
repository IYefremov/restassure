package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
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
        Utils.clickElement(servicepackageslink);
	}

	public void clickUsersLink() {
        Utils.clickElement(userslink);
	}

	public void clickEmployeesLink() {
        Utils.clickElement(employeeslink);
	}

	public void clickServicesLink() {
        Utils.clickElement(serviceslink);
		wait.until(ExpectedConditions.titleContains("Services"));
	}

	public void clickTeamsLink() {
        Utils.clickElement(teamslink);
	}

	public void clickJobsLink() {
        Utils.clickElement(jobslink);
	}

	public void clickAreasLink() {
        Utils.clickElement(areaslink);
	}

	public void clickEmailTemplatesLink() {
	    Utils.clickElement(emailtemplateslink);
	}

	public void clickPrintServersLink() {
        Utils.clickElement(printserverslink);
	}

	public void clickPrintTemplatesConfigurationsLink() {
        Utils.clickElement(printtemplatesconfiglink);
	}

	public void clickInsuranceCompaniesLink() {
        Utils.clickElement(insurancecompanieslink);
	}

	public void clickServiceAdvisorsLink() {
        Utils.clickElement(serviceadvisorslink);
	}

	public void clickQuestionsFormsLink() {
        Utils.clickElement(questionsformslink);
	}

	public void clickInspectionTypesLink() {
        Utils.clickElement(inspectiontypeslink);
	}

	public void clickSuppliesLink() {
        Utils.clickElement(supplieslink);
	}

	public void clickExpensesTypesLink() {
        Utils.clickElement(expensestypeslink);
	}

	public void clickInvoiceTypesLink() {
        Utils.clickElement(invoicetypeslink);
	}

	public void clickWorkOrderTypesLink() {
        Utils.clickElement(workordertypeslink);
	}

	public void clickPriceMatricesLink() {
	    Utils.clickElement(pricematriceslink);
	}

	public void clickServiceContractTypesLink() {
	    Utils.clickElement(servicecontracttypeslink);
	}

	public void clickServiceRequestTypesLink() {
	    Utils.clickElement(servicerequesttypeslink);
	}

	public void clickVehiclePartsLink() {
        Utils.clickElement(vehiclepartslink);
	}

	public void clickTimesheetTypesLink() {
        Utils.clickElement(timesheetypesslink);
	}

	public void clickManageLicencesLink() {
        Utils.clickElement(managelicenceslink);
	}

	public void clickManageDevicesLink() {
        Utils.clickElement(managedeviceslink);
	}

	public void clickInterApplicationExchangeLink() {
        Utils.clickElement(interApplicationExchangeLink);
	}
}
