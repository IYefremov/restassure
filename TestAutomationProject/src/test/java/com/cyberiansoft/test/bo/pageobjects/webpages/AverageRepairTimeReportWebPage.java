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

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class AverageRepairTimeReportWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	//Search Panel
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_Input")
	private ComboBox searchlocationcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_DropDown")
	private DropDown searchlocationdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboOrderType_Input")
	private ComboBox searchwotypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboOrderType_DropDown")
	private DropDown searchwotypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_popupButton")
	private WebElement searchfrompopubbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_popupButton")
	private WebElement searchtopopubbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")
	private TextField searchdatefromfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")
	private TextField searchdatetofld;
	
	@FindBy(xpath = "//label[text()='Show Details']")
	private WebElement showdetailschkbox;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement findbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	public AverageRepairTimeReportWebPage(WebDriver driver) {		
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
	
	public void selectSearchLocation(String location) { 
		selectComboboxValue(searchlocationcmb, searchlocationdd, location);
	}

	public void selectSearchWOType(String wotype) { 
		selectComboboxValue(searchwotypecmb, searchwotypedd, wotype);
	}
	
	public void setSearchFromDate(String dateformat) { 
		clearAndType(searchdatefromfld, dateformat);
	}
	
	public void setSearchToDate(String dateformat) { 
		clearAndType(searchdatetofld, dateformat);
	}
	
	public void clickFindButton() { 
		waitABit(1500);
		clickAndWait(findbtn);
		waitABit(3000);
	}
	
	public void verifySearchResults(String location, String wotype) {
        WebElement element = driver.findElement(By.xpath("//tr/td/div[text()='" + wotype + "']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed());
	}
	
	public void verifyDetailReportSearchResults(String location, String wotype, String VIN, String make, String model, String year) {
        WebElement element = driver.findElement(By.xpath("//tr/td/div[text()='" + wotype + "']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed());

		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + VIN + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + make + "']")).isDisplayed());
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + model + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + year + "']")).isDisplayed());
	}
	
	public boolean areLocationResultsDisplayed(String location) {
	    try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(
                    driver.findElements(By.xpath(".//tr/td/div[text()='" + location + "']")))).size() > 0;
        } catch (TimeoutException e) {
	        return false;
        }
	}
	
	public void checkShowDetails() {
		showdetailschkbox.click();
	}
}
