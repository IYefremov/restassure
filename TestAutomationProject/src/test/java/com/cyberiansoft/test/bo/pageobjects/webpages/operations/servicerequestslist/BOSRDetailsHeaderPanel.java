package com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOSRDetailsHeaderPanel extends BaseWebPage {

    @FindBy(xpath = "//div[contains(@class, 'servicerequestedit-container')]/div[@class='btn-group']")
    private WebElement buttonsBlock;

    @FindBy(xpath = "//span[@id='lbCheckIn' and text()='Undo Check-In']")
    private WebElement undoCheckInButton;

    @FindBy(xpath = "//span[@id='lbCheckIn' and text()='Check-In']")
    private WebElement checkInButton;

    public BOSRDetailsHeaderPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
