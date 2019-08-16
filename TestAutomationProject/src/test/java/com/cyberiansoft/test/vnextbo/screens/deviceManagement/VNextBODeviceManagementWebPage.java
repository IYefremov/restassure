package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBODeviceManagementWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//button[contains(@class, 'btn-add-new-device')]")
    private WebElement addNewDeviceButton;

    @FindBy(xpath = "//a[text()='Active Devices']")
    private WebElement activeDevicesTab;

    @FindBy(xpath = "//a[text()='Pending Registrations']")
    private WebElement pendingRegistrationsTab;

    public VNextBODeviceManagementWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}