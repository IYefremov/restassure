package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class KanbanWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboLocations_Input")
	WebElement searchLocationButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_BtnFind")
	WebElement searchButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_tbTimerInterval")
	WebElement intervalField;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnRefreshOnOff")
	WebElement autoRefreshButton;

	public KanbanWebPage(WebDriver driver) {
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

	public boolean checkSearhResultColumns() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Completed']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Not Started']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='In Progress']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Start Phase']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Phase Rework']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Finished']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='QC']")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;

	}

	public boolean checkIntervalFieldLessThan(int intervalBorder) {
		intervalField.clear();

		intervalField.sendKeys(String.valueOf(intervalBorder - 1));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		waitForLoading();
        return !intervalField.getAttribute("value").equals(String.valueOf(intervalBorder));
    }

	public boolean checkIntervalFieldOverThan(int intervalBorder) {
		intervalField.clear();

		intervalField.sendKeys(Integer.toString(intervalBorder + 1));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForLoading();
        return !intervalField.getAttribute("value").equals(String.valueOf(intervalBorder));
    }

	public boolean checkIntervalFieldInputSymbol(String string) {
		intervalField.clear();

		intervalField.sendKeys(string);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		waitForLoading();
        return intervalField.getAttribute("value").equals(string);
    }

	public boolean checkIntervalField(int interval) {
		intervalField.clear();

		intervalField.sendKeys(Integer.toString(interval));
		wait.until(ExpectedConditions.elementToBeClickable(autoRefreshButton)).click();
        waitForLoading();
        return intervalField.getAttribute("value").equals(String.valueOf(interval));
    }
}
