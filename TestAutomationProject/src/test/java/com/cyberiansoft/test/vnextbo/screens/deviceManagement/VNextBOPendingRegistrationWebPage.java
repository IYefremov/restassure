package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPendingRegistrationWebPage extends VNextBODeviceManagementWebPage {

    public VNextBOPendingRegistrationWebPage() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getDeleteDeviceButton(String user) {
        try {
            return driver.findElement(By.xpath("//td[text()='" + user + "']/preceding-sibling::td/i"));
        } catch (Exception ignored) {
            return null;
        }
    }
}