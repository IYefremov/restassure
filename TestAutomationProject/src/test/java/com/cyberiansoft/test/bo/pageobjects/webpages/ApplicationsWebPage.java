package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ApplicationsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_applications_ctl00")
	private WebTable applicationstable;	
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboApplication_Input")
	private WebElement applicationsearchcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboStatus_Input")
	private ComboBox statussearchcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboStatus_DropDown")
	private DropDown statussearchdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbxUserName")
	private TextField usernamesearchfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;
	
	@FindBy(xpath = "//div[@class='rgWrap rgInfoPart']")
	private WebElement pagesizelabel;
	
	public ApplicationsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(applicationstable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyApplicationsTableColumnsAreVisible() {
		
		Assert.assertTrue(applicationstable.tableColumnExists("Application"));
		Assert.assertTrue(applicationstable.tableColumnExists("Description"));
		Assert.assertTrue(applicationstable.tableColumnExists("Host"));
		Assert.assertTrue(applicationstable.tableColumnExists("Culture"));
		Assert.assertTrue(applicationstable.tableColumnExists("Theme"));
		Assert.assertTrue(applicationstable.tableColumnExists("TimeZone"));
		Assert.assertTrue(applicationstable.tableColumnExists("Status"));
		Assert.assertTrue(applicationstable.tableColumnExists("Created"));
		Assert.assertTrue(applicationstable.tableColumnExists("Modified"));
	}
	
	public void verifyApplicationsSearchParametersAreVisible() {
		Assert.assertTrue(applicationsearchcmb.isDisplayed());
		Assert.assertTrue(statussearchcmb.isDisplayed());
		Assert.assertTrue(usernamesearchfld.isDisplayed());
	}
	
	public void selectSearchApplication(String _application) throws InterruptedException {
		waitABit(1000);
		applicationsearchcmb.click();
		applicationsearchcmb.sendKeys(_application);
		waitABit(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + _application + "']")));
		driver.findElement(By.xpath("//li[text()='" + _application + "']")).click();
	}
	
	public void selectSearchStatus(String status) {
		selectComboboxValue(statussearchcmb, statussearchdd, status);
	}
	
	public void setSearchUsername(String username) {
		clearAndType(usernamesearchfld, username);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public int getApplicationsTableRowCount() {
		return getApplicationsTableRows().size();
	}
	
	public List<WebElement>  getApplicationsTableRows() {
		return applicationstable.getTableRows();
	}
	
	public void verifySearchResultsByApplication(String appname) {
		List<WebElement> rows = getApplicationsTableRows();
		for (WebElement row :  rows) {
			Assert.assertEquals(row.findElement(By.xpath(".//td[5]/a")).getText(), appname);
		}
	}
}
