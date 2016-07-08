package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	public boolean searchPanelIsExpanded() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		return searchtab.getAttribute("class").contains("open");
	}
	
	public TeamsWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
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
	
	public void verifyTeamsTableColumnsAreVisible() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		Assert.assertTrue(teamstable.isTableColumnExists("Guests"));
		Assert.assertTrue(teamstable.isTableColumnExists("Type"));
		Assert.assertTrue(teamstable.isTableColumnExists("Team"));
		Assert.assertTrue(teamstable.isTableColumnExists("Services"));
		Assert.assertTrue(teamstable.isTableColumnExists("Location"));
		Assert.assertTrue(teamstable.isTableColumnExists("Area"));
		Assert.assertTrue(teamstable.isTableColumnExists("Timesheet type"));
		Assert.assertTrue(teamstable.isTableColumnExists("Time Zone"));
		Assert.assertTrue(teamstable.isTableColumnExists("Description"));
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
	
	public boolean isTeamExists(String team) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(teamstable.getWrappedElement()));
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  teamstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + team + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
		if (isTeamExists(team)) {
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
}
