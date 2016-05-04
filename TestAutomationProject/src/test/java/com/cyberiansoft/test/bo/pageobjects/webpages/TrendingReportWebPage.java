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

public class TrendingReportWebPage extends BaseWebPage {
	
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
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpFrom_dateInput")
	private TextField searchfromdatefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpTo_dateInput")
	private TextField searchdatetofld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement findbtn;
	
	public TrendingReportWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void selectSearchLocation(String location) { 
		selectComboboxValue(searchlocationcmb, searchlocationdd, location);
	}

	public void selectSearchWOType(String wotype) { 
		selectComboboxValue(searchwotypecmb, searchwotypedd, wotype);
	}
	
	public void setSearchFromDate(String month, String year) { 
		click(searchfromdatefld.getWrappedElement());
		click(driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")));
		click(driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")));
		click(driver.findElement(By.id("rcMView_OK")));
		waitABit(1000);
	}
	
	public void setSearchToDate(String month, String year) { 
		click(searchdatetofld.getWrappedElement());
		click(driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")));
		click(driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")));
		click(driver.findElement(By.id("rcMView_OK")));
		waitABit(1000);
	}
	
	public void clickFindButton() { 
		click(findbtn);
		waitABit(5000);
		//new WebDriverWait(driver, 10)
		//  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
	
	public void verifySearchResults(String location, String wotype) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td/div[text()='" + location + "']")));
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + location + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr/td/div[text()='" + wotype + "']")).isDisplayed());
	}

}
