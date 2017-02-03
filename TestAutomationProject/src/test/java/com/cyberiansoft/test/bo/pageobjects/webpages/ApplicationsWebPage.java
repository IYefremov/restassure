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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(applicationstable.getWrappedElement()));
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
		
		Assert.assertTrue(applicationstable.isTableColumnExists("Application"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Description"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Host"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Culture"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Theme"));
		Assert.assertTrue(applicationstable.isTableColumnExists("TimeZone"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Status"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Created"));
		Assert.assertTrue(applicationstable.isTableColumnExists("Modified"));
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
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='" + _application + "']")));
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
