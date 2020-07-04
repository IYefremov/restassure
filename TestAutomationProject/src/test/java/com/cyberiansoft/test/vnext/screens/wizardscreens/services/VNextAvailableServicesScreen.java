package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.Button;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextAvailableServicesScreen extends VnextBaseServicesScreen {

    @FindBy(xpath = "//div[@data-page='services-list']")
    private WebElement servicesScreen;

    @FindBy(xpath = "//*[@data-autotests-id='available-services-list']")
    private Button allServicesList;

    @FindBy(xpath = "//*[@data-autotests-id='available-services-list']/div")
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

    public void selectSingleService(String serviceName) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            getServiceListItem(serviceName).clickAddService();
            return true;
        });
    }

    public ServiceListItem getServiceListItem(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("service not found " + serviceName));
    }

}
