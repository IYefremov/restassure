package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

public class RepairOrdersWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_LinkButton1")
	private WebElement fulldisplaylink;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable repairorderstable;
	
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbVIN")
	private TextField searchvinfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_Input")
	private ComboBox searchlocationcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_DropDown")
	private DropDown searchlocationdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_Input")
	private TextField searchcustomercmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboCustomer_DropDown")
	private DropDown searchcustomerdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_Input")
	private ComboBox searchtimeframecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_DropDown")
	private DropDown searchtimeframedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbStockNo")
	private TextField searchstocknofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbRONo")
	private TextField searchronofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboRepairStatus_Input")
	private WebElement searchrepairstatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboOrderType_Input")
	private ComboBox searchwotypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboOrderType_DropDown")
	private DropDown searchwotypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbWO")
	private TextField searchwonofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_popupButton")
	private WebElement searchfrompopupbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_popupButton")
	private WebElement searchtopopupbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;
	
	
	public RepairOrdersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public boolean searchPanelIsVisible() {
		return searchvinfld.isDisplayed();
	}
	
	public void clickFullDisplayLink() {
		click(fulldisplaylink);
	}
	
	public boolean repairOrdersTableIsVisible() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(repairorderstable.getWrappedElement()));
		return repairorderstable.isDisplayed();
	}
	
	public int getRepairOrdersTableRowCount() {
		return repairorderstable.getTableRowCount();
	}
	
	public List<WebElement> getRepairOrdersTableRows() {
		return repairorderstable.getTableRows();
	}
	
	public void selectSearchLocation(String location) { 
		selectComboboxValue(searchlocationcmb, searchlocationdd, location);
	}
	
	public void selectSearchCustomer(String customer)  { 
		selectComboboxValueWithTyping(searchcustomercmb, searchcustomerdd, customer);
	}
	
	public void setSearchVIN(String vin) { 
		clearAndType(searchvinfld, vin);
	}
	
	public void selectSearchTimeframe(String timeframe) { 
		selectComboboxValue(searchtimeframecmb, searchtimeframedd, timeframe);
	}
	
	public void setSearchFromDate(String date, String month, String year) { 
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(searchfrompopupbtn)).click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();		
		driver.findElement(By.id("rcMView_OK")).click();
		waitABit(1000);
		driver.findElement(By.xpath("//tr/td/a[text()='" + date + "']")).click();
	}
	
	public void setSearchToDate(String date, String month, String year) { 
		searchtopopupbtn.click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		waitABit(1000);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}
	
	public void selectSearchWOType(String wotype)  { 
		selectComboboxValue(searchwotypecmb, searchwotypedd, wotype);
	}
	
	public void setSearchWoNumber(String wo) { 
		clearAndType(searchwonofld, wo);
	}
	
	public void setSearchStockNumber(String stocknum) { 
		clearAndType(searchstocknofld, stocknum);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public void verifyRepairOrdersTableColumnsAreVisible() {
		Assert.assertTrue(repairorderstable.isTableColumnExists("!"));
		Assert.assertTrue(repairorderstable.isTableColumnExists("Flag"));
		Assert.assertEquals(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[3]")).getText(), "Order /\nType");
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[3]")).isDisplayed());
		Assert.assertEquals(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[4]")).getText(), "Customer /\nVehicle /\nVIN#");
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[4]")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//a[text()='Stock# / RO#']")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//a[text()='Amount']")).isDisplayed());
		Assert.assertEquals(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[7]")).getText(), "Start Date /\nTarget Date");
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[7]")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//a[text()='Active Phase']")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//a[text()='Completed(%)']")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[10]")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tr/th[11]")).isDisplayed());
	}
	
	public WebElement getTableRowWithRepairOrder(String wo) {
		List<WebElement> rows = getRepairOrdersTableRows();		
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]/a")).getText().equals(wo)) {
				return row;
			}
		} 
		return null;
	}
	
	public boolean isRepairOrderExistsInTable(String wo) {
		boolean exists = false;
		WebElement row = getTableRowWithRepairOrder(wo);
		if (row != null) {
			exists = true;
		}
		return exists;
	}
	
	public VendorOrderServicesWebPage clickOnWorkOrderLinkInTable(String wo) {
		WebElement row = getTableRowWithRepairOrder(wo);
		if (row != null) {
			row.findElement(By.xpath(".//td[3]/a[contains(text(), '" + wo + "')]")).click();
		} else {
			Assert.assertTrue(false, "Can't find " + wo + " repair order");	
		}
		return PageFactory.initElements(
				driver, VendorOrderServicesWebPage.class); 
	}
	
	public void verifyTableCustomerAndVinColumnValuesAreVisible(String customer, String vin) {
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tbody/tr/td/div/span[contains(text(), '" + customer + "')]")).isDisplayed());
		Assert.assertTrue(repairorderstable.getWrappedElement().findElement(By.xpath(".//tbody/tr/td/div/div[contains(text(), '" + vin + "')]")).isDisplayed());
	}
	
	public void openFullDisplayWOMonitorandVerifyContent() throws InterruptedException {
		clickFullDisplayLink();
		waitForNewTab();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through your windows
		while (it.hasNext()) {
			String parent = it.next();
			String newwin = it.next();
			driver.switchTo().window(newwin);
			new WebDriverWait(driver, 90)
			  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='fullGrid']")));
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='VIN']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='StockNo']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='ClientName']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='OrderDate']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='PhaseName']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='ActiveTechnicians']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='Completed']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//tr/th[@data-field='OrderDescription']")).isDisplayed());
			new WebDriverWait(driver, 10)
			  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("fullGrid")).findElement(By.xpath("./table/tbody/tr[2]"))));
			// perform actions on new window
			driver.close();
			driver.switchTo().window(parent);
		}
	}
	

}
