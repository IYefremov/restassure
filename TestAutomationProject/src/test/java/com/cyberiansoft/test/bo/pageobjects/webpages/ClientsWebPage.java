package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class ClientsWebPage extends WebPageWithPagination {

	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable clientstable;

	@FindBy(xpath = "//table[contains(@id, 'Content_Main_gvDeleted')]")
	private WebTable clientsarchivedtable;

	@FindBy(xpath = "//a[contains(@id, 'lbInsert') and @class='add']")
	private WebElement addclientbtn;

	@FindBy(xpath = "//input[contains(@id, 'btnImport') and @value='Import']")
	private WebElement importbtn;

	@FindBy(xpath = "//input[@title='Archive']")
	private WebElement archiveButton;

	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Active']")
	private WebElement activetab;

	@FindBy(xpath = "//span[@class='rtsIn']/span[text()='Archived']")
	private WebElement archivedtab;

	// Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_comboType_Input")
	private ComboBox searchtypecbx;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_comboType_DropDown")
	private DropDown searchtypedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch")
	private TextField searchclientfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl08_filterer_BtnFind")
	private WebElement findbtn;

	// Import clients

	@FindBy(xpath = "//input[@class='ruFakeInput radPreventDecorate']")
	private WebElement importfilefld;

	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_SubmitButton') and @value='Upload file']")
	private WebElement uploadfilebtn;

	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_btnGoToGrid') and @value='Next']")
	private WebElement fileinputnextbtn;

	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_btnNext') and @value='Import']")
	private WebElement importclientsbtn;

	@FindBy(xpath = "//input[contains(@id, 'Main_importControl_Button2') and @value='To Clients']")
	private WebElement toclientsbtn;

	// Client Services
	@FindBy(id = "ctl00_Content_filterer_comboPackages_Input")
	private ComboBox servicepackagcmb;

	@FindBy(id = "ctl00_Content_filterer_comboPackages_DropDown")
	private DropDown servicepackagdd;

	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebElement clientservicestable;

	// Client Users
	@FindBy(id = "ctl00_Content_filterer_tbSearch")
	private TextField clientuserssearchfld;

	@FindBy(id = "ctl00_Content_filterer_BtnFind")
	private WebElement clientusersfindbtn;

	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientuserstable;

	@FindBy(xpath = "//label[text()='Contact Verification Disabled']")
	private WebElement contverifdisablechkbox;

	@FindBy(className = "updateProcess")
	private WebElement updateProcess;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
	private WebElement searchEmployeeFld;

	@FindBy(xpath = "(//td/span/input[contains(@id, 'ctl00_ctl00_Content_Main_gv')])[1]")
	private WebElement wholesaleCheckbox;

	@FindBy(xpath = "(//td/span/input[contains(@id, 'ctl00_ctl00_Content_Main_gv')])[2]")
	private WebElement singleWOtypeCheckbox;

	@FindBy(linkText = "Select")
	private WebElement selectButton;

	@FindBy(xpath = "//div[@class='rmSlide' and contains(@style, 'block')]")
	private WebElement slideDisplayed;

	@FindBy(className = "rmBottomArrow")
	private WebElement bottomArrow;

	@FindBy(xpath = "//span[text()='WO Types']")
	private WebElement woTypesOption;

	public ClientsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean searchPanelIsExpanded() {
		wait.until(ExpectedConditions.visibilityOf(searchbtn));
		return searchtab.getAttribute("class").contains("open");
	}

	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}

	public boolean isFirstWholeSaleCheckboxChecked() {
		return isCheckboxChecked(wholesaleCheckbox);
	}

	public boolean isFirstSingleWOtypeCheckboxChecked() {
		return isCheckboxChecked(singleWOtypeCheckbox);
	}

	public void verifyEmployeesTableColumnsAreVisible() {
		Assert.assertTrue(clientstable.tableColumnExists("Client"));
		Assert.assertTrue(clientstable.tableColumnExists("Address"));
		Assert.assertTrue(clientstable.tableColumnExists("Email"));
		Assert.assertTrue(clientstable.tableColumnExists("Phone"));
		Assert.assertTrue(clientstable.tableColumnExists("Notes"));
		Assert.assertTrue(clientstable.tableColumnExists("Wholesale"));
		Assert.assertTrue(clientstable.tableColumnExists("PO# req."));
		Assert.assertTrue(clientstable.tableColumnExists("Commission"));
		Assert.assertTrue(clientstable.tableColumnExists("Action"));
	}

	public void clickArchivedTab() {
		clickAndWait(archivedtab);
	}

	public void clickActiveTab() {
		clickAndWait(activetab);
	}

	public void clickFindButton() {
		clickAndWait(wait.until(ExpectedConditions.elementToBeClickable(findbtn)));
		waitForLoading();
	}

	public void verifyEmployeeIsActive(String clientName) {
		List<WebElement> activeClients = clientstable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + clientName + "']"));
		try {
			if (activeClients.isEmpty()) {
				clickArchivedTab();
				unarchiveClient(clientName);
				clickActiveTab();
			}
		} catch (Exception e) {
			System.err.println("The client is not found: " + e);
		}
	}

	private void unarchiveClient(String clientName) {
		WebElement row = getTableRowWithArchivedClient(clientName);
		if (row != null) {
			restoreTableRow(row);
		} else
			Assert.fail("Can't find " + clientName + " employee");
	}

	public boolean isContactVerifDisableChkboxChecked() {
		return isCheckboxChecked(contverifdisablechkbox);
	}

	public void clickContactVerifDisableChkbox() {
		if (!isContactVerifDisableChkboxChecked()) {
			clickAndWait(contverifdisablechkbox);
		}
	}

	public void unclickContactVerifDisableChkbox() {
		if (isContactVerifDisableChkboxChecked()) {
			clickAndWait(contverifdisablechkbox);
		}
	}

	public int getClientsTableRowsCount() {
		return getClientsTableRows().size();
	}

	public List<WebElement> getClientsTableRows() {
		wait.until(ExpectedConditions.visibilityOf(clientstable.getWrappedElement()));
		return clientstable.getTableRows();
	}

	public List<WebElement> getArchivedClientsTableRows() {
		return clientsarchivedtable.getTableRows();
	}

	public void setClientSearchCriteria(String name) {
		clearAndType(searchclientfld, name);
	}

	public void searchClientByName(String companyname) {
		makeSearchPanelVisible();
		setClientSearchCriteria(companyname);
		clickFindButton();
	}

	public void deleteUserViaSearch(String clientname) {
		makeSearchPanelVisible();
		setClientSearchCriteria(clientname);
		clickFindButton();
		waitForLoading();
		archiveFirstClient();
	}

	public void archiveFirstClient() {
		wait.until(ExpectedConditions.elementToBeClickable(archiveButton)).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Are you sure you want archive client?");
		alert.accept();
		waitForLoading();
	}

	public void clickAddClientButton() {
		click(addclientbtn);
	}

	public void selectSearchType(String _type) {
		selectComboboxValue(searchtypecbx, searchtypedd, _type);
	}

	public void deleteClient(String clientname) {
		driver.findElement(By.xpath("//td[text()='" + clientname + "']")).findElement(By.xpath(".."))
				.findElements(By.tagName("td")).get(1).findElement(By.tagName("input")).click();

		driver.switchTo().alert().accept();
		waitForLoading();
	}

	public void restoreClient(String clientname) {
		WebElement archivedclientstablerow = getTableRowWithArchivedClient(clientname);
		if (archivedclientstablerow != null) {
			archivedclientstablerow.findElement(By.xpath(".//td[1]/input")).click();
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			waitForLoading();
		} else {
			Assert.fail("Can't find client: " + clientname);
		}
	}

	public String moveToClientNotesGridAndGetNoteContent(String clientname) {
		String notetxt = "";
		WebElement clientstablerow = getTableRowWithClient(clientname);
		if (clientstablerow != null) {
			waitABit(1000);
			Actions action = new Actions(driver);
			action.moveToElement(clientstablerow.findElement(By.xpath("./td[" + clientstable.getTableColumnIndex("Notes") + "]/img"))).clickAndHold().build()
					.perform();
			// action.moveToElement(clientstablerow.findElement(By.xpath("./td[11]/img"))).build().perform();
			waitABit(1000);
			List<WebElement> notes = driver.findElements(By.xpath("//div[contains(@id, 'RadToolTipNote')]"));
			for (WebElement note : notes) {
				if (note.isDisplayed()) {
					notetxt = note
							.getText()
							.replaceAll("\\u00A0", "")
							.trim();
					break;
				}
			}
			action.release().build().perform();
		} else {
			Assert.fail("Can't find client: " + clientname);
		}
		return notetxt;
	}

	private WebElement getTableRowWithClient(String clientname) {
		List<WebElement> clientsTableRows = getClientsTableRows();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Client']")));
		try {
			return clientsTableRows
					.stream()
					.filter(e -> e.findElement(By
							.xpath(".//td[" + clientstable.getTableColumnIndex("Client") + "]"))
							.getText()
							.equals(clientname))
					.findFirst()
					.get();
		} catch (NoSuchElementException ignored) {
		}
		return null;
	}

	private WebElement getTableRowWithArchivedClient(String clientname) {
		try {
			List<WebElement> archivedclientstablerows = getArchivedClientsTableRows();
			for (WebElement archivedclientstablerow : archivedclientstablerows) {
				if (archivedclientstablerow.findElement(By.xpath(".//td[2]")).getText().equals(clientname)) {
					return archivedclientstablerow;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("The archived client is not displayed in the table!\n" + e);
		}
		return null;
	}

	public void clickEditClient(String clientname) {
		WebElement clientstablerow = getTableRowWithClient(clientname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.fail("Can't find client: " + clientname);
		}
	}

	// method for click on client users link and open new dialog window
	public void clickClientUsersLinkForClientOpenDialogWindow(String clientname) {
		clickInspectionSelectExpandableMenu(clientname, "Client Users");
		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}


	private WebElement clickSelectButtonForClient(String clientname) {
		WebElement row = getTableRowWithClient(clientname);
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath(".//span[text()='Select']"))).click().build().perform();
			waitABit(300);
			act.click(row.findElement(By.xpath(".//span[text()='Select']"))).build().perform();
		}
		return row;
	}

	public boolean isWOtypeForFirstClientDisplayed() {
		Actions actions = new Actions(driver);
		actions.moveToElement(selectButton).click().build().perform();

		try {
			wait.until(ExpectedConditions.visibilityOf(slideDisplayed));
			if (woTypesOption.isDisplayed()) {
				return true;
			}
		} catch (Exception e) {
			try {
				actions.moveToElement(selectButton).moveToElement(bottomArrow).pause(Duration.ofMillis(1000)).build().perform();
				wait.until(ExpectedConditions.visibilityOf(woTypesOption));
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public void clickInspectionSelectExpandableMenu(String clientName, String menuItem) {
		WebElement row = clickSelectButtonForClient(clientName);
		if (row != null) {
			try {
				wait.until(ExpectedConditions.visibilityOf(row.findElement(By.xpath(".//div[@class='rmSlide']"))));

				Actions act = new Actions(driver);
				WebElement element = getTableRowWithClient(clientName)
						.findElement(By.xpath(".//span[text()='" + menuItem + "']"));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				act.click(element).perform();
			} catch (TimeoutException e) {

			}
		} else {
			Assert.fail("Can't find " + clientName + " client");
		}
	}

	// method for click on contacts link and open new dialog window
	public void clickContactsLinkForClientOpenDialogWindow(String clientname) {

		clickInspectionSelectExpandableMenu(clientname, "Contacts");
		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}

	public boolean isClientPresentInTable(String clientname) {
		List<WebElement> elements = null;
		try {
			elements = clientstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + clientname + "']"));
			if (!elements.isEmpty()) {
				return true;
			}
		} catch (StaleElementReferenceException e) {
			waitABit(3000);
			if (elements != null && !elements.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean clientExistsInArchivedTable(String clientname) {
		return clientsarchivedtable.getWrappedElement()
				.findElements(By.xpath(".//tr/td[text()='" + clientname + "']")).size() > 0;
	}

	public void importClients(String fileimport) {
		click(importbtn);
		waitABit(2000);
		wait.until(ExpectedConditions.visibilityOf(importfilefld));
		File importfile = new File("data/" + fileimport);
		driver.findElement(By.xpath("//input[@class='ruFileInput']")).sendKeys(importfile.getAbsolutePath());
		waitABit(2000);
		click(uploadfilebtn);
		waitABit(2000);
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//span[text()='File uploaded successfully']"))));

		click(fileinputnextbtn);
		wait.until(ExpectedConditions.visibilityOf(importclientsbtn));
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//input[contains(@id, 'IsWholesale_3_Input')]")))).click();
		waitABit(300);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='IsWholeSaler']"))).click();
		click(importclientsbtn);
		waitABit(1000);
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//span[text()='Imported: 1 Not imported: 0']"))));
		click(toclientsbtn);
		wait.until(ExpectedConditions.visibilityOf(clientstable.getWrappedElement()));
	}

	public Set<String> clickServicesLinkForClient(String clientname) {
		clickInspectionSelectExpandableMenu(clientname, "Services");

		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return driver.getWindowHandles();
	}

	public Set<String> clickClientUsersLinkForClient(String clientname) {
		clickInspectionSelectExpandableMenu(clientname, "Client Users");
		waitForNewTab();
		String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return driver.getWindowHandles();
	}

	public void selectClientServicePackage(String servicepackage) {
		selectComboboxValue(servicepackagcmb, servicepackagdd, servicepackage);
		waitForLoading();
	}

	public List<WebElement> getClientServicesTableRows() {
		wait.until(ExpectedConditions.visibilityOf(clientservicestable));
		return clientservicestable.findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_Content_gv_ctl00')]"));
	}

	public void searchClientUser(String clienuser) {
		clearAndType(clientuserssearchfld, clienuser);
		clickAndWait(clientusersfindbtn);
		waitABit(3000);
	}

	public List<WebElement> getClientUsersTableRows() {
		wait.until(ExpectedConditions.visibilityOf(clientuserstable.getWrappedElement()));
		return clientuserstable.getTableRows();
	}

	public int getClientUsersTableRowCount() {
		wait.until(ExpectedConditions.visibilityOf(clientuserstable.getWrappedElement()));
		return clientuserstable.getTableRowCount();
	}

	public void closeClientServicesTab(String mainWindowHandle) {
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}

	public void scrollDownToText(String text) {
		WebElement element = driver.findElement(By.xpath("//td[text()='" + text + "']"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", element);
		waitABit(500);
	}

	public void verifyClientIsPresentInActiveTab(String companyname) {
		if (!isClientPresentInTable(companyname)) {
			clickArchivedTab();
			searchClientByName(companyname);
			Assert.assertTrue(clientExistsInArchivedTable(companyname));
			restoreClient(companyname);
			clickActiveTab();
		}
	}

	public void verifyOneClientIsFound() {
		Assert.assertEquals(1, getClientsTableRowsCount(), "The client is not unique");
	}

	public void verifyClientIsNotPresentInActiveTab(String retailcompanyname, String retailcompanynameed) {
		while (isClientPresentInTable(retailcompanyname)) {
			deleteClient(retailcompanyname);
		}

		while (isClientPresentInTable(retailcompanynameed)) {
			deleteClient(retailcompanynameed);
		}
	}

	public void setSearchUserParameter(String clientName) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch"))));
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl08_filterer_tbSearch")).sendKeys(clientName);
	}
}
