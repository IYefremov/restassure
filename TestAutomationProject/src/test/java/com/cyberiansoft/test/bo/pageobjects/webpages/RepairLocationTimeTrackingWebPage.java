package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

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

public class RepairLocationTimeTrackingWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	//Search Panel
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_Input")
	private ComboBox searchlocationcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_DropDown")
	private DropDown searchlocationdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")
	private TextField searchfromdatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_popupButton")
	private WebElement searchfrompopubbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")
	private TextField searchdatetofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_popupButton")
	private WebElement searchtopopubbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement findbtn;
	
	//Pagination
	//Pagination
	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl05_ctl00_First_ctl00_ctl00")
	private WebElement gotofirstpage;
		
	@FindBy(xpath = "//input[@title='Previous Page']")
	private WebElement gotopreviouspage;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl05_ctl00_Next_ctl00_ctl00")
	private WebElement gotonextpage;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl05_ctl00_Last_ctl00_ctl00")
	private WebElement gotolastpage;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl05_ctl00_TotalPages")
	private WebElement lastpagenumber;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl05_ctl00_CurrentPage")
	private WebElement currentlyselectedpagenumber; 
	
	public RepairLocationTimeTrackingWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(searchbtn));
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void selectSearchLocation(String location) { 
		selectComboboxValue(searchlocationcmb, searchlocationdd, location);
	}
	
	public void setSearchFromDate(String date, String month, String year) { 
		click(searchfrompopubbtn);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();		
		driver.findElement(By.id("rcMView_OK")).click();
		
		driver.findElement(By.xpath("//tr/td/a[text()='" + date + "']")).click();
	}
	
	public void setSearchToDate(String date, String month, String year) { 
		click(searchtopopubbtn);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		waitABit(1000);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}
	
	public void setSearchFromDate(String dateformat) { 
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")).clear();	
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")).sendKeys(dateformat);
	}
	
	public void setSearchToDate(String dateformat) { 
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")).clear();	
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")).sendKeys(dateformat);
	}
	
	public void clickFindButton() { 
		click(findbtn);
		waitABit(5000);
		//new WebDriverWait(driver, 10)
		  //.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
	
	public void verifySearchResults(String[] wonumbers) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td/div[text()='" + wonumbers[0] + "']")));
		for (int i =0; i< wonumbers.length; i++) {
			Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + wonumbers[i] + "']")).isDisplayed());
		}
	}
	
	public boolean isWONumberExistsIntable(String wonumber) {
		return driver.findElements(By.xpath("//tr/td/div[text()='" + wonumber + "']")).size() > 0;
	}
	
	public boolean searchWorkOrderInTable(String wonumber) {
		boolean found = false;
		for (int i = 1; i <= Integer.valueOf(getLastPageNumber()); i++) {
			if (!isWONumberExistsIntable(wonumber)) {
				clickGoToNextPage();
			} else {
				found = true;
				break;
			}		
		}
		return found;
	}
	
	public void clickGoToLastPage() {
		click(gotolastpage);
		new WebDriverWait(driver, 10)
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ctl00_ctl00_Content_Main_report_ctl05_ctl00_Last_ctl01_ctl00' and @disabled='disabled']")));
	}
	
	public void clickGoToFirstPage() {
		click(gotofirstpage);
		new WebDriverWait(driver, 10)
		  		 .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title='First Page' and @disabled='disabled']")));
	}
	
	public void clickGoToNextPage()  {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int nextpage = currenpage + 1;
		click(gotonextpage);		
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Page " + nextpage + " of ')]")));
	}
	
	public void clickGoToPreviousPage() {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		click(gotopreviouspage);
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Page " + previouspage + " of ')]")));
	}
	
    public String getCurrentlySelectedPageNumber() {		
		return driver.findElement(By.xpath("//input[@id='ctl00_ctl00_Content_Main_report_ctl05_ctl00_CurrentPage']")).getAttribute("value");
	}
    
    public String getLastPageNumber() {	
		return driver.findElement(By.xpath("//span[@id='ctl00_ctl00_Content_Main_report_ctl05_ctl00_TotalPages']")).getText();
	} 

}
