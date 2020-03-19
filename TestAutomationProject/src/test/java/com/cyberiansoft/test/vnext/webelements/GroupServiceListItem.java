package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GroupServiceListItem implements IWebElement {
    private WebElement rootElement;
    private String groupServiceNameLocator = ".//div[@class='list-item-text']";

    public GroupServiceListItem(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void selectGroupService() {
        WaitUtils.click(rootElement);
    }

    public String getServiceName() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(groupServiceNameLocator)).getText().trim());
    }
}
