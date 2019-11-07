package com.cyberiansoft.test.vnextbo.screens.devicemanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VNextBODeviceManagementWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//a[text()='Device Management']")
    private WebElement deviceManagementBreadCrumb;

    @FindBy(xpath = "//a[text()='Pending Registrations']")
    private WebElement pendingRegistrationsTab;

    @FindBy(xpath = "//li[@data-item-id='active-devices-tab']")
    private WebElement activeDevicesTab;

    @FindBy(xpath = "//li[@data-item-id='pre-registred-devices-tab']")
    private WebElement pendingRegistrationTab;

    @FindBy(xpath = "//div[@id='devicesListTable-wrapper']//table")
    private WebElement devicesTable;

    @FindBy(xpath = "//tbody[@data-template='devices-view-row-template']/tr")
    private List<WebElement> deviceRecords;

    @FindBy(xpath = "//li[@data-item-id='active-devices-tab']")
    private WebElement addNewDeviceButton;

    @FindBy(xpath = "//div[@id='devices-list-view']//div[@data-bind='visible: progressMessage']")
    private WebElement noDevicesFoundMessage;

    @FindBy(xpath = "//div[@id='devicesListTable-wrapper']/table[@class='grid']//tr[@role='option']/td[3]//i")
    private List<WebElement> platformColumnIconsList;

    public List<WebElement> columnTextCellsByTitle(String columnTitle) {

        int searchColumnIndex = driver.findElements(By.xpath("//div[@id='devicesListTable-wrapper']/table[@class='grid']//th")).
                stream().map(WebElement::getText).collect(Collectors.toList()).indexOf(columnTitle) + 1;
        return driver.findElements(By.xpath("//div[@id='devicesListTable-wrapper']/table[@class='grid']//tr[@role='option']/td[" + searchColumnIndex + "]"));
    }

    public VNextBODeviceManagementWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}