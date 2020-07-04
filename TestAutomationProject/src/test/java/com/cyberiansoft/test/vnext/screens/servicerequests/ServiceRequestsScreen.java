package com.cyberiansoft.test.vnext.screens.servicerequests;

import com.cyberiansoft.test.vnext.screens.monitoring.MonitorScreen;
import com.cyberiansoft.test.vnext.webelements.ServiceRequestListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ServiceRequestsScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@data-page='service-requests-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='service requests-list']")
    private WebElement serviceRequestsList;

    @FindBy(xpath = "//div[@data-autotests-id='service requests-list']/div")
    private List<ServiceRequestListElement> serviceRequestsListElements;

    public ServiceRequestsScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
