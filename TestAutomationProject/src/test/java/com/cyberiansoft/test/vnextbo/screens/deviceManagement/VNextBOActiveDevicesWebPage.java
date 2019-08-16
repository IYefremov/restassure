package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOActiveDevicesWebPage extends VNextBODeviceManagementWebPage {

    public VNextBOActiveDevicesWebPage() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getReplaceButtonByDeviceName(String deviceName) {
        try {
            return driver.findElement(By.xpath("//td[text()='" + deviceName + "']/..//button[text()='Replace']"));
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    public WebElement getRegistrationNumberByDeviceName(String deviceName) {
        try {
            return driver.findElement(By.xpath("//td[text()='" + deviceName + "']/..//td[@class='grid__centered']/span"));
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    public WebElement getRegistrationNumberClearButtonForDevice(String deviceName) {
        try {
            return driver.findElement(By.xpath("//td[text()='" + deviceName +
                    "']/..//i[contains(@data-bind, 'removeRegCode')]"));
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}