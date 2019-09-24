package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.utils.RequestMethod;
import com.cyberiansoft.test.bo.utils.URLStatusChecker;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

public class ServiceContractsWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable repairlocationstable;
	
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboType_Input")
	private TextField searchtypefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboStatus_Input")
	private WebElement searchstatuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
	private TextField searchtextfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;
	
	////////////////////////////
	@FindBy(xpath = "//span[text()='Contract Types']")
	private WebElement contracttypesbtn;
	
	@FindBy(xpath = "//span[text()='Contract Data']")
	private WebElement contractdatabtn;
	
	@FindBy(xpath = "//span[text()='Claim Data']")
	private WebElement claimdatabtn;
	
	@FindBy(xpath = "//span[text()='Portfolio']")
	private WebElement portfoliobtn;
	
	@FindBy(xpath = "//*[contains(@id, 'dpPortfolioFrom_popupButton')]")
	private WebElement portfoliocalendarfrombtn;
	
	@FindBy(xpath = "//*[contains(@id, 'dpPortfolioTo_popupButton')]")
	private WebElement portfoliocalendartobtn;
	
	@FindBy(xpath = "//*[contains(@id, 'btnExportToPortfolio')]")
	private WebElement portfolioexportbtn;
	
	@FindBy(xpath = "//ul[@class='rtbActive rtbGroup rtbLevel1']")
	private WebElement dropdownmenu;
	
	public ServiceContractsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(repairlocationstable.getWrappedElement()));
	}
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyServiceContactsTableColumnsAreVisible() {
	    WaitUtilsWebDriver.waitABit(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chkAllItems")));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Invoice #"));
		Assert.assertEquals(repairlocationstable.getWrappedElement().findElement(By.xpath(".//tr/th[4]")).getText(), "ID /\nContract#");
		Assert.assertEquals(repairlocationstable.getWrappedElement().findElement(By.xpath(".//tr/th[6]")).getText(), "Issue Date /\nExpiration");
		Assert.assertTrue(repairlocationstable.tableColumnExists("Status"));
		Assert.assertEquals(repairlocationstable.getWrappedElement().findElement(By.xpath(".//tr/th[8]")).getText(), "Type /\nDescription");
		Assert.assertTrue(repairlocationstable.tableColumnExists("Client"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Vehicle"));
		Assert.assertEquals(repairlocationstable.getWrappedElement().findElement(By.xpath(".//tr/th[11]")).getText(), "Dealer /\nAgent Name");
		Assert.assertTrue(repairlocationstable.tableColumnExists("Valid"));
		Assert.assertTrue(repairlocationstable.tableColumnExists("Service Request"));
	}
	
	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchtypefld.isDisplayed());
		Assert.assertTrue(searchtextfld.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
	}
	
	public ServiceContractTypesWebPage clickContractTypesButton() {
		click(contracttypesbtn);
		return PageFactory.initElements(
				driver, ServiceContractTypesWebPage.class);
	}
	
	public void clickContractDataButton() {
		wait.until(ExpectedConditions.elementToBeClickable(repairlocationstable.getWrappedElement()));
		click(contractdatabtn);
		waitABit(1000);
	}
	
	public void clickClaimDataButton() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(repairlocationstable.getWrappedElement()));
		click(claimdatabtn);
		waitABit(1000);
	}
	
	public void clickPortfolioButton() {
		click(portfoliobtn);
	}
	
	public void verifyDropDownMenuIsOpened(String browsertype) throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException {
		wait.until(ExpectedConditions.visibilityOf(dropdownmenu));
		Assert.assertTrue(dropdownmenu.isDisplayed());
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		List<WebElement> _elemnts = dropdownmenu.findElements(By.xpath(".//li"));
		for (WebElement el : _elemnts) {
			String url = el.findElement(By.xpath(".//a")).getAttribute("href");
			urlChecker.setURIToCheck(url);
		    urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		    if (browsertype == "ie") {
		    	Assert.assertEquals(urlChecker.getHTTPSStatusCode(), 200);
		    } else {
		    	Assert.assertEquals(urlChecker.getHTTPSStatusCode(), 200);
		    }
		}
	}
	
	public void verifyPortfolioOptionsAreOpened() {
		Assert.assertTrue(portfoliocalendarfrombtn.isDisplayed());
		Assert.assertTrue(portfoliocalendartobtn.isDisplayed());
		Assert.assertTrue(portfolioexportbtn.isDisplayed());
	}
}
