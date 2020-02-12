package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCloseRODialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='closeRoModal']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//p[text()='Reason']/../span[@aria-owns]")
    private WebElement reasonDropDown;

    @FindBy(xpath = "//ul[@aria-hidden='false']//*[text()='Completed']")
    private WebElement completedDropDownOption;

    @FindBy(xpath = "//button[text()='Close RO']")
    private WebElement closeROButton;

    public VNextBOCloseRODialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}