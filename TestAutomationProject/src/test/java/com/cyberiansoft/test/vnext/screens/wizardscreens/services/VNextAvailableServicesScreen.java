package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextAvailableServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement servicesscreen;

    @FindBy(xpath = "//*[@data-autotests-id='all-services']")
    private WebElement allserviceslist;

    @FindBy(xpath = "//*[@data-view-mode='available']/div")
    private List<ServiceListItem> servicesList;

    public VNextAvailableServicesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public void openServiceDetails(String serviceName) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).openServiceDetails();
            return true;
        });
    }

    public void selectServiceGroup(String groupName) {
        allserviceslist.findElement(By.xpath(".//*[@action='select-group']/*[ contains(text(), '"+ groupName + "')]")).click();
    }

    public void selectSingleService(String serviceName) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).clickAddService();
            return true;
        });
    }

    public ServiceListItem getServiceListItem(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("service not found " + serviceName));
    }

    public String getTotalPriceValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//span[@id='total']"))));
        return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }

}
