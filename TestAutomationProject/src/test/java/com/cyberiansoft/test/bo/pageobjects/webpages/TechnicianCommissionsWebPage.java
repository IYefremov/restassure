package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class TechnicianCommissionsWebPage extends WebPageWithPagination {

	// Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_Input")
	private ComboBox searchstatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_DropDown")
	private DropDown searchstatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboTeams_Input")
	private ComboBox searchteamvendorcmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboTeams_DropDown")
	private DropDown searchteamvendordd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlEmployee_Input")
	private TextField searchtechniciancmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlEmployee_DropDown")
	private DropDown searchtechniciandd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbVehicle")
	private TextField searchvinfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbWO")
	private TextField searchordernofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbInvoice")
	private TextField searchinvoicenofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbStock")
	private TextField searchstocknofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_Input")
	private ComboBox searchtimeframecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_DropDown")
	private DropDown searchtimeframedd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_popupButton")
	private WebElement searchfrompopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_popupButton")
	private WebElement searchtopopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable techniciancommissionstable;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement searchBTN;

	@FindBy(xpath = "//img[@src='/Reserved.ReportViewerWebControl.axd?OpType=Resource&Version=10.0.40219.329&Name=Microsoft.ReportingServices.Rendering.HtmlRenderer.RendererResources.unsorted.gif']")
	private List<WebElement> originalFilterBTN;

	@FindBy(xpath = "//img[@src='/Reserved.ReportViewerWebControl.axd?OpType=Resource&Version=10.0.40219.329&Name=Microsoft.ReportingServices.Rendering.HtmlRenderer.RendererResources.sortAsc.gif']")
	private WebElement ascFilterBTN;

	@FindBy(id = "VisibleReportContentctl00_ctl00_Content_Main_report_ctl09")
	private WebElement contentTable;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpDateFrom_dateInput")
	private WebElement fromDateField;

	public TechnicianCommissionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void verifyInvoicesTableColumnsAreVisible() {
        WaitUtilsWebDriver.waitForElementNotToBeStale(techniciancommissionstable.getWrappedElement());
		wait.until(ExpectedConditions.visibilityOf(techniciancommissionstable.getWrappedElement()));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chkAllInvoices")));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Vendor Name"));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Order #"));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Order Amount"));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Tech Amount"));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Vendor Bill #"));
		Assert.assertTrue(techniciancommissionstable.tableColumnExists("Billing Status"));
	}

	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchteamvendorcmb.isDisplayed());
		Assert.assertTrue(searchordernofld.isDisplayed());
		Assert.assertTrue(searchstocknofld.isDisplayed());
		Assert.assertTrue(searchtimeframecmb.isDisplayed());
		Assert.assertTrue(searchtechniciancmb.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
		Assert.assertTrue(searchvinfld.isDisplayed());
		Assert.assertTrue(searchinvoicenofld.isDisplayed());
	}

	public int getTechnicianCommissionsTableRowCount() {
		return techniciancommissionstable.getTableRowCount();
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
	}

	public boolean technicianCommissionsTableTableIsVisible() {
		return techniciancommissionstable.isDisplayed();
	}

	public void selectSearchStatus(String status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
	}

	public void selectSearchTeamVendor(String teamvendor) {
		selectComboboxValue(searchteamvendorcmb, searchteamvendordd, teamvendor);
	}

	public void selectSearchTechnician(String technician) {
		waitABit(1000);
		searchtechniciancmb.clearAndType(technician);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + technician + "']")));
		driver.findElement(By.xpath("//li[text()='" + technician + "']")).click();
	}

	public void selectSearchTimeFrame(WebConstants.TimeFrameValues timeframe) {
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe.getName());
	}

	public void setSearchInvoice(String invoicenumber) {
		searchinvoicenofld.clearAndType(invoicenumber);
	}

	public void setSearchFromDate(String date, String month, String year) {
		click(searchfrompopupbtn);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		waitABit(1000);
		driver.findElement(By.xpath("//tr/td/a[text()='" + date + "']")).click();
	}

	public void setSearchToDate(String date, String month, String year) {
		click(searchtopopupbtn);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		waitABit(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}

	public void setSearchFromDate(String date) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")))).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")))).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")).sendKeys(date);
	}

	public void verifySearchResults(String ordernumber) {
		wait.until(ExpectedConditions.visibilityOf(techniciancommissionstable.getWrappedElement()
				.findElement(By.xpath(".//tr/td[text()='" + ordernumber + "']"))));
	}

	public void clickSearchBTN() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBTN)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_report_ctl09")));
	}

	public void clickOnLastSearchResult() {
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("VisibleReportContentctl00_ctl00_Content_Main_report_ctl09")));
		waitABit(1500);
		List<WebElement> results = driver
				.findElement(By.id("VisibleReportContentctl00_ctl00_Content_Main_report_ctl09"))
				.findElements(By.tagName("a"));
		results.get(results.size() - 1).click();
		waitForLoading();
	}

	public boolean checkResultsTable() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Date']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Invoice #']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='WO #']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='RO #']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Customer']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Gross']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Net']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Tech. $']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Comm']")));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean checkSortAbility() {
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("VisibleReportContentctl00_ctl00_Content_Main_report_ctl09")));
		List<WebElement> allNames = new ArrayList<>();

		int counter = 4;
		while (true) {
			try {
				allNames.add(driver.findElement(By.xpath("//a[@tabindex='" + counter + "']")));
				counter += 2;
			} catch (Exception e) {
				break;
			}
		}

		List<String> results = allNames.stream().map(e -> e.getText()).collect(Collectors.toList());
		if (results.size() == 0) {
			return true;
		}
		String firstResultBeforeSort = results.get(0);
		String lastResultBeforeSort = results.get(results.size() - 1);

		String firstResultAfterSort = "";
		String lastResultAfterSort = "";

		if (!firstResultBeforeSort.equals(lastResultBeforeSort)) {
			originalFilterBTN.get(0).click();
			waitForLoading();

			allNames = new ArrayList<>();

			counter = 4;
			while (true) {
				try {
					allNames.add(driver.findElement(By.xpath("//a[@tabindex='" + counter + "']")));
					counter += 2;
				} catch (Exception e) {
					break;
				}
			}
			results = allNames.stream().map(e -> e.getText()).collect(Collectors.toList());

			firstResultAfterSort = results.get(0);
			lastResultAfterSort = results.get(results.size() - 1);

			if (!firstResultBeforeSort.equals(lastResultAfterSort)
					&& !lastResultBeforeSort.equals(firstResultAfterSort)) {
				ascFilterBTN.click();
				waitForLoading();
				allNames.clear();

				counter = 4;
				while (true) {
					try {
						allNames.add(driver.findElement(By.xpath("//a[@tabindex='" + counter + "']")));
						counter += 2;
					} catch (Exception e) {
						break;
					}
				}
//				results = allNames.stream().map(e -> e.getText()).collect(Collectors.toList());
//				
//				firstResultAfterSort = results.get(0);
//				lastResultAfterSort = results.get(results.size() - 1);
//				
//				if (!firstResultBeforeSort.equals(lastResultAfterSort)
//						&& !lastResultBeforeSort.equals(firstResultAfterSort)) {
//					return false;
//				}
			}
		}

		return true;
	}

	public void setSearchFromDate() {
		LocalDateTime date = LocalDateTime.of(2017, 9, 1, 0, 0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		fromDateField.clear();
		fromDateField.sendKeys(date.format(formatter));
	}
}
