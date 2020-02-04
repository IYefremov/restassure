package com.cyberiansoft.test.vnextbo.screens.commonobjects;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOToasterNotification extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@class='notification__content']")
    private WebElement message;

    public VNextBOToasterNotification() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}