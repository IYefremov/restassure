package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

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
	}

	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}

	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}

	public void setSearchLocation(String typelocation) {
		clearAndType(searchlocationfld, typelocation);
	}

	public void selectSearchStatus(String status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
		waitABit(3000);
	}

	public void clickAddRepairLocationButton() {
		clickAndWait(addrepairlocationbtn);
	}

	public void addNewRepairLocation(String repairlocationname, String approxrepairtime, String workingday, String starttime, String finishtime, boolean phaseenforcement) {
		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(this.driver);
		clickAddRepairLocationButton();
		newrepairlocdialog.setNewRepairLocationName(repairlocationname);
		newrepairlocdialog.setNewRepairLocationApproxRepairTime(approxrepairtime);
		newrepairlocdialog.setNewRepairLocationWorkingHours(workingday, starttime, finishtime);
		if (phaseenforcement)
			newrepairlocdialog.selectPhaseEnforcementOption();
		newrepairlocdialog.clickOKButton();
	}

	public void addPhaseForRepairLocation(String repairlocationname, String phasename, String phasetype, String transitiontime, String repairtime, boolean trackindividualstatuses) {
		final String mainWindowHandle = driver.getWindowHandle();
		RepairLocationPhasesTabWebPage repairlocationphasestab = new RepairLocationPhasesTabWebPage(this.driver);
		clickRepairLocationPhasesLink(repairlocationname);
		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(phasename);
		repairlocationphasestab.selectNewRepairLocationPhaseType(phasetype);
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(transitiontime);
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(repairtime);

		if (trackindividualstatuses)
			repairlocationphasestab.selectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		closeNewTab(mainWindowHandle);
	}

	public void assignServiceForRepairLocation(String repairlocationname, String WOType, String servicename, String phase) {
		final String mainWindowHandle = driver.getWindowHandle();
		RepairLocationPhaseServicesTabWebPage repairlocationphaseservicestab = new RepairLocationPhaseServicesTabWebPage(this.driver);
		clickRepairLocationServicesLink(repairlocationname);
//        repairlocationphaseservicestab.selectLocation(repairlocationname);
		repairlocationphaseservicestab.selectWOType(WOType);
		repairlocationphaseservicestab.selectPhase(phase);
		//todo here fails
		repairlocationphaseservicestab.selectPhaseServiceInTable(servicename);
		repairlocationphaseservicestab.clickAssignToSelectedservicesButton();
		closeNewTab(mainWindowHandle);
	}

	public void clickRepairLocationPhasesLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Phases']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickRepairLocationServicesLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Services']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickRepairLocationDepartmentsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Departments']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickRepairLocationClientsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Clients']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickRepairLocationManagersLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='Managers']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickRepairLocationUserSettingsLink(String repairlocationname) {
		WebElement row = getTableRowWithRepairLocation(repairlocationname);
		if (row != null) {
			click(row.findElement(By.xpath(".//a[text()='User Settings']")));
			waitForNewTab();
			String mainWindowHandle = driver.getWindowHandle();
			for (String activeHandle : driver.getWindowHandles()) {
				if (!activeHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(activeHandle);
				}
			}
		}
	}

	public void clickEditRepairLocation(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocation + " repair location");
		}
	}

	public void deleteRepairLocation(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + repairlocation + " repair location");
		}
	}

	public void deleteRepairLocationIfExists(String repairlocation) {
		if (repairLocationExists(repairlocation)) {
			deleteRepairLocation(repairlocation);
		}
	}

	public void deleteRepairLocationAndCancelDeleting(String repairlocation) {
		WebElement row = getTableRowWithRepairLocation(repairlocation);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.fail("Can't find " + repairlocation + " repair location");
		}
	}

	public int getRepairLocationsTableRowCount() {
		return repairlocationstable.getTableRowCount();
	}

	public List<WebElement> getRepairLocationsTableRows() {
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

	public void verifyRepairLocationsTableColumnsAreVisible() {
		Assert.assertTrue(repairlocationstable.tableColumnExists("Phases"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Services"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Managers"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("User Settings"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Location"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Status"));
	}

	public boolean repairLocationExists(String repairlocation) {
		try {
			return repairlocationstable.getWrappedElement()
					.findElements(By.xpath(".//tr/td[text()='" + repairlocation + "']")).size() > 0;
		} catch (Exception ignored) {
			return false;
		}
	}
}
