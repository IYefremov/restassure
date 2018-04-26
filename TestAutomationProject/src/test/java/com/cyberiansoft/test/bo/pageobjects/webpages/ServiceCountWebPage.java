package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServiceCountWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement searchBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_Input")
	private WebElement locationField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpDateFrom_dateInput")
	private WebElement searchDateFrom;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpDateTo_dateInput")
	private WebElement searchDateTo;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpDateTo_popupButton")
	private WebElement calendarDateToBTN;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_dpDateFrom_popupButton")
	private WebElement calendarDateFromBTN;

	public ServiceCountWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickSearchButton() {
		searchBTN.click();
	}

	public boolean verifySearchFields() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		try {
			waitABit(1500);
			if (!locationField.getAttribute("value").equals("(all)")) {
				return false;
			}
			StringBuilder criteriaDate = new StringBuilder(formatter.format(now.minusWeeks(1)));
			waitABit(1500);
			if (!searchDateFrom.getAttribute("value").equals(criteriaDate.toString())) {
				return false;
			}
			criteriaDate = new StringBuilder(formatter.format(now));
			waitABit(1500);
			if (!searchDateTo.getAttribute("value").equals(criteriaDate.toString())) {
				return false;
			}
			waitABit(1500);
			calendarDateFromBTN.click();
			waitABit(1500);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpDateFrom_calendar_Top")));
			waitABit(1500);
			calendarDateToBTN.click();
			waitABit(1500);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_dpDateTo_calendar_Top")));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean verifySearchResultGrid() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Customer']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Phase']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Service']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='VIN']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Stock No.']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='RO No.']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Open']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Closed']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Customer']")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
}
