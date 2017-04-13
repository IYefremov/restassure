package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class WebPageWithPagination extends BaseWebPage {
	
	public final Integer MAX_TABLE_ROW_COUNT_VALUE = 50;
	
	//Pagination
	@FindBy(xpath = "//input[@title='First Page']")
	private WebElement gotofirstpage;
		
	@FindBy(xpath = "//input[@title='Previous Page']")
	private WebElement gotopreviouspage;
		
	@FindBy(xpath = "//input[@title='Next Page']")
	private WebElement gotonextpage;
		
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
	
	public String getGoToPageFieldValue() throws InterruptedException {
		return gotopagefld.getAttribute("value");
	}
	
	public void setPageSize(String pagesize) throws InterruptedException {
		pagesizefld.clear();
		//Thread.sleep(1000);
		pagesizefld.sendKeys(pagesize + "\n");
		//changesizebtn.click();
		//Thread.sleep(700);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		//Thread.sleep(3000);
	}
	
	public void clickGoToLastPage() {
		gotolastpage.click();
		//waitABit(700);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
	}
	
	public void clickGoToLastPage(String browsertype) throws InterruptedException {
		if (browsertype.equals("ie") ) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", gotolastpage);
		} else {
			gotolastpage.click();
		}
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
		//waitABit(300);
		if (driver.findElements(By.xpath("//div[contains(text(), 'Loading...')]")).size() > 0) {
			wait.withTimeout(1, TimeUnit.MINUTES).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			wait.withTimeout(30, TimeUnit.SECONDS);
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='1']")));
	}
	
	public void clickGoToNextPage()  {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int nextpage = currenpage + 1;
		gotonextpage.click();		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + nextpage  + "']")));
	}
	
	public void clickGoToPreviousPage() throws InterruptedException {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		gotopreviouspage.click();
		//Thread.sleep(300);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + previouspage  + "']")));
	}

}
