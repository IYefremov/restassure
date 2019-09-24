package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WhiteBoardWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboLocations_Input")
	private WebElement searchLocationButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_BtnFind")
	private WebElement searchButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_tbTimerInterval")
	private WebElement intervalField;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnRefreshOnOff")
	private WebElement autoRefreshButton;

	public WhiteBoardWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void setSearchLocation(String location) {
		searchLocationButton.click();
		driver.findElement(By.className("rcbList")).findElements(By.tagName("li")).stream()
				.filter(e -> e.getText().equals(location)).findFirst().get().click();
	}

	public void clickSearchButton() {
		searchButton.click();
		waitForLoading();
	}

	public boolean checkSearchResultColumns() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Not Started']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='In Progress']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Start Phase']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Phase Rework']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Do not track']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='QC']")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;

	}

	public boolean checkIntervalFieldLessThan(int intervalBorder) {
		setAttribute(intervalField, "value", "");
		wait.until(ExpectedConditions.elementToBeClickable(intervalField)).clear();

		intervalField.sendKeys(String.valueOf(intervalBorder - 1));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		} catch (Exception e) {
			waitABit(2000);
			searchButton.click();
		}
		waitForLoading();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(intervalField));
		} catch (Exception e) {
			waitABit(3000);
		}
		return !wait.until(ExpectedConditions.elementToBeClickable(intervalField))
				.getAttribute("value")
				.equals(String.valueOf(intervalBorder));
	}

	public boolean checkIntervalFieldOverThan(int intervalBorder) {
		setAttribute(intervalField, "value", "");
		wait.until(ExpectedConditions.elementToBeClickable(intervalField)).clear();

		intervalField.sendKeys(Integer.toString(intervalBorder + 1));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		} catch (Exception e) {
			waitABit(2000);
			searchButton.click();
		}
		waitForLoading();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(intervalField));
		} catch (Exception e) {
			waitABit(3000);
		}
		return !wait.until(ExpectedConditions.elementToBeClickable(intervalField))
				.getAttribute("value")
				.equals(String.valueOf(intervalBorder));
	}

	public boolean checkIntervalFieldInputSymbol(String string) {
		setAttribute(intervalField, "value", "");
		wait.until(ExpectedConditions.elementToBeClickable(intervalField)).clear();

		intervalField.sendKeys(string);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		} catch (Exception e) {
			waitABit(2000);
			searchButton.click();
		}
		waitForLoading();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(intervalField));
		} catch (Exception e) {
			waitABit(3000);
		}
		return wait.until(ExpectedConditions.elementToBeClickable(intervalField))
				.getAttribute("value")
				.equals(string);
	}

	public boolean checkIntervalField(int interval) {
		setAttribute(intervalField, "value", "");
		wait.until(ExpectedConditions.elementToBeClickable(intervalField)).clear();

		wait.until(ExpectedConditions.elementToBeClickable(intervalField)).sendKeys(Integer.toString(interval));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(autoRefreshButton)).click();
		} catch (Exception e) {
			waitABit(2000);
			autoRefreshButton.click();
		}
		waitForLoading();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(intervalField));
		} catch (Exception e) {
			waitABit(3000);
		}
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(intervalField))
					.getAttribute("value")
					.equals(String.valueOf(interval));
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
}
