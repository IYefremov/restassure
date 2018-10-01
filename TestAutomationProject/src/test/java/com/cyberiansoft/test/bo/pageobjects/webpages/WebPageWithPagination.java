package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;


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
	
	@FindBy(xpath = "//input[contains(@id, 'ChangePageSizeTextBox')]")
	private WebElement pagesizefld;
		
	@FindBy(xpath = "//*[contains(@id, 'ChangePageSizeLinkButton')]")
	private WebElement changesizebtn;
		
	@FindBy(xpath = "//input[contains(@id, 'GoToPageTextBox')]")
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
		return pageoflabel.getText().substring(oftxt.length(), pageoflabel.getText().length());
	}
	
	public String getCurrentlySelectedPageNumber() {
		return paginations.findElement(By.xpath(".//a[@class='rgCurrentPage']/span")).getText();
	}
	
	public String getGoToPageFieldValue() {
		return gotopagefld.getAttribute("value");
	}

	public String getPageFieldValue() {
        return getGoToPageFieldValue().replace(",", "");
	}
	
	public void setPageSize(String pageSize) {
		wait.until(ExpectedConditions.elementToBeClickable(pagesizefld)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(pagesizefld)).sendKeys(pageSize);
        pagesizefld.sendKeys(Keys.ENTER);
		waitForLoading();
    }
	
	public void clickGoToLastPage() {
	    wait.until(ExpectedConditions.elementToBeClickable(gotolastpage));
		gotolastpage.click();
        waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
	}
	
	public void clickGoToLastPage(String browsertype) {
		if (browsertype.equals("ie") ) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", gotolastpage);
		} else {
			gotolastpage.click();
		}
		wait.until(ExpectedConditions.visibilityOf(updateProcess));
		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
		wait.withTimeout(30, TimeUnit.SECONDS);
	}
	
	public void clickGoToFirstPage() {
		gotofirstpage.click();
		try{
            wait.until(ExpectedConditions.visibilityOf(updateProcess));
            wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		} catch(TimeoutException e) {}
		waitForLoading();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='1']")));
		waitABit(1000);
	}
	
	public void clickGoToNextPage()  {
		int currentPage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int nextPage = currentPage + 1;
		goToNextPage.click();
		waitForLoading();
		try {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + nextPage  + "']")));
        } catch (TimeoutException ignored) {}
	}
	
	public void clickGoToPreviousPage() {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		gotopreviouspage.click();
		try {
		    wait.until(ExpectedConditions.visibilityOf(updateProcess));
		    wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		} catch(TimeoutException ignored){}
		waitForLoading();
	}
}