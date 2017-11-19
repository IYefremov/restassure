package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class SubscriptionsWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	WebElement addButton;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboLocations_Input")
	WebElement locationField;
	
	@FindBy(className = "rcbList")
	WebElement listWithItems;
	
	@FindBy(id="ctl00_Content_ctl01_ctl02_BtnOk")
	WebElement okPopUpButton;
	
	@FindBy(id="ctl00_Content_ctl01_ctl01_Card_tbDescription")
	WebElement descriptionField;
	
	@FindBy(id="ctl00_Content_ctl01_ctl01_Card_rtpStartTime_dateInput")
	WebElement startTimeField;

	public SubscriptionsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean checkGrid() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Description']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Disabled']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Schedule Recurs']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Time Zone Id']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Start Time']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[text()='Export Format']")));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void clickAddButton() throws InterruptedException {
		addButton.click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public boolean checkAddPopUpContent() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbDescription")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_chbDisabled")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_tbScheduleReccurs")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_ddlTimeZones")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_rtpStartTime_dateInput")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_ddlFormat")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_comboLocations_Input")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_chbPhasesInRow")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_ctl01_ctl01_Card_ddlTimeframe_Input")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void setSubscriptionPopUpLocation(String location) {
		locationField.click();
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(location)).findFirst()
				.get().click();
	}

	public void setSubscriptionPopUpPhase1(String phase) throws InterruptedException {
		waitABit(2000);
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_comboPhase_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
	}

	public void setSubscriptionPopUpStatuses1(String...statuses) throws InterruptedException {
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_ddlStatus_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(e -> e.click());
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_ddlStatus_Arrow")).click();
	}
	
	public void setSubscriptionPopUpPhase2(String phase) throws InterruptedException {
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_comboPhase2_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
	}
	
	public void setSubscriptionPopUpStatuses2(String...statuses) throws InterruptedException {
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_ddlStatus2_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(e -> e.click());
		driver.findElement(By.id("ctl00_Content_ctl01_ctl01_Card_ddlStatus2_Arrow")).click();
	}

	public void clickOkAddPopUpButton() {
		okPopUpButton.click();
	}

	public void setSubscriptionPopUpDescription(String desc) {
		descriptionField.clear();
		descriptionField.sendKeys(desc);
	}

	public void setSubscriptionPopUpStartTime(String time) {
		startTimeField.clear();
		startTimeField.sendKeys(time);
	}

}
