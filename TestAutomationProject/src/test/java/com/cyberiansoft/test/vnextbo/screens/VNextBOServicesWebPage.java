package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOServicesWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='services-view']//button[@class='btn btn-add-new-service pull-left']")
    private WebElement addservicebtn;

    @FindBy(id = "serviceTable")
    private WebTable servicestable;

    @FindBy(id = "services-search")
    private WebElement searchservicespanel;

    @FindBy(xpath = "//div[@id='services-search']//input[@type='checkbox']")
    private WebElement searchServicesArchiveCheckbox;

    @FindBy(xpath = "//section[@id='services-view']//i[@data-bind='click: showAdvancedSearch']")
    private WebElement advancedSearchServicesPanel;

    @FindBy(id = "advSearchServices-freeText")
    private TextField searchservicefld;

    @FindBy(xpath = "//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")
    private WebElement searchicon;

    @FindBy(xpath = "//div[@id='pagingPanel']/button[contains(text(), 'Next')]")
    private WebElement nextpagebtn;

    @FindBy(id = "advSearchServices-form")
    private WebElement advancedsearchform;

    @FindBy(id = "advSearchServices-name")
    private WebElement advancedSearchNameField;

    @FindBy(xpath = "//span[@aria-owns='advSearchServices-type_listbox']/span")
    private WebElement advancedSearchTypeField;

    @FindBy(id = "advSearchServices-type-list")
    private WebElement advancedSearchTypeDropDown;

    @FindBy(xpath = "//ul[@id='advSearchServices-type_listbox']/li")
    private List<WebElement> advancedSearchTypeListBox;

    @FindBy(id = "advSearchServices-type_listbox")
    private WebElement advancedSearchServicesTypeListBox;

    @FindBy(xpath = "//form[@id='advSearchServices-form']//button[@class='btn btn-black btn-wide']")
    private WebElement advancedSearchButton;

    @FindBy(xpath = "//span[@data-bind='click: unArchiveService']")
    private WebElement restoreButton;

    @FindBy(xpath = "//div[@id='services-list-view']/div/p")
    private WebElement servicesListView;

    By editButton = By.xpath("//td[@class='grid__actions']/span[@class='icon-pencil2']");

    public VNextBOServicesWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(addservicebtn));
    }

    public VNextBOAddNewServiceDialog clickAddNewServiceButton() {
        Utils.clickElement(addservicebtn);
        WaitUtilsWebDriver.waitABit(1000);
        return PageFactory.initElements(
                driver, VNextBOAddNewServiceDialog.class);
    }

    public void setSearchFreeTextValue(String searchtext) {
        waitABit(1000);
        searchservicefld.clearAndType(searchtext);
    }

    public VNextBOServicesWebPage searchServiceByServiceName(String searchtext) {
        setSearchFreeTextValue(searchtext);
        waitABit(1500);
        Utils.clickElement(searchservicespanel.findElement(By
                .xpath(".//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")));
        waitForLoading();
        return this;
    }

    public void advancedSearchService(String searchText, boolean archived) {
        setSearchFreeTextValue(searchText);
        openAdvancedSearchPanel();
        Utils.clickElement(searchServicesArchiveCheckbox);
        if (archived) {
            checkboxSelect(searchServicesArchiveCheckbox);
        } else {
            checkboxUnselect(searchServicesArchiveCheckbox);
        }
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickElement(searchServicesArchiveCheckbox);
        clickSearchButtonForAdvancedSearch();
    }

    public void performAdvancedSearchService(String searchText, String serviceType, boolean archived) {
        openAdvancedSearchPanel();
        setAdvancedSearchServiceName(searchText);
        if (archived) {
            wait.until(ExpectedConditions.elementToBeClickable(searchServicesArchiveCheckbox)).click();
        }
        selectAdvancedServiceType(serviceType);
        clickSearchButtonForAdvancedSearch();
    }

    private VNextBOServicesWebPage setAdvancedSearchServiceName(String serviceName) {
        wait.until(ExpectedConditions.elementToBeClickable(advancedSearchNameField)).clear();
        advancedSearchNameField.sendKeys(serviceName);
        waitABit(1000);
        return this;
    }

    public VNextBOServicesWebPage searchServiceByServiceType(String servicetype) {
        WaitUtilsWebDriver.waitForLoading();
        openAdvancedSearchPanel();
        wait.until(ExpectedConditions.elementToBeClickable(searchServicesArchiveCheckbox));
        selectAdvancedServiceType(servicetype);
        clickSearchButtonForAdvancedSearch();
        return this;
    }

    private void selectAdvancedServiceType(String serviceType) {
        Utils.clickElement(advancedSearchTypeField);
        Utils.selectOptionInDropDown(advancedSearchTypeDropDown, advancedSearchTypeListBox, serviceType, true);
    }

    private void clickSearchButtonForAdvancedSearch() {
        Utils.clickElement(advancedSearchButton);
        waitForLoading();
    }

    public void openAdvancedSearchPanel() {
        Utils.clickElement(advancedSearchServicesPanel);
        try {
            WaitUtilsWebDriver.waitForVisibility(advancedsearchform, 5);
        } catch (Exception e) {
            Utils.clickWithJS(advancedSearchServicesPanel);
            waitABit(1000);
        }
    }

    public boolean isServicePresentOnCurrentPageByServiceName(String servicename) {
        try {
            if (isNoServiceDisplayed()) {
                return false;
            } else {
                WebElement row = getTableRowWithServiceByServiceName(servicename);
                if (row != null) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    public int getNumOfServicesOnCurrentPageByServiceName(String servicename) {
        try {
            if (!isNoServiceDisplayed()) {
                List<WebElement> rows = getServicesTableRows();
                if (rows.size() > 0) {
                    try {
                        return wait
                                .ignoring(StaleElementReferenceException.class)
                                .until(ExpectedConditions.visibilityOfAllElements(rows)).stream().filter(e -> e
                                        .findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]"))
                                        .getText()
                                        .equals(servicename))
                                .collect(Collectors.toList())
                                .size();
                    } catch (NoSuchElementException ignored) {}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isNoServiceDisplayed() {
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(servicesListView, 7);
        try {
            return servicesListView.getText().equals("No services to show");
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
                        .until(ExpectedConditions.visibilityOfAllElements(rows))
                        .stream()
                        .filter(e -> e
                        .findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]"))
                        .getText()
                        .equals(servicename))
                        .findAny()
                        .get();
            } catch (Exception ignored) {}
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

    public List<WebElement> getServicesTableRows() {
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
            Actions actions = new Actions(driver);
            actions.moveToElement(wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(tablerow
                            .findElement(By.xpath("./td[@class='grid__actions']")))))
                    .click(tablerow.findElement(By.xpath(".//span[@class='icon-pencil2']")))
                    .build()
                    .perform();
            addNewServiceDialog = PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
            if (!addNewServiceDialog.isNewServicePopupDisplayed()) {
                clickWithJS(tablerow.findElement(editButton));
                addNewServiceDialog = PageFactory.initElements(driver, VNextBOAddNewServiceDialog.class);
            }
        }
        waitABit(2000);
        return addNewServiceDialog;
    }

    public VNextBOServicesWebPage deleteServiceByServiceName(String servicename) {
        WebElement tablerow = getTableRowWithServiceByServiceName(servicename);
        clickDeleteServiceButton(tablerow);
        Assert.assertEquals(VNextBOConfirmationDialogInteractions.clickYesAndGetConfirmationDialogMessage(),
                "Are you sure you want to delete \"" + servicename + "\" service?");
        return this;
    }

    private VNextBOConfirmationDialog clickDeleteServiceButton(WebElement tablerow) {
        Actions act = new Actions(driver);
        try {
            act.moveToElement(wait
                    .until(ExpectedConditions.elementToBeClickable(tablerow
                            .findElement(By.xpath(".//td[@class='grid__actions']")))))
                    .click(tablerow.findElement(By.xpath(".//span[@title='Delete']")))
                    .build()
                    .perform();
            return PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
        } catch (Exception ignored) {
            System.err.println("********* Clicked with JS *********");
            clickWithJS(tablerow.findElement(By.xpath(".//span[@title='Delete']")));
            return PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
        }
    }

    public void clickDeleteServiceButtonAndAcceptAlert(WebElement tablerow) {
        clickDeleteServiceButton(tablerow);
        VNextBOConfirmationDialogInteractions.clickYesButton();
        waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
        wait.until(ExpectedConditions.elementToBeClickable(addservicebtn));
    }

    public void unarchiveServiceByServiceName(String serviceName) {
        clickUnarchiveButtonForService(serviceName);
        Assert.assertEquals(VNextBOConfirmationDialogInteractions.clickYesAndGetConfirmationDialogMessage(),
                "Are you sure you want to restore \"" + serviceName + "\" service?");
        waitForLoading();
        waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
    }

    public VNextBOConfirmationDialog clickUnarchiveButtonForService(String servicename) {
        WebElement servicerow = getTableRowWithServiceByServiceName(servicename);
        Actions act = new Actions(driver);
        act.moveToElement(servicerow.findElement(By.xpath("./td[@class='grid__actions']")))
                .click(servicerow.findElement(By.xpath(".//span[@data-bind='click: unArchiveService']")))
                .build()
                .perform();
        return PageFactory.initElements(
                driver, VNextBOConfirmationDialog.class);
    }

    public VNextBOServicesWebPage deleteServiceIfPresent(String percentageservicename) {
        while (isServicePresentOnCurrentPageByServiceName(percentageservicename))
            deleteServiceByServiceName(percentageservicename);
        return this;
    }
}
