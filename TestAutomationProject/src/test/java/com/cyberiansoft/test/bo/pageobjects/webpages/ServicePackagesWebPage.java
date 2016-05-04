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

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ServicePackagesWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
	private WebElement searchtab;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvServiceGroup_ctl00")
	private WebTable servicepackagestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvServiceGroup_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addservicepackagebtn;
	
	//Search Panel
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_calFrom_dateInput")
	private TextField searchfromdatefld;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_calTo_dateInput")
	private TextField searchtodatefld;
		
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_bRecalcCommissions")
	private WebElement recalccomissionsbtn;
	
	public ServicePackagesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public boolean searchPanelIsExpanded() {
		return searchtab.getAttribute("class").contains("open");
	}
	
	public void makeSearchPanelVisible() {
		if (!searchPanelIsExpanded()) {
			click(searchbtn);
		}
	}
	
	public int getServicePackagesTableRowsCount() {
		return getServicePackagesTableRows().size();
	}
	
	public List<WebElement>  getServicePackagesTableRows() {
		return servicepackagestable.getTableRows();
	}
	
	public WebElement getTableRowWithServicePackage(String servicepackagename) {
		List<WebElement> servicepackagetablerows = getServicePackagesTableRows();
		for (WebElement servicepackagetablerow : servicepackagetablerows) {
			if (servicepackagetablerow.findElement(By.xpath(".//td[5]")).getText().equals(servicepackagename)) {
				return servicepackagetablerow;
			}
		}
		return null;
	}
	
	public void deleteServicePackage(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			deleteTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicepackagename + " service package");		
	}
	
	public void deleteServicePackageAndCancelDeleting(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + servicepackagename + " service package");	
		}
	}
	
	public NewServicePackageDialogWebPage clickEditServicePackage(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + servicepackagename + " service package");
		return PageFactory.initElements(
				driver, NewServicePackageDialogWebPage.class);
	}
	
	public NewServicePackageDialogWebPage clickAddServicePackageButton() {
		addservicepackagebtn.click();
		waitUntilPageReloaded();
		return PageFactory.initElements(
				driver, NewServicePackageDialogWebPage.class);
	}
	
	public boolean isServicePackageExists(String servicepackagename) {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(servicepackagestable.getWrappedElement()));
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  servicepackagestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servicepackagename + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return exists;
	}
	
	public String getTableServicePackageType(String servicepackagename) {
		String servicepkgtype = "";
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			servicepkgtype = row.findElement(By.xpath(".//td[6]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicepackagename + " service package");
		return servicepkgtype;
	}
	
	public String getTableServicePackageFormType(String servicepackagename) {
		String servicepkgfromtype = "";
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			servicepkgfromtype = row.findElement(By.xpath(".//td[7]")).getText();
		} else 
			Assert.assertTrue(false, "Can't find " + servicepackagename + " service package");
		return servicepkgfromtype;
	}
	
}
