package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

@Getter
public class TeamsWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
	private WebTable teamsTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addteambtn;

	//Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxTeamName")
	private TextField searchteamlocationfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
	private ComboBox searchTypeCbx;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_DropDown")
	private DropDown searchTypeDd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_timeZones")
	private WebElement searchtimezonecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@title='Delete']")
	private WebElement deletemarker;

	public TeamsWebPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}


	public boolean searchPanelIsExpanded() {
		wait.until(ExpectedConditions.visibilityOf(teamsTable.getWrappedElement()));
		return searchtab.getAttribute("class").contains("open");
	}

	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
			waitABit(2000);
		}
	}

	public void clickAddTeamButton() {
		click(addteambtn);
	}

	public boolean isAddTeamButtonExists() {
		return addteambtn.isDisplayed();
	}

	public void createNewTeam(String team) {
		NewTeamsDialogWebPage newteamsdialog = new NewTeamsDialogWebPage(this.driver);
		clickAddTeamButton();
		newteamsdialog.setNewTeamName(team);
		newteamsdialog.clickAddTeamOKButton();
	}

	public void createNewTeam(String team, String area) {
		NewTeamsDialogWebPage newteamsdialog = new NewTeamsDialogWebPage(this.driver);
		clickAddTeamButton();
		newteamsdialog.setNewTeamName(team);
		newteamsdialog.selectTeamArea(area);
		newteamsdialog.clickAddTeamOKButton();
	}

	public void verifyTeamsTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(teamsTable.getWrappedElement()));
		Assert.assertTrue(teamsTable.tableColumnExists("Guests"));
		Assert.assertTrue(teamsTable.tableColumnExists("Managers"));
		Assert.assertTrue(teamsTable.tableColumnExists("Service Prices"));
		Assert.assertTrue(teamsTable.tableColumnExists("Team"));
		Assert.assertTrue(teamsTable.tableColumnExists("Type"));
		Assert.assertTrue(teamsTable.tableColumnExists("Service Assign"));
		Assert.assertTrue(teamsTable.tableColumnExists("Location"));
		Assert.assertTrue(teamsTable.tableColumnExists("Area"));
		Assert.assertTrue(teamsTable.tableColumnExists("Timesheet type"));
		Assert.assertTrue(teamsTable.tableColumnExists("Time Zone"));
		Assert.assertTrue(teamsTable.tableColumnExists("Description"));
	}

	public String getTableTeamType(String team) {
		String teamtype = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtype = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Type") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamtype;
	}

	public String getTableTeamLocation(String team) {
		String teamlocation = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamlocation = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Location") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamlocation;
	}

	public String getTableTeamArea(String team) {
		String teamarea = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamarea = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Area") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamarea;
	}

	public String getTableTeamTimesheetType(String team) {
		String teamtimesheettype = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtimesheettype = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Timesheet type") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamtimesheettype;
	}

	public String getTableTeamTimeZone(String team) {
		String teamtimezone = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtimezone = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Time Zone") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamtimezone;
	}

	public String getTableTeamDescription(String team) {
		String teamdesc = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamdesc = row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Description") + "]")).getText();
		} else
			Assert.fail("Can't find " + team + " team");
		return teamdesc;
	}


	public void clickFindButton() {
		clickAndWait(findbtn);
	}

	public int getTeamsTableRowsCount() {
		return getTeamsTableRows().size();
	}

	public List<WebElement> getTeamsTableRows() {
		return teamsTable.getTableRows();
	}

	public TeamsWebPage setTeamLocationSearchCriteria(String teamlocation) {
		clearAndType(searchteamlocationfld, teamlocation);
		return PageFactory.initElements(
				driver, TeamsWebPage.class);
	}

	public void selectSearchType(String _type) {
		selectComboboxValue(searchTypeCbx, searchTypeDd, _type);
	}

	public void selectSearchTimeZone(String timezone) {
		final Select selectBox = new Select(searchtimezonecmb);
		selectBox.selectByValue(timezone);
	}

	public void verifySearchResultsByTeamLocation(String tmlocation) {
		Assert.assertTrue(teamsTable.getWrappedElement().findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gvTeams_ctl00')]/td[text()='" + tmlocation + "']")).size() > 0);
	}

	public WebElement getTableRowWithTeam(String team) {
		List<WebElement> rows = getTeamsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + teamsTable.getTableColumnIndex("Team") + "]")).getText().contains(team)) {
				return row;
			}
		}
		return null;
	}

	public boolean teamExists(String team) {
		wait.until(ExpectedConditions.visibilityOf(teamsTable.getWrappedElement()));
		return teamsTable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + team + "']")).size() > 0;
	}

	public void clickEditTeam(String team) {
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			clickEditTableRow(row);
		} else
			Assert.fail("Can't find " + team + " team");
		waitABit(1000);
	}

	public void deleteTeam(String team) {
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			deleteTableRow(row);
		} else
			Assert.fail("Can't find " + team + " team");
	}

	public void deleteTeamIfExists(String team) {
		while (teamExists(team)) {
			deleteTeam(team);
		}
	}

	public void deleteTeamAndCancelDeleting(String team) {
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else
			Assert.fail("Can't find " + team + " team");
	}

	public void verifyTeamsDoNotExist(String team, String teamedited) {
		while (teamExists(team)) {
			deleteTeam(team);
		}
		while (teamExists(teamedited)) {
			deleteTeam(teamedited);
		}
	}
}
