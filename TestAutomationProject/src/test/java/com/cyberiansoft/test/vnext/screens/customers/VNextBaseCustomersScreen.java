package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextBaseCustomersScreen extends VNextBaseScreen {

    @FindBy(xpath="//span[@class='client-mode']")
    private WebElement clientmode;

    @FindBy(xpath="//*[@data-autotests-id='customers-list']")
    private WebElement customerslist;

    @FindBy(xpath="//a[@action='select-customer']")
    private WebElement firstcustomer;

    @FindBy(xpath="//a[@action='add']")
    private WebElement addcustomerbtn;

    @FindBy(xpath="//*[@action='search']")
    private WebElement searchbtn;

    @FindBy(xpath="//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    @FindBy(xpath="//*[@data-autotests-id='search-cancel']")
    WebElement cancelsearchbtn;

    @FindBy(xpath="//*[@action='select-retail']")
    private WebElement retailcustomertab;

    @FindBy(xpath="//*[@action='select-wholesale']")
    private WebElement wholesalecustomertab;

    @FindBy(xpath="//div[@class='notice-plate']")
    private WebElement presetcustomerpanel;

    public VNextBaseCustomersScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
    }

    public void selectCustomer(AppCustomer customer) {
        if (elementExists("//*[@data-automation-id='search-icon']")) {
            searchCustomerByName(customer.getFullName());
        }
        if (customerslist.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")).size() > 0) {
            WebElement elem = customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']"));
            JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
            je.executeScript("arguments[0].scrollIntoView(true);",elem);
            //waitABit(1000);
            tap(customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
        } else {
            List<WebElement> ctmrs = customerslist.findElements(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name']"));
            WebElement elem = ctmrs.get(ctmrs.size()-1);
            JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
            je.executeScript("arguments[0].scrollIntoView(true);",elem);
            //waitABit(1000);
            tap(customerslist.findElement(By.xpath(".//*[@action='select']/p[@class='list-item-text list-item-name' and text()='" + customer.getFullName() + "']")));
            //waitABit(1000);
        }
    }

    public void selectCustomerByCompanyName(String customercompany) {
        WebElement elem = customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]"));
        JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
        je.executeScript("arguments[0].scrollIntoView(true);",elem);
        tap(customerslist.findElement(By.xpath(".//p[@class='list-item-text list-item-name' and contains(text(), '" + customercompany + "')]")));
        BaseUtils.waitABit(1000);
    }



    public boolean isAddCustomerButtonExists() {
        return elementExists("//a[@action='add']");
    }

    public boolean isCustomerExists(AppCustomer customer) {
        searchCustomerByName(customer.getFullName());
        return customerslist.findElements(By.xpath(".//p[text()='" + customer.getFullName() + "']")).size() > 0;
    }

    public void clickBackButton() {
        if (cancelsearchbtn.isDisplayed())
            tap(cancelsearchbtn);
        clickScreenBackButton();
    }

    public void switchToRetailMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(retailcustomertab));
        tap(retailcustomertab);
    }

    public void switchToWholesaleMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(wholesalecustomertab));
        tap(wholesalecustomertab);
    }

    public void searchCustomerByName(String customername) {
        typeSearchParameters(customername);

    }

    public void clickSearchButton() {
        tap(searchbtn);
    }

    public void clickCancelSearchButton() {
        tap(cancelsearchbtn);
    }

    public void typeSearchParameters(String searchtxt) {
        if (appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")).isDisplayed())
            appiumdriver.findElement(By.xpath("//*[@data-automation-id='search-icon']")).click();

        if (searchfld.getAttribute("value").length() > 0) {
            searchfld.clear();
        }
        WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@data-autotests-id='search-input']"), appiumdriver);
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
