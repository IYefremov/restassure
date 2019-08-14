package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.PricesUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextAvailableServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[contains(@class,'services-list')]")
    private WebElement servicesscreen;

    @FindBy(xpath = "//*[@action='add']")
    private WebElement addservicesbtn;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement savebtn;

    @FindBy(xpath = "//div[contains(@class, 'services-list-block')]")
    private WebElement addedserviceslist;

    @FindBy(xpath = "//*[@data-autotests-id='selected-services']")
    private WebElement selectedserviceslist;

    @FindBy(xpath = "//*[@data-autotests-id='all-services']")
    private WebElement allserviceslist;


    public VNextAvailableServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextAvailableServicesScreen() {
    }

    public VNextServiceDetailsScreen openServiceDetailsScreen(String servicename) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
        return new VNextServiceDetailsScreen(appiumdriver);
    }

    public void openServiceDetails(String servicename) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
    }

    public void openLaborServiceDetails(String servicename) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
    }

    public void clickSaveButton() {
        tap(savebtn);
    }

    public List<WebElement> getAllServicesListItems() {
        return allserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
    }

    public void selectService(String serviceName) {
        WebElement servicerow = getServiceListItem(serviceName);
        String servicePrice = "";
        if (servicerow != null) {
            try {
                servicePrice = servicerow.findElement(By.xpath(".//div[@class='checkbox-item-subtitle checkbox-item-price']")).getText().trim();
                tap(WaitUtils.waitUntilElementIsClickable(servicerow.findElement(By.xpath(".//*[@action='select-item']"))));
                WaitUtils.waitUntilElementInvisible(By.xpath("//div[@class='notifier-contaier']"));
            } catch (WebDriverException e) {
                WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
                WaitUtils.click(servicerow.findElement(By.xpath(".//*[@action='select-item']")));
            }

            WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
            if (PricesUtils.isServicePriceEqualsZero(servicePrice)) {
                VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(appiumdriver);
                serviceDetailsScreen.clickServiceDetailsDoneButton();
            }
            new VNextAvailableServicesScreen(appiumdriver);

        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
    }

    public void selectPartService(String serviceName) {
        getServiceListItem(serviceName).click();
    }

    public int getServiceAmountSelectedValue(String serviceName) {
        int amaount = 0;
        WebElement servicerow = getServiceListItem(serviceName);
        amaount = Integer.valueOf(servicerow.findElement(By.xpath(".//*[@action='select-item']")).getAttribute("data-counter"));
        if (servicerow != null) {
            amaount = Integer.valueOf(servicerow.findElement(By.xpath(".//*[@action='select-item']")).getAttribute("data-counter"));
        } else
            Assert.assertTrue(false, "Can't find service: " + serviceName);
        return amaount;
    }

    public void selectLaborService(String laborServiceName) {
        WebElement servicerow = getServiceListItem(laborServiceName);
        if (servicerow != null) {
            tap(servicerow);
        } else
            Assert.assertTrue(false, "Can't find service: " + laborServiceName);
    }

    public void selectServices(String[] serviceslist) {
        for (String servicename : serviceslist)
            selectService(servicename);
    }

    public void selectServices(List<ServiceData> serviceslist) {
        for (ServiceData servicename : serviceslist)
            selectService(servicename.getServiceName());
    }

    public void selectAllServices() {
        int count = 50;
        int selected = 0;
        List<WebElement> servicerows = getAllServicesListItems();
        for (WebElement servicerow : servicerows) {
            tap(servicerow.findElement(By.xpath(".//*[@action='select-item']")));
            selected++;
            if (selected > count)
                break;
        }
    }

    public String getTotalPriceValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//span[@id='total']"))));
        return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }

    public List<WebElement> getServicesListItems() {
        return allserviceslist.findElements(By.xpath(".//div[@class='checkbox-item']"));
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public WebElement getServiceListItem(String servicename) {
        WebElement serviceListItem = null;
        List<WebElement> services = getServicesListItems();
        for (WebElement srv : services)
            if (getServiceListItemName(srv).equals(servicename)) {
                serviceListItem = srv;
                break;
            }
        return serviceListItem;
    }

    public void openMatrixServiceDetails(String matrixservicename) {
        WebElement servicerow = getServiceListItem(matrixservicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//*[@action='select-item']")));
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
    }
}
