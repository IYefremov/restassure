package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class EmployeesWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable employeestable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvDeleted_ctl00")
	private WebTable archivedemployeestable;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Active']")
	private WebElement activetab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Archived']")
	private WebElement archivedtab;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement employeeaddbtn;

	// Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboTeams_Input")
	private ComboBox searchteamcbx;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboTeams_DropDown")
	private DropDown searchteamdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
	private TextField searchemployeefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(xpath = "//li[contains(@class, 'rtsLast')]/a[contains(@class, 'rtsSelected')]//span[text()='Active']")
	private WebElement selectedActiveTab;

	public EmployeesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void verifyEmployeeIsActive(String employeefirstname, String employeelastname) {
		String employeename = employeefirstname + " " + employeelastname;
		List<WebElement> activeEmployees = employeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']"));
		try {
			if (activeEmployees.isEmpty()) {
				clickArchivedTab();
				unarchiveEmployee(employeefirstname, employeelastname);
				clickActiveTab();
			}
		} catch (Exception e) {
			System.err.println("The employee is not found: " + e);
		}
	}

	public void verifyEmployeeIsActive(String employeeName) {
		List<WebElement> activeEmployees = employeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeeName + "']"));
		try {
			if (activeEmployees.isEmpty()) {
				clickArchivedTab();
				unarchiveEmployee(employeeName);
				clickActiveTab();
			}
		} catch (Exception e) {
			System.err.println("The employee is not found: " + e);
		}
	}

	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}

	public EmployeesWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
		return this;
	}

	public void verifyEmployeesTableColumnsAreVisible() {
		Assert.assertTrue(employeestable.tableColumnExists("Team"));
		Assert.assertTrue(employeestable.tableColumnExists("Employee"));
		Assert.assertTrue(employeestable.tableColumnExists("Password"));
		Assert.assertTrue(employeestable.tableColumnExists("Roles"));
		Assert.assertTrue(employeestable.tableColumnExists("Accounting ID"));
		Assert.assertTrue(employeestable.tableColumnExists("Type"));
		Assert.assertTrue(employeestable.tableColumnExists("Address"));
		Assert.assertTrue(employeestable.tableColumnExists("Email"));
		Assert.assertTrue(employeestable.tableColumnExists("Phone"));
		Assert.assertTrue(employeestable.tableColumnExists("Commissions"));
	}

	public void verifyTabsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(activetab));
		Assert.assertTrue(activetab.isDisplayed());
		Assert.assertTrue(archivedtab.isDisplayed());
	}

	public void clickArchivedTab() {
		clickAndWait(archivedtab);
	}

	public void clickActiveTab() {
		wait.until(ExpectedConditions.elementToBeClickable(activetab));
		clickAndWait(activetab);
	}

	public void selectSearchTeam(String team) {
		selectComboboxValue(searchteamcbx, searchteamdd, team);
	}

	public void setSearchUserParameter(String username) {
		clearAndType(searchemployeefld, username);
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
	}

	public int getEmployeesTableRowCount() {
		return getEmployeesTableRows().size();
	}

	public List<WebElement> getEmployeesTableRows() {
		return employeestable.getTableRows();
	}

	public List<WebElement> getArchivedEmployeesTableRows() {
		return archivedemployeestable.getTableRows();
	}

	public WebElement getTableRowWithActiveEmployee(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		try {
			List<WebElement> employeestablerows = getEmployeesTableRows();
			for (WebElement employeestablerow : employeestablerows) {
				if (employeestablerow.findElement(By.xpath(".//td[" + employeestable
						.getTableColumnIndex("Employee") + "]")).getText().equals(employeename)) {
					return employeestablerow;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("The active employee is not displayed in the table!\n" + e);
		}
		return null;
	}

	public WebElement getTableRowWithActiveEmployee(String employeeName) {
		try {
			List<WebElement> employeestablerows = getEmployeesTableRows();
			for (WebElement employeestablerow : employeestablerows) {
				if (employeestablerow.findElement(By.xpath(".//td[" + employeestable
						.getTableColumnIndex("Employee") + "]")).getText().equals(employeeName)) {
					return employeestablerow;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("The active employee is not displayed in the table!\n" + e);
		}
		return null;
	}

	public WebElement getTableRowWithActiveEmployeeAndTeam(String employeeName, String teamName) {
		try {
			List<WebElement> employeestablerows = getEmployeesTableRows();
			for (WebElement employeestablerow : employeestablerows) {
				if (employeestablerow.findElement(By.xpath(".//td[" + employeestable
						.getTableColumnIndex("Employee") + "]")).getText().equals(employeeName) &&
						employeestablerow.findElement(By.xpath(".//td[" + employeestable
								.getTableColumnIndex("Team") + "]")).getText().equals(teamName)) {
					return employeestablerow;
				}
			}
		} catch (NoSuchElementException ignored) {
		}
		return null;
	}

	public WebElement getTableRowWithArchivedEmployee(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		try {
			List<WebElement> employeestablerows = getArchivedEmployeesTableRows();
			for (WebElement employeestablerow : employeestablerows) {
				if (employeestablerow.findElement(By.xpath(".//td[3]")).getText().equals(employeename)) {
					return employeestablerow;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("The archived employee is not displayed in the table!\n" + e);
		}
		return null;
	}

	public WebElement getTableRowWithArchivedEmployee(String employeename) {
		try {
			List<WebElement> employeestablerows = getArchivedEmployeesTableRows();
			for (WebElement employeestablerow : employeestablerows) {
				if (employeestablerow.findElement(By.xpath(".//td[3]")).getText().equals(employeename)) {
					return employeestablerow;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("The archived employee is not displayed in the table!\n" + e);
		}
		return null;
	}

	public List<String> getActiveEmployees() {
		List<String> employee = new ArrayList<>();
		List<WebElement> employeeact = employeestable.getWrappedElement()
				.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gv_ctl00')]/td[4]"));
		for (WebElement useractcell : employeeact) {
			employee.add(useractcell.getText());
		}
		return employee;
	}

	public String verifyEmployeesDuplicatesArchived(List<String> employeeact) {
		List<String> employeearch = new ArrayList<>();
		List<WebElement> usercolumnsarch = archivedemployeestable.getWrappedElement()
				.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gvDeleted_ctl00')]/td[3]"));
		for (WebElement useractcell : usercolumnsarch) {
			employeearch.add(useractcell.getText());
		}
		return findDuplicateNames(employeeact, employeearch);

	}

	public void archiveEmployee(String employeeName) {
		WebElement row = getTableRowWithActiveEmployee(employeeName);
		if (row != null) {
			archiveTableRow(row);
		} else
			Assert.fail("Can't find " + employeeName + " employee");
	}

	public void archiveEmployee(String firstname, String lastname) {
		waitABit(3000);
		WebElement row = getTableRowWithActiveEmployee(firstname, lastname);
		if (row != null) {
			archiveTableRow(row);
		} else
			Assert.fail("Can't find " + firstname + " " + lastname + " employee");
	}

	public void unarchiveEmployee(String firstname, String lastname) {
		WebElement row = getTableRowWithArchivedEmployee(firstname, lastname);
		if (row != null) {
			restoreTableRow(row);
		} else
			Assert.fail("Can't find " + firstname + " " + lastname + " employee");
	}

	public void unarchiveEmployee(String employeename) {
		WebElement row = getTableRowWithArchivedEmployee(employeename);
		if (row != null) {
			restoreTableRow(row);
		} else {
			Assert.fail("Can't find " + employeename + " employee");
		}
	}

	public String findDuplicateNames(List<String> employeeact, List<String> employeearch) {
		String duplicates = "";
		for (String username : employeeact) {
			if (employeearch.contains(username))
				duplicates = username;
		}
		return duplicates;
	}

	public boolean activeEmployeeExists(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		wait.until(ExpectedConditions.visibilityOf(employeestable.getWrappedElement()));
		return employeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
	}

	public boolean archivedEmployeeExists(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		wait.until(ExpectedConditions.visibilityOf(archivedemployeestable.getWrappedElement()));
		return archivedemployeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
	}

	public boolean activeEmployeeExists(String employeename) {
		wait.until(ExpectedConditions.visibilityOf(employeestable.getWrappedElement()));
		return employeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
	}

	public boolean archivedEmployeeExists(String employeename) {
		wait.until(ExpectedConditions.visibilityOf(archivedemployeestable.getWrappedElement()));
		return archivedemployeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
	}

	public void clickAddEmployeeButton() {
		clickAndWait(employeeaddbtn);
	}

	public void clickEditEmployee(String firstname, String lastname) {
		WebElement row = getTableRowWithActiveEmployee(firstname, lastname);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.fail("Can't find " + firstname + " " + lastname + " employee");
		}
	}

	public void clickEditEmployee(String employeeName) {
		WebElement row = getTableRowWithActiveEmployee(employeeName);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.fail("Can't find " + employeeName + " employee");
		}
	}

	public void clickEditEmployeeFromTeam(String employeeName, String teamName) {
		WebElement row = getTableRowWithActiveEmployeeAndTeam(employeeName, teamName);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			changeTeamForEmployee(employeeName, teamName);
			clickEditTableRow(getTableRowWithActiveEmployeeAndTeam(employeeName, teamName));
		}
	}

	private void changeTeamForEmployee(String employeeName, String teamName) {
		clickEditEmployee(employeeName);
		NewEmployeeDialogWebPage newEmployeeDialog = new NewEmployeeDialogWebPage(driver);
		InfoContentDialogWebPage infoContentDialog = new InfoContentDialogWebPage(driver);
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.verifyInfoContentDialogIsDisplayed(),
				"The Info Content Dialog has not been opened");
		infoContentDialog.chooseEmployeeToReassign(employeeName);
		infoContentDialog.reassignEmployee();
		newEmployeeDialog.selectNewEmployeeTeam(teamName);
		newEmployeeDialog.clickOKButton();
	}

	public void verifyActiveEmployeeDoesNotExist(String employeefirstname, String employeelastname,
												 String employeefirstnameed, String employeelastnameed) {
		if (activeEmployeeExists(employeefirstname, employeelastname)) {
			archiveEmployee(employeefirstname, employeelastname);
		}
		if (activeEmployeeExists(employeefirstnameed, employeelastnameed)) {
			archiveEmployee(employeefirstnameed, employeelastnameed);
		}
	}

	public void verifyActiveEmployeeDoesNotExist(String employeename) {
		makeSearchPanelVisible();
		setSearchUserParameter(employeename);
		clickFindButton();
		waitABit(2000);
		if (!activeEmployeeExists(employeename)) {
			clickArchivedTab();
			makeSearchPanelVisible();
			setSearchUserParameter(employeename);
			clickFindButton();
			unarchiveEmployee(employeename);
			clickActiveTab();
		}
	}
}
