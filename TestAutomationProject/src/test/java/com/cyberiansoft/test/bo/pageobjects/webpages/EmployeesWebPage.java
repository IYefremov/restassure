package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

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

	public EmployeesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		wait.until(ExpectedConditions.visibilityOf(employeestable.getWrappedElement()));
	}

	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}

	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}

	public void verifyEmployeesTableColumnsAreVisible() {
		Assert.assertTrue(employeestable.isTableColumnExists("Team"));
		Assert.assertTrue(employeestable.isTableColumnExists("Employee"));
		Assert.assertTrue(employeestable.isTableColumnExists("Password"));
		Assert.assertTrue(employeestable.isTableColumnExists("Roles"));
		Assert.assertTrue(employeestable.isTableColumnExists("Accounting ID"));
		Assert.assertTrue(employeestable.isTableColumnExists("Address"));
		Assert.assertTrue(employeestable.isTableColumnExists("Type"));
		Assert.assertTrue(employeestable.isTableColumnExists("Email"));
		Assert.assertTrue(employeestable.isTableColumnExists("Phone"));
		Assert.assertTrue(employeestable.isTableColumnExists("Commissions"));
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

	public void clickFindButton() throws InterruptedException {
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
	
//TODO
	public WebElement getTableRowWithActiveEmployee(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		List<WebElement> employeestablerows = getEmployeesTableRows();
		for (WebElement employeestablerow : employeestablerows) {
			if (employeestablerow.findElement(By.xpath(".//td["+ employeestable.getTableColumnIndex("Employee") +"]")).getText().equals(employeename)) {
				return employeestablerow;
			}
		}
		return null;
	}

	public WebElement getTableRowWithArchivedEmployee(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		List<WebElement> employeestablerows = getArchivedEmployeesTableRows();
		for (WebElement employeestablerow : employeestablerows) {
			if (employeestablerow.findElement(By.xpath(".//td[3]")).getText().equals(employeename)) {
				return employeestablerow;
			}
		}
		return null;
	}

	public List<String> getActiveEmployees() {
		List<String> employee = new ArrayList<String>();
		List<WebElement> employeeact = employeestable.getWrappedElement()
				.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gv_ctl00')]/td[4]"));
		for (WebElement useractcell : employeeact) {
			employee.add(useractcell.getText());
		}
		return employee;
	}

	public String verifyEmployeesDuplicatesArchived(List<String> employeeact) {
		List<String> employeearch = new ArrayList<String>();
		List<WebElement> usercolumnsarch = archivedemployeestable.getWrappedElement()
				.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gvDeleted_ctl00')]/td[3]"));
		for (WebElement useractcell : usercolumnsarch) {
			employeearch.add(useractcell.getText());
		}
		return findDuplicateNames(employeeact, employeearch);

	}

	public void archiveEmployee(String firstname, String lastname) throws InterruptedException {
		waitABit(1000);
		WebElement row = getTableRowWithActiveEmployee(firstname, lastname);
		if (row != null) {
			archiveTableRow(row);
		} else
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " employee");
	}

	public void unarchiveEmployee(String firstname, String lastname) throws InterruptedException {
		WebElement row = getTableRowWithArchivedEmployee(firstname, lastname);
		if (row != null) {
			restoreTableRow(row);
		} else
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " employee");
	}

	public String findDuplicateNames(List<String> employeeact, List<String> employeearch) {
		String duplicates = "";
		for (String username : employeeact) {
			if (employeearch.contains(username))
				duplicates = username;
		}
		return duplicates;
	}

	public boolean isActiveEmployeeExists(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		wait.until(ExpectedConditions.visibilityOf(employeestable.getWrappedElement()));
		boolean exists = employeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
		return exists;
	}

	public boolean isArchivedEmployeeExists(String firstname, String lastname) {
		String employeename = firstname + " " + lastname;
		wait.until(ExpectedConditions.visibilityOf(archivedemployeestable.getWrappedElement()));
		boolean exists = archivedemployeestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
		return exists;
	}

	public NewEmployeeDialogWebPage clickAddEmployeeButton() {
		clickAndWait(employeeaddbtn);
		return PageFactory.initElements(driver, NewEmployeeDialogWebPage.class);
	}

	public NewEmployeeDialogWebPage clickEditEmployee(String firstname, String lastname) {
		WebElement row = getTableRowWithActiveEmployee(firstname, lastname);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + firstname + " " + lastname + " employee");
		}
		return PageFactory.initElements(driver, NewEmployeeDialogWebPage.class);
	}
}
