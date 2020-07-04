package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.ApproveServicesListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextApproveServicesScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='approve-services']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='select-all' and @value='1']")
    private WebElement approveAllBtn;

    @FindBy(xpath = "//*[@action='select-all' and @value='2']")
    private WebElement declineAllBtn;

    @FindBy(xpath = "//*[@action='select-all' and @value='3']")
    private WebElement skipAllBtn;

    @FindBy(xpath = "//*[@data-autotests-id='sapprove-services-list']")
    private WebElement serviceslist;

    @FindBy(xpath = "//*[@data-autotests-id='sapprove-services-list']/div")
    private List<ApproveServicesListElement> servicesList;

    public VNextApproveServicesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public boolean isServiceExistsForApprove(String serviceName) {
        return servicesList.stream().anyMatch(listElement -> listElement.getName().equals(serviceName));
    }

    public ApproveServicesListElement getServiceElement(String serviceName) {
        return servicesList.stream().filter(listElement -> listElement.getName().equals(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("service not found " + serviceName));
    }
}
