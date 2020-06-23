package com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOSRDetailsPanel extends BaseWebPage {

    @FindBy(xpath = "//div[@class='editServiceRequestPanel']/iframe")
    private WebElement detailsPanelFrame;

    public BOSRDetailsPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
