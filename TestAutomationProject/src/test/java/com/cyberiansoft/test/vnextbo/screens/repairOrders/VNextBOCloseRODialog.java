package com.cyberiansoft.test.vnextbo.screens.repairOrders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCloseRODialog extends VNextBOBaseWebPage {

    @FindBy(id = "closeRoModal")
    private WebElement closeROModal;

    @FindBy(xpath = "//p[text()='Reason']/../span[@aria-owns]")
    private WebElement reasonBox;

    @FindBy(xpath = "//ul[@class='k-list k-reset' and @aria-hidden='false']")
    private WebElement listBoxDropDown;

    @FindBy(xpath = "//ul[@class='k-list k-reset' and @aria-hidden='false']/li")
    private List<WebElement> listBoxOptions;

    @FindBy(xpath = "//button[text()='Close RO']")
    private WebElement closeROButton;

    public VNextBOCloseRODialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}