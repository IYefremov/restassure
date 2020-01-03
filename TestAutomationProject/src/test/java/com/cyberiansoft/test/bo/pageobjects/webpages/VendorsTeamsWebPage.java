package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class VendorsTeamsWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
	private WebTable activeVendorsTeamsTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeamsDeleted_ctl00")
	private WebTable archivedTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeamsDeleted_ctl00")
	private WebTable archivedVendorsTeamsTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addvendorteamsbtn;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Active']")
	private WebElement activetab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Archived']")
	private WebElement archivedtab;

	//Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxTeamName")
	private TextField searchteamlocationfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
	private ComboBox searchteamtypecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_DropDown")
	private DropDown searchteamtypedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_timeZones")
	private WebElement searchteamtimezonecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	//Audit log tab
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebElement auditLogTable;

	@FindBy(id = "ctl00_Content_gv_ctl00__0")
	private WebElement auditLogTableLine1;

	@FindBy(id = "ctl00_Content_gv_ctl00__1")
	private WebElement auditLogTableLine2;

	//Vendor guests
	@FindBy(id = "ctl00_Content_comboEmployee_Input")
	private WebElement teamguestemployeecmb;

	@FindBy(id = "ctl00_Content_btnAddEmployee")
	private WebElement addteamguestemployeebtn;

	@FindBy(id = "ctl00_Content_gvEmployees_ctl00")
	private WebTable teamguestemployeestable;

	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updateteamguestemployeestablebtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeamsDeleted_ctl00_ctl03_ctl01_GoToPageTextBox")
	private WebElement openedPageForArchivedVendor;

	@FindBy(xpath = "//table[@id='ctl00_ctl00_Content_Main_gvTeamsDeleted_ctl00']" +
			"//td[@class='rgPagerCell NextPrevNumericAndAdvanced']//a[not(contains(@title, 'Next Pages'))]")
	private List<WebElement> archivedVendorpages;

	public VendorsTeamsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}

	public VendorsTeamsWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
		return PageFactory.initElements(
				driver, VendorsTeamsWebPage.class);
	}

	public VendorsTeamsWebPage setSearchTeamLocation(String teamlocation) {
		clearAndType(searchteamlocationfld, teamlocation);
		return PageFactory.initElements(
				driver, VendorsTeamsWebPage.class);
	}

	public VendorsTeamsWebPage selectSearchType(String type) {
		selectComboboxValue(searchteamtypecmb, searchteamtypedd, type);
		return PageFactory.initElements(
				driver, VendorsTeamsWebPage.class);
	}

	public VendorsTeamsWebPage selectSearchTimeZone(String timezone) {
		final Select selectBox = new Select(searchteamtimezonecmb);
		selectBox.selectByValue(timezone);
		return PageFactory.initElements(
				driver, VendorsTeamsWebPage.class);
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
	}

	public int getVendorsTeamsTableRowCount() {
		return getVendorsTeamsTableRows().size();
	}

	public List<WebElement> getVendorsTeamsTableRows() {
		return activeVendorsTeamsTable.getTableRows();
	}

	public List<WebElement> getArchivedVendorsTeamsTableRows() {
		return archivedVendorsTeamsTable.getTableRows();
	}

	public WebElement getTableRowWithVendorTeam(String vendorteam) {
		List<WebElement> rows = getVendorsTeamsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().equals(vendorteam)) {
				return row;
			}
		}
		return null;
	}

	public WebElement getTableRowWithArchivedVendorTeam(String vendorteam) {
		List<WebElement> rows = getArchivedVendorsTeamsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(vendorteam)) {
				return row;
			}
		}
		return null;
	}

	public void verifyVendorsTeamsTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(activeVendorsTeamsTable.getWrappedElement()));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Guests"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Vendor/Team"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Type"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Service Assign"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Location"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Area"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Timesheet type"));
		Assert.assertTrue(activeVendorsTeamsTable.tableColumnExists("Description"));
	}

	public boolean isActiveVendorTeamPresent(String vendorTeam) {
		return getVendorsOnPage(activeVendorsTeamsTable, vendorTeam).size() > 0;
	}

	public boolean isArchivedVendorTeamPresent(String vendorTeam) {
		return getVendorsOnPage(archivedVendorsTeamsTable, vendorTeam).size() > 0;
	}

	public int getArchivedVendorTeamsCountOnPage(String vendorTeam) {
		return getVendorsOnPage(archivedVendorsTeamsTable, vendorTeam).size();
	}

	public int getActiveVendorTeamsCountOnPage(String vendorTeam) {
		return getVendorsOnPage(activeVendorsTeamsTable, vendorTeam).size();
	}

	private List<WebElement> getVendorsOnPage(WebTable vendorElement, String vendorTeam) {

		return vendorElement.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + vendorTeam + "']"));
	}

	public int getArchivedVendorTeamsCount(String vendorTeam) {
		// the zero page should be opened by default (page 1 on the website)
		int pageNumber = 0;
		openFirstPageForArchivedVendor(archivedVendorpages);
		int archivedVendorTeamsCount = getArchivedVendorTeamsCountOnPage(vendorTeam);

		while (++pageNumber < archivedVendorpages.size()) {
			archivedVendorpages.get(pageNumber).click();
			waitForLoading();
			archivedVendorTeamsCount += getArchivedVendorTeamsCountOnPage(vendorTeam);
		}
		return archivedVendorTeamsCount;
	}

	private void openFirstPageForArchivedVendor(List<WebElement> pages) {
		if (!openedPageForArchivedVendor.getAttribute("value").equals(String.valueOf(1))) {
			pages.get(0).click();
			waitForLoading();
		}
	}

	public void verifyThatActiveVendorTeamExists(String vendorTeam, String timeZone, String vendorDescription, String vendorTimesheetType, String defLocation, String additionalLocation) {
		if (!isActiveVendorTeamPresent(vendorTeam)) {
			createNewVendorTeam(vendorTeam, timeZone, vendorDescription,
					vendorTimesheetType, defLocation, additionalLocation);
		}
	}

	public void verifyThatActiveVendorTeamExists(String vendorTeam) {
		if (!isActiveVendorTeamPresent(vendorTeam)) {
			clickArchivedTab();
			restoreVendorTeam(vendorTeam);
		}
	}

	public String getTableVendorTeamLocation(String vendorteam) {
		String vendorteamlocation = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamlocation = row.findElement(By.xpath(".//td[8]")).getText();
		} else
			Assert.fail("Can't find " + vendorteam + " vendor/team");
		return vendorteamlocation;
	}

	public String getTableVendorTeamType(String vendorteam) {
		String vendorteamtype = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamtype = row.findElement(By.xpath(".//td[6]")).getText();
		} else
			Assert.fail("Can't find " + vendorteam + " vendor/team");
		return vendorteamtype;
	}

	public String getTableVendorTeamTimeZone(String vendorteam) {
		String vendorteamtimezone = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamtimezone = row.findElement(By.xpath(".//td[9]")).getText();
		} else
			Assert.fail("Can't find " + vendorteam + " vendor/team");
		return vendorteamtimezone;
	}

	public NewVendorTeamDialogWebPage clickAddVendorTeamButton() {
		clickAndWait(addvendorteamsbtn);
		return PageFactory.initElements(
				driver, NewVendorTeamDialogWebPage.class);
	}

	public void createNewVendorTeam(String vendorname, String vendortimezone, String vendordesc, String vendortimesheettype, String defaultlocation, String additionallocation) {
		NewVendorTeamDialogWebPage newvendordialog = clickAddVendorTeamButton();
		newvendordialog.setNewVendorTeamName(vendorname);
		newvendordialog.selectNewVendorTeamTimezone(vendortimezone);
		newvendordialog.setNewVendorTeamDescription(vendordesc);
		newvendordialog.selectNewVendorTeamTimesheetType(vendortimesheettype);
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(defaultlocation);
		newvendordialog.selectNewVendorTeamAdditionalRepairLocations(additionallocation);
		newvendordialog.clickOKButton();
	}

	public void clickEditVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			clickEditTableRow(row);
		} else
			Assert.fail("Can't find " + vendorteam + " vendor/team");
	}

	public void archiveVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			archiveTableRow(row);
		} else {
			Assert.fail("Can't find " + vendorteam + " vendor/team");
		}
	}

	public void restoreVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithArchivedVendorTeam(vendorteam);
		if (row != null) {
			restoreTableRow(row);
		} else
			Assert.fail("Can't find archived " + vendorteam + " vendor/team");
	}

	public void archiveVendorTeamAndCancelArchiving(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			clickArchiveTableRow(row);
		} else {
			Assert.fail("Can't find " + vendorteam + " vendor/team");
		}
	}

	public void clickArchivedTab() {
		clickAndWait(archivedtab);
	}

	public void clickActiveTab() {
		wait.until(ExpectedConditions.elementToBeClickable(activetab));
		clickAndWait(activetab);
	}

	public Set<String> clickAuditLogButtonForVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[@title='Audit Log']")));
		} else
			Assert.fail("Can't find " + vendorteam + " invoice type");

		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return driver.getWindowHandles();
	}

	public List<WebElement> getVendorsTeamsAuditLogTableRows() {
		return auditLogTable.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_Content_gv_ctl00')]"));
	}

	public WebElement getAuditLogVendorsTeamsTable() {
		return auditLogTable;
	}

	public String getAuditLogVendorsTeamsTableLine1Text() {
		return auditLogTableLine1.findElement(By.xpath("./td[3]")).getText();
	}

	public String getAuditLogVendorsTeamsTableLine2Text() {
		return auditLogTableLine2.findElement(By.xpath("./td[3]")).getText();
	}

	public Set<String> clickGuestsLinkForVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			row.findElement(By.xpath(".//a[text()='Guests']")).click();
		} else
			Assert.fail("Can't find " + vendorteam + " invoice type");

		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return driver.getWindowHandles();
	}

	public void selectTeamGuestEmployees(String employeename) {
		wait.until(ExpectedConditions.visibilityOf(teamguestemployeecmb)).click();
		teamguestemployeecmb.sendKeys(employeename);
		waitABit(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='" + employeename + "']"))).click();
	}

	public void clickAddTeamGuestEmployeesButton() {
		clickAndWait(addteamguestemployeebtn);
	}

	public void clickUpdateTeamGuestEmployeesButton() {
		clickAndWait(updateteamguestemployeestablebtn);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Guest employees have been updated for this Team']")).isDisplayed());
	}

	public List<WebElement> getVendorsTeamGuestEmployeesTableRows() {
		return teamguestemployeestable.getTableRows();
	}

	public boolean isTeamGuestEmployeesExists(String employeename) {
		return teamguestemployeestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
	}

	public WebElement getTableRowWithTeamGuestEmployee(String employeename) {
		List<WebElement> rows = getVendorsTeamGuestEmployeesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[2]")).getText().equals(employeename)) {
				return row;
			}
		}
		return null;
	}

	public void deleteTeamGuestEmployee(String employeename) {
		WebElement row = getTableRowWithTeamGuestEmployee(employeename);
		if (row != null) {
			clickDeleteTableRow(row);
		} else {
			Assert.fail("Can't find " + employeename + " team guest employee");
		}
	}
}
