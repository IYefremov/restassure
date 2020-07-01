package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.WebElement;

public class VehicleInfoListElement implements IWebElement {
    private WebElement rootElement;

    public VehicleInfoListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getFieldName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.getAttribute("name").trim());
    }

    public String getFieldValue() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.getAttribute("value").trim());
    }

    public void setValue(String value) {
        ControlUtils.setValue(rootElement, value);
    }

    public void click() {
        rootElement.click();
    }

    public WebElement getElement() {
        return rootElement;
    }
}
