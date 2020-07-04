package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
public class ServiceRequestListElement implements IWebElement {

    private WebElement rootElement;

    String expandButtonLocator = ".//*[@action='toggle_item']";

    private String status = ".//div[@class='service-request-status']";

    private String stockNumber = ".//span[contains(text(), 'Stock')]/..";

    private String vin = ".//span[contains(text(), 'VIN')]/..";

    private String serviceRequestNumber = ".//div[contains(text(), 'R-')]/..";

    private String inspectionsCount = ".//div[@data-name='Inspections']/div[@class='counter-button-count']";

    public ServiceRequestListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }
}
