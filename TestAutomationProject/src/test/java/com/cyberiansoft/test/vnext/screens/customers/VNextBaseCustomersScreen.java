package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.CustomersListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextBaseCustomersScreen extends VNextBaseScreen {

    @FindBy(xpath = "//span[@class='client-mode']")
    private WebElement clientmode;

    @FindBy(xpath = "//*[@data-autotests-id='customers-list']")
    private WebElement customersList;

    @FindBy(xpath = "//*[@data-autotests-id='customers-list']/div")
    private List<CustomersListElement> customersListArray;

    @FindBy(xpath = "//*[@action='select-customer']")
    private WebElement firstcustomer;

    @FindBy(xpath = "//*[@action='add']")
    private WebElement addcustomerbtn;

    @FindBy(xpath = "//*[@action='search']")
    private WebElement searchbtn;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    WebElement cancelsearchbtn;

    @FindBy(xpath = "//*[@action='select-retail']")
    private WebElement retailcustomertab;

    @FindBy(xpath = "//*[@action='select-wholesale']")
    private WebElement wholesalecustomertab;

    @FindBy(xpath = "//div[@class='notice-plate']")
    private WebElement presetcustomerpanel;

    public VNextBaseCustomersScreen(WebDriver appiumdriver) {
        super(appiumdriver);
    }

    public void selectCustomer(AppCustomer customer) {

        WaitUtils.waitUntilElementIsClickable(customersList);
        if (customer.isWholesale()) {
            switchToRetailMode();
        } else {
            switchToWholesaleMode();
        }
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(customer.getFullName());
        tap(customersList.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
    }


    public boolean isCustomerExists(AppCustomer customer) {
        searchCustomerByName(customer.getFullName());
        return customersList.findElements(By.xpath(".//p[text()='" + customer.getFullName() + "']")).size() > 0;
    }

    public void clickBackButton() {
        if (cancelsearchbtn.isDisplayed())
            tap(cancelsearchbtn);
        clickScreenBackButton();
    }

    public void switchToRetailMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(retailcustomertab));
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Actions actions = new Actions(appiumdriver);
            actions.moveToElement(retailcustomertab, 30, 0).click().perform();
            return true;
        });
    }

    public void switchToWholesaleMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(wholesalecustomertab));
        tap(wholesalecustomertab);
    }

    public void searchCustomerByName(String customername) {
        typeSearchParameters(customername);
        cancelsearchbtn.click();
    }

    public void clickSearchButton() {
        tap(searchbtn);
    }

    public void typeSearchParameters(String searchtxt) {
        BaseUtils.waitABit(1000);
        if (appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")).isDisplayed())
            tap(appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(searchfld));
        if (searchfld.getAttribute("value").length() > 0) {
            WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@data-automation-id='search-clear']"), appiumdriver);
            tap(appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-clear']")));
        }
        WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@data-autotests-id='search-input']"), appiumdriver);
        //tap(searchfld);
        searchfld.sendKeys(searchtxt);
        BaseUtils.waitABit(1000);
    }
}
