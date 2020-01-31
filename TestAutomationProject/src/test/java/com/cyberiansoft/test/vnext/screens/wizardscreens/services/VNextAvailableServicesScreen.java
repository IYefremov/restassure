package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.PricesUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextAvailableServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
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

    @Getter
    @FindBy(xpath = "//div[contains(@class,'service-item')]")
    private List<WebElement> servicesListItems;


    public VNextAvailableServicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextAvailableServicesScreen() {
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextServiceDetailsScreen openServiceDetailsScreen(String servicename) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
        return new VNextServiceDetailsScreen(appiumdriver);
    }

    public void openServiceDetails(String serviceName) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + serviceName + "']")));
    }

    public void openLaborServiceDetails(String servicename) {
        tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
    }

    public void clickSaveButton() {
        tap(savebtn);
    }

    public void selectService(String serviceName) {
        WebElement servicerow = getServiceListItem(serviceName);
        String servicePrice =  servicerow.findElement(By.xpath(".//div[@class='checkbox-item-subtitle checkbox-item-price']")).getText().trim();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).findElement(By.xpath(".//*[@action='add-service']")).click();
            return true;
        });
        if (PricesUtils.isServicePriceEqualsZero(servicePrice)) {
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
            serviceDetailsScreen.clickServiceDetailsDoneButton();
        } else {
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOf(
                    ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@class='notifier-contaier']"))
            ));
        }
    }

    public void selectServiceGroup(String groupName) {
        allserviceslist.findElement(By.xpath(".//*[@action='select-group']/*[ contains(text(), '"+ groupName + "')]")).click();
    }

    public void selectSingleService(String serviceName) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).findElement(By.xpath(".//*[@action='add-service']")).click();
            return true;
        });
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOf(
                ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@class='notifier-contaier']"))
        ));
    }

    public void clickAddServiceButton(String serviceName) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).findElement(By.xpath(".//*[@action='add-service']")).click();
            return true;
        });
    }

    public int getServiceAmountSelectedValue(String serviceName) {
        int amaount = 0;
        WebElement servicerow = getServiceListItem(serviceName);
        try {
            amaount = Integer.valueOf(servicerow.findElement(By.xpath(".//*[@action='add-service']//input")).getAttribute("data-counter"));
        } catch (Exception ex) {
            amaount = Integer.valueOf(servicerow.findElement(By.xpath(".//*[@action='add-service']")).getAttribute("data-counter"));
        }
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

    public String getTotalPriceValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//span[@id='total']"))));
        return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }

    public String getServiceListItemName(WebElement srvlistitem) {
        return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
    }

    public WebElement getServiceListItem(String servicename) {
        return WaitUtils.getGeneralFluentWait().until(driver -> getServicesListItems().stream()
                .filter(element ->
                        element.getText().split("\n")[0].equals(servicename))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found " + servicename)));
    }

    public void openMatrixServiceDetails(String matrixservicename) {
        WebElement servicerow = getServiceListItem(matrixservicename);
        if (servicerow != null)
            tap(servicerow.findElement(By.xpath(".//*[@action='add-service']")));
        else
            Assert.assertTrue(false, "Can't find service: " + matrixservicename);
    }
}
