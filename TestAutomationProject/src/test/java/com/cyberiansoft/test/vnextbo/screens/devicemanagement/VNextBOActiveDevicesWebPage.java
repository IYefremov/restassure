package com.cyberiansoft.test.vnextbo.screens.devicemanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class VNextBOActiveDevicesWebPage extends VNextBODeviceManagementWebPage {

    public VNextBOActiveDevicesWebPage() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    @FindBy(xpath = "//div[@id='devicesListTable-wrapper']//table")
    private WebElement devicesTable;

    @FindBy(xpath = "//div[@id='devicesListTable-wrapper']/table[@class='grid']//tr[@role='option']/td[3]//i")
    private List<WebElement> platformColumnIconsList;

    @FindBy(xpath = "//div[@id='devicesListTable-wrapper']/table[@class='grid']//th[text()!='']")
    private List<WebElement> columnsTitlesList;

    @FindBy(xpath = "//div[@id='devices-list-view']//div[@data-bind='visible: progressMessage']")
    public WebElement noDevicesFoundMessage;

    private WebElement deviceRowByName(String deviceName) {

        return driver.findElement(By.xpath("//td[text()='" + deviceName + "']/ancestor::tr"));
    }

    public WebElement actionsDeviceButtonByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//i[@class='icon-list menu-trigger']"));
    }

    public WebElement editDeviceButtonByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//div[@data-bind='click: editDevice']"));
    }

    public WebElement deleteDeviceButtonByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//div[@data-bind='click: deleteDevice']"));
    }

    public WebElement auditLogButtonByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//div[@data-bind='click: showLog']"));
    }

    public WebElement replaceButtonByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//button[@data-bind='click: replaceRegCode']"));
    }

    public WebElement registrationCodeByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//i[@data-bind='click: removeRegCode']/preceding-sibling::span"));
    }

    public WebElement hideRegistrationCodeXIconByDeviceName(String deviceName) {

        return deviceRowByName(deviceName).findElement(By.xpath("//i[@data-bind='click: removeRegCode']"));
    }

    public List<WebElement> columnTextCellsByTitle(String columnTitle) {

        int searchColumnIndex = columnsTitlesList.stream().map(WebElement::getText).collect(Collectors.toList()).indexOf(columnTitle) + 2;
        return driver.findElements(By.xpath("//div[@id='devicesListTable-wrapper']/table[@class='grid']//tr[@role='option']/td[" + searchColumnIndex + "]"));
    }
}