package com.cyberiansoft.test.bo.pageobjects.webpages;

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

import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

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
	
	//Service Package Items
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable servicepackageitemstable;

	@FindBy(xpath = "//div[@class='radwindow_Vista']")
	private WebElement servicePackageModalWindow;
	
	public ServicePackagesWebPage(WebDriver driver) {
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
	
	public int getServicePackagesTableRowsCount() {
		return getServicePackagesTableRows().size();
	}
	
	public List<WebElement>  getServicePackagesTableRows() {
		return servicepackagestable.getTableRows();
	}
	
	//todo add search by column name with CSS
	public WebElement getTableRowWithServicePackage(String servicepackagename) {
		List<WebElement> servicepackagetablerows = getServicePackagesTableRows();
		for (WebElement servicepackagetablerow : servicepackagetablerows) {
			if (servicepackagetablerow.findElement(By.xpath(".//td[" + 
					servicepackagestable.getTableColumnIndex("Service Package") + "]")).getText().equals(servicepackagename)) {
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
            Assert.fail("Can't find " + servicepackagename + " service package");
	}
	
	public void deleteServicePackageAndCancelDeleting(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
            Assert.fail("Can't find " + servicepackagename + " service package");
		}
	}
	
	public NewServicePackageDialogWebPage clickEditServicePackage(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			clickEditTableRow(row);
		} else
            Assert.fail("Can't find " + servicepackagename + " service package");
		wait.until(ExpectedConditions.visibilityOf(servicePackageModalWindow));
		return PageFactory.initElements(
				driver, NewServicePackageDialogWebPage.class);
	}
	
	public NewServicePackageDialogWebPage clickAddServicePackageButton() {
		addservicepackagebtn.click();
		waitForLoading();
		return PageFactory.initElements(
				driver, NewServicePackageDialogWebPage.class);
	}
	
	public boolean isServicePackageExists(String servicepackagename) {
		wait.until(ExpectedConditions.visibilityOf(servicepackagestable.getWrappedElement()));
        return servicepackagestable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + servicepackagename + "']")).size() > 0;
	}
	
	public String getTableServicePackageType(String servicepackagename) {
        return getTableServicePackage(servicepackagename, ".//td[6]");
	}

    public String getTableServicePackage(String servicepackagename, String s) {
        String servicepkgtype = "";
        WebElement row = getTableRowWithServicePackage(servicepackagename);
        if (row != null) {
            servicepkgtype = row.findElement(By.xpath(s))
                    .getText()
                    .replaceAll("\\u00A0", "")
                    .trim();
        } else
            Assert.fail("Can't find " + servicepackagename + " service package");
        return servicepkgtype;
    }

    public String getTableServicePackageFormType(String servicepackagename) {
        return getTableServicePackage(servicepackagename, ".//td[7]");
	}
	
	public Set<String> clickServicesLinkForServicePackage(String servicepackagename) {
		WebElement row = getTableRowWithServicePackage(servicepackagename);
		if (row != null) {
			row.findElement(By.xpath(".//a[contains(text(), 'Services')]")).click();
		} else
            Assert.fail("Can't find " + servicepackagename + " service package");
		waitForNewTab();
    	String mainWindowHandle = driver.getWindowHandle();
		for (String activeHandle : driver.getWindowHandles()) {
	        if (!activeHandle.equals(mainWindowHandle)) {
	        	driver.switchTo().window(activeHandle);
	        }
	    }
		return driver.getWindowHandles();
	}
	
	////////////////////////
	public int getServicePackageItemsTableRowsCount() {
		return getServicePackageItemsTableRows().size();
	}
	
	public List<WebElement> getServicePackageItemsTableRows() {
		return servicepackageitemstable.getTableRows();
	}
	
	public List<WebElement> getAllServicePackageItems() {
		return servicepackageitemstable.getWrappedElement().findElements(By.xpath(".//tr[contains(@id, 'ctl00_Content_gv_ctl00')]/td[" + servicepackageitemstable.getTableColumnIndex("Service") + "]"));
	}

    public void verifyServicePackagesDoNotExist(String servicepackagename, String servicepackagenameedited) {
        while (isServicePackageExists(servicepackagename)) {
            deleteServicePackage(servicepackagename);
        }
        while (isServicePackageExists(servicepackagenameedited)) {
            deleteServicePackage(servicepackagenameedited);
        }
    }
}
