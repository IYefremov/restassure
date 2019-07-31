package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class VNexBOServicesWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//button[contains(@class, 'btn-add-new-service')]/i")
	private WebElement addservicebtn;
	
	@FindBy(id = "serviceTable")
	private WebTable servicestable;
	
	@FindBy(id = "services-search")
	private WebElement searchservicespanel;
	
	@FindBy(id = "advSearchServices-freeText")
	private TextField searchservicefld;
	
	@FindBy(xpath = "//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")
	private WebElement searchicon;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[contains(text(), 'Next')]")
	private WebElement nextpagebtn;
	
	@FindBy(id = "advSearchServices-form")
	private WebElement advancedsearchform;
	
	@FindBy(id = "advSearchServices-name")
	private WebElement advancedsearchnamefld;
	
	@FindBy(xpath = "//span[@aria-owns='advSearchServices-type_listbox']/span")
	private WebElement advancedsearchtypefld;

	By editButton = By.xpath("//td[@class='grid__actions']/span[@class='icon-pencil2']");
	
	public VNexBOServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait
		  .until(ExpectedConditions.visibilityOf(addservicebtn));
	}
	
	public VNextBOAddNewServiceDialog clickAddNewServiceButton() {
		wait.until(ExpectedConditions.elementToBeClickable(addservicebtn)).click();
		waitABit(1000);
		return PageFactory.initElements(
				driver, VNextBOAddNewServiceDialog.class);
	}
	
	public void setSearchFreeTextValue(String searchtext) {
		searchservicefld.clearAndType(searchtext);
	}
	
	public VNexBOServicesWebPage searchServiceByServiceName(String searchtext) {
		setSearchFreeTextValue(searchtext);
		waitABit(1500);
		wait
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.elementToBeClickable(searchservicespanel.findElement(By
                        .xpath(".//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']"))))
                .click();
		waitForLoading();
		return this;
	}
	
	public void advancedSearchService(String searchtext, boolean archived) {
		setSearchFreeTextValue(searchtext);
		openAdvancedSearchPanel();
		wait
		  .until(ExpectedConditions.elementToBeClickable(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']"))));
		if (archived)
			checkboxSelect(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			checkboxUnselect(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']")));
		waitABit(1000);
		searchservicespanel.findElement(By.xpath(".//button[@type='submit']")).click();
		waitABit(1500);
	}
	
	public VNexBOServicesWebPage advancedSearchServiceByServiceType(String servicetype) {
		openAdvancedSearchPanel();
		wait
                .until(ExpectedConditions.elementToBeClickable(searchservicespanel
                        .findElement(By.xpath(".//input[@type='checkbox']"))));
		wait
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(By.xpath("//span[@aria-owns='advSearchServices-type_listbox']/span/span[2]"))))
                .click();
		wait
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("advSearchServices-type_listbox"))));
		WebElement advserchcmb = driver.findElement(By.id("advSearchServices-type_listbox"));
		waitABit(500);
		wait
                .until(ExpectedConditions.elementToBeClickable(advserchcmb.
                        findElement(By.xpath(".//li/span[text()='" + servicetype + "']"))))
                .click();
		waitABit(500);
		wait
                .until(ExpectedConditions.elementToBeClickable(searchservicespanel
                        .findElement(By.xpath(".//button[@type='submit']"))))
                .click();
		waitABit(1500);
		return this;
	}
	
	public void openAdvancedSearchPanel() {
		wait
                .until(ExpectedConditions.elementToBeClickable(searchservicespanel
                .findElement(By.xpath(".//i[@data-bind='click: showAdvancedSearch']"))))
                .click();
		wait
                .until(ExpectedConditions.visibilityOf(advancedsearchform));
	}
	
	public boolean isServicePresentOnCurrentPageByServiceName(String servicename) {
        try {
            if (!driver.findElement(By.xpath("//div[@id='services-list-view']/div/p")).getText().equals("No services to show")) {
                WebElement row = getTableRowWithServiceByServiceName(servicename);
                if (row != null) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }
	
	public String getServiceTypeValue(String servicename) {
		String servicetype = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			servicetype = row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Type") + "]")).getText().trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return servicetype; 
	}
	
	public String getServicePriceValue(String servicename) {
		String serviceprice = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			serviceprice = row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Price") + "]")).getText().trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return serviceprice; 
	}
	
	public String getServiceDescriptionValue(String servicename) {
		String serviceprice = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Description") + "]/i/i"))).perform();
			serviceprice = wait
                    .until(ExpectedConditions.visibilityOf(row.findElement(By.xpath("./td[" + servicestable
                            .getTableColumnIndex("Description") + "]/i/div"))))
                    .getText()
                    .trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return serviceprice; 
	}
	
	private WebElement getTableRowWithServiceByServiceName(String servicename) {
        waitABit(300);
        List<WebElement> rows = getServicesTableRows();
        if (rows.size() > 0) {
            try {
                return wait
                        .ignoring(StaleElementReferenceException.class)
                        .until(ExpectedConditions.visibilityOfAllElements(rows)).stream().filter(e -> e
                                .findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]"))
                                .getText()
                                .equals(servicename))
                        .findAny()
                        .get();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

	public String getFirstServiceNameInTableRow() {
        waitABit(300);
        List<WebElement> rows = getServicesTableRows();
        if (rows.size() > 0) {
            try {
                return wait
                        .ignoring(StaleElementReferenceException.class)
                        .until(ExpectedConditions.visibilityOf(rows.get(0)))
                        .findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]"))
                        .getText();
            } catch (NoSuchElementException ignored) {}
        }
        return null;
    }

	public List<WebElement>  getServicesTableRows() {
		waitLong.until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		return servicestable.getTableRows();
	}
	
	public int getServicesTableRowCount() {
		return getServicesTableRows().size();
	}
	
	public VNextBOAddNewServiceDialog clickEditServiceByServiceName(String servicename) {
		WebElement tablerow = getTableRowWithServiceByServiceName(servicename);
        VNextBOAddNewServiceDialog addNewServiceDialog = null;
		if (tablerow != null) {
            wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(editButton)));
            Actions actions = new Actions(driver);
            actions.moveToElement(tablerow.findElement(By.xpath("./td[@class='grid__actions']")))
                    .click(tablerow.findElement(By.xpath(".//span[@class='icon-pencil2']")))
                    .build()
                    .perform();
            addNewServiceDialog = PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
            if (!addNewServiceDialog.isNewServicePopupDisplayed()) {
                System.out.println("Clicked with JS!!!");
                clickWithJS(tablerow.findElement(By.xpath("//td[@class='grid__actions']/span[@class='icon-pencil2']")));
            }
        }
            return addNewServiceDialog;
	}
	
	public VNexBOServicesWebPage deleteServiceByServiceName(String servicename) {
		WebElement tablerow = getTableRowWithServiceByServiceName(servicename);
		clickDeleteServiceButton(tablerow);
		VNextBOConfirmationDialog confirmdialog = PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
		Assert.assertEquals(confirmdialog.clickYesAndGetConfirmationDialogMessage(), 
				"Are you sure you want to delete \"" + servicename + "\" service?");
//		waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
		wait.until(ExpectedConditions.elementToBeClickable(addservicebtn));
		waitABit(1000);
		return this;
	}
	
	public VNextBOConfirmationDialog clickDeleteServiceButton(WebElement tablerow) {
		Actions act = new Actions(driver);
		try {
            act.moveToElement(tablerow.findElement(By.xpath("./td[@class='grid__actions']")))
                    .click(tablerow.findElement(By.xpath(".//span[@title='Delete']")))
                    .build()
                    .perform();
            return PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
        } catch (Exception e) {
            clickWithJS(tablerow.findElement(By.xpath(".//span[@title='Delete']")));
            return PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
        }
	}
	
	public void clickDeleteServiceButtonAndAcceptAlert (WebElement tablerow) {
		clickDeleteServiceButton(tablerow);
		VNextBOConfirmationDialog confirmdialog = PageFactory.initElements(
				driver, VNextBOConfirmationDialog.class);
		confirmdialog.clickYesButton();
		waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
		wait.until(ExpectedConditions.elementToBeClickable(addservicebtn));
	}
	
	
	public void unarchiveServiceByServiceName(String servicename) {
		VNextBOConfirmationDialog confirmdialog = clickUnarchiveButtonForService(servicename);
		Assert.assertEquals(confirmdialog.clickYesAndGetConfirmationDialogMessage(), 
				"Are you sure you want to restore \"" + servicename + "\" service?");
		waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
	}
	
	public VNextBOConfirmationDialog clickUnarchiveButtonForService(String servicename) {
		WebElement servicerow = getTableRowWithServiceByServiceName(servicename);
		Actions act = new Actions(driver);
		act.moveToElement(servicerow.findElement(By.xpath("./td[@class='grid__actions']"))).click(servicerow.findElement(By.xpath(".//span[@data-bind='click: unArchiveService']"))).build().perform();
		return PageFactory.initElements(
				driver, VNextBOConfirmationDialog.class);
	}

    public VNexBOServicesWebPage deleteServiceIfPresent(String percentageservicename) {
        if (isServicePresentOnCurrentPageByServiceName(percentageservicename))
            deleteServiceByServiceName(percentageservicename);
        return this;
    }
}
