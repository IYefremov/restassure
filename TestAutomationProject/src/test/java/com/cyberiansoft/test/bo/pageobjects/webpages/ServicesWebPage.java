package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ServicesWebPage extends WebPageWithPagination {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable servicestable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00__0")
	private WebTable archivedservicestable;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Active - ALL']")
	private WebElement activetab;
	
	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Archived']")
	private WebElement archivedtab;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addservicebtn;
	
	//Search Panel

 	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboType_Input")
	private ComboBox searchservicetypecbx;

  	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboType_DropDown")
	private DropDown searchservicetypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboPriceType_Input")
	private ComboBox searchpricetypecbx;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_comboPriceType_DropDown")
	private DropDown searchpricetypedd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_tbSearch")
	private TextField searchservicefld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_filterer_BtnFind")
	private WebElement findbtn;
	
	@FindBy(xpath = "//div[@class='rgWrap rgInfoPart']")
	private WebElement pagesizelabel;


	@FindBy(xpath = "//input[@title='Delete']")
	private WebElement deletemarker;

	public ServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	
	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public void verifyServicesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		Assert.assertTrue(servicestable.isTableColumnExists("Packages"));
		Assert.assertTrue(servicestable.isTableColumnExists("Type"));
		Assert.assertTrue(servicestable.isTableColumnExists("Service"));
		Assert.assertTrue(servicestable.isTableColumnExists("Description"));
		Assert.assertTrue(servicestable.isTableColumnExists("Wh. Price"));
		Assert.assertTrue(servicestable.isTableColumnExists("Retail Price"));
		Assert.assertTrue(servicestable.isTableColumnExists("Multiple"));
		Assert.assertTrue(servicestable.isTableColumnExists("Acc.ID"));
		Assert.assertTrue(servicestable.isTableColumnExists("Acc.ID 2"));
		Assert.assertTrue(servicestable.isTableColumnExists("Action"));
	}
	
	public WebElement getTableRowWithActiveService(String servicename) {
		List<WebElement> employeestablerows = getServicesTableRows();
		for (WebElement employeestablerow : employeestablerows) {
			if (employeestablerow.findElement(By.xpath(".//td[7]")).getText().equals(servicename)) {
				return employeestablerow;
			}
		}
		return null;
	}
	

	public WebElement getTableRowWithArchivedService(String servicename) {
		List<WebElement> employeestablerows = getArchivedServicesTableRows();
		for (WebElement employeestablerow : employeestablerows) {
			System.out.println(employeestablerow.findElement(By.xpath(".//td[4]")).getText());
			System.out.println(employeestablerow.findElement(By.xpath(".//td[3]")).getText());
			if (employeestablerow.findElement(By.xpath(".//td[4]")).getText().equals(servicename)) {
				return employeestablerow;
			}
		}
//if(employeestablerows.size() ==1)		
//				return employeestablerows.get(0);
			
		
		return null;
	}
	
	public int getServicesTableRowsCount() {
		return getServicesTableRows().size();
	}
	
	public List<WebElement> getServicesTableRows() {
		return servicestable.getTableRows();
	}
	
	public List<WebElement> getArchivedServicesTableRows() {
		return archivedservicestable.getTableRows();
	}
	
	public void setServiceSearchCriteria(String name) {
		searchservicefld.clearAndType(name);
	}

	public void selectSearchServiceType(String servicetype) {
		selectComboboxValue(searchservicetypecbx, searchservicetypedd, servicetype);
	}
	
	public void selectSearchPriceType(String pricetype) {
		selectComboboxValue(searchpricetypecbx, searchpricetypedd, pricetype);
	}
	
	public void clickFindButton() { 
		clickAndWait(findbtn);
	}
	
	public void clickArchivedTab() {
		clickAndWait(archivedtab);
	}
	
	public void clickActiveTab() {
		wait.until(ExpectedConditions.elementToBeClickable(activetab));
		clickAndWait(activetab);
	}

	public void archiveService(String servicename) throws InterruptedException {
		Thread.sleep(2000);
		activetab.click();
		WebElement row = getTableRowWithActiveService(servicename);
		if (row != null) {
			archiveTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicename + " service");		
	}
	
	public void unarchiveService(String servicename) {
		waitABit(1000);
		WebElement row = getTableRowWithArchivedService(servicename);
		if (row != null) {
			restoreTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicename + " service");			
	}
	
	public boolean isActiveServiceExists(String servicename) {
		wait.until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		boolean exists =  servicestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servicename + "']")).size() > 0;
		return exists;
	}
	
	public boolean isArchivedServiceExists(String servicename) {
		waitABit(1500);
		wait.until(ExpectedConditions.visibilityOf(archivedservicestable.getWrappedElement()));
		waitABit(1500);
		boolean exists =  archivedservicestable.getWrappedElement().findElements(By.xpath("//td[text()='" + servicename + "']")).size() > 0;
		return exists;
	}
	
	public NewServiceDialogWebPage clickAddServiceButton() {
		clickAndWait(addservicebtn);
		return PageFactory.initElements(
				driver, NewServiceDialogWebPage.class);
	}
	
	public NewServiceDialogWebPage clickEditService(String servicename) {
		WebElement row = getTableRowWithActiveService(servicename);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");
		}
		return PageFactory.initElements(
				driver, NewServiceDialogWebPage.class);
	}
}
