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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class RepairLocationsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvLocations_ctl00")
	private WebTable repairlocationstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvLocations_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addrepairlocationbtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
	private TextField searchlocationfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
	private ComboBox searchstatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_DropDown")
	private DropDown searchstatusdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;
	
	public RepairLocationsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 20)
		  .until(ExpectedConditions.visibilityOf(repairlocationstable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public RepairLocationsWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
		return PageFactory.initElements(
				driver, RepairLocationsWebPage.class);
	}
	
	public RepairLocationsWebPage setSearchLocation(String typelocation) { 
		clearAndType(searchlocationfld, typelocation);
		return PageFactory.initElements(
				driver, RepairLocationsWebPage.class);
	}
	
	public RepairLocationsWebPage selectSearchStatus(String status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
		return PageFactory.initElements(
				driver, RepairLocationsWebPage.class);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public NewRepairLocationDialogWebPage clickAddRepairLocationButton() { 
		clickAndWait(addrepairlocationbtn);
		return PageFactory.initElements(
				driver, NewRepairLocationDialogWebPage.class);
	}
	
	public void addNewRepairLocation(String repairlocationname, String approxrepairtime, String workingday, String starttime, String finishtime, boolean phaseenforcement) { 
		NewRepairLocationDialogWebPage newrepairlocdialog = clickAddRepairLocationButton();
		newrepairlocdialog.setNewRepairLocationName(repairlocationname);
		newrepairlocdialog.setNewRepairLocationApproxRepairTime(approxrepairtime);
		newrepairlocdialog.setNewRepairLocationWorkingHours(workingday, starttime, finishtime);	
		if (phaseenforcement)
			newrepairlocdialog.selectPhaseEnforcementOption();
		newrepairlocdialog.clickOKButton();
	}
	
	public void addPhaseForRepairLocation(String repairlocationname, String phasename, String phasetype, String transitiontime, String repairtime, boolean trackindividualstatuses) { 
		final String mainWindowHandle = driver.getWindowHandle();
		RepairLocationPhasesTabWebPage repairlocationphasestab = clickRepairLocationPhasesLink(repairlocationname);
		repairlocationphasestab.clickAddPhasetButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(phasename);
		repairlocationphasestab.selectNewRepairLocationPhaseType(phasetype);
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(transitiontime);
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(repairtime);
		
		if (trackindividualstatuses)
			repairlocationphasestab.selectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		closeNewTab(mainWindowHandle);
	}
	
	public void assignServiceForRepairLocation(String repairlocationname, String servicename, String phase) { 
		final String mainWindowHandle = driver.getWindowHandle();
		RepairLocationPhaseServicesTabWebPage repairlocationphaseservicestab = clickRepairLocationServicesLink(repairlocationname);
		repairlocationphaseservicestab.selectPhase(phase);
		repairlocationphaseservicestab.selectPhaseServiceInTable(servicename);
		repairlocationphaseservicestab.clickAssignToSelectedservicesButton();
		closeNewTab(mainWindowHandle);		
	}
	
	public RepairLocationPhasesTabWebPage clickRepairLocationPhasesLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Phases']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationPhasesTabWebPage.class);
	}
	
	public RepairLocationPhaseServicesTabWebPage clickRepairLocationServicesLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Services']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationPhaseServicesTabWebPage.class);
	}
	
	public RepairLocationDepartmentsTabWebPage clickRepairLocationDepartmentsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Departments']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationDepartmentsTabWebPage.class);
	}
	
	public RepairLocationClientsTabWebPage clickRepairLocationClientsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Clients']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationClientsTabWebPage.class);
	}
	
	public RepairLocationManagersTabWebPage clickRepairLocationManagersLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Managers']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationManagersTabWebPage.class);
	}
	
	public RepairLocationUserSettingsTabWebPage clickRepairLocationUserSettingsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='User Settings']")));
			waitForNewTab();
		    String mainWindowHandle = driver.getWindowHandle();
		    driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
		return PageFactory.initElements(
				driver, RepairLocationUserSettingsTabWebPage.class);
	}
	
	public NewRepairLocationDialogWebPage clickEditRepairLocation(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocation + " repair location");	
		}
		return PageFactory.initElements(
				driver, NewRepairLocationDialogWebPage.class);
	}
	
	public void deleteRepairLocation(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocation + " repair location");	
		}
	}
	
	public void deleteRepairLocationifExists(String repairlocation) {
		if (isRepairLocationExists(repairlocation)) {
			deleteRepairLocation(repairlocation);
		}
	}
	
	public void deleteRepairLocationAndCancelDeleting(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocation + " repair location");	
		}
	}
	
	public int getRepairLocationsTableRowCount() {
		return repairlocationstable.getTableRowCount();
	}
	
	public List<WebElement>  getRepairLocationsTableRows() {
		return repairlocationstable.getTableRows();
	}
	
	public WebElement getTableRowWithRepairLocation(String repairlocation) {
		List<WebElement> rows = getRepairLocationsTableRows();		
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[9]")).getText().equals(repairlocation)) {
				return row;
			}
		} 
		return null;
	}
	
	public void verifyRRepairLocationsTableColumnsAreVisible() {		
		Assert.assertTrue(repairlocationstable.isTableColumnExists("Phases"));
		Assert.assertTrue(repairlocationstable.isTableColumnExists("Services"));
		Assert.assertTrue(repairlocationstable.isTableColumnExists("Managers"));
		Assert.assertTrue(repairlocationstable.isTableColumnExists("User Settings"));
		Assert.assertTrue(repairlocationstable.isTableColumnExists("Location"));
		Assert.assertTrue(repairlocationstable.isTableColumnExists("Status"));
	}
	
	public boolean isRepairLocationExists(String repairlocation) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  repairlocationstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + repairlocation + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
}
