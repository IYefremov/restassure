package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOActiveDevicesWebPage extends VNextBODeviceManagementWebPage {

    public VNextBOActiveDevicesWebPage() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getDeviceByName(String deviceName) {
        try {
            return driver.findElement(By.xpath("//td[text()='" + deviceName + "']"));
        } catch (NullPointerException | NoSuchElementException ignored) {
            return null;
        }
    }

    public WebElement getDeviceByPartialNameMatch(String deviceName) {
        try {
            return driver.findElement(By.xpath("//td[contains(text(), '" + deviceName.split(" ")[0] + "')]"));
        } catch (NullPointerException | NoSuchElementException ignored) {
            return null;
        }
    }

    public WebElement getReplaceButtonByDeviceName(String deviceName) {
        return getWebElementIgnoringNPE(deviceName, ".//..//button[text()='Replace']");
    }

    public WebElement getRegistrationNumberByDeviceName(String deviceName) {
        return getWebElementIgnoringNPE(deviceName, ".//..//td[@class='grid__centered']/span");
    }

    public WebElement getRegistrationNumberClearButtonForDevice(String deviceName) {
        return getWebElementIgnoringNPE(deviceName, ".//..//i[contains(@data-bind, 'removeRegCode')]");
    }

    public WebElement getActionsArrowByDeviceName(String deviceName) {
        return getWebElementIgnoringNPE(deviceName,
                ".//..//td[@class='grid__actions']//i[contains(@class, 'actions-menu')]");
    }

    public WebElement getActionsEditButtonByDeviceName(String deviceName) {
        return getWebElementIgnoringNPE(deviceName, ".//..//div[contains(@data-bind, 'editDevice')]");
    }

    public WebElement getActionsDropDownForDevice(String deviceName) {
        return getWebElementIgnoringNPE(deviceName, ".//..//div[contains(@data-bind, 'deviceMenuVisible')]");
    }

    private WebElement getWebElementIgnoringNPE(String deviceName, String elementXpath) {
        try {
            return getDeviceByName(deviceName).findElement(By.xpath(elementXpath));
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}