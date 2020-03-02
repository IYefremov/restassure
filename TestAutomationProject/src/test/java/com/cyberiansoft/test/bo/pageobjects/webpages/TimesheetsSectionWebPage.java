package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TimesheetsSectionWebPage extends BaseWebPage{
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_dateFrom_dateInput")
	private WebElement fromDateField;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_dateTo_dateInput")
	private WebElement toDateField;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_ddlTeam")
	private WebElement teamField;
	
	@FindBy(className = "rcbList")
	private WebElement listOfItems;
	
	@FindBy(id ="ctl00_ctl00_Content_Main_filterer_BtnFind")
	private WebElement findBTN;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl04_lnkEmployee")
	private WebElement firstEmployee;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl06_gvTimeSheets_ctl00__0")
	private WebElement employeeStartDate;
	
	public TimesheetsSectionWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void setFromDate(String date) {
		fromDateField.clear();
		fromDateField.sendKeys(date);
	}

	public void setToDate(String date) {
		toDateField.clear();
		toDateField.sendKeys(date);
	}

	public void setTeam(String team) {
		teamField.click();
		waitABit(2000);
		listOfItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(team)).findFirst().get().click();
	}

	public void clickFindButton() {
		findBTN.click();
        waitForLoading();
    }

	public void expandFirstEmployee() {
		firstEmployee.click();
        waitForLoading();
    }

	public boolean checkStartingDay(String day) {
		String startDate = employeeStartDate.findElements(By.tagName("td")).get(1).getText().substring(0,3);
		return startDate.equals(day);
	}
}
