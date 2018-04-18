package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class VendorBillsWebPage extends WebPageWithFilter {

	public final static String WOTABLE_DATE_COLUMN_NAME = "Date";
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable vendorbillstable;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_Input")
	private ComboBox searchstatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboStatus_DropDown")
	private DropDown searchstatusdd;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboTeams_Input")
	private WebElement searchteamvendorcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlEmployee_Input")
	private WebElement searchtechniciancmb;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe")
	private WebElement searchtimeframecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboPaidStatus_Input")
	private WebElement searchpaidstatuscmb;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;
	
	
	public VendorBillsWebPage(WebDriver driver) {
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
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public void selectSearchStatus(String status) { 
		selectComboboxValue(searchstatuscmb, searchstatusdd, status);
	}
	
	public void verifyVendorBillsTableColumnsAreVisible() {
		wait.withTimeout(1, TimeUnit.MINUTES).until(ExpectedConditions.visibilityOf(vendorbillstable.getWrappedElement()));
		Assert.assertTrue(vendorbillstable.tableColumnExists("Invoice #"));
		Assert.assertTrue(vendorbillstable.tableColumnExists("Vendor"));
		Assert.assertTrue(vendorbillstable.tableColumnExists("Date"));
		Assert.assertTrue(vendorbillstable.tableColumnExists("Amount"));
		Assert.assertTrue(vendorbillstable.tableColumnExists("Status"));
		wait.withTimeout(30, TimeUnit.SECONDS);
	}
	
	public WebTable getVendorBillsTable() {
		return vendorbillstable;
	}
	
	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchteamvendorcmb.isDisplayed());
		Assert.assertTrue(searchtechniciancmb.isDisplayed());
		Assert.assertTrue(searchtimeframecmb.isDisplayed());
		Assert.assertTrue(searchpaidstatuscmb.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
	}

}
