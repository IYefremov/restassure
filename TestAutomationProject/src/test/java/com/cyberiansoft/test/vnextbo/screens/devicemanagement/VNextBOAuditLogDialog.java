package com.cyberiansoft.test.vnextbo.screens.devicemanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAuditLogDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='device-audit-log-popup']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//div[@id='device-audit-log-popup']//button[@class='close']")
    private WebElement closeDialogXIcon;

    public VNextBOAuditLogDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}