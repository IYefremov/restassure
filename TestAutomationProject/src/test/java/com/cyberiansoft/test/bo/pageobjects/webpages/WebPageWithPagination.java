package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;


public class WebPageWithPagination extends BaseWebPage {
	
	public final Integer MAX_TABLE_ROW_COUNT_VALUE = 50;
	
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
		return pageoflabel.getText().substring(oftxt.length(), pageoflabel.getText().length());
	}
	
	public String getCurrentlySelectedPageNumber() {
		return paginations.findElement(By.xpath(".//a[@class='rgCurrentPage']/span")).getText();
	}
	
	public String getGoToPageFieldValue() {
		return gotopagefld.getAttribute("value");
	}
	
	public void setPageSize(String pageSize) throws InterruptedException {
		pagesizefld.clear();
		Thread.sleep(1000);
		pagesizefld.sendKeys(pageSize + "\n");
		waitForLoading();
	}
	
	public void clickGoToLastPage() {
		gotolastpage.click();
        waitForLoading();
        waitABit(10000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
	}
	
	public void clickGoToLastPage(String browsertype) {
		if (browsertype.equals("ie") ) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", gotolastpage);
		} else {
			gotolastpage.click();
		}
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(300);
		if (driver.findElements(By.xpath("//div[contains(text(), 'Loading...')]")).size() > 0) {
			wait.withTimeout(1, TimeUnit.MINUTES).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		}	
	//Thread.sleep(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
		wait.withTimeout(30, TimeUnit.SECONDS);
	}
	
	public void clickGoToFirstPage() {
		gotofirstpage.click();
		try{
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		}catch(TimeoutException e){}
		//waitABit(300);
//		if (driver.findElements(By.xpath("//div[contains(text(), 'Loading...')]")).size() > 0) {
//			wait.withTimeout(1, TimeUnit.MINUTES).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
//			wait.withTimeout(30, TimeUnit.SECONDS);
//		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='1']")));
	}
	
	public void clickGoToNextPage()  {
		int currentPage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int nextPage = currentPage + 1;
		goToNextPage.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + nextPage  + "']")));
	}
	
	public void clickGoToPreviousPage() {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		gotopreviouspage.click();
		try{
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		}catch(TimeoutException e){}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void waitForLoading(){
		try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		} catch (TimeoutException ignored) {}
	}
}
