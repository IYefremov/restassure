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

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class TeamsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
	private WebTable teamstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addteambtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxTeamName")
	private TextField searchteamlocationfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
	private ComboBox searchtypecbx;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_DropDown")
	private DropDown searchtypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_timeZones")
	private WebElement searchtimezonecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@title='Delete']")
	private WebElement deletemarker;

	public TeamsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	
	public boolean searchPanelIsExpanded() {
		wait.until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		return searchtab.getAttribute("class").contains("open");
	}
	
	public TeamsWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
			waitABit(2000);
		}
		return PageFactory.initElements(
				driver, TeamsWebPage.class);
	}
	
	public NewTeamsDialogWebPage clickAddTeamButton() {
		click(addteambtn);
		return PageFactory.initElements(
				driver, NewTeamsDialogWebPage.class);
	}
	
	public boolean isAddTeamButtonExists() {
		return addteambtn.isDisplayed();
	}
	
	public void createNewTeam(String team) {
		NewTeamsDialogWebPage newteamsdialog = clickAddTeamButton();
		newteamsdialog.setNewTeamName(team);
		newteamsdialog.clickAddTeamOKButton();
	}
	
	public void createNewTeam(String team , String area) {
		NewTeamsDialogWebPage newteamsdialog = clickAddTeamButton();
		newteamsdialog.setNewTeamName(team);
		newteamsdialog.selectTeamArea(area);
		newteamsdialog.clickAddTeamOKButton();
	}
	
	public void verifyTeamsTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		Assert.assertTrue(teamstable.tableColumnExists("Guests"));
		Assert.assertTrue(teamstable.tableColumnExists("Managers"));
		Assert.assertTrue(teamstable.tableColumnExists("Tax Rates"));
		Assert.assertTrue(teamstable.tableColumnExists("Team"));
		Assert.assertTrue(teamstable.tableColumnExists("Area"));
		Assert.assertTrue(teamstable.tableColumnExists("Timesheet type"));
		Assert.assertTrue(teamstable.tableColumnExists("Time Zone"));
		Assert.assertTrue(teamstable.tableColumnExists("Description"));
	}
	
	public String getTableTeamType(String team) {
		String teamtype = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtype = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Type") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamtype;
	}
	
	public String getTableTeamLocation(String team) {
		String teamlocation = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamlocation = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Location") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamlocation;
	}
	
	public String getTableTeamArea(String team) {
		String teamarea = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamarea = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Area") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamarea;
	}
	
	public String getTableTeamTimesheetType(String team) {
		String teamtimesheettype = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtimesheettype = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Timesheet type") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamtimesheettype;
	}
	
	public String getTableTeamTimeZone(String team) {
		String teamtimezone = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamtimezone = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Time Zone") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamtimezone;
	}
	
	public String getTableTeamDescription(String team) {
		String teamdesc = "";
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			teamdesc = row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Description") + "]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return teamdesc;
	}
	
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
		waitABit(3000);
	}
	
	public int getTeamsTableRowsCount() {
		return getTeamsTableRows().size();
	}
	
	public List<WebElement> getTeamsTableRows() {
		return teamstable.getTableRows();
	}
	
	public TeamsWebPage setTeamLocationSearchCriteria(String teamlocation) {
		clearAndType(searchteamlocationfld, teamlocation);
		return PageFactory.initElements(
				driver, TeamsWebPage.class);
	}

	public void selectSearchType(String _type)  { 
		selectComboboxValue(searchtypecbx, searchtypedd, _type);
	}
	
	public void selectSearchTimeZone(String timezone)  { 
		final Select selectBox = new Select(searchtimezonecmb);
	    selectBox.selectByValue(timezone);
	}
	
	public void verifySearchResultsByTeamLocation(String tmlocation) {
		Assert.assertTrue(teamstable.getWrappedElement().findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gvTeams_ctl00')]/td[text()='" + tmlocation + "']")).size() > 0);
	}
	
	public WebElement getTableRowWithTeam(String team) {
		List<WebElement> rows = getTeamsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + teamstable.getTableColumnIndex("Team") + "]")).getText().contains(team)) {
				return row;
			}
		} 
		return null;
	}	
	
	public boolean teamExists(String team) {
		wait.until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		boolean exists =  teamstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + team + "']")).size() > 0;
		return exists;
	}
	
	public NewTeamsDialogWebPage clickEditTeam(String team) {
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");
		return PageFactory.initElements(
				driver, NewTeamsDialogWebPage.class);
	}
	
	public void deleteTeam(String team) {
		WebElement row = getTableRowWithTeam(team);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + team + " team");		
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
			Assert.assertTrue(false, "Can't find " + team + " team");		
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
