package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

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

	@FindBy(id = "//div[@id='ctl00_ctl00_Content_Main_report']")
	private WebElement mainReportContent;

	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl09")
    private WebElement repairLocationTimeTrackingTable;
	
	public RepairLocationTimeTrackingWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn));
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
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}
	
	public void setSearchFromDate(String dateformat) { 
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")).clear();	
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")).sendKeys(dateformat);
	}
	
	public void setSearchToDate(String dateformat) { 
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")).clear();	
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")).sendKeys(dateformat);
	}

	public boolean isTableIsDisplayed() {
	    try {
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'Customer')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'WO #')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//div[contains(text(), 'Amount')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'WO Type')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'Start Date')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//div[contains(text(), 'Target Date')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'Completed Date')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//span[contains(text(), 'Target')]"))));
            wait.until(ExpectedConditions.visibilityOf(repairLocationTimeTrackingTable
                    .findElement(By.xpath("//td[@colspan]/div[contains(text(), 'Repair Time')]"))));
            return true;
        } catch (TimeoutException e) {
	        e.printStackTrace();
	        return false;
        }
    }
	
	public void clickFindButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(findbtn)).click();
        waitForLoading();
    }
	
	public void verifySearchResults(String[] wonumbers) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td/div[text()='" + wonumbers[0] + "']")));
		for (String wonumber : wonumbers) {
			Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + wonumber + "']")).isDisplayed());
		}
	}
	
	public boolean isWONumberDisplayedInTable(String wonumber) {
		return driver.findElements(By.xpath("//tr/td/div[text()='" + wonumber + "']")).size() > 0;
	}
	
	public boolean searchWorkOrderInTable(String wonumber) {
		boolean found = false;
		for (int i = 1; i <= Integer.parseInt(getLastPageNumber()); i++) {
			if (!isWONumberDisplayedInTable(wonumber)) {
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
		wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//input[@id='ctl00_ctl00_Content_Main_report_" +
                "ctl05_ctl00_Last_ctl01_ctl00' and @disabled='disabled']")));

	}
	
	public void clickGoToFirstPage() {
		click(gotofirstpage);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title='First Page' and @disabled='disabled']")));
	}
	
	public void clickGoToNextPage()  {
		int currenpage = Integer.parseInt(getCurrentlySelectedPageNumber());
		int nextpage = currenpage + 1;
		click(gotonextpage);		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Page " + nextpage + " of ')]")));
	}
	
	public void clickGoToPreviousPage() {
		int currenpage = Integer.parseInt(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		click(gotopreviouspage);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Page " + previouspage + " of ')]")));
	}
	
    public String getCurrentlySelectedPageNumber() {		
		return driver.findElement(By.xpath("//input[@id='ctl00_ctl00_Content_Main_report_ctl05_ctl00_CurrentPage']")).getAttribute("value");
	}
    
    public String getLastPageNumber() {	
		return driver.findElement(By.xpath("//span[@id='ctl00_ctl00_Content_Main_report_ctl05_ctl00_TotalPages']")).getText();
	}
}
