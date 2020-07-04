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

import java.util.List;

@Getter
public class VNextSelectedServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement servicesScreen;

    @FindBy(xpath = "//*[@data-autotests-id='added-services-list']/div")
    private List<ServiceListItem> servicesList;

    public VNextSelectedServicesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public ServiceListItem getServiceListItem(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("service not found " + serviceName));
    }

    public long getQuantityOfSelectedService(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).count();
    }

    public long getNumberOfSelectedServices(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).count();
    }

    public String getTotalPriceValue() {
        WaitUtils.elementShouldBeVisible(servicesScreen, true);
        WaitUtils.waitUntilElementIsClickable(servicesScreen.findElement(By.xpath(".//span[@id='total']")));
        return servicesScreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
    }
}
