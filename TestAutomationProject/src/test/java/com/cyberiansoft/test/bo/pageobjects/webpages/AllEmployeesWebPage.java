package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class AllEmployeesWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable employeestable;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboApplication_Input")
	private WebElement applicationsearchcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbSearch")
	private TextField employeesearchfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_BtnFind")
	private WebElement findbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	public AllEmployeesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(employeestable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyAllEmployeesTableColumnsAreVisible() {
		
		Assert.assertTrue(employeestable.tableColumnExists("Employee"));
		Assert.assertTrue(employeestable.tableColumnExists("Application / Team / Area"));
		Assert.assertTrue(employeestable.tableColumnExists("Contact Info"));
		Assert.assertTrue(employeestable.tableColumnExists("Password"));
		Assert.assertTrue(employeestable.tableColumnExists("Accounting ID"));
		Assert.assertTrue(employeestable.tableColumnExists("Commissions"));
	}
	
	public void verifyAllEmployeeSearchParametersAreVisible() {
		Assert.assertTrue(applicationsearchcmb.isDisplayed());
		Assert.assertTrue(employeesearchfld.isDisplayed());
	}
	
	public void selectSearchApplication(String _application) {
//		wait.until(ExpectedConditions.visibilityOf(updateProcess));
//		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(1000);
		applicationsearchcmb.click();
		applicationsearchcmb.sendKeys(_application);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li/em[text()='" + _application + "']"))).click();
		//driver.findElement(By.xpath("//li/em[text()='" + _application + "']")).click();
	}
	
	public void setSearchEmployeeParameter(String employee) {
		clearAndType(employeesearchfld, employee);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public int getAllEmployeesTableRowCount() {
		return getAllEmployeesTableRows().size();
	}
	
	public List<WebElement>  getAllEmployeesTableRows() {
		return employeestable.getTableRows();
	}
	
	public void  verifyProfilesLinkWorks() {
		String parent = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[text()='Profiles']")).click();
		waitForNewTab();
			String newwin = "";
			for(String window:driver.getWindowHandles()){
				if(!window.equals(parent)){
					newwin = window;
				}
			}
			driver.switchTo().window(newwin);
			waitABit(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gvEmployee_ctl00"))).isDisplayed();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gv_ctl00"))).isDisplayed();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_EmployeeCommissions_gv_ctl00"))).isDisplayed();
			// perform actions on new window
			driver.close();
			driver.switchTo().window(parent);
	}
	
	public void verifySearchResultsByApplication(String appname) {
		List<WebElement> rows = getAllEmployeesTableRows();
		for (WebElement row :  rows) {
			Assert.assertEquals(row.findElement(By.xpath(".//td[2]/a")).getText(), appname);
		}
	}
}
