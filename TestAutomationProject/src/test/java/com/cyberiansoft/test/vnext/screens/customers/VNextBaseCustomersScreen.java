package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextBaseCustomersScreen extends VNextBaseScreen {

    @FindBy(xpath = "//span[@class='client-mode']")
    private WebElement clientmode;

    @FindBy(xpath = "//*[@data-autotests-id='customers-list']")
    private WebElement customersList;

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

    public VNextBaseCustomersScreen() {
    }

    public void selectCustomer(AppCustomer customer) {
        if (WaitUtils.isElementPresent(By.xpath("//*[@action='select-retail']")))
            if (customer.isWholesale()) {
                if (wholesalecustomertab.isDisplayed())
                    switchToWholesaleMode();
            } else {
                if (retailcustomertab.isDisplayed())
                    switchToRetailMode();
            }
        if (WaitUtils.isElementPresent(By.xpath("//*[@data-automation-id='search-icon']"))) {
            searchCustomerByName(customer.getFullName());
        }
        if (customersList.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")).size() > 0) {
            WebElement elem = customersList.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']"));
            JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
            je.executeScript("arguments[0].scrollIntoView(true);", elem);
            //waitABit(1000);
            tap(customersList.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
        } else {
            List<WebElement> ctmrs = customersList.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name']"));
            WebElement elem = ctmrs.get(ctmrs.size() - 1);
            JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
            je.executeScript("arguments[0].scrollIntoView(true);", elem);
            //waitABit(1000);
            tap(customersList.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
            //waitABit(1000);
        }
    }

    public void selectCustomerByCompanyName(String customercompany) {
        WebElement elem = customersList.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]"));
        JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
        je.executeScript("arguments[0].scrollIntoView(true);", elem);
        tap(customersList.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]")));
        BaseUtils.waitABit(1000);
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

    public void clickCancelSearchButton() {
        tap(cancelsearchbtn);
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


    public VNextNewCustomerScreen openCustomerForEdit(AppCustomer customer) {
        selectCustomer(customer);
        VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(appiumdriver);
        return customersMenuScreen.clickEditCustomerMenuItem();
    }

    public VNextCustomersScreen setCustomerAsDefault(AppCustomer customer) {
        selectCustomer(customer);
        VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(appiumdriver);
        return customersMenuScreen.clickSetCustomerAsDefault();
    }

    public String getDefaultCustomerValue() {
        return clientmode.getText();
    }

    public String getPresetCustomerPanelValue() {
        return presetcustomerpanel.findElement(By.xpath(".//div[@class='notice-plate-info-name']")).getText();
    }

    public void resetPresetCustomer() {
        presetcustomerpanel.findElement(By.xpath(".//div[@class='notice-plate-remove']")).click();
    }
}
