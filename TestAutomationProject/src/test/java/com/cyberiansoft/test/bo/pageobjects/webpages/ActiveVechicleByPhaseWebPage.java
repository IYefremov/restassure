package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class ActiveVechicleByPhaseWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_Input")
	WebElement locationField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	WebElement findBTN;

	@FindBy(className = "rcbList")
	WebElement listWithItems;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlTimeframe_Input")
	WebElement timeFrameField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_chbPhasesInRow")
	WebElement phasesInRowCheckBox;

	@FindBy(id = "VisibleReportContentctl00_ctl00_Content_Main_report_ctl09")
	WebElement reportContent;
	
	@FindBy(xpath = "//a[text()='Subscriptions']")
	WebElement subscriptionsBTN;

	public ActiveVechicleByPhaseWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean checkSearchFields() {
		try {
			if (!locationField.getAttribute("value").equals("(all)")) {
				return false;
			}

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase2_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus_Input")));
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus2_Input")));

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickFindButton() {
		findBTN.click();
	}

	public void setLocationFilter(String location) {
		locationField.click();
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(location)).findFirst()
				.get().click();
	}

	public boolean checkTimeFrameField(String string) {
		if (!timeFrameField.getAttribute("value").equals("Last " + string + " Days"))
			return false;
		return true;
	}

	public boolean checkPhasesInRowCheckBox() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(phasesInRowCheckBox));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean checkSearchResults(String string) {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("VisibleReportContentctl00_ctl00_Content_Main_report_ctl09")));
			reportContent.findElement(By.xpath("//div[text()='" + string + "']"));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='WO Date']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='WO No']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='VIN']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Year']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Make']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Model']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Stock#']")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void setPhase1(String phase) throws InterruptedException {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
	}

	public void setPhase2(String phase) throws InterruptedException {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase2_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
	}

	public void setStatuses1(String... statuses) throws InterruptedException {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(e -> e.click());
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus_Arrow")).click();
	}

	public void setStatuses2(String... statuses) throws InterruptedException {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus2_Input")).click();
		Thread.sleep(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(e -> e.click());
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus2_Arrow")).click();
	}

	public void clickPhasesInRow() {
		phasesInRowCheckBox.click();
	}

	public boolean checkThatAllPhasesAreInStatus(String phase, String... statuses) {
		List<WebElement> tableRows = driver.findElements(By.xpath("//tr[@valign='top']"));
		for (int i = 0; i < 13; i++) {
			tableRows.remove(0);
		}

		tableRows.stream().filter(e -> {
			try {
				e.findElement(By.xpath("//div[text()='" + phase + "']"));
				return true;
			} catch (Exception ex) {
				return false;
			}
		}).allMatch(e -> {
			for (String status : statuses) {
				if (e.findElements(By.tagName("td")).get(8).getText().equals(status))
					return true;
			}
			return false;
		});

		return true;
	}

	public SubscriptionsWebPage clickSubscriptionsButton() throws InterruptedException {
		String mainWindow = driver.getWindowHandle();
		subscriptionsBTN.click();
		Thread.sleep(1500);
		for(String window : driver.getWindowHandles()){
			if(!window.equals(mainWindow))
				driver.switchTo().window(window);
			return PageFactory.initElements(
					driver, SubscriptionsWebPage.class);
		}
		return null;
	}
}
