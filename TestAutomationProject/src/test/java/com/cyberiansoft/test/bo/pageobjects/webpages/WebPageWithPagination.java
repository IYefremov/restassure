package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

@Getter
public class WebPageWithPagination extends BaseWebPage {

	public final int MAX_TABLE_ROW_COUNT_VALUE = 50;

	//Pagination
	@FindBy(xpath = "//input[@title='First Page']")
	private WebElement gotofirstpage;

	@FindBy(xpath = "//input[@title='Previous Page']")
	private WebElement gotopreviouspage;

	@FindBy(xpath = "//input[@title='Next Page']")
	private WebElement goToNextPage;

	@FindBy(xpath = "//input[@title='Last Page']")
	private WebElement gotolastpage;

	@FindBy(xpath = "//div[@class='rgWrap rgNumPart']")
	private WebElement paginations;

	@FindBy(xpath = "//input[contains(@id, 'ChangePageSizeTextBox')][1]")
	private WebElement pageSizeField;

	@FindBy(xpath = "//*[contains(@id, 'ChangePageSizeLinkButton')]")
	private WebElement changeSizeButton;

	@FindBy(xpath = "//input[contains(@id, 'GoToPageTextBox') and @type='text']")
	private WebElement gotopagefld;

	@FindBy(xpath = "//*[contains(@id, 'GoToPageLinkButton')]")
	private WebElement gotopagebtn;

	@FindBy(xpath = "//*[contains(@id, 'PageOfLabel')]")
	private WebElement pageoflabel;

	@FindBy(xpath = "//div[@class='rgWrap rgInfoPart']")
	private WebElement pagesizelabel;

	@FindBy(className = "updateProcess")
	private WebElement updateProcess;

	public WebPageWithPagination(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public String getLastPageNumber() {
		String oftxt = "of ";
		wait.ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOf(pageoflabel));
		return pageoflabel.getText().substring(oftxt.length());
	}

	public String getCurrentlySelectedPageNumber() {
		return paginations.findElement(By.xpath(".//a[@class='rgCurrentPage']/span")).getText();
	}

	public String getGoToPageFieldValue() {
		return Utils.getInputFieldValue(gotopagefld);
	}

	public String getPageFieldValue() {
		return getGoToPageFieldValue().replaceAll(",", "");
	}

	public void setPageSize(String pageSize) {
		setAttribute(pageSizeField, "value", "");
		wait.until(ExpectedConditions.elementToBeClickable(pageSizeField)).clear();
		wait.until(ExpectedConditions.elementToBeClickable(pageSizeField)).sendKeys(pageSize);
		pageSizeField.sendKeys(Keys.ENTER);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(changeSizeButton)).click();
		} catch (WebDriverException e) {
			wait
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(changeSizeButton));
			clickWithJS(changeSizeButton);
		}
		waitForLoading();
	}

	public void clickGoToLastPage() {
        Utils.clickElement(gotolastpage);
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber() + "']")));
	}

	public void clickGoToLastPage(String browsertype) {
		if (browsertype.equals("ie")) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", gotolastpage);
		} else {
		    Utils.clickElement(gotolastpage);
		}
		try {
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		} catch (Exception e) {
			e.printStackTrace();
		}
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber() + "']")));
		wait.withTimeout(30, TimeUnit.SECONDS);
	}

	public void clickGoToFirstPage() {
		gotofirstpage.click();
		try {
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		} catch (TimeoutException e) {
		}
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='1']")));
		waitABit(1000);
	}

	public void clickGoToNextPage() {
		int currentPage = Integer.parseInt(getCurrentlySelectedPageNumber());
		int nextPage = currentPage + 1;
		goToNextPage.click();
		waitForLoading();
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + nextPage + "']")));
		} catch (TimeoutException ignored) {
		}
	}

	public void clickGoToPreviousPage() {
		int currenpage = Integer.parseInt(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		gotopreviouspage.click();
		try {
			wait.until(ExpectedConditions.visibilityOf(updateProcess));
			wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		} catch (TimeoutException ignored) {
		}
		waitForLoading();
	}
}