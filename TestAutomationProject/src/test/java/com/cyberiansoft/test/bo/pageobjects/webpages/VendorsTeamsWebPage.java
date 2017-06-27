package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.Set;
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

public class VendorsTeamsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeams_ctl00")
	private WebTable vendorsteamstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvTeamsDeleted_ctl00")
	private WebTable archivedvendorsteamstable;
	
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
	private WebElement auditlogtable;
	
	//Vendor guests
	@FindBy(id = "ctl00_Content_comboEmployee_Input")
	private WebElement teamguestemployeecmb;
	
	@FindBy(id = "ctl00_Content_btnAddEmployee")
	private WebElement addteamguestemployeebtn;
	
	@FindBy(id = "ctl00_Content_gvEmployees_ctl00")
	private WebTable teamguestemployeestable;
	
	@FindBy(id = "ctl00_Content_btnUpdate")
	private WebElement updateteamguestemployeestablebtn;
	
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
	
	public VendorsTeamsWebPage selectSearchTimeZone(String timezone)  { 
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
	
	public List<WebElement>  getVendorsTeamsTableRows() {
		return vendorsteamstable.getTableRows();
	}
	
	public List<WebElement>  getArchivedVendorsTeamsTableRows() {
		return archivedvendorsteamstable.getTableRows();
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
		wait.until(ExpectedConditions.visibilityOf(vendorsteamstable.getWrappedElement()));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Guests"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Vendor/Team"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Type"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Service Assign"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Location"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Area"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Timesheet type"));
		Assert.assertTrue(vendorsteamstable.isTableColumnExists("Description"));
	}
	
	public boolean isVendorTeamExists(String vendorteam) {
		boolean exists =  vendorsteamstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + vendorteam + "']")).size() > 0;
		return exists;
	}
	
	public boolean isArchivedVendorTeamExists(String vendorteam) {
		boolean exists =  archivedvendorsteamstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + vendorteam + "']")).size() > 0;
		return exists;
	}
	
	public String getTableVendorTeamLocation(String vendorteam) {
		String vendorteamlocation = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamlocation = row.findElement(By.xpath(".//td[8]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");
		return vendorteamlocation;
	}
	
	public String getTableVendorTeamType(String vendorteam) {
		String vendorteamtype = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamtype = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");
		return vendorteamtype;
	}
	
	public String getTableVendorTeamTimeZone(String vendorteam) {
		String vendorteamtimezone = "";
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			vendorteamtimezone = row.findElement(By.xpath(".//td[9]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");
		return vendorteamtimezone;
	}
	
	public NewVendorTeamDialogWebPage clickAddVendorTeamButton() {
		clickAndWait(addvendorteamsbtn);
		return PageFactory.initElements(
				driver, NewVendorTeamDialogWebPage.class);
	}
	
	public void createNewVendorTeam(String vendorname, String vendortimezone, String vendordesc, String vendortimesheettype, String defaultlocation, String additionallocation) throws InterruptedException {
		NewVendorTeamDialogWebPage newvendordialog = clickAddVendorTeamButton();
		newvendordialog.setNewVendorTeamName(vendorname);
		newvendordialog.selectNewVendorTeamTimezone(vendortimezone);
		newvendordialog.setNewVendorTeamDescription(vendordesc);
		newvendordialog.selectNewVendorTeamTimesheetType(vendortimesheettype);
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(defaultlocation);
		newvendordialog.selectNewVendorTeamAdditionalRepairLocations(additionallocation);
		newvendordialog.clickOKButton();
	}
	
	public NewVendorTeamDialogWebPage clickEditVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");
		return PageFactory.initElements(
				driver, NewVendorTeamDialogWebPage.class);
	}
	
	public void archiveVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			archiveTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");	
		}
	}
	
	public void restoreVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithArchivedVendorTeam(vendorteam);
		if (row != null) {
			restoreTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find archived " + vendorteam + " vendor/team");			
	}
	
	public void archiveVendorTeamAndCancelArchiving(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			clickArchiveTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + vendorteam + " vendor/team");	
		}
	}
	
	public void clickArchivedTab() {
		clickAndWait(archivedtab);
	}
	
	public void clickActiveTab() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(activetab));
		clickAndWait(activetab);
	}
	
	public Set<String> clickAuditLogButtonForVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[@title='Audit Log']")));
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " invoice type");
		
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
		return auditlogtable.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_Content_gv_ctl00')]"));
	}
	
	public WebElement getAuditLogVendorsTeamsTable() {
		return auditlogtable;
	}

	public Set<String> clickGuestsLinkForVendorTeam(String vendorteam) {
		WebElement row = getTableRowWithVendorTeam(vendorteam);
		if (row != null) {
			row.findElement(By.xpath(".//a[text()='Guests']")).click();
		} else 
			Assert.assertTrue(false, "Can't find " + vendorteam + " invoice type");
		
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
		boolean exists =  teamguestemployeestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + employeename + "']")).size() > 0;
		return exists;
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
			Assert.assertTrue(false, "Can't find " + employeename + " team guest employee");	
		}
	}

}
