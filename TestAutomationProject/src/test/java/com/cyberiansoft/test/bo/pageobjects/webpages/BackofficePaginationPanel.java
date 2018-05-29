package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BackofficePaginationPanel  extends BaseWebPage {
	
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
	
	@FindBy(xpath = "//*[contains(@id, 'ChangePageSizeTextBox')]")
	private WebElement pagesizefld;
	
	@FindBy(xpath = "//*[contains(@id, 'ChangePageSizeLinkButton')]")
	private WebElement changesizebtn;
	
	@FindBy(xpath = "//*[contains(@id, 'GoToPageTextBox')]")
	private WebElement gotopagefld;
	
	@FindBy(xpath = "//*[contains(@id, 'GoToPageLinkButton')]")
	private WebElement gotopagebtn;
	
	@FindBy(xpath = "//*[contains(@id, 'PageOfLabel')]")
	private WebElement pageoflabel;
	
	@FindBy(xpath = "//div[@class='rgWrap rgInfoPart']")
	private WebElement pagesizelabel;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	public BackofficePaginationPanel(WebDriver driver) {
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
		wait.until(ExpectedConditions.visibilityOf(gotopagefld));
		return gotopagefld.getAttribute("value");
	}
	
	public void setPageSize(String pagesize) throws InterruptedException {
		pagesizefld.clear();
		wait.until(ExpectedConditions.visibilityOf(updateProcess));
		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(1000);
		pagesizefld.sendKeys(pagesize + "\n");
		changesizebtn.click();
		wait.until(ExpectedConditions.visibilityOf(updateProcess));
		wait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(3000);
	}
	
	public void clickGoToLastPage() {
		gotolastpage.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + getLastPageNumber()  + "']")));
	}
	
	public void clickGoToFirstPage() {
		gotofirstpage.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='1']")));
	}
	
	public void clickGoToNextPage()  {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int nextpage = currenpage + 1;
		gotonextpage.click();		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + nextpage  + "']")));
	}
	
	public void clickGoToPreviousPage() {
		int currenpage = Integer.valueOf(getCurrentlySelectedPageNumber());
		int previouspage = currenpage - 1;
		gotopreviouspage.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='rgCurrentPage']/span[text()='" + previouspage  + "']")));
	}
	
}
