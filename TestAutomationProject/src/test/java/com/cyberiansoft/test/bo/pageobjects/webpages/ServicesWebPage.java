package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

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
	private WebElement activeAllTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Active - Parts']")
	private WebElement activePartsTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Active - Labor']")
	private WebElement activeLaborTab;

	@FindBy(xpath = "//span[@class='rtsTxt' and text()='Archived']")
	private WebElement archivedtab;

	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addServiceButton;

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

	@FindBy(className = "rgNoRecords")
	private WebElement noData;

	public ServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}


	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}

	public ServicesWebPage makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
		return PageFactory.initElements(driver, ServicesWebPage.class);
	}

	public void verifyServicesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		Assert.assertTrue(servicestable.tableColumnExists("Packages"));
		Assert.assertTrue(servicestable.tableColumnExists("Type"));
		Assert.assertTrue(servicestable.tableColumnExists("Service"));
		Assert.assertTrue(servicestable.tableColumnExists("Description"));
		Assert.assertTrue(servicestable.tableColumnExists("Wh. Price"));
		Assert.assertTrue(servicestable.tableColumnExists("Retail Price"));
		Assert.assertTrue(servicestable.tableColumnExists("Multiple"));
		Assert.assertTrue(servicestable.tableColumnExists("Acc.ID"));
		Assert.assertTrue(servicestable.tableColumnExists("Acc.ID 2"));
		Assert.assertTrue(servicestable.tableColumnExists("Action"));
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

	public void clickActiveAllTab() {
		clickAndWait(activeAllTab);
	}

	public void clickActivePartsTab() {
		clickAndWait(activePartsTab);
	}

	public void clickActiveLaborTab() {
		clickAndWait(activeLaborTab);
	}

	public void archiveServiceForActiveAllTab(String serviceName) {
		waitABit(1000);
        Utils.clickElement(activeAllTab);
		archiveService(serviceName);
	}

	public void archiveService(String serviceName) {
		WebElement row = getTableRowWithActiveService(serviceName);
		if (row != null) {
			archiveTableRow(row);
		} else
			Assert.fail("Can't find " + serviceName + " service");
	}

	public void unarchiveService(String servicename) {
//		waitABit(1000);
//		WebElement row = getTableRowWithArchivedService(servicename);
//		if (row != null) {
//			restoreTableRow(row);
//		} else
//			Assert.assertTrue(false, "Can't find " + servicename + " service");
		waitABit(3000);
		driver.findElement(By.xpath("//td[text()='" + servicename + "']")).findElement(By.xpath(".."))
				.findElement(By.xpath("//input[@alt='Restore']")).click();
		driver.switchTo().alert().accept();
		waitForLoading();
	}

	public void verifyActiveServiceDoesNotExist(String serviceName) {
		try {
			while (activeServiceExists(serviceName)) {
				archiveService(serviceName);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		Assert.assertFalse(activeServiceExists(serviceName), "The service has not been deleted");
	}

	public boolean activeServiceExists(String servicename) {
		wait.until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		try {
			return servicestable.getWrappedElement()
					.findElements(By.xpath(".//tr/td[text()='" + servicename + "']")).size() > 0;
		} catch (TimeoutException | NoSuchElementException ignored) {
			return false;
		}
	}

	public boolean archivedServiceExists(String servicename) {
		wait.until(ExpectedConditions.visibilityOf(archivedservicestable.getWrappedElement()));
		try {
			return archivedservicestable.getWrappedElement()
					.findElements(By.xpath("//td[text()='" + servicename + "']")).size() > 0;
		} catch (TimeoutException | NoSuchElementException ignored) {
			return false;
		}
	}

	public void clickAddServiceButton() {
		clickAndWait(addServiceButton);
	}

	public void clickEditService(String servicename) {
		WebElement row = getTableRowWithActiveService(servicename);
		if (row != null) {
			clickEditTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");
		}
	}
}
