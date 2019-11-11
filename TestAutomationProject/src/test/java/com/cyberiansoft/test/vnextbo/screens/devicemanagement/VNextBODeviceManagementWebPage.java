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

    @FindBy(xpath = "//li[@data-item-id='active-devices-tab']/a")
    private WebElement activeDevicesTab;

    @FindBy(xpath = "//li[@data-item-id='pre-registred-devices-tab']/a")
    private WebElement pendingRegistrationTab;

    @FindBy(xpath = "//tbody[@data-template='devices-view-row-template']/tr")
    private List<WebElement> deviceRecords;

    @FindBy(xpath = "//button[@class='btn btn-add-new-device pull-left']")
    private WebElement addNewDeviceButton;

    public VNextBODeviceManagementWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}