package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;

public class ActiveVechicleByPhaseWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_comboLocations_Input")
	private WebElement locationField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement findBTN;

	@FindBy(className = "rcbList")
	private WebElement listWithItems;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlTimeframe_Input")
	private WebElement timeFrameField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_chbPhasesInRow")
	private WebElement phasesInRowCheckBox;

	@FindBy(xpath = "//div[@id='VisibleReportContentctl00_ctl00_Content_Main_report_ctl09']")
	private WebElement reportContent;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlTimeframe_DropDown")
	private WebElement timeFrameDropDown;

	@FindBy(xpath = "//div[@id='ctl00_ctl00_Content_Main_ctl01_filterer_ddlTimeframe_DropDown']//li")
	private List<WebElement> timeFrameListBox;

	// @FindBy(xpath = "//a[text()='Subscriptions']")
	@FindBy(linkText = "Subscriptions")
	private WebElement subscriptionsBTN;

    @FindBy(xpath = "//input[contains(@id, 'filterer_dpDateFrom_dateInput') and @type='text']")
    private TextField searchdatefromfld;

    @FindBy(xpath = "//input[contains(@id, 'filterer_dpDateTo_dateInput') and @type='text']")
    private TextField searchdatetofld;

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
		wait.until(ExpectedConditions.elementToBeClickable(findBTN)).click();
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
			wait.until(ExpectedConditions.visibilityOf(reportContent));
			reportContent.findElement(By.xpath("//div[text()='" + string + "']"));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Customer']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Phase']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Service']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='VIN']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Stock No.']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='RO No.']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Open']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Closed']")));
			return true;
		} catch (Exception e) {
		    e.printStackTrace();
			return false;
		}
	}

	public void setPhase1(String phase) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase_Input")).click();
		waitABit(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
        waitABit(2500);
	}

	public void setPhase2(String phase) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_comboPhase2_Input")).click();
        waitABit(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> e.getText().equals(phase)).findFirst().get()
				.click();
        waitABit(2500);
	}

	public void setStatuses1(String... statuses) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus_Input")).click();
        waitABit(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(WebElement::click);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus_Arrow")).click();
	}

	public void setStatuses2(String... statuses) {
        waitABit(2500);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_ddlStatus2_Input")).click();
        waitABit(1500);
		listWithItems.findElements(By.tagName("li")).stream().filter(e -> {
			for (String status : statuses) {
				if (e.getText().equals(status))
					return true;
			}
			return false;
		}).forEach(WebElement::click);
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
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(mainWindow)) {
				driver.switchTo().window(window);
				return PageFactory.initElements(driver, SubscriptionsWebPage.class);
			}
		}
		return null;
	}

    public void setSearchFromDate(String dateformat) {
        clearAndType(searchdatefromfld, dateformat);
    }

    public void setSearchToDate(String dateformat) {
        clearAndType(searchdatetofld, dateformat);
    }

	public int countLocationsInResultTable() {
        WaitUtilsWebDriver.elementShouldBeVisible(By.id("ctl00_ctl00_Content_Main_report_fixedTable"), true);
		return driver.findElements(By.xpath("//span[contains(text(), 'Location')]")).size();
	}

	public boolean checkTimeFrameFilter() {
		try {
            Utils.clickElement(timeFrameField);
			return listWithItems.findElements(By.tagName("li")).stream()
					.allMatch(e -> e.getText().equals("Last 30 Days") || e.getText().equals("Last 90 Days")
							|| e.getText().equals("Last 180 Days") || e.getText().equals("Last 365 Days")
							|| e.getText().equals("Custom Dates"));

		} catch (Exception e) {
		    e.printStackTrace();
			return false;
		}
	}

	public void setTimeFrameFilter(String timeFrame) {
            Utils.clickElement(timeFrameField);
            Utils.selectOptionInDropDown(timeFrameDropDown, timeFrameListBox, timeFrame);
	}

	public boolean checkGrid() {
		try {
			Thread.sleep(3000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='WO Date']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='WO No']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='VIN']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Year']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Make']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Model']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Stock#']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='PDR Station']")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
