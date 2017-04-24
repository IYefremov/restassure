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

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

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
		
		Assert.assertTrue(employeestable.isTableColumnExists("Employee"));
		Assert.assertTrue(employeestable.isTableColumnExists("Application / Team / Area"));
		Assert.assertTrue(employeestable.isTableColumnExists("Contact Info"));
		Assert.assertTrue(employeestable.isTableColumnExists("Password"));
		Assert.assertTrue(employeestable.isTableColumnExists("Accounting ID"));
		Assert.assertTrue(employeestable.isTableColumnExists("Commissions"));
	}
	
	public void verifyAllEmployeeSearchParametersAreVisible() {
		Assert.assertTrue(applicationsearchcmb.isDisplayed());
		Assert.assertTrue(employeesearchfld.isDisplayed());
	}
	
	public void selectSearchApplication(String _application) throws InterruptedException {
//		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
//		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
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
		driver.findElement(By.xpath("//a[text()='Profiles']")).click();
		waitForNewTab();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through your windows
		while (it.hasNext()) {
			String parent = it.next();
			String newwin = it.next();
			driver.switchTo().window(newwin);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gvEmployee_ctl00"))).isDisplayed();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gv_ctl00"))).isDisplayed();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_EmployeeCommissions_gv_ctl00"))).isDisplayed();
			// perform actions on new window
			driver.close();
			driver.switchTo().window(parent);
		}
	}
	
	public void verifySearchResultsByApplication(String appname) {
		List<WebElement> rows = getAllEmployeesTableRows();
		for (WebElement row :  rows) {
			Assert.assertEquals(row.findElement(By.xpath(".//td[2]/a")).getText(), appname);
		}
	}
}
