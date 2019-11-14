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
public class VNextBOPendingRegistrationWebPage extends VNextBODeviceManagementWebPage {

    public VNextBOPendingRegistrationWebPage() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    @FindBy(xpath = "//div[@id='pre-registred-device-list-view']//div[@data-bind='visible: progressMessage']/p")
    private WebElement noDevicesFoundMessage;

    @FindBy(xpath = "//div[@id='preRegistredDevicesListTable-wrapper']/table[@class='grid']//th[text()!='']")
    private List<WebElement> columnsTitlesList;

    public WebElement deviceRowByName(String deviceNickName) {

        return driver.findElement(By.xpath("//td[text()='" + deviceNickName + "']/ancestor::tr"));
    }

    public WebElement deleteDeviceButtonByDeviceName(String deviceNickName) {

        return deviceRowByName(deviceNickName).findElement(By.xpath(".//i[@data-bind='click: deletePreRegistred']"));
    }

    public WebElement phoneNumberFieldByDeviceName(String deviceNickName) {

        return deviceRowByName(deviceNickName).findElement(By.xpath(".//input[contains(@data-bind, 'value: phone')]"));
    }

    public WebElement columnCellByDeviceNickName(String deviceNickName, String columnTitle) {

        int searchColumnIndex = columnsTitlesList.stream().map(WebElement::getText).collect(Collectors.toList()).indexOf(columnTitle) + 2;
        return deviceRowByName(deviceNickName).findElement(By.xpath(".//td[" + searchColumnIndex + "]"));
    }
}